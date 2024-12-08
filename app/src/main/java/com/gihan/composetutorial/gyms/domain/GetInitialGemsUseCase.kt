package com.gihan.composetutorial.gyms.domain

import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGym
import javax.inject.Inject

class GetInitialGemsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortGymsUseCase: GetSortGymsUseCase
) {


    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortGymsUseCase()
    }
}