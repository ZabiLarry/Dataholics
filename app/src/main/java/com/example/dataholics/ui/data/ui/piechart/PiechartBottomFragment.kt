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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.bottom_fragment_piechart.*

class PiechartBottomFragment : Fragment() {

    private lateinit var piechartBottomViewModel: PiechartBottomViewModel
    var root : View? = null
    private lateinit var piechartActivity : PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        piechartBottomViewModel = ViewModelProviders.of(this).get(PiechartBottomViewModel::class.java)
        root = inflater.inflate(R.layout.bottom_fragment_piechart, container, false)

        piechartActivity = root!!.findViewById(R.id.piechartActivity)
        piechartActivity.isRotationEnabled=true
        val buttonRefresh : Button = root!!.findViewById(R.id.refreshButton)
        refresh()
        buttonRefresh.setOnClickListener{
            refresh()
            Toast.makeText(context, "Refreshed", Toast.LENGTH_LONG).show()
        }
        return root
    }

    private fun refresh(){
        val dbHelper = TaskDBHelper(context!!)
        val activityValue = arrayOf(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f)
        val activityName = arrayOf("Sleep","Eating","Leisure","School","Paid Job","Homework","Errands","Exercise","Travel","Social","Health","Dating")
        val activityList : ArrayList<Int> = ArrayList(dbHelper.getActivities())
        for (i  in 0 until activityList.size){
            when (activityList[i]){
                1 -> activityValue[0] = activityValue[0] + 1
                2 -> activityValue[1] = activityValue[1] + 1
                3 -> activityValue[2] = activityValue[2] + 1
                4 -> activityValue[3] = activityValue[3] + 1
                5 -> activityValue[4] = activityValue[4] + 1
                6 -> activityValue[5] = activityValue[5] + 1
                7 -> activityValue[6] = activityValue[6] + 1
                8 -> activityValue[7] = activityValue[7] + 1
                9 -> activityValue[8] = activityValue[8] + 1
                10 -> activityValue[9] = activityValue[9] + 1
                11 -> activityValue[10] = activityValue[10] + 1
                12 -> activityValue[11] = activityValue[11] + 1
            }
        }

        val pieEntries : ArrayList<PieEntry> = ArrayList()
        val xEntries : ArrayList<String> = ArrayList()

        for (i in activityValue.indices){
            pieEntries.add(PieEntry(activityValue[i], i))
        }
        for (element in activityName){
            xEntries.add(element)
        }
        val colors : ArrayList<Int> = ArrayList()
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

        val pieDataSet = PieDataSet(pieEntries,"Activities")
        pieDataSet.sliceSpace= 2f
        pieDataSet.valueTextSize = 12f
        pieDataSet.colors = colors
        piechartActivity.holeRadius=10f
        piechartActivity.transparentCircleRadius=0f

        val data = PieData(pieDataSet)

        piechartActivity.data=data
        piechartActivity.notifyDataSetChanged()
        piechartActivity.invalidate()


    }

}

