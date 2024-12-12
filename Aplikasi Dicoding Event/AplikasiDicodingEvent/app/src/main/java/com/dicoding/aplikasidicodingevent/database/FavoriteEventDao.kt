package com.dicoding.aplikasidicodingevent.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteEventDao {
    @Query("SELECT * FROM favorite_events WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: FavoriteEvent)

    @Delete
    suspend fun removeFavorite(event: FavoriteEvent)

    @Query("SELECT * FROM favorite_events")
    fun getAllFavorites(): LiveData<List<FavoriteEvent>>
}
