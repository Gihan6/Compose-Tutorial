package com.gihan.composetutorial

import com.gihan.composetutorial.gyms.data.RemoteDatabase.GymsApiService
import com.gihan.composetutorial.gyms.data.RemoteDatabase.RemoteGym
import com.gihan.composetutorial.gyms.data.localDatabase.GymsDAO
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGym
import com.gihan.composetutorial.gyms.data.localDatabase.LocalGymFavouriteState
import com.gihan.composetutorial.gyms.domain.Gym

fun getDummyRemoteGymsData(): List<RemoteGym> {

    return listOf(
        RemoteGym(1, "gym1", "Description of gym 1", isOpen = true),
        RemoteGym(2, "gym2", "Description of gym 2",  isOpen = false)
    )

}
fun getDummyGymsData(): List<Gym> {

    return listOf(
        Gym(1, "gym1", "Description of gym 1", favourite = false, isOpen = true),
        Gym(2, "gym2", "Description of gym 2", favourite = false, isOpen = false)
    )

}
fun getDummyRemoteGym():RemoteGym{
    return  RemoteGym(1, "gym1", "Description of gym 1",  isOpen = true)
}


class FakeDataBase : GymsDAO {
    private var gyms = HashMap<Int, LocalGym>()
    override suspend fun saveGyms(gyms: List<LocalGym>) {
        gyms.forEach {
            this.gyms[it.id] = it
        }
    }

    override suspend fun getGyms(): List<LocalGym> {
        return gyms.values.toList()
    }

    override suspend fun updateGym(gymFavouriteState: LocalGymFavouriteState) {
        updateGymData(gymFavouriteState)
    }

    override suspend fun getFavouriteGyms(): List<LocalGym> {
        return gyms.values.filter { it.favourite }
    }

    override suspend fun updateAllGyms(gymsFavouriteState: List<LocalGymFavouriteState>) {
        gymsFavouriteState.forEach {
            updateGymData(it)
        }
    }

    private fun updateGymData(it: LocalGymFavouriteState) {
        val gym = this.gyms[it.id]
        gym?.let {
            this.gyms[gym.id] = gym.copy(favourite = !gym.favourite)
        }
    }

    override suspend fun getGym(gymId: Int): LocalGym {
        return gyms.values.first { it.id == gymId }
    }


}

class FakeApiService : GymsApiService {
    override suspend fun getGyms(): List<RemoteGym> {
        return getDummyRemoteGymsData()

    }

    override suspend fun getGymById(id: Int): Map<String, RemoteGym> {
        TODO("Not yet implemented")
    }

}