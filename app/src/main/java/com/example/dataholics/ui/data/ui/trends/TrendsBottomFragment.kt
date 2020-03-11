package com.example.dataholics.ui.data.ui.histogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R

class TrendsBottomFragment : Fragment() {

    private lateinit var trendsBottomViewModel: TrendsBottomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trendsBottomViewModel = ViewModelProviders.of(this).get(trendsBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_trends, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_histogram)
        histogramBottomViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}