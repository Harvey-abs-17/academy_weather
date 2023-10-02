package com.example.academy_weather.ui.base

import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenterImpl() :BasePresenter {

    var disposable :Disposable? = null

    override fun onStop() {
        disposable?.let {
            if(!disposable!!.isDisposed){
                disposable!!.dispose()
            }
        }
    }
}