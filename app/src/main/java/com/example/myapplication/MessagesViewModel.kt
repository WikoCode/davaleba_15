package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MessagesViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<MessagesItem>>(emptyList())
    val items: StateFlow<List<MessagesItem>> get() = _items.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    private val retrofitApi = RetrofitInstance.api

    fun getMessages() {
        viewModelScope.launch {
            try {
                val response: Response<List<MessagesItem>> = retrofitApi.getMessages()

                if (response.isSuccessful) {
                    val result: List<MessagesItem>? = response.body()

                    if (result != null) {
                        _items.value = result
                        Log.d("MessagesFragment", "Received messages: $result")
                    } else {
                        _error.value = "Error: Response body is null"
                    }
                } else {
                    _error.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }


}
