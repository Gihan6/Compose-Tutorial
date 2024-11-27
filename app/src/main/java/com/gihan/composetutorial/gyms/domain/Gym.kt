package com.gihan.composetutorial.gyms.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Gym(
    val id: Int,
    val name: String,
    val desc: String,
    val favourite: Boolean = false,
    var isOpen: Boolean
)


