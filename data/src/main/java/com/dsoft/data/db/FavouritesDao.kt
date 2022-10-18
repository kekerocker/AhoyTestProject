package com.dsoft.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsoft.domain.model.Favourite

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(cityName: Favourite)

    @Query("SELECT * FROM favourites_table")
    fun readAllFavourites(): LiveData<List<Favourite>>

    @Query("DELETE FROM favourites_table")
    suspend fun deleteAllFavourites()

    @Query("DELETE FROM favourites_table WHERE cityName = :cityName")
    suspend fun deleteByCityName(cityName: String)
}