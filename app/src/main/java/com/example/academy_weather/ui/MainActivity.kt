package com.example.academy_weather.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.academy_weather.R
import com.example.academy_weather.databinding.ActivityMainBinding
import com.example.academy_weather.utils.showView
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    //others
    private lateinit var navHostFragment: NavHostFragment

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initial navHostFragment
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        //check internet connection by rx java
        ReactiveNetwork
            .observeNetworkConnectivity(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                showInternetError(it.available())
            }
    }

    //manage views by connection value
        private fun showInternetError( isShow :Boolean ){
        binding.apply {
            if (isShow){
                fragmentContainerView.showView(true)
                connectionLayout.showView(false)
            }else{
                fragmentContainerView.showView(false)
                connectionLayout.showView(true)
            }
        }
    }
}