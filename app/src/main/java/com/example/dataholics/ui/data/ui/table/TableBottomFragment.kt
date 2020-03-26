package com.example.dataholics.ui.data.ui.histogram

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.example.dataholics.database.Task
import com.example.dataholics.database.TaskDBHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.lang.Integer.parseInt


class TableBottomFragment : Fragment() {

    private lateinit var tableBottomViewModel: TableBottomViewModel
    private lateinit var lineChartTask: BarChart
    private var displayYear = 2020
    private var taskList: ArrayList<Task>? = null
    var value = IntArray(12)
    private var monthValue : Array<FloatArray> = emptyArray()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tableBottomViewModel = ViewModelProviders.of(this).get(TableBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_table, container, false)
        lineChartTask = root!!.findViewById(R.id.lineChartTask)
        val textViewYear: TextView = root.findViewById(R.id.textViewYear)
        displayYear = parseInt(textViewYear.text.toString())
        val nextButton: Button = root!!.findViewById(R.id.nextButton)
        val previousButton: Button = root!!.findViewById(R.id.previousButton)
        refresh()
        nextButton.setOnClickListener {
            textViewYear.text = (parseInt(textViewYear.text.toString()) + 1).toString()
            displayYear = parseInt(textViewYear.text.toString())
            refresh()
        }
        previousButton.setOnClickListener {
            textViewYear.text = (parseInt(textViewYear.text.toString()) - 1).toString()
            displayYear = parseInt(textViewYear.text.toString())
            refresh()
        }
        return root
    }

    private fun refresh() {
        monthValue = arrayOf(
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),//jan
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),//feb
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
            floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        )
        val dbHelper = TaskDBHelper(context!!)

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.rgb(0, 168, 244))
        colors.add(Color.rgb(52, 129, 55))
        colors.add(Color.rgb(255, 87, 34))
        colors.add(Color.rgb(106, 16, 211))
        colors.add(Color.rgb(7, 186, 150))
        colors.add(Color.rgb(255, 193, 7))
        colors.add(Color.rgb(233, 30, 99))
        colors.add(Color.rgb(31, 58, 188))
        colors.add(Color.rgb(205, 220, 57))
        colors.add(Color.rgb(139, 195, 74))
        colors.add(Color.rgb(156, 39, 176))
        colors.add(Color.rgb(6, 208, 234))



        taskList = ArrayList(dbHelper.allTasks())
        for (i in 0 until taskList!!.size) { //get all tasks
            val task = taskList!![i]
            val yearID = displayYear * 1000000
            if (task.TaskId > yearID && task.TaskId < yearID + 200000) { //get year
                val month = (task.TaskId - (yearID)) / 10000 //get month
                monthValue[month - 1][task.activity - 1] =
                    monthValue[month - 1][task.activity - 1] + 1
            }
        }



        val lineDataSets = ArrayList<BarDataSet>()
        val activityName = arrayOf("Sleep","Eating","Leisure","School","Paid Job","Homework",
                                    "Errands","Exercise","Travel","Social","Health","Dating")
        for (i in 1..12) {//month
            val lineEntries: ArrayList<BarEntry> = ArrayList()
            //for (j in 1..12) {//activity
                lineEntries.add(BarEntry((i-1).toFloat(), monthValue[i-1]))
                val lineDataSet = BarDataSet(lineEntries, activityName[i-1])
                lineDataSet.colors=colors
                lineDataSet.barBorderWidth=3f
                lineDataSets.add(lineDataSet)
            //}
        }


        val data = BarData(lineDataSets as List<IBarDataSet>?)

        val xAxis = lineChartTask.xAxis
        xAxis.valueFormatter = MyXAxisFormatter()
        xAxis.textSize = 12F
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE

        lineChartTask.data = data
        lineChartTask.notifyDataSetChanged()
        lineChartTask.invalidate()

    }


    class MyXAxisFormatter : ValueFormatter() {

        private val activityName = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return activityName.getOrNull(value.toInt()) ?: value.toString()
        }


    }
}