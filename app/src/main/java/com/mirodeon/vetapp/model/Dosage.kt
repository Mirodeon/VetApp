package com.mirodeon.vetapp.model

import android.os.Parcelable
import com.mirodeon.vetapp.room.entity.DosageEntity
import com.mirodeon.vetapp.room.entity.DosageWithMethod
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dosage(
    val dosageId: Long = 0,
    val concentration: Int,
    val activeIngredient: Int,
    val interval: Int? = null,
    val number: Int? = null,
    val methods: List<Method>,
    val isFav: Boolean = false
) : Parcelable {
    fun toDosageWithMethod(): DosageWithMethod = DosageWithMethod(
        DosageEntity(
            dosageId,
            concentration,
            activeIngredient,
            interval,
            number,
            isFav
        ),
        methods.map { it.toMethodEntity() }
    )
}