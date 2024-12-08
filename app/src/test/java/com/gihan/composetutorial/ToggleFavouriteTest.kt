package com.gihan.composetutorial

import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.domain.GetSortGymsUseCase
import com.gihan.composetutorial.gyms.domain.ToggleFavouriteStateUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ToggleFavouriteTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun setFavouriteGym_isCorrectly() = scope.runTest {
        //setup
        val repository = GymsRepository(FakeDataBase(), FakeApiService(), dispatcher)
        val sortGymsUseCase = GetSortGymsUseCase(repository)
        val toggleFavouriteUseCase = ToggleFavouriteStateUseCase(repository, sortGymsUseCase)

        repository.loadGyms()
        advanceUntilIdle()
        val gyms = getDummyGymsData()
        val gymUnderTest = gyms[0]
        val isFav = gymUnderTest.favourite

       val updateGymList= toggleFavouriteUseCase.invoke(gymUnderTest.id, isFav)
        advanceUntilIdle()

        assert(updateGymList[0].favourite==!isFav)

    }
}