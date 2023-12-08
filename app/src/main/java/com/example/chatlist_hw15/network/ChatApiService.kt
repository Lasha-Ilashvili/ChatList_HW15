package com.example.chatlist_hw15.network

import com.example.chatlist_hw15.model.Chat
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://run.mocky.io/v3/744fa574-a483-43f6-a1d7-c65c87b5d9b2"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ChatApiService {
    @GET("")
    suspend fun getData(): String
}

object ChatApi {
    val retrofitService: ChatApiService by lazy {
        retrofit.create(ChatApiService::class.java)
    }
}