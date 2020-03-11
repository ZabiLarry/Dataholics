package com.example.dataholics.ui.data.ui.histogram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PiechartBottomViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bottom piechart Fragment"
    }
    val text: LiveData<String> = _text
}