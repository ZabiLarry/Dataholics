package com.example.dataholics.ui.settings

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.example.dataholics.database.TaskDBHelper
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val dbHelper = TaskDBHelper(context!!)
        val resetButton : Button = root.findViewById(R.id.resetButton)

        resetButton.setOnClickListener{
            dbHelper.deleteAll()
            Toast.makeText(context, "Data reset", Toast.LENGTH_LONG).show()
        }


        /*val chooseTime:TextView = root.findViewById(R.id.showTimeChosen)
        chooseTime.setOnClickListener{
            val cal =Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                chooseTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
         */
        return root
    }

}