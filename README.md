<h1 align="center">Weather App with Map ( MVP ) </h1>
<p align="center">
  User can select the location to understand the weather condition in this location. We get the location information by the osmdroid library. After that we can use the location information ( latitude, longitude ) to find the weather information ( current ) by the  weather api.
</p> &nbsp;&nbsp;
<h2 align="center"> Android App only for educational purpose and inspired by hamrah -e aval academy project with MVP architecture</h2> &nbsp;&nbsp;
<p align="center">
<img width="400" height="800" src="https://github.com/Harvey-abs-17/academy_weather/assets/138676273/78b064f5-6f2c-447e-a88e-aaa953627af7"/>
<img width="400" height="800" src="https://github.com/Harvey-abs-17/academy_weather/assets/138676273/01dad77c-8919-466e-b4b7-a64a60e94737"/>
</p> &nbsp;&nbsp;
<h2 align="left">Patterns and Third party libraries applied</h2> &nbsp;&nbsp;
<ol>
        <li>Hilt : for dependency injection.</li>
        <li>Retrofit : Construct the REST APIs and paging network data.</li>
        <li>Rx java : a library for composing asynchronous and event-based programs by using observable sequences.</li>
        <li>Picasso : Loading images from network/resource.</li>
        <li>Single Activity Pattern.</li>
        <li>Navigation Component : allows the user to move from one screen to another and back out to a different one.</li>
        <li>View binding : makes it easier to write code that interacts with views.</li>
        <li> <a href="https://github.com/osmdroid/osmdroid">  osmdroid </a> : An android library which help us to use map in our applications.</li>
        <li> <a href="https://www.weatherapi.com/">  weather api </a> : a free weather forecast api.</li>
</ol>
<h2 align="left">How to work with Osmdroid</h2>
<ol>
        <li>to set default marker location on map when application started.</li> &nbsp;&nbsp;
  
        ```

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
  
        ```
<li>to access location which user choosed we must to create MapEventListener.</li> &nbsp;&nbsp;
  
        ```

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
                    }
                    return false
                }

                override fun longPressHelper(p: GeoPoint): Boolean {
                    return false
                }
            }
  
        ```
  <li>By default, in MapEventListener when the user clicks on the map, several markers are created on the map. To prevent this, the following code is used, so whenever the user clicks on the map, the previous marker must be replaced with a new marker.</li> &nbsp;&nbsp;
  
        ```
         //remove last marker
         map.overlays.forEach { (it as? Marker)?.remove(map) }
         //add new marker on map
         map.overlays.add(this)
         map.postInvalidate()
        ```

   <li>Now it's time to add MapEventListener to our map view</li> &nbsp;&nbsp;
  
        ```
         //add event to map view
         val overlayEvents = MapEventsOverlay(mReceive)
         map.overlays.add(overlayEvents)
        ```
        
</ol>

<h2 align="left">How to work with Weather Api</h2>
<ol>

<li>to get current condition of weather we must to pass latitude and longitude as <code>q</code> query parameter to server</li>&nbsp;&nbsp;

     ```
              @GET("current.json")
              fun getWeatherResponse(
                @Query("key") key :String = API_KEY,
                @Query("q") q :String
          ) :Observable<WeatherResponse>
  
    ```

   <li>get api key from <a href"https://www.weatherapi.com/">here</a> and replace your key in this line. you can find this line in <code>Utils/Constants</code></li>&nbsp;&nbsp;

     ```
             const val API_KEY = "your api key"
  
    ```
  
</ol>


