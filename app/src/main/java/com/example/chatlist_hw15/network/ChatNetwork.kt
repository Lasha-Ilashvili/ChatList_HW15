package com.example.chatlist_hw15.network

import com.example.chatlist_hw15.service.ChatService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ChatNetwork {

    private const val BASE_URL = "https://run.mocky.io/v3/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun chatService(): ChatService = retrofit.create(ChatService::class.java)
}