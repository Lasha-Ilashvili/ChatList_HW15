package com.example.chatlist_hw15.service

import com.example.chatlist_hw15.model.Chat
import retrofit2.Response
import retrofit2.http.GET

interface ChatService {
    @GET("744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getChats(): Response<List<Chat>>
}