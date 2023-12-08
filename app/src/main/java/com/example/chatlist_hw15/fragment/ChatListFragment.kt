package com.example.chatlist_hw15.fragment

import android.text.Editable
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chatlist_hw15.adapter.ChatItemAdapter
import com.example.chatlist_hw15.base.BaseFragment
import com.example.chatlist_hw15.databinding.FragmentChatListBinding
import com.example.chatlist_hw15.json_reader.getJsonDataFromAsset
import com.example.chatlist_hw15.model.Chat
import com.example.chatlist_hw15.network.ChatApi
import com.example.chatlist_hw15.view_model.ChatViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class ChatListFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModels()

    private lateinit var adapter: ChatItemAdapter

    override fun setData() {
        lifecycleScope.launch {
            val json = ChatApi.retrofitService.getData()
            chatViewModel.setInitialList(chatViewModel.getChatData(json))
        }
    }

//    private fun getData(): List<Chat> {
//        val json = getJsonDataFromAsset(requireContext(), "chats.json")
//
//        return Gson().fromJson(json, object : TypeToken<List<Chat>>() {}.type)
//    }

    override fun setRecycler() {
        adapter = ChatItemAdapter().apply {
            submitList(chatViewModel.getList())
        }
        binding.rvChatList.adapter = adapter
    }

    override fun setListeners() {
        binding.etSearch.addTextChangedListener { owner: Editable? ->
            owner?.let { chatViewModel.search(it) }
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.chatFlow.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}