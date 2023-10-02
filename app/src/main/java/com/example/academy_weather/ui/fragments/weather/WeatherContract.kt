package com.example.academy_weather.ui.fragments.weather

import com.example.academy_weather.data.model.WeatherResponse

interface WeatherContract {

    interface View{
        fun showLoading()
        fun hideLoading()
        fun loadData( weather :WeatherResponse )
        fun checkInternet() :Boolean
        fun showInternetError( isShow :Boolean )
    }

    interface Presenter{
        fun callWeatherResponsePresenter( q :String )
    }


}