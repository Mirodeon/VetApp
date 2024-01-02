package com.mirodeon.vetapp.model

import android.os.Parcelable
import com.mirodeon.vetapp.room.entity.MethodEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Method(
    val methodId: Long,
    val name: String
) : Parcelable {
    fun toMethodEntity(): MethodEntity = MethodEntity(methodId, name)
}