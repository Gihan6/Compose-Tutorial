package com.gihan.composetutorial.gyms.data.RemoteDatabase

import com.gihan.composetutorial.gyms.data.RemoteDatabase.RemoteGym
import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiService {

    @GET("gyms.json")
   suspend fun getGyms(): List<RemoteGym>

   @GET("gyms.json?orderBy=\"id\"")
   suspend fun getGymById(@Query("equalTo") id:Int):Map<String, RemoteGym>
}
