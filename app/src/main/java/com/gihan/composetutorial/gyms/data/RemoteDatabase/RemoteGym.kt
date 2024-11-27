package com.gihan.composetutorial.gyms.data.RemoteDatabase

import com.google.gson.annotations.SerializedName

data class RemoteGym(
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val desc: String,
    @SerializedName("is_open")
    var isOpen: Boolean
)
