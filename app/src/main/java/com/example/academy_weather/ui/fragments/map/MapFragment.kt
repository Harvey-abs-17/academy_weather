package com.example.academy_weather.ui.fragments.map

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.academy_weather.R
import com.example.academy_weather.databinding.FragmentMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentMapBinding

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

        binding.apply {
            // initial map view for first time
            map.setTileSource(TileSourceFactory.MAPNIK)
            val mapController = map.controller
            mapController.setZoom(9.5)
            val startPoint = GeoPoint(48.8583, 2.2944)
            mapController.setCenter(startPoint)
            //set event listener for map view
            // by this code user can choose location for search
            var mReceive: MapEventsReceiver = object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(geoPoint: GeoPoint): Boolean {
                    Marker(map).apply {
                        this.position = geoPoint
                        this.icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.baseline_location_on_24
                        )
                        Toast.makeText(requireContext(), geoPoint.toString(), Toast.LENGTH_SHORT)
                            .show()
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