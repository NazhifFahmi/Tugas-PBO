package com.dicoding.aplikasidicodingevent.repository

import androidx.lifecycle.LiveData
import com.dicoding.aplikasidicodingevent.database.FavoriteEvent
import com.dicoding.aplikasidicodingevent.database.FavoriteEventDao

class FavoriteRepository(private val dao: FavoriteEventDao) {
    val allFavorites: LiveData<List<FavoriteEvent>> = dao.getAllFavorites()

    suspend fun addFavorite(event: FavoriteEvent) {
        dao.addFavorite(event)
    }

    suspend fun removeFavorite(event: FavoriteEvent) {
        dao.removeFavorite(event)
    }

    fun getFavoriteById(id: Int): LiveData<FavoriteEvent?> {
        return dao.getFavoriteEventById(id)
    }
}
