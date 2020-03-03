package com.example.dataholics.ui.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.*

class DataFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data, container, false)

        val bottonNav : BottomNavigationView = root.findViewById(R.id.bottom_navigation)
        //bottonNav.setOnNavigationItemSelectedListener(navListener)
        /*val textView: TextView = root.findViewById(R.id.text_data)
        dataViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it*/
        //})
        return root
    }

    /*private val navListener : BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
        }*/

}