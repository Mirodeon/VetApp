package com.mirodeon.vetapp.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["dosageId", "methodId"])
data class DosageMethodCrossRef(
    val dosageId: Long,
    val methodId: Long
)