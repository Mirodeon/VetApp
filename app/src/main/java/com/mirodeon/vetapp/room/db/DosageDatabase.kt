package com.mirodeon.vetapp.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mirodeon.vetapp.room.dao.DosageDao
import com.mirodeon.vetapp.room.entity.Dosage
import com.mirodeon.vetapp.room.entity.DosageMethodCrossRef
import com.mirodeon.vetapp.room.entity.Method
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Database(entities = [Dosage::class, Method::class, DosageMethodCrossRef::class], version = 1)
abstract class DosageDatabase : RoomDatabase() {

    abstract fun dosageDao(): DosageDao

    fun initializeMethods() {
        CoroutineScope(IO).launch {
            if (dosageDao().getCountMethod() <= 0) {
                dosageDao().insertMethod(
                    Method(name = "injection intramusculaire"),
                    Method(name = "injection intraveineuse"),
                    Method(name = "injection sous-cutanée"),
                    Method(name = "voie orale"),
                    Method(name = "voie intramammaire"),
                    Method(name = "voie topique"),
                    Method(name = "voie intra-utérine")
                )
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DosageDatabase? = null

        fun getDatabase(context: Context): DosageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DosageDatabase::class.java,
                    "MyApp.db"
                ).build()

                instance.initializeMethods()

                INSTANCE = instance

                instance
            }
        }
    }
}