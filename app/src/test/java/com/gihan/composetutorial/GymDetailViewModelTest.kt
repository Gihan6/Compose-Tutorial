package com.gihan.composetutorial

import androidx.lifecycle.SavedStateHandle
import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.prsentation.gymDetail.GymDetailScreenState
import com.gihan.composetutorial.gyms.prsentation.gymDetail.GymDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GymDetailViewModelTest {

    private val dispatcher= StandardTestDispatcher()
    private val scope= TestScope(dispatcher)
   private val savedState = SavedStateHandle(mapOf("gymId" to 1))



    private fun getGymDetailViewModel():GymDetailViewModel{
        val dataBase=FakeDataBase()
        val apiService=FakeApiService()
        val repo=GymsRepository(dataBase,apiService,dispatcher)
        return GymDetailViewModel(repo,savedState,dispatcher)
    }

    @Test
    fun loadingState_isSetCorrectly() =scope.runTest{
        val viewModel=getGymDetailViewModel()
        val state=viewModel.getGymDetailScreenState()
        val loadingState= GymDetailScreenState(
            null,
            true,
            null
        )
        assert(state==loadingState)
    }

    @Test
    fun setData_isCorrectly()=scope.runTest {
        val viewModel=getGymDetailViewModel()
        advanceUntilIdle()
        val state=viewModel.getGymDetailScreenState()
        val loadedState= GymDetailScreenState(
            viewModel.getGymByIDFromRemoteDB(savedState.get<Int>("gymId")?:0),
            false,
            null
        )
        assert(state==loadedState)
    }
}