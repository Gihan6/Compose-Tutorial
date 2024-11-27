package com.gihan.composetutorial.gyms.prsentation.gymDetail

import com.gihan.composetutorial.gyms.domain.Gym

data class GymDetailScreenState (val gym: Gym?, val isLoading:Boolean, val error:String?)