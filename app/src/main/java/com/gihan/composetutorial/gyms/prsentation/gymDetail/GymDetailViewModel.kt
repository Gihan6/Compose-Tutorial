package com.gihan.composetutorial.gyms.prsentation.gymDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.data.di.MainDispatcher
import com.gihan.composetutorial.gyms.domain.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GymDetailViewModel @Inject constructor(
    private val gymRepo: GymsRepository,
    private val stateHandel: SavedStateHandle,
    @MainDispatcher private val dispatcher: CoroutineDispatcher

) : ViewModel() {
    private var gymDetailState by mutableStateOf(
        GymDetailScreenState(
            null,
            true,
            null
        )
    )
    fun getGymDetailScreenState(): GymDetailScreenState {
        return gymDetailState
    }

    private var errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        gymDetailState = gymDetailState.copy(isLoading = false, error = throwable.message)
    }

    init {
        val gymId = stateHandel.get<Int>("gymId") ?: 0

        viewModelScope.launch(errorHandler+dispatcher) {
            gymDetailState = gymDetailState.copy(gym = getGymByIDFromRemoteDB(gymId), false, null)

        }
    }

     suspend fun getGymByIDFromRemoteDB(id: Int): Gym = withContext(dispatcher) {
        val gym = gymRepo.getGymFromID(id)
        if (gym != null)
            return@withContext gym
        else
            throw Exception("No Data Find please connect to internet")
    }
}