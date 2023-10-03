package com.example.academy_weather.ui.fragments.weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.academy_weather.data.model.WeatherResponse
import com.example.academy_weather.databinding.FragmentWeatherBinding
import com.example.academy_weather.utils.showView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
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

    @SuppressLint("SetTextI18n")
    override fun loadData(weather: WeatherResponse) {
        binding.apply {
            detailActionBarTitle.text = weather.location?.region
            locationName.text = weather.location?.tzId
            timeTxt.text = weather.location?.localtime
            // api does not support images :(((
            Picasso.get()
                .load("${weather.current?.condition?.icon}")
                .into(weatherImage)
            currentTempTxt.text = weather.current?.tempC.toString()
            weatherSituationTxt.text = weather.current?.condition?.text
            windSpeedTxt.text = "${weather.current?.windKph.toString()} Kph"
            humidityTxt.text = "${weather.current?.humidity.toString()}%"
            windDirectionTxt.text = weather.current?.windDir
            pressureTxt.text = weather.current?.pressureIn.toString()
            visibilityTxt.text = "${weather.current?.visKm.toString()} Km"
            uvTxt.text = weather.current?.uv.toString()

        }
    }



    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }


}