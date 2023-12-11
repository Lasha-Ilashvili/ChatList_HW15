package com.example.chatlist_hw15.fragment

import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chatlist_hw15.adapter.ChatItemAdapter
import com.example.chatlist_hw15.base.BaseFragment
import com.example.chatlist_hw15.databinding.FragmentChatListBinding
import com.example.chatlist_hw15.view_model.ChatViewModel
import kotlinx.coroutines.launch

class ChatListFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModels()

    private lateinit var adapter: ChatItemAdapter

    override fun setData() {
        chatViewModel.setInitialList()
    }

    override fun setRecycler() {
        adapter = ChatItemAdapter()
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
                chatViewModel.chatResult.collect { result ->
                    when (result) {
                        is ChatViewModel.ChatResult.Success -> {
                            binding.progressBar.visibility = View.VISIBLE
                            adapter.submitList(result.chats)
                        }

                        is ChatViewModel.ChatResult.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                        }
                    }
                }
            }
        }
    }
}