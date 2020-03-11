package com.example.dataholics.ui.data.ui.histogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R

class TableBottomFragment : Fragment() {

    private lateinit var tableBottomViewModel: TableBottomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tableBottomViewModel = ViewModelProviders.of(this).get(tableBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_table, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_histogram)
        histogramBottomViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}