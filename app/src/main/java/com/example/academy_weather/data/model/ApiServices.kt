package com.example.academy_weather.data.model

import com.example.academy_weather.utils.API_KEY
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("current.json")
    fun getWeatherResponse(
        @Query("key") key :String = API_KEY,
        @Query("q") q :String
    ) :Observable<WeatherResponse>

}