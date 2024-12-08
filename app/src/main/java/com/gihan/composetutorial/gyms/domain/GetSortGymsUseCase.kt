package com.gihan.composetutorial.gyms.domain

import com.gihan.composetutorial.gyms.data.GymsRepository
import javax.inject.Inject

class GetSortGymsUseCase @Inject constructor(private val repository: GymsRepository) {


    suspend operator fun invoke(): List<Gym> {

        repository.loadGyms()
        return repository.getGyms().sortedBy { it.name }
            .map { Gym(it.id, it.name, it.desc, it.favourite, it.isOpen) }
    }
}