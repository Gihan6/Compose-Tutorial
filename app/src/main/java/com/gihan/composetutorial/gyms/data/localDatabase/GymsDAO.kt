package com.gihan.composetutorial.gyms.data.localDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface GymsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGyms(gyms: List<LocalGym>)

    @Query("SELECT * FROM gyms")
    suspend fun getGyms(): List<LocalGym>

    @Update(entity = LocalGym::class)
    suspend fun updateGym(gymFavouriteState: LocalGymFavouriteState)

    @Query("SELECT * FROM gyms WHERE favourite = 1")
    suspend fun getFavouriteGyms():List<LocalGym>

    @Update(entity = LocalGym::class)
    suspend fun updateAllGyms(gymsFavouriteState: List<LocalGymFavouriteState>)

    @Query("SELECT * FROM gyms WHERE id= :gymId ")
    suspend fun getGym(gymId:Int): LocalGym
}