package com.gihan.composetutorial.gyms.data

import com.gihan.composetutorial.gyms.data.RemoteDatabase.GymsApiService
import com.gihan.composetutorial.gyms.data.RemoteDatabase.RemoteGym
import com.gihan.composetutorial.gyms.data.localDatabase.GymsDAO
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGym
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGymFavouriteState
import com.gihan.composetutorial.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private var database: GymsDAO,
    private var apiService: GymsApiService
) {

    private lateinit var gyms: List<RemoteGym>


    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (database.getGyms().isEmpty()) {
                throw Exception("There is no data found please try to connect to internet ")
            }
        }
        //make sort  as business logic

    }

    suspend fun getGyms(): List<LocalGym> = withContext(Dispatchers.IO) {
        database.getGyms()
    }

    private suspend fun updateLocalDatabase() {
        gyms = getGymsFromServer()
        val favouriteGymsList = database.getFavouriteGyms()
        database.saveGyms(gyms.map { LocalGym(it.id, it.name, it.desc, false, it.isOpen) })
        database.updateAllGyms(
            favouriteGymsList.map { LocalGymFavouriteState(id = it.id, true) }
        )
    }


    private suspend fun getGymsFromServer() = apiService.getGyms()

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO) {

            database.updateGym(LocalGymFavouriteState(gymId, state))
            return@withContext database.getGyms()

        }

    suspend fun getGymFromID(id: Int): Gym? {
        try {
            apiService.getGymById(id)[id.toString()]?.let {
                return Gym(it.id, it.name, it.desc, false, it.isOpen)
            }

        } catch (e: Exception) {
            val localGym = database.getGym(id)
            return Gym(
                localGym.id,
                localGym.name,
                localGym.desc,
                localGym.favourite,
                localGym.isOpen
            )

        }
        return null
    }

}