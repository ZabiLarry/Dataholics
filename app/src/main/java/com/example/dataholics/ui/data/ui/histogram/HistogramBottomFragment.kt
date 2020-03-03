package com.example.dataholics.ui.data.ui.histogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.example.dataholics.ui.data.ui.histogram.HistogramBottomViewModel

class HistogramBottomFragment : Fragment() {

    private lateinit var histogramBottomViewModel: HistogramBottomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        histogramBottomViewModel = ViewModelProviders.of(this).get(HistogramBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_histogram, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_histogram)
        histogramBottomViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}