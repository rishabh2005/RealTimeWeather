package com.example.realtimeweather.Activites


import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.cardview.widget.CardView

import com.example.realtimeweather.Models.Weather
import com.example.realtimeweather.R
import com.example.realtimeweather.Utilites.ApiInterface
import com.example.realtimeweather.databinding.ActivityHomeScreenBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Home_Screen : AppCompatActivity() {

    private val binding: ActivityHomeScreenBinding by lazy {
        ActivityHomeScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            fetchWeatherData("Bikaner")
        } catch (e: Exception) {
            // Handle exception during initial weather fetch
            Toast.makeText(this, "Error fetching weather data: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        SearchCity()


    }

    private fun SearchCity() {
        val searchView = binding.SearchBar
        val button = findViewById<CardView>(R.id.BikanerCard)



        button.setOnClickListener {
            val intent = Intent(this, Cityscreen::class.java)
            intent.putExtra("QUERY",searchView.id)
            startActivity(intent)
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    try {
                        fetchWeatherData(query)
                    } catch (e: Exception) {
                        // Handle exception during search weather fetch
                        Toast.makeText(this@Home_Screen, "Error fetching weather data for '$query': ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


    }

    private fun fetchWeatherData(cityName: String) {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofit.WeatherData(cityName, "c320af29ec8e2240282a9d291256383f", "metric")

        response.enqueue(object : Callback<Weather> {

            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temp = responseBody.main.temp.toString()
                    binding.temperature.text = "$temp °"
                    binding.City.text = "$cityName"

                    binding.temperature2.text=responseBody.main.temp.toString()
                    // ... update other UI elements with weather data
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Toast.makeText(this@Home_Screen, "Error fetching weather data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
