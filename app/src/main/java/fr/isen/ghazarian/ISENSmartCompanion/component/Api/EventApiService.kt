package fr.isen.ghazarian.isensmartcompanion.component.Api

import fr.isen.ghazarian.isensmartcompanion.component.Event
import retrofit2.Call
import retrofit2.http.GET

// Retrofit API interface
interface EventApiService {
    @GET("events.json")
    fun getEvents(): Call<List<Event>>
}