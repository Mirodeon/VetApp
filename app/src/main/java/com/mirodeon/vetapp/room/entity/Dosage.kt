package com.mirodeon.vetapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dosage(
    @PrimaryKey(autoGenerate = true)
    val dosageId: Long = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "concentration")
    val concentration: Int,
    @ColumnInfo(name = "active_ingredient")
    val activeIngredient: Int,
    @ColumnInfo(name = "interval")
    val interval: Int? = null,
    @ColumnInfo(name = "number")
    val number: Int? = null,
    @ColumnInfo(name = "withdrawal")
    val withdrawal: Int? = null,
    @ColumnInfo(name = "isFav")
    val isFav: Boolean = false
)