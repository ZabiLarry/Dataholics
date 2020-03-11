package com.example.dataholics.ui.data.ui.histogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R

class PiechartBottomFragment : Fragment() {

    private lateinit var piechartBottomViewModel: PiechartBottomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        piechartBottomViewModel = ViewModelProviders.of(this).get(PiechartBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_piechart, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_histogram)
        histogramBottomViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}