package com.example.realtimeweather.Utilites


import com.example.realtimeweather.Models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("weather")
    fun WeatherData(
        @Query("q") q:String,
        @Query("APPID") appid:String,
        @Query("units") units:String
    ):Call<Weather>


}