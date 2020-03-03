package com.example.dataholics.ui.create_account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateAccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is create account Fragment"
    }
    val text: LiveData<String> = _text
}