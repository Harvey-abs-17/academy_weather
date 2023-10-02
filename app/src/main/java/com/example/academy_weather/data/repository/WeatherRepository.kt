package com.example.academy_weather.data.repository

import com.example.academy_weather.data.service.ApiServices
import javax.inject.Inject

class WeatherRepository @Inject constructor( private val services: ApiServices) {

    fun getWeatherResponseRepository( q :String ) = services.getWeatherResponse(q = q)

}