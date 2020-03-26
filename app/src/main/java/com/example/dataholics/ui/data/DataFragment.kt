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
import com.example.dataholics.ui.data.ui.histogram.HistogramBottomFragment
import com.example.dataholics.ui.data.ui.histogram.PiechartBottomFragment
import com.example.dataholics.ui.data.ui.histogram.TableBottomFragment
import com.example.dataholics.ui.data.ui.histogram.TrendsBottomFragment
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

        var selectedFragment: Fragment = PiechartBottomFragment()
        fragmentManager!!.beginTransaction().replace(R.id.bottom_fragment_container, selectedFragment).commit()


        val bottonNav : BottomNavigationView = root.findViewById(R.id.bottom_navigation)
        bottonNav.setOnNavigationItemSelectedListener{

            when (it.itemId) {
                R.id.nav_piechart -> selectedFragment = PiechartBottomFragment()
                R.id.nav_histogram -> selectedFragment = HistogramBottomFragment()
                R.id.nav_trends -> selectedFragment = TableBottomFragment()
                //R.id.nav_table -> selectedFragment = TableBottomFragment()
            }

            fragmentManager!!.beginTransaction().replace(R.id.bottom_fragment_container, selectedFragment).commit()


            return@setOnNavigationItemSelectedListener true
        }


        return root
    }



}