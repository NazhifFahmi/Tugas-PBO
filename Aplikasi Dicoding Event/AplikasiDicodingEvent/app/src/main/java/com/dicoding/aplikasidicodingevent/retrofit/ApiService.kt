package com.dicoding.aplikasidicodingevent.retrofit

import com.dicoding.aplikasidicodingevent.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events?active=1")
    fun getEvents(): Call<EventResponse>

    @GET("events?active=0")
    fun getFinishedEvents(): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<EventResponse>

    @GET("events?active=-1")
    fun getSearchEvents(@Query("q") keyword: String): Call<EventResponse>
}