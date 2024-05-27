package com.example.realtimeweather.Activites


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast


import com.example.realtimeweather.Models.Weather
import com.example.realtimeweather.R
import com.example.realtimeweather.Utilites.ApiInterface
import com.example.realtimeweather.databinding.ActivityCityscreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// c320af29ec8e2240282a9d291256383f (API key)

class Cityscreen : AppCompatActivity() {

    private val binding: ActivityCityscreenBinding by lazy {
        ActivityCityscreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchWeatherData("Bikaner")
        try {
            fetchWeatherData("Bikaner")
        } catch (e: Exception) {
            // Handle exception during initial weather fetch
            Toast.makeText(this, "Error fetching weather data: ${e.message}", Toast.LENGTH_SHORT).show()
        }

//        val searchView = intent.getStringExtra("QUERY")
//        binding.City.text= intent.getStringExtra("QUERY")
//
//            if (searchView != null) {
//                binding.City.text = searchView
//                fetchWeatherData(searchView)
//            } else {

//        try {
//            val searchView = intent.getStringExtra("QUERY")
//
//            if (searchView != null) {
//                binding.City.text = searchView
//                fetchWeatherData(searchView)
//            } else {
//                // Handle case where intent extra is missing (e.g., show default message)
//                Toast.makeText(this, "City name not found in intent", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            // Handle exceptions getting data from intent
//            Log.e("Cityscreen", "Error getting city name from intent: ${e.message}")
//            Toast.makeText(this, "Error retrieving city name", Toast.LENGTH_SHORT).show()
//        }
//    }
    }
    private fun fetchWeatherData(cityName: String) {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.WeatherData(cityName, "c320af29ec8e2240282a9d291256383f", "metric")  // Replace "YOUR_API_KEY" with your actual key

        response.enqueue(object : Callback<Weather> {

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temp = responseBody.main.temp.toString()
                    val h = responseBody.main.humidity.toString()
                    val w = responseBody.wind.speed.toString()
                    val s = responseBody.weather.firstOrNull()?.main ?: ""

                    binding.Sky.text = s
                    binding.W.text = "W : $w °"
                    binding.H.text = "H : $h °"
                    binding.temperature.text = "$temp °"
                } else {
                    // Handle unsuccessful response (e.g., check error code)
                    Toast.makeText(this@Cityscreen, "Error fetching weather data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Toast.makeText(this@Cityscreen, "Error fetching weather data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })








            }
        }




