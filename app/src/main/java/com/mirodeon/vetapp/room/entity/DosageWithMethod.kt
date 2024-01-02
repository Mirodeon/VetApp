package com.mirodeon.vetapp.room.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DosageWithMethod(
    @Embedded
    val dosage: Dosage,
    @Relation(
        parentColumn = "dosageId",
        entityColumn = "methodId",
        associateBy = Junction(DosageMethodCrossRef::class)
    )
    val methods: List<Method>
)