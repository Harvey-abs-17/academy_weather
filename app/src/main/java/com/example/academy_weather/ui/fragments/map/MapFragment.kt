package com.example.academy_weather.ui.fragments.map

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.academy_weather.R
import com.example.academy_weather.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class MapFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentMapBinding

    //other
    //initial these variables with iran location
    private var longitude: Double = 53.68
    private var latitude: Double = 32.42

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            //initialize the osmdroid configuration
            Configuration.getInstance()
                .load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
            // initial map view for first time
            initialMapForFirstTime()
            //set event listener for map view
            // by this code user can choose location for search
            mapEventListener()
            //search btn clickListener
            binding.searchBtn.setOnClickListener {
                findNavController().navigate(MapFragmentDirections.actionMapFragmentToWeatherFragment(latitude.toFloat(), longitude.toFloat()))
            }


    }

    private fun initialMapForFirstTime() {
        binding.apply {
            map.setTileSource(TileSourceFactory.MAPNIK)
            Marker(map).apply {
                this.position = GeoPoint(latitude, longitude)
                this.icon = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_location_on_24
                )
                this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                map.overlays.add(this)
                map.postInvalidate()
            }
            val mapController = map.controller
            mapController.setZoom(6.0)
            val startPoint = GeoPoint(latitude, longitude)
            mapController.setCenter(startPoint)
        }
    }

    private fun mapEventListener() {
        binding.apply {
            val mReceive: MapEventsReceiver = object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(geoPoint: GeoPoint): Boolean {
                    Marker(map).apply {
                        this.position = geoPoint
                        latitude =
                            geoPoint.latitude.toString().subSequence(0, 5).toString().toDouble()
                        longitude =
                            geoPoint.longitude.toString().subSequence(0, 5).toString().toDouble()
                        this.icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.baseline_location_on_24
                        )
                        this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                        //remove last marker
                        map.overlays.forEach { (it as? Marker)?.remove(map) }
                        //add new marker on map
                        map.overlays.add(this)
                        map.postInvalidate()
                    }
                    return false
                }

                override fun longPressHelper(p: GeoPoint): Boolean {
                    return false
                }
            }
            //add event to map view
            val overlayEvents = MapEventsOverlay(mReceive)
            map.overlays.add(overlayEvents)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }


}