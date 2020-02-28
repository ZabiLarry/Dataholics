package com.example.dataholics.ui.export

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R

class ExportFragment : Fragment() {

    private lateinit var exportViewModel: ExportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exportViewModel =
            ViewModelProviders.of(this).get(ExportViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_export, container, false)
        val textView: TextView = root.findViewById(R.id.text_export)
        exportViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}