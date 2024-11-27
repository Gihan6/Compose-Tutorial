package com.gihan.composetutorial.gyms.domain

import com.gihan.composetutorial.gyms.data.GymsRepository
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGym
import javax.inject.Inject

class GetSortGymsUseCase @Inject constructor(private val repository: GymsRepository) {


    suspend operator fun invoke():List<LocalGym>{

        repository.loadGyms()
        return repository.getGyms().sortedBy { it.name   }
    }
}