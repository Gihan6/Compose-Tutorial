package com.gihan.composetutorial.gyms.prsentation.gymDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gihan.composetutorial.gyms.data.RemoteDatabase.GymsApiService
import com.gihan.composetutorial.gyms.data.localDatabase.GymsDAO
import com.gihan.composetutorial.gyms.domain.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GymDetailViewModel @Inject constructor(
    private val database: GymsDAO,
    private val apiService: GymsApiService,
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
        var gymId = stateHandel.get<Int>("gymId") ?: 0

        viewModelScope.launch(errorHandler) {
            gymDetailState = gymDetailState.copy(gym = getGymByIDFromRemoteDB(gymId), false, null)

        }
    }

    private suspend fun getGymByIDFromRemoteDB(id: Int): Gym = withContext(Dispatchers.IO) {
        try {
            apiService.getGymById(id)[id.toString()]?.let {
                return@withContext Gym(it.id, it.name, it.desc, false, it.isOpen)
            }

        } catch (e: Exception) {
            throw Exception("No Data Find please connect to internet")
        }
        val localGym = database.getGym(id)
        return@withContext Gym(
            localGym.id,
            localGym.name,
            localGym.desc,
            localGym.favourite,
            localGym.isOpen
        )


    }
}