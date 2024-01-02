package com.mirodeon.vetapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mirodeon.vetapp.room.entity.Dosage
import com.mirodeon.vetapp.room.entity.DosageMethodCrossRef
import com.mirodeon.vetapp.room.entity.DosageWithMethod
import com.mirodeon.vetapp.room.entity.Method
import kotlinx.coroutines.flow.Flow

@Dao
interface DosageDao {
    @Transaction
    @Query("SELECT * FROM dosage ORDER BY name ASC")
    fun getAllDosage(): Flow<List<DosageWithMethod>>

    @Transaction
    @Query("SELECT * FROM dosage WHERE name LIKE '%' || :search ORDER BY name ASC")
    fun getDosageBySearch(search: String): Flow<List<DosageWithMethod>>

    @Query("SELECT * FROM method ORDER BY name ASC")
    fun getAllMethod(): List<Method>

    @Query("SELECT COUNT(*) FROM method")
    fun getCountMethod(): Int

    @Transaction
    @Query("SELECT * FROM dosage WHERE dosageId = :id")
    fun getDosageById(id: Long): Flow<DosageWithMethod>

    @Transaction
    @Query("SELECT * FROM dosage WHERE isFav = :isFav ORDER BY name ASC")
    fun getByFav(isFav: Boolean): Flow<List<DosageWithMethod>>

    @Query("UPDATE dosage SET isFav=:isFav WHERE dosageId = :id")
    fun setFav(id: Long, isFav: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDosage(dosage: Dosage): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMethod(vararg method: Method)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRef(ref: DosageMethodCrossRef)

    @Query("DELETE FROM dosagemethodcrossref WHERE dosageId = :id")
    fun deleteRefByDosageId(id: Long)

    @Delete
    fun deleteDosage(vararg dosages: Dosage)

    @Update
    fun updateDosage(vararg dosages: Dosage)

    @Transaction
    fun insertAllDosage(vararg dosages: DosageWithMethod): List<Long> {
        val inserted = mutableListOf<Long>()
        for (dosage in dosages) {
            val dosageId = insertDosage(dosage.dosage)
            inserted.add(dosageId)
            for (method in dosage.methods) {
                insertRef(DosageMethodCrossRef(dosageId, method.methodId))
            }
        }
        return inserted
    }

    @Transaction
    fun deleteAllDosage(vararg dosages: DosageWithMethod) {
        for (dosage in dosages) {
            deleteRefByDosageId(dosage.dosage.dosageId)
            deleteDosage(dosage.dosage)
        }
    }

    @Transaction
    fun updateDosageWithMethod(vararg dosages: DosageWithMethod) {
        for (dosage in dosages) {
            updateDosage(dosage.dosage)
            deleteRefByDosageId(dosage.dosage.dosageId)
            for (method in dosage.methods) {
                insertRef(DosageMethodCrossRef(dosage.dosage.dosageId, method.methodId))
            }
        }
    }
}