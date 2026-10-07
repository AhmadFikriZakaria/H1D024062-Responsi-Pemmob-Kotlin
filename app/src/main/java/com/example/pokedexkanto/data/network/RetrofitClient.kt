package com.example.pokedexkanto.data.network

import com.example.pokedexkanto.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton: Retrofit hanya dibuat sekali, saat pertama kali dipakai (by lazy)
object RetrofitClient {
    val apiService: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }
}
