package com.example.dataholics.ui.input

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import java.text.SimpleDateFormat
import java.util.*

class InputFragment : Fragment() {

    private lateinit var inputViewModel: InputViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputViewModel = ViewModelProviders.of(this).get(InputViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_input, container, false)

        val cal =Calendar.getInstance()

        val chooseTimeFrom:TextView = root.findViewById(R.id.chooseTimeStart)
        chooseTimeFrom.text = SimpleDateFormat("HH:00").format(cal.time)
        val chooseDate:TextView = root.findViewById(R.id.chooseDate)
        chooseDate.text = SimpleDateFormat("yyyy/MM/dd").format(cal.time)

        chooseTimeFrom.setOnClickListener{

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, _: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, 0)
                chooseTimeFrom.text = SimpleDateFormat("HH:00").format(cal.time)
            }
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        chooseDate.setOnClickListener{

            val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                chooseDate.text = SimpleDateFormat("yyyy/MM/dd").format(cal.time)
            }
            DatePickerDialog(
                context!!, dateSetListener , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }



        return root
    }

}