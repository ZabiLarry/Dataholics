package com.example.dataholics.ui.data.ui.histogram

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.example.dataholics.database.TaskDBHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class HistogramBottomFragment : Fragment() {

    private lateinit var histogramBottomViewModel: HistogramBottomViewModel
    var root: View? = null
    private lateinit var barChartCompany: BarChart
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        histogramBottomViewModel =
            ViewModelProviders.of(this).get(HistogramBottomViewModel::class.java)
        root = inflater.inflate(R.layout.bottom_fragment_histogram, container, false)
        barChartCompany = root!!.findViewById(R.id.barChartCompany)

        val buttonRefresh: Button = root!!.findViewById(R.id.refreshButton)
        refresh()
        buttonRefresh.setOnClickListener {
            refresh()
            Toast.makeText(context, "Refreshed", Toast.LENGTH_LONG).show()
        }
        return root
    }

    private fun refresh() {
        val dbHelper = TaskDBHelper(context!!)
        val companyValue = arrayOf(0f, 0f, 0f, 0f, 0f)
        val companyList: ArrayList<Int> = ArrayList(dbHelper.getCompanies())
        for (i in 0 until companyList.size) {
            when (companyList[i]) {
                1 -> companyValue[0] = companyValue[0] + 1
                2 -> companyValue[1] = companyValue[1] + 1
                3 -> companyValue[2] = companyValue[2] + 1
                4 -> companyValue[3] = companyValue[3] + 1
                5 -> companyValue[4] = companyValue[4] + 1
            }
        }

        val barEntries: ArrayList<BarEntry> = ArrayList()

        for (i in companyValue.indices) {
            barEntries.add(BarEntry((i*2).toFloat(), companyValue[i]))
        }

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.rgb(240, 240, 240))
        colors.add(Color.rgb(211, 211, 211))
        colors.add(Color.rgb(170, 170, 170))
        colors.add(Color.rgb(125, 125, 125))
        colors.add(Color.rgb(70, 70, 70))

        val barDataSet = BarDataSet(barEntries, "Companies")
        barDataSet.colors = colors


        val data = BarData(barDataSet)
        barChartCompany.data = data

        val xAxis = barChartCompany.xAxis
        xAxis.valueFormatter = MyXAxisFormatter()
        xAxis.textSize= 12F
        xAxis.position=XAxis.XAxisPosition.TOP_INSIDE

        barChartCompany.notifyDataSetChanged()
        barChartCompany.invalidate()
    }

    class MyXAxisFormatter : ValueFormatter() {
        private val companyName = arrayOf("Alone", "","Partner", "","Friends","", "Family", "","Co-workers","")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return companyName.getOrNull(value.toInt()) ?: value.toString()
        }


    }

}