package com.example.academy_weather.utils

import androidx.fragment.app.Fragment
import com.example.academy_weather.ui.fragments.weather.WeatherContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object ContractViewModule {

    @Provides
    @FragmentScoped
    fun provideWeatherView (fragment :Fragment) :WeatherContract.View = fragment as WeatherContract.View

}