package com.example.academy_weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity

fun Context.isNetworkAvailable() :Boolean{
    val connectivityManager = getSystemService( Context.CONNECTIVITY_SERVICE ) as ConnectivityManager
    val info = connectivityManager.activeNetworkInfo
    return info != null && info.isAvailable
}

fun View.showView( isShow :Boolean ){
    if (isShow){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.INVISIBLE
    }
}