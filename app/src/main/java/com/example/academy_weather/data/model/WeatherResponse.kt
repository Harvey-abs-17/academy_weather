package com.example.academy_weather.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WeatherResponse(
    @SerializedName("current")
    var current: Current?,
    @SerializedName("location")
    var location: Location?
) : Parcelable {
    @Parcelize
    data class Current(
        @SerializedName("cloud")
        var cloud: Int?, // 7
        @SerializedName("condition")
        var condition: Condition?,
        @SerializedName("feelslike_c")
        var feelslikeC: Double?, // 23.1
        @SerializedName("feelslike_f")
        var feelslikeF: Double?, // 73.6
        @SerializedName("gust_kph")
        var gustKph: Double?, // 15.7
        @SerializedName("gust_mph")
        var gustMph: Double?, // 9.7
        @SerializedName("humidity")
        var humidity: Int?, // 17
        @SerializedName("is_day")
        var isDay: Int?, // 0
        @SerializedName("last_updated")
        var lastUpdated: String?, // 2023-10-03 00:45
        @SerializedName("last_updated_epoch")
        var lastUpdatedEpoch: Int?, // 1696281300
        @SerializedName("precip_in")
        var precipIn: Double?, // 0.0
        @SerializedName("precip_mm")
        var precipMm: Double?, // 0.0
        @SerializedName("pressure_in")
        var pressureIn: Double?, // 29.88
        @SerializedName("pressure_mb")
        var pressureMb: Double?, // 1012.0
        @SerializedName("temp_c")
        var tempC: Double?, // 23.9
        @SerializedName("temp_f")
        var tempF: Double?, // 75.0
        @SerializedName("uv")
        var uv: Double?, // 1.0
        @SerializedName("vis_km")
        var visKm: Double?, // 10.0
        @SerializedName("vis_miles")
        var visMiles: Double?, // 6.0
        @SerializedName("wind_degree")
        var windDegree: Int?, // 302
        @SerializedName("wind_dir")
        var windDir: String?, // WNW
        @SerializedName("wind_kph")
        var windKph: Double?, // 7.9
        @SerializedName("wind_mph")
        var windMph: Double? // 4.9
    ) : Parcelable {
        @Parcelize
        data class Condition(
            @SerializedName("code")
            var code: Int?, // 1000
            @SerializedName("icon")
            var icon: String?, // //cdn.weatherapi.com/weather/64x64/night/113.png
            @SerializedName("text")
            var text: String? // Clear
        ) : Parcelable
    }

    @Parcelize
    data class Location(
        @SerializedName("country")
        var country: String?, // Iran
        @SerializedName("lat")
        var lat: Double?, // 32.42
        @SerializedName("localtime")
        var localtime: String?, // 2023-10-03 0:45
        @SerializedName("localtime_epoch")
        var localtimeEpoch: Int?, // 1696281316
        @SerializedName("lon")
        var lon: Double?, // 53.68
        @SerializedName("name")
        var name: String?, // Shamsabad-E `Aqda
        @SerializedName("region")
        var region: String?, // Yazd
        @SerializedName("tz_id")
        var tzId: String? // Asia/Tehran
    ) : Parcelable
}