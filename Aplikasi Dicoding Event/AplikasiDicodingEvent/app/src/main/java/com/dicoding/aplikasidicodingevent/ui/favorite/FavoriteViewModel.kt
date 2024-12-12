package com.dicoding.aplikasidicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.aplikasidicodingevent.database.EventDatabase
import com.dicoding.aplikasidicodingevent.database.FavoriteEvent
import com.dicoding.aplikasidicodingevent.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository

    init {
        val favoriteEventDao = EventDatabase.getInstance(application).favoriteEventDao()
        repository = FavoriteRepository(favoriteEventDao)
    }

    val allFavorites: LiveData<List<FavoriteEvent>> = repository.allFavorites

    fun addFavorite(event: FavoriteEvent) = viewModelScope.launch {
        repository.addFavorite(event)
    }

    fun removeFavorite(event: FavoriteEvent) = viewModelScope.launch {
        repository.removeFavorite(event)
    }

    fun getFavoriteById(id: Int): LiveData<FavoriteEvent?> {
        return repository.getFavoriteById(id)
    }
}
