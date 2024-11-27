package com.gihan.composetutorial.gyms.data.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGym::class],
    version = 2,
    exportSchema = false
)
abstract class GymsDataBase : RoomDatabase() {

    abstract val dao: GymsDAO


}