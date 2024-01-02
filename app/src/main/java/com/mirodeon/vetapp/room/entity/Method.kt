package com.mirodeon.vetapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Method(
    @PrimaryKey(autoGenerate = true)
    val methodId: Long = 0,
    @ColumnInfo(name = "name")
    var name: String
)