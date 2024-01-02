package com.mirodeon.vetapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mirodeon.vetapp.model.Method

@Entity
data class MethodEntity(
    @PrimaryKey(autoGenerate = true)
    val methodId: Long = 0,
    @ColumnInfo(name = "name")
    var name: String
) {
    fun toMethod(): Method = Method(methodId, name)
}