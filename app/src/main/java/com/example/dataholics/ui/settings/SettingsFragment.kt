package com.example.dataholics.ui.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
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
import com.example.dataholics.Notification.AlertReceiver
import com.example.dataholics.R
import com.example.dataholics.database.TaskDBHelper
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.DateFormat
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
        val dbHelper = context?.let { TaskDBHelper(it) }
        val resetButton: Button = root.findViewById(R.id.resetButton)
        val chooseTimeBtn: Button = root.findViewById(R.id.chooseTimeBtn)
        val showTimeChosen: TextView = root.findViewById(R.id.showTimeChosen)
        val cal = Calendar.getInstance()

        resetButton.setOnClickListener {
            dbHelper!!.deleteAll()
            Toast.makeText(context, "Data reset", Toast.LENGTH_LONG).show()
        }

        showTimeChosen.setOnClickListener {
            val timeSetListener =
                TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, min: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, min)
                    showTimeChosen.text = SimpleDateFormat("HH:mm").format(cal.time)
                }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()

        }
        chooseTimeBtn.setOnClickListener {
            startNotification(cal)
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


    private fun startNotification(c: Calendar) {
        if (dailyReminder.isChecked) {
            val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)

            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1)
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
            Toast.makeText(context, "Alarm set", Toast.LENGTH_LONG)
                .show()
        }
    }

}