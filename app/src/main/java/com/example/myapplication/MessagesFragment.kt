package com.example.myapplication

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentMessagesBinding
import com.example.myapplication.fragments.BaseFragment
import kotlinx.coroutines.launch

class MessagesFragment : BaseFragment<FragmentMessagesBinding>(FragmentMessagesBinding::inflate) {

    private val viewModel: MessagesViewModel by viewModels {
        MessagesViewModelFactory()
    }

    private lateinit var adapter: MessagesAdapter


    override fun setupUI() {

        setupRecyclerView()
        observeViewModel()
        viewModel.getMessages()

    }

    override fun setupListeners() {
    }

    private fun setupRecyclerView() {
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        adapter = MessagesAdapter()
        binding.rvMessages.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.items.collect { messages ->
                    Log.d("MessagesFragment", "Received messages: $messages")

                    adapter.submitList(messages)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.error.collect { error ->
                    Log.e("MessagesFragment", "Error: $error")

                }
            }
        }
    }


}