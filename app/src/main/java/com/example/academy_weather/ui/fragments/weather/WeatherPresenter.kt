package com.example.academy_weather.ui.fragments.weather

import android.util.Log
import com.example.academy_weather.data.repository.WeatherRepository
import com.example.academy_weather.ui.base.BasePresenterImpl
import com.example.academy_weather.utils.ERROR_TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherPresenter @Inject constructor( private val repository: WeatherRepository, private val view :WeatherContract.View ) :BasePresenterImpl(), WeatherContract.Presenter {


    override fun callWeatherResponsePresenter(q: String) {
        if (view.checkInternet()){
            view.showLoading()
            disposable = repository.getWeatherResponseRepository(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.hideLoading()
                    view.loadData(it)
                }, {
                    Log.e(ERROR_TAG, it.message.toString())
                })
        }else{
            view.showInternetError(false)
        }
    }

}