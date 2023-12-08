package com.example.chatlist_hw15.view_model

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatlist_hw15.model.Chat
import com.example.chatlist_hw15.network.ChatApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _chatFlow = MutableStateFlow(emptyList<Chat>())
    val chatFlow get() = _chatFlow.asStateFlow()

    fun setInitialList(list: List<Chat>) {
        _chatFlow.value = list
    }

    fun getChatData(json: String): List<Chat> {
        return Gson().fromJson(json, object : TypeToken<List<Chat>>() {}.type)
    }

    fun getList() = _chatFlow.value

    fun search(owner: Editable) {
        viewModelScope.launch {
            _chatFlow.value = _chatFlow.value.filter {
                it.owner.contains(owner, ignoreCase = true)
            }
        }
    }
}