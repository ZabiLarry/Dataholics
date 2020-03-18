package com.example.dataholics.ui.data.ui.histogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.github.mikephil.charting.charts.PieChart
import kotlinx.android.synthetic.main.bottom_fragment_piechart.*

class PiechartBottomFragment : Fragment() {

    private lateinit var piechartBottomViewModel: PiechartBottomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        piechartBottomViewModel = ViewModelProviders.of(this).get(PiechartBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_piechart, container, false)

        val piechartActivity : PieChart = root.findViewById(R.id.piechartActivity)
        piechartActivity.setUsePercentValues(true)



        return root
    }
}