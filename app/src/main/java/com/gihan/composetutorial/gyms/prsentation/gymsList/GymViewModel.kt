package com.gihan.composetutorial.gyms.prsentation.gymsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gihan.composetutorial.gyms.domain.GetInitialGemsUseCase
import com.gihan.composetutorial.gyms.domain.Gym
import com.gihan.composetutorial.gyms.domain.ToggleFavouriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymViewModel @Inject constructor(
   private val getInitialGymsUseCase : GetInitialGemsUseCase,
    private val toggleFavouriteUseCase : ToggleFavouriteStateUseCase
) : ViewModel() {

    private var gymState by mutableStateOf(
        GymsScreenState(
            gyms = emptyList(),
            isLoading = true,
            error = null
        )
    )

    fun getGymsState(): GymsScreenState {
        return gymState
    }


    private val errorHandle = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        gymState = gymState.copy(isLoading = false, error = throwable.message)

    }


    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandle) {
            gymState = gymState.copy(
                gyms = getInitialGymsUseCase().map {
                    Gym(
                        it.id,
                        it.name,
                        it.desc,
                        it.favourite,
                        it.isOpen
                    )
                },
                isLoading = false,
                error = null
            )

        }

    }


    fun setFavouriteGym(itemId: Int, oldValue: Boolean) {

        viewModelScope.launch {
            val updatedGymsList = toggleFavouriteUseCase(
                itemId,
                oldValue
            )
            gymState = gymState.copy(gyms = updatedGymsList.map {
                Gym(
                    it.id,
                    it.name,
                    it.desc,
                    it.favourite,
                    it.isOpen
                )
            })
        }

    }


}