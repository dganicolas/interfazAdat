package com.example.interfazadat.apiservice
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://app-adat-9a4d.onrender.com/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)  // Tiempo de espera para la conexión
        .readTimeout(50, TimeUnit.SECONDS)     // Tiempo de espera para leer la respuesta
        .writeTimeout(50, TimeUnit.SECONDS)    // Tiempo de espera para escribir la solicitud
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}