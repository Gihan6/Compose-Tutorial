package com.gihan.composetutorial.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.gihan.composetutorial.gyms.data.RemoteDatabase.GymsApiService
import com.gihan.composetutorial.gyms.data.localDatabase.GymsDAO
import com.gihan.composetutorial.gyms.data.localDatabase.GymsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Provides
    fun provideRoomDB(db: GymsDataBase): GymsDAO {
        return db.dao
    }

    @Singleton
    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context): GymsDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            GymsDataBase::class.java,
            "gymsDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): GymsApiService {
        return retrofit.create(GymsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://signin-227110-default-rtdb.firebaseio.com/")
            .build()

    }

}