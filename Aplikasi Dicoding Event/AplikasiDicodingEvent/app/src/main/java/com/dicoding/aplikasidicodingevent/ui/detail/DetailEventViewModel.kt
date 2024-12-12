package com.dicoding.aplikasidicodingevent.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.aplikasidicodingevent.response.EventResponse
import com.dicoding.aplikasidicodingevent.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel(application: Application) : AndroidViewModel(application) {

    private val _event = MutableLiveData<ListEventsItem?>()
    val event: LiveData<ListEventsItem?> = _event

    fun getDetailEvent(id: Int) {
        ApiConfig.getApiService().getDetailEvent(id.toString()).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _event.value = response.body()?.event
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _event.value = null
            }
        })
    }
}