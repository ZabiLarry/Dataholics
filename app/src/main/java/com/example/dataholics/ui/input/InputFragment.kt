package com.example.dataholics.ui.input

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dataholics.R
import com.example.dataholics.database.TaskDBHelper
import kotlinx.android.synthetic.main.fragment_input.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class InputFragment : Fragment(), View.OnClickListener {

    private lateinit var inputViewModel: InputViewModel
    var date = 0
    var time = 0
    var activity = 0
    var company = 0
    var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputViewModel = ViewModelProviders.of(this).get(InputViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_input, container, false)

        val cal = Calendar.getInstance()

        val chooseTimeFrom: TextView = root!!.findViewById(R.id.chooseTimeStart)
        chooseTimeFrom.text = SimpleDateFormat("HH:00").format(cal.time)
        val chooseDate: TextView = root!!.findViewById(R.id.chooseDate)
        chooseDate.text = SimpleDateFormat("yyyy/MM/dd").format(cal.time)

        val addActivity: Button = root!!.findViewById(R.id.addActivity)

        val sleepButton: Button = root!!.findViewById(R.id.sleep)
        sleepButton.setOnClickListener(this)
        val eatingButton: Button = root!!.findViewById(R.id.eating)
        eatingButton.setOnClickListener(this)
        val leisureButton: Button = root!!.findViewById(R.id.leisure)
        leisureButton.setOnClickListener(this)
        val schoolButton: Button = root!!.findViewById(R.id.school)
        schoolButton.setOnClickListener(this)
        val paidJobButton: Button = root!!.findViewById(R.id.paid_job)
        paidJobButton.setOnClickListener(this)
        val hmwrkButton: Button = root!!.findViewById(R.id.hmwork)
        hmwrkButton.setOnClickListener(this)
        val errandsButton: Button = root!!.findViewById(R.id.errands)
        errandsButton.setOnClickListener(this)
        val exerciseButton: Button = root!!.findViewById(R.id.exercise)
        exerciseButton.setOnClickListener(this)
        val datingButton: Button = root!!.findViewById(R.id.dating)
        datingButton.setOnClickListener(this)
        val healthButton: Button = root!!.findViewById(R.id.health)
        healthButton.setOnClickListener(this)
        val socialButton: Button = root!!.findViewById(R.id.social)
        socialButton.setOnClickListener(this)
        val travelButton: Button = root!!.findViewById(R.id.travel)
        travelButton.setOnClickListener(this)


        val aloneButton: Button = root!!.findViewById(R.id.alone)
        aloneButton.setOnClickListener(this)
        val partnerButton: Button = root!!.findViewById(R.id.partner)
        partnerButton.setOnClickListener(this)
        val familyButton: Button = root!!.findViewById(R.id.family)
        familyButton.setOnClickListener(this)
        val friendsButton: Button = root!!.findViewById(R.id.friends)
        friendsButton.setOnClickListener(this)
        val coworkersButton: Button = root!!.findViewById(R.id.coworkers)
        coworkersButton.setOnClickListener(this)

        chooseTimeFrom.setOnClickListener {

            val timeSetListener =
                TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hour: Int, _: Int ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, 0)
                    chooseTimeFrom.text = SimpleDateFormat("HH:00").format(cal.time)
                }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        chooseDate.setOnClickListener {

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    chooseDate.text = SimpleDateFormat("yyyy/MM/dd").format(cal.time)
                }
            DatePickerDialog(
                context!!, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        addActivity.setOnClickListener {
            addTask()
        }
        return root
    }

    private fun addTask() {

        var x = 0
        val timeCut = StringBuilder(2)
        timeCut.append(chooseTimeStart.text.toString()[0])
        timeCut.append(chooseTimeStart.text.toString()[1])
        time = Integer.parseInt(timeCut.toString())
        val dateAsArray = ArrayList<Int>()
        val dataAsChar = chooseDate.text.toString().toCharArray();
        //YYYY/MM/DD
        for (i in 0..9) {
            if (i != 4 && i != 7) {
                dateAsArray.add(dataAsChar[i].toString().toInt())
            }
        }
        date = 0;
        for (j in dateAsArray) {
            date = 10 * date + j
        }

        val taskDBHelper = TaskDBHelper(context!!)

        if (activity == 0 || company == 0) {
            Toast.makeText(context, "Activity or company not selected", Toast.LENGTH_LONG)
                .show()
        } else {
            while (x < Integer.parseInt(durationTime.text.toString())) {
                if (time >= 24) {
                    date++
                    time = 0
                    if ((date % 100) > 28) {
                        if ((date % 10000) / 100 == 2) {
                            date += 100
                            date -= 28
                        }
                    }
                    if ((date % 100) > 30) {
                        if ((date % 10000) / 100 == 4 && (date % 10000) / 100 == 6 && (date % 10000) / 100 == 9
                            && (date % 10000) / 100 == 11
                        ) {
                            date += 100
                            date -= 30
                        }
                    }
                    if (date % 100 > 31) {
                        date += 100
                        date -= 31
                    }
                    if ((date % 10000) / 100 > 12) {
                        date += 10000
                        date -= 1200
                    }
                }
                taskDBHelper.addTask(activity, company, (100 * date) + time)
                time++
                x++
            }
            if (time < 10) {
                chooseTimeStart.text = "0$time:00"
            } else {
                chooseTimeStart.text = "$time:00"
            }

            Toast.makeText(context, "Activity added for $x hour(s)", Toast.LENGTH_LONG)
                .show()
        }//makes time an ID
    }

    fun selectChoice(view: View) {
        when (view.id) {
            R.id.sleep -> {
                activity = 1
                Toast.makeText(context!!, "Activity set to sleep", Toast.LENGTH_LONG).show()
            }
            R.id.eating -> {
                activity = 2
                Toast.makeText(context!!, "Activity set to eating", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.leisure -> {
                activity = 3
                Toast.makeText(context!!, "Activity set to leisure", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.school -> {
                activity = 4
                Toast.makeText(context!!, "Activity set to school", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.paid_job -> {
                activity = 5
                Toast.makeText(context!!, "Activity set to paid job", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.hmwork -> {
                activity = 6
                Toast.makeText(context!!, "Activity set to homework", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.errands -> {
                activity = 7
                Toast.makeText(context!!, "Activity set to errands", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.exercise -> {
                activity = 8
                Toast.makeText(context!!, "Activity set to exercise", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.travel -> {
                activity = 9
                Toast.makeText(context!!, "Activity set to travel", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.social -> {
                activity = 10
                Toast.makeText(context!!, "Activity set to social", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.health -> {
                activity = 11
                Toast.makeText(context!!, "Activity set to health", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.dating -> {
                activity = 12
                Toast.makeText(context!!, "Activity set to dating", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.alone -> {
                company = 1
                Toast.makeText(context!!, "Company set to alone", Toast.LENGTH_LONG).show()
            }
            R.id.partner -> {
                company = 2
                Toast.makeText(context!!, "Company set to partner", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.friends -> {
                company = 3
                Toast.makeText(context!!, "Company set to friends", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.family -> {
                company = 4
                Toast.makeText(context!!, "Company set to family", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.coworkers -> {
                company = 5
                Toast.makeText(context!!, "Company set to co-workers", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onClick(v: View?) {
        selectChoice(v!!)

    }

}