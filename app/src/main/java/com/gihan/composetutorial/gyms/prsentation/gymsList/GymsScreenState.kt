package com.gihan.composetutorial.gyms.prsentation.gymsList

import com.gihan.composetutorial.gyms.domain.Gym

data class GymsScreenState(
    val gyms:List<Gym>,
    val isLoading: Boolean,
    val error: String?

    )
