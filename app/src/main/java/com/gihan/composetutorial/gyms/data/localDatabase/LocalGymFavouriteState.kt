package com.gihan.composetutorial.gyms.data.localDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalGymFavouriteState(
    @PrimaryKey
    val id: Int,
    val favourite: Boolean = false

)
