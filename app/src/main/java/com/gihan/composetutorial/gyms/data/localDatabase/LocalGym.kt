package com.gihan.composetutorial.gyms.data.localDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("gyms")
data class LocalGym(
    @PrimaryKey
    val id: Int,
    val name: String,
    val desc: String,
    val favourite: Boolean = false,
    var isOpen: Boolean
)
