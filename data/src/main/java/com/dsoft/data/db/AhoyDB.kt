package com.dsoft.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dsoft.domain.model.Favourite

@Database(
    entities = [Favourite::class],
    version = 1,
    exportSchema = false
)
abstract class AhoyDB : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}