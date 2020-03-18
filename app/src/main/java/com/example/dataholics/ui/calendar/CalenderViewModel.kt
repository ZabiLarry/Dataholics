package com.example.dataholics.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalenderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
    }
    val text: LiveData<String> = _text
}