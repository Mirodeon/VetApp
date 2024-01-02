package com.mirodeon.vetapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirodeon.vetapp.application.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DosageViewModel : ViewModel() {

    //private val dosageDao = MyApp.instance.database.dosageDao()

    //fun fullDosage(): Flow<List<DosageWithMethod>> = dosageDao.getAll()

    //fun fullMethod(): Flow<List<Method>> = methodDao.getAll()

    //fun fullDosageByFav(isFav: Boolean): Flow<List<DosageWithMethod>> = dosageDao.getByFav(isFav)

    //fun setDosageFav(id: Long, isFav: Boolean) = dosageDao.isFav(id, isFav)

    //fun insertDosageWithMethod(dosage: DosageWithMethod) = dosageDao.insert(dosage)
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