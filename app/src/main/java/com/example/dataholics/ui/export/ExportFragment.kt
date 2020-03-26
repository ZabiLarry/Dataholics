package com.example.dataholics.ui.export

import android.Manifest
import android.Manifest.permission
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.example.dataholics.database.Task
import com.example.dataholics.database.TaskDBHelper
import com.example.dataholics.ui.data.ui.histogram.HistogramBottomFragment
import com.example.dataholics.ui.data.ui.histogram.TrendsBottomFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itextpdf.awt.geom.Rectangle2D
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfTemplate
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*


class ExportFragment : Fragment() {

    lateinit var sendViewModel: SendViewModel
    lateinit var piechartActivity: PieChart
    lateinit var barChartCompany: BarChart
    lateinit var lineChartTask: LineChart
    var taskList: ArrayList<Task>? = null
    var monthValue: Array<Array<Int>> = emptyArray()
    var displayYear = 2020
    var root: View? = null
    private var REQUEST_EXTERNAL_STORAGE = 1;
    private var PERMISSIONS_STORAGE: Array<String> = arrayOf(
        permission.READ_EXTERNAL_STORAGE,
        permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProviders.of(this).get(SendViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_export, container, false)
        val exportButton: Button = root!!.findViewById(R.id.exportButton)
        piechartActivity = root!!.findViewById(R.id.piechartActivity)
        piechartActivity.isRotationEnabled = true
        barChartCompany = root!!.findViewById(R.id.barChartCompany)
       // lineChartTask = root!!.findViewById(R.id.lineChartTask)
        // lineChartTask = root!!.findViewById(R.id.lineChartTask)
        exportButton.setOnClickListener {
            export()
            Toast.makeText(context, "Exported", Toast.LENGTH_LONG).show()
        }
        return root
    }

    private fun export() {
        piechartActivity = pieChart()
        val pie: Bitmap = piechartActivity.drawToBitmap()
        barChartCompany = barChart()
        val bar: Bitmap = barChartCompany.drawToBitmap()
       // lineChartTask = lineChart()
        //val line : Bitmap = lineChartTask.drawToBitmap()
        newPDF(pie, "/pieChart.pdf")
        newPDF(bar, "/barChart.pdf")
        //newPDF(line, "/lineChart.pdf")
        Toast.makeText(context, "Exported to your documents file", Toast.LENGTH_LONG).show()
    }





    private fun newPDF(chart: Bitmap, fileName: String) {
        activity?.let { verifyStoragePermissions(it) }
        val stream: ByteArrayOutputStream = ByteArrayOutputStream();
        chart.compress(Bitmap.CompressFormat.PNG, 100, stream);
        val bitmapData: ByteArray = stream.toByteArray()
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(path, fileName)
        val doc = Document(PageSize.A4)

        try {
            PdfWriter.getInstance(doc, FileOutputStream(file));
            doc.open();

            // Creating image by file name
            val image: Image = Image.getInstance(bitmapData);
            doc.add(image);

        } catch (e: DocumentException) {
            e.printStackTrace();
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            doc.close();
        }

    }

    private fun pieChart(): PieChart {
        val dbHelper = TaskDBHelper(context!!)
        val activityValue = arrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        val activityName = arrayOf(
            "Sleep",
            "Eating",
            "Leisure",
            "School",
            "Paid Job",
            "Homework",
            "Errands",
            "Exercise",
            "Travel",
            "Social",
            "Health",
            "Dating"
        )
        val activityList: ArrayList<Int> = ArrayList(dbHelper.getActivities())
        for (i in 0 until activityList.size) {
            activityValue[activityList[i] - 1] = activityValue[activityList[i] - 1] + 1

        }

        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val xEntries: ArrayList<String> = ArrayList()

        for (i in activityValue.indices) {
            pieEntries.add(PieEntry(activityValue[i], i))
        }
        for (element in activityName) {
            xEntries.add(element)
        }
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

        val pieDataSet = PieDataSet(pieEntries, "Activities")
        pieDataSet.sliceSpace = 2f
        pieDataSet.valueTextSize = 12f
        pieDataSet.colors = colors
        piechartActivity.holeRadius = 10f
        piechartActivity.transparentCircleRadius = 0f

        val data = PieData(pieDataSet)

        piechartActivity.data = data
        piechartActivity.notifyDataSetChanged()
        piechartActivity.invalidate()
        return piechartActivity
    }

    private fun barChart(): BarChart {
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
            barEntries.add(BarEntry((i * 2).toFloat(), companyValue[i]))
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
        xAxis.valueFormatter = ExportFragment.MyBarXAxisFormatter()
        xAxis.textSize = 12F
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE

        barChartCompany.notifyDataSetChanged()
        barChartCompany.invalidate()
        return barChartCompany
    }

    class MyBarXAxisFormatter : ValueFormatter() {
        private val companyName =
            arrayOf("Alone", "", "Partner", "", "Friends", "", "Family", "", "Co-workers", "")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return companyName.getOrNull(value.toInt()) ?: value.toString()
        }


    }

    private fun lineChart(): LineChart {
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
        val activityName = arrayOf(
            "Sleep",
            "Eating",
            "Leisure",
            "School",
            "Paid Job",
            "Homework",
            "Errands",
            "Exercise",
            "Travel",
            "Social",
            "Health",
            "Dating"
        )
        for (i in 1..12) {//month
            val lineEntries: ArrayList<Entry> = ArrayList()
            for (j in 1..12) {//activity
                lineEntries.add(Entry((i - 1).toFloat(), monthValue[i - 1][j - 1].toFloat()))
                val lineDataSet = LineDataSet(lineEntries, activityName[j - 1])
                lineDataSet.color = colors[j - 1]
                lineDataSet.lineWidth = 8f
                lineDataSets.add(lineDataSet)
            }
        }


        val data = LineData(lineDataSets as List<ILineDataSet>?)

        val xAxis = lineChartTask.xAxis
        xAxis.valueFormatter = ExportFragment.MyLineXAxisFormatter()
        xAxis.textSize = 12F
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE

        lineChartTask.data = data
        lineChartTask.notifyDataSetChanged()
        lineChartTask.invalidate()

        return lineChartTask
    }

    class MyLineXAxisFormatter : ValueFormatter() {

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

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        var permission =
            ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}