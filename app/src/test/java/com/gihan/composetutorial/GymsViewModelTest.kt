package com.gihan.composetutorial

import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.data.RemoteDatabase.GymsApiService
import com.gihan.composetutorial.gyms.data.RemoteDatabase.RemoteGym
import com.gihan.composetutorial.gyms.data.localDatabase.GymsDAO
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGym
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGymFavouriteState
import com.gihan.composetutorial.gyms.domain.GetInitialGemsUseCase
import com.gihan.composetutorial.gyms.domain.GetSortGymsUseCase
import com.gihan.composetutorial.gyms.domain.ToggleFavouriteStateUseCase
import com.gihan.composetutorial.gyms.prsentation.gymsList.GymViewModel
import com.gihan.composetutorial.gyms.prsentation.gymsList.GymsScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GymsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun loadingState_isSetCorrectly() = scope.runTest {
        val viewModel = getViewModel()
        val state = viewModel.getGymsState()
        assert(state == GymsScreenState(emptyList(), true, null))
    }

    @Test
    fun loadedDataState_isSetCorrectly() = scope.runTest {
        val viewModel = getViewModel()
        advanceUntilIdle() //to work tasks in coroutine scope

        val state = viewModel.getGymsState()

        assert(state == GymsScreenState(
            getDummyGymsData(),
            false,
            null)
        )
    }



    private fun getViewModel(): GymViewModel {

        val gymsRepository = GymsRepository(FakeDataBase(), FakeApiService(),dispatcher)
        val getSortGymsUseCase = GetSortGymsUseCase(gymsRepository)

        val getInitialGemsUseCase = GetInitialGemsUseCase(gymsRepository, getSortGymsUseCase)
        val toggleFavouriteStateUseCase =
            ToggleFavouriteStateUseCase(gymsRepository, getSortGymsUseCase)
        return GymViewModel(getInitialGemsUseCase, toggleFavouriteStateUseCase, dispatcher)


    }

}
