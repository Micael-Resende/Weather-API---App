package com.example.weather_api_app

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: EditText
    private lateinit var searchButton: Button
    private lateinit var cityText: TextView
    private lateinit var temperatureText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var umidityText: TextView
    private lateinit var windText: TextView
    private lateinit var tempMaxText: TextView
    private lateinit var tempMinText: TextView
    private lateinit var errorMessage: TextView
    private lateinit var loader: ProgressBar
    private lateinit var backgroundImage: ImageView
    private lateinit var flagImageView: ImageView
    private lateinit var mapIcon: ImageView
    private lateinit var resultContainer: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)
        searchButton = findViewById(R.id.searchButton)
        cityText = findViewById(R.id.city)
        temperatureText = findViewById(R.id.temperature)
        descriptionText = findViewById(R.id.description)
        umidityText = findViewById(R.id.umidity)
        windText = findViewById(R.id.wind)
        tempMaxText = findViewById(R.id.tempMax)
        tempMinText = findViewById(R.id.tempMin)
        errorMessage = findViewById(R.id.errorMessage)
        loader = findViewById(R.id.loader)
        backgroundImage = findViewById(R.id.backgroundImage)
        flagImageView = findViewById(R.id.flagImageView)
        mapIcon = findViewById(R.id.mapIcon)
        resultContainer = findViewById(R.id.resultContainer)

        // Inicialmente, oculta o ícone do mapa e a bandeira
        mapIcon.visibility = View.GONE
        flagImageView.visibility = View.GONE

        searchButton.setOnClickListener {
            val city = inputField.text.toString()
            if (city.isNotBlank()) {
                mapIcon.visibility = View.VISIBLE // Mostra o ícone do mapa
                fetchWeatherData(city)
            } else {
                mapIcon.visibility = View.GONE // Esconde o ícone do mapa se o campo estiver vazio
                flagImageView.visibility = View.GONE // Esconde a bandeira se o campo estiver vazio
                Toast.makeText(this, "Digite o nome de uma cidade", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchWeatherData(city: String) {
        val apiKeyWeather = "d8ffd4f83688c91cffc26c7a574645b0"
        val apiKeyUnsplash = "zlIvtbEv3FT-2aw_gPwHUHT6BEyzv4CLpj0rHgvLUNI"
        val apiCountryURL = "https://flagsapi.com/"
        val apiUrlWeather = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKeyWeather&units=metric"
        val apiUrlUnsplash = "https://api.unsplash.com/photos/random?client_id=$apiKeyUnsplash&query=$city"

        loader.visibility = View.VISIBLE
        errorMessage.visibility = View.GONE

        thread {
            try {
                val client = OkHttpClient()

                // Fetch weather data
                val weatherRequest = Request.Builder().url(apiUrlWeather).build()
                val weatherResponse = client.newCall(weatherRequest).execute()

                if (weatherResponse.isSuccessful) {
                    val weatherData = weatherResponse.body?.string()
                    weatherData?.let {
                        val jsonObject = JSONObject(it)
                        val main = jsonObject.getJSONObject("main")
                        val weatherArray = jsonObject.getJSONArray("weather")
                        val weather = weatherArray.getJSONObject(0)
                        val country = jsonObject.getJSONObject("sys").getString("country")

                        val temp = main.getDouble("temp")
                        val tempMax = main.getDouble("temp_max")
                        val tempMin = main.getDouble("temp_min")
                        val description = weather.getString("description")
                        val humidity = main.getInt("humidity")
                        val windSpeed = jsonObject.getJSONObject("wind").getDouble("speed")

                        runOnUiThread {
                            cityText.text = city
                            temperatureText.text = "${temp.toInt()}°C"
                            descriptionText.text = description.capitalize()
                            umidityText.text = "Umidade: $humidity%"
                            windText.text = "$windSpeed km/h"
                            tempMaxText.text = "Temp Máx: ${tempMax.toInt()}°C"
                            tempMinText.text = "Temp Mín: ${tempMin.toInt()}°C"

                            // Ajusta a visibilidade do container de resultados
                            resultContainer.setBackgroundResource(R.drawable.result_background)

                            // Carrega a bandeira do país
                            val flagUrl = "$apiCountryURL$country/flat/64.png"
                            Glide.with(this@MainActivity).load(flagUrl).into(flagImageView)
                            flagImageView.visibility = View.VISIBLE
                        }
                    }
                } else {
                    runOnUiThread {
                        showError("Cidade não encontrada")
                    }
                }

                // Fetch Unsplash image
                val imageRequest = Request.Builder().url(apiUrlUnsplash).build()
                val imageResponse = client.newCall(imageRequest).execute()
                if (imageResponse.isSuccessful) {
                    val imageData = imageResponse.body?.string()
                    imageData?.let {
                        val jsonObject = JSONObject(it)
                        val imageUrl = jsonObject.getJSONObject("urls").getString("regular")
                        runOnUiThread {
                            Glide.with(this@MainActivity)
                                .load(imageUrl)
                                .centerCrop()
                                .into(backgroundImage)
                        }
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    showError("Erro ao buscar dados")
                }
            } finally {
                runOnUiThread {
                    loader.visibility = View.GONE
                }
            }
        }
    }

    private fun showError(message: String) {
        errorMessage.text = message
        errorMessage.visibility = View.VISIBLE
        loader.visibility = View.GONE
        mapIcon.visibility = View.GONE // Oculta o ícone do mapa no caso de erro
        flagImageView.visibility = View.GONE // Oculta a bandeira no caso de erro
    }
}
