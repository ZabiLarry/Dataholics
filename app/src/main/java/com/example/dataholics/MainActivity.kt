package com.example.dataholics

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dataholics.Notification.AlertReceiver
import com.example.dataholics.Notification.TimePickerFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.dataholics.database.DBContract
import com.example.dataholics.database.Task
import com.example.dataholics.database.TaskDBHelper
import com.example.dataholics.ui.data.ui.histogram.PiechartBottomFragment
import com.example.dataholics.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_account.view.*
import kotlinx.android.synthetic.main.fragment_input.*
import kotlinx.android.synthetic.main.fragment_input.view.*
import kotlinx.android.synthetic.main.fragment_input.view.chooseDate
import kotlinx.android.synthetic.main.nav_header_main.*

import java.lang.StringBuilder

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.DateFormat
import java.util.*



class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var activity = 0
    var company = 0
    var date = 0
    var time = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            super.onBackPressed()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(

            setOf(
                R.id.nav_profile, R.id.nav_data, R.id.nav_input, R.id.nav_settings,
                R.id.nav_tools, R.id.nav_share, R.id.nav_export
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    // Notification----------------------------------------------------------------------------
    fun setNotTime(view: View){
        val timePicker : DialogFragment =
            TimePickerFragment()
        timePicker.show(supportFragmentManager, "time picker")
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val showTime : TextView = findViewById(R.id.showTimeChosen)
        showTime.text = ("Hour: " + hourOfDay + "Minute: " + minute)

        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)

        updateTimeText(c)
        startNotification(c)
    }
    private fun updateTimeText(c:Calendar) {
        var timeText : String = "Alarm set for: "
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)

        showTimeChosen.text = timeText
    }
    private fun startNotification(c: Calendar) {
        if(dailyReminder.isChecked) {
            val alarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1)
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        }
    }
//--------------------------------------------------------------------------------------------------------



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    @Override
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun shareButton(view: View) {

        val message: String = "www.dataholics.yes/downloadlink"

        val target = Intent()

        target.action = Intent.ACTION_SEND
        target.putExtra(Intent.EXTRA_TEXT, message)
        target.type = "text/plain"

        startActivity(
            Intent.createChooser(
                target,
                "Please select in which application you'd like to share: "
            )
        )

    }

    fun selectChoice(view: View) {
        when (view.id) {
            R.id.sleep -> {
                activity = 1
                Toast.makeText(this@MainActivity, "Activity set to sleep", Toast.LENGTH_LONG).show()
            }
            R.id.eating -> {
                activity = 2
                Toast.makeText(this@MainActivity, "Activity set to eating", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.leisure -> {
                activity = 3
                Toast.makeText(this@MainActivity, "Activity set to leisure", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.school -> {
                activity = 4
                Toast.makeText(this@MainActivity, "Activity set to school", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.paid_job -> {
                activity = 5
                Toast.makeText(this@MainActivity, "Activity set to paid job", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.hmwork -> {
                activity = 6
                Toast.makeText(this@MainActivity, "Activity set to homework", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.errands -> {
                activity = 7
                Toast.makeText(this@MainActivity, "Activity set to errands", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.exercise -> {
                activity = 8
                Toast.makeText(this@MainActivity, "Activity set to exercise", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.travel -> {
                activity = 9
                Toast.makeText(this@MainActivity, "Activity set to travel", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.social -> {
                activity = 10
                Toast.makeText(this@MainActivity, "Activity set to social", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.health -> {
                activity = 11
                Toast.makeText(this@MainActivity, "Activity set to health", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.dating -> {
                activity = 12
                Toast.makeText(this@MainActivity, "Activity set to dating", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.alone -> {
                company = 1
                Toast.makeText(this@MainActivity, "Company set to alone", Toast.LENGTH_LONG).show()
            }
            R.id.partner -> {
                company = 2
                Toast.makeText(this@MainActivity, "Company set to partner", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.friends -> {
                company = 3
                Toast.makeText(this@MainActivity, "Company set to friends", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.family -> {
                company = 4
                Toast.makeText(this@MainActivity, "Company set to family", Toast.LENGTH_LONG)
                    .show()
            }
            R.id.coworkers -> {
                company = 5
                Toast.makeText(this@MainActivity, "Company set to co-workers", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    fun addTask(view: View) {
        val chooseTimeStart = findViewById<TextView>(R.id.chooseTimeStart)
        val chooseDate = findViewById<TextView>(R.id.chooseDate)
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

        val taskDBHelper = TaskDBHelper(this.applicationContext)

        if (activity == 0 || company == 0) {
            Toast.makeText(this@MainActivity, "Activity or company not selected", Toast.LENGTH_LONG)
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
            Toast.makeText(this@MainActivity, "Activity added for $x hour(s)", Toast.LENGTH_LONG)
                .show()
        }


    }

}

private fun Any.replace(navHostFragmentContainer: Int, selectedFragment: Fragment) {

}


