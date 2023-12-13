package com.example.chatlist_hw15.view_model

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlist_hw15.model.Chat
import com.example.chatlist_hw15.network.ChatNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class ChatViewModel : ViewModel() {

    private val _chatResult = MutableStateFlow<ChatResult?>(null)
    val chatResult get() = _chatResult.asStateFlow()

    fun setInitialList() {
        viewModelScope.launch {
            try {
                val response = ChatNetwork.chatService().getChats()

                if (response.isSuccessful) {
                    _chatResult.value = ChatResult.Success(response.body()!!)
                } else {
                    _chatResult.value = ChatResult.Error("Failed to fetch chats ${response.errorBody()}")
                }

            } catch (e: IOException) {
                _chatResult.value = ChatResult.Error("Network error: ${e.message}")
            }
        }
    }

    fun search(owner: Editable) {
        viewModelScope.launch {
            val response = ChatNetwork.chatService().getChats()

            if (response.isSuccessful) {
                val filteredChats = response.body()?.filter {
                    it.owner.contains(owner, ignoreCase = true)
                } ?: emptyList()
                _chatResult.value = ChatResult.Success(filteredChats)
            } else {
                _chatResult.value = ChatResult.Error("Failed to fetch chats")
            }
        }
    }

    sealed class ChatResult {
        data class Success(val chats: List<Chat>) : ChatResult()
        data class Error(val errorMessage: String) : ChatResult()
    }
}