package com.example.academy_weather.ui.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.academy_weather.R
import com.example.academy_weather.data.model.WeatherResponse
import com.example.academy_weather.databinding.FragmentWeatherBinding
import com.example.academy_weather.utils.isNetworkAvailable
import com.example.academy_weather.utils.showView
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment(), WeatherContract.View {

    //binding
    private lateinit var binding: FragmentWeatherBinding
    //others
    private val navArgs :WeatherFragmentArgs by navArgs()
    @Inject
    lateinit var presenter: WeatherPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get data from internet
        presenter.callWeatherResponsePresenter("${navArgs.latitude},${navArgs.longitude}")
        //check internet connection
        ReactiveNetwork
            .observeNetworkConnectivity(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                showInternetError(it.available())
            }

        //back button
        binding.apply {
            detailBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun showLoading() {
        binding.apply {
            detailProgressBar.showView(true)
            mainInfoLayout.showView(false)
        }
    }

    override fun hideLoading() {
        binding.apply {
            detailProgressBar.showView(false)
            mainInfoLayout.showView(true)
        }
    }

    override fun loadData(weather: WeatherResponse) {
        binding.apply {
            detailActionBarTitle.text = weather.location?.region
            locationName.text = weather.location?.tzId
            timeTxt.text = weather.location?.localtime
            Picasso.get()
                .load(weather.current?.condition?.icon)
                .into(weatherImage)
            currentTempTxt.text = weather.current?.tempC.toString()
            weatherSituationTxt.text = weather.current?.condition?.text
            windSpeedTxt.text = weather.current?.windKph.toString()
            humidityTxt.text = weather.current?.humidity.toString()
            windDirectionTxt.text = weather.current?.windDir
            pressureTxt.text = weather.current?.pressureIn.toString()
            visibilityTxt.text = weather.current?.visKm.toString()
            uvTxt.text = weather.current?.uv.toString()

        }
    }

    override fun checkInternet(): Boolean {
        return requireContext().isNetworkAvailable()
    }

    override fun showInternetError(isShow: Boolean) {
        binding.apply {
            if (isShow){
                mainInfoLayout.showView(true)
                connectionLayout.showView(false)
            }else{
                mainInfoLayout.showView(false)
                connectionLayout.showView(true)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }


}