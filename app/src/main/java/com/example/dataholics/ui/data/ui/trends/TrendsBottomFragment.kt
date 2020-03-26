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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.lang.Integer.parseInt

class TrendsBottomFragment : Fragment() {

    private lateinit var trendsBottomViewModel: TrendsBottomViewModel
    private lateinit var lineChartTask: LineChart
    private var displayYear = 2020
    private var taskList: ArrayList<Task>? = null
    var value = IntArray(12)
    private var monthValue : Array<Array<Int>> = emptyArray()    //sleep eat leisure                    dating


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trendsBottomViewModel = ViewModelProviders.of(this).get(TrendsBottomViewModel::class.java)
        val root = inflater.inflate(R.layout.bottom_fragment_trends, container, false)
        lineChartTask = root!!.findViewById(R.id.lineChartTask)
        val textViewYear: TextView = root.findViewById(R.id.textViewYear)
        displayYear = parseInt(textViewYear.text.toString())
        val nextButton: Button = root!!.findViewById(R.id.nextButton)
        val previousButton: Button = root!!.findViewById(R.id.previousButton)
        refresh()
        nextButton.setOnClickListener {
            textViewYear.text = (parseInt(textViewYear.text.toString()) + 1).toString()
            refresh()
        }
        previousButton.setOnClickListener {
            textViewYear.text = (parseInt(textViewYear.text.toString()) - 1).toString()
            refresh()
        }
        return root
    }

    private fun refresh() {
        monthValue = arrayOf(
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),//jan
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),//feb
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
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



        val lineDataSets = ArrayList<LineDataSet>()
        val activityName = arrayOf("Sleep","Eating","Leisure","School","Paid Job","Homework","Errands","Exercise","Travel","Social","Health","Dating")
        for (i in 1..12) {//month
            val lineEntries: ArrayList<Entry> = ArrayList()
            for (j in 1..12) {//activity
                lineEntries.add(Entry((i-1).toFloat(), monthValue[i-1][j-1].toFloat()))
                val lineDataSet = LineDataSet(lineEntries, activityName[j-1])
                lineDataSet.color=colors[j-1]
                lineDataSet.lineWidth=8f
                lineDataSets.add(lineDataSet)
            }
        }


        val data = LineData(lineDataSets as List<ILineDataSet>?)

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