package com.gihan.composetutorial.gyms.prsentation.gymDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.domain.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GymDetailViewModel @Inject constructor(
    private val gymRepo: GymsRepository,
    private val stateHandel: SavedStateHandle
) : ViewModel() {

    private var errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        gymDetailState = gymDetailState.copy(isLoading = false, error = throwable.message)
    }
    var gymDetailState by mutableStateOf<GymDetailScreenState>(
        GymDetailScreenState(
            null,
            true,
            null
        )
    )

    init {
        val gymId = stateHandel.get<Int>("gymId") ?: 0

        viewModelScope.launch(errorHandler) {
            gymDetailState = gymDetailState.copy(gym = getGymByIDFromRemoteDB(gymId), false, null)

        }
    }

    private suspend fun getGymByIDFromRemoteDB(id: Int): Gym = withContext(Dispatchers.IO) {
        val gym = gymRepo.getGymFromID(id)
        if (gym != null)
            return@withContext gym
        else
            throw Exception("No Data Find please connect to internet")
    }
}