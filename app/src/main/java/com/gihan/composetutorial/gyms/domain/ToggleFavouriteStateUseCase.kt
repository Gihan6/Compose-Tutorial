package com.gihan.composetutorial.gyms.domain

import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGym
import javax.inject.Inject

class ToggleFavouriteStateUseCase @Inject constructor(
    private val repository: GymsRepository,
    private val sortGymsUseCase: GetSortGymsUseCase
) {


    suspend operator fun invoke(itemId: Int, oldState: Boolean): List<LocalGym> {
        val newState = oldState.not()
        repository.toggleFavouriteGym(itemId, newState)
        return sortGymsUseCase()


    }
}