package com.mirodeon.vetapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirodeon.vetapp.application.MyApp
import com.mirodeon.vetapp.room.entity.DosageWithMethod
import com.mirodeon.vetapp.room.entity.Method
import kotlinx.coroutines.flow.Flow

class DosageViewModel : ViewModel() {

    private val dosageDao = MyApp.instance.database.dosageDao()

    fun allDosage(): Flow<List<DosageWithMethod>> = dosageDao.getAllDosage()

    fun searchDosage(search: String): Flow<List<DosageWithMethod>> = dosageDao.getDosageBySearch(search)

    fun allMethod(): List<Method> = dosageDao.getAllMethod()

    fun allDosageByFav(isFav: Boolean): Flow<List<DosageWithMethod>> = dosageDao.getByFav(isFav)

    fun dosageById(id: Long): Flow<DosageWithMethod> = dosageDao.getDosageById(id)

    fun setDosageFav(id: Long, isFav: Boolean) = dosageDao.setFav(id, isFav)

    fun insertDosage(dosage: DosageWithMethod) = dosageDao.insertAllDosage(dosage).first()

    fun deleteDosage(dosage: DosageWithMethod) = dosageDao.deleteAllDosage(dosage)

    fun updateDosage(dosage: DosageWithMethod) = dosageDao.updateDosageWithMethod(dosage)

}

class DosageViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DosageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DosageViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}