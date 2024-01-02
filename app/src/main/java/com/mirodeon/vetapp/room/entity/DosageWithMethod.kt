package com.mirodeon.vetapp.room.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mirodeon.vetapp.model.Dosage

data class DosageWithMethod(
    @Embedded
    val dosage: DosageEntity,
    @Relation(
        parentColumn = "dosageId",
        entityColumn = "methodId",
        associateBy = Junction(DosageMethodCrossRef::class)
    )
    val methods: List<MethodEntity>
) {
    fun toDosage(): Dosage = Dosage(
        dosage.dosageId,
        dosage.concentration,
        dosage.activeIngredient,
        dosage.interval,
        dosage.number,
        methods.map { it.toMethod() },
        dosage.isFav
    )
}