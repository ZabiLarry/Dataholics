package com.example.dataholics
import android.os.Environment
import com.example.dataholics.ui.export.ExportFragment
import org.junit.Assert
import org.junit.Test
import java.io.File


class TestPDF {

    private val exportFragment = ExportFragment()

    @Test
    fun testConnection() {
        Assert.assertEquals(true, exportFragment.isOpen())
    }

    @Test
    fun pieChartCreated() {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "/pieChart.pdf"
        val file = File(path, fileName)
        Assert.assertEquals(true, file.exists())
    }

    @Test
    fun barChartCreated() {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "/barChart.pdf"
        val file = File(path, fileName)
        Assert.assertEquals(true, file.exists())
    }



}
