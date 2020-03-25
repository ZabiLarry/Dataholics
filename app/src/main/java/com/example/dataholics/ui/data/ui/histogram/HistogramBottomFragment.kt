package com.example.dataholics.ui.data.ui.histogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R

class HistogramBottomFragment : Fragment() {

    private lateinit var histogramBottomViewModel: HistogramBottomViewModel
    var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        histogramBottomViewModel = ViewModelProviders.of(this).get(HistogramBottomViewModel::class.java)
        root = inflater.inflate(R.layout.bottom_fragment_histogram, container, false)

        val buttonRefresh : Button = root!!.findViewById(R.id.refreshButton)
        refresh()
        buttonRefresh.setOnClickListener {
            refresh()
            Toast.makeText(context, "Refreshed", Toast.LENGTH_LONG).show()
        }
        return root
    }

    private fun refresh() {

    }
}