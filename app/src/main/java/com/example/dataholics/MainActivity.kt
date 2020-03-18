package com.example.dataholics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.CalendarView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.dataholics.database.Task
import com.example.dataholics.ui.data.ui.histogram.PiechartBottomFragment
import com.example.dataholics.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_create_account.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_profile, R.id.nav_data, R.id.nav_input, R.id.nav_settings,
                R.id.nav_tools, R.id.nav_share, R.id.nav_export), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



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
    override fun onBackPressed(){
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
    fun shareButton(view: View){

        val message: String = "www.dataholics.yes/downloadlink"

        val target = Intent()

        target.action = Intent.ACTION_SEND
        target.putExtra(Intent.EXTRA_TEXT, message)
        target.type = "text/plain"

        startActivity(Intent.createChooser(target, "Please select in which application you'd like to share: "))

    }

    
    fun markDates(view: View){
        var calendarView : CalendarView
        calendarView = findViewById(R.id.calendarView)
        calendarView.setDate(1584572400000)
    }


    fun selectChoice(view:View){
        when(view.id){
            R.id.sleep -> Toast.makeText(this@MainActivity,  "Activity set to sleep", Toast.LENGTH_LONG).show()
            R.id.eating -> Toast.makeText(this@MainActivity,  "Activity set to eating", Toast.LENGTH_LONG).show()
            R.id.leisure -> Toast.makeText(this@MainActivity,  "Activity set to leisure", Toast.LENGTH_LONG).show()
            R.id.school -> Toast.makeText(this@MainActivity,  "Activity set to school", Toast.LENGTH_LONG).show()
            R.id.paid_job -> Toast.makeText(this@MainActivity,  "Activity set to paid job", Toast.LENGTH_LONG).show()
            R.id.hmwork -> Toast.makeText(this@MainActivity,  "Activity set to homework", Toast.LENGTH_LONG).show()
            R.id.errands -> Toast.makeText(this@MainActivity,  "Activity set to errands", Toast.LENGTH_LONG).show()
            R.id.exercise -> Toast.makeText(this@MainActivity,  "Activity set to exercise", Toast.LENGTH_LONG).show()
            R.id.travel -> Toast.makeText(this@MainActivity,  "Activity set to travel", Toast.LENGTH_LONG).show()
            R.id.social -> Toast.makeText(this@MainActivity,  "Activity set to social", Toast.LENGTH_LONG).show()
            R.id.health -> Toast.makeText(this@MainActivity,  "Activity set to health", Toast.LENGTH_LONG).show()
            R.id.dating -> Toast.makeText(this@MainActivity,  "Activity set to dating", Toast.LENGTH_LONG).show()
            R.id.alone -> Toast.makeText(this@MainActivity,  "Company set to alone", Toast.LENGTH_LONG).show()
            R.id.partner -> Toast.makeText(this@MainActivity,  "Company set to partner", Toast.LENGTH_LONG).show()
            R.id.friends -> Toast.makeText(this@MainActivity,  "Company set to friends", Toast.LENGTH_LONG).show()
            R.id.family -> Toast.makeText(this@MainActivity,  "Company set to family", Toast.LENGTH_LONG).show()
            R.id.coworkers -> Toast.makeText(this@MainActivity,  "Company set to coworkers", Toast.LENGTH_LONG).show()
        }
    }



}

private fun Any.replace(navHostFragmentContainer: Int, selectedFragment: Fragment) {

}


