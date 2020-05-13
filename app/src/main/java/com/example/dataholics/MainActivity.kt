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
import com.example.dataholics.ui.login.LoginFragment
import com.example.dataholics.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_account.view.*
import kotlinx.android.synthetic.main.fragment_input.*
import kotlinx.android.synthetic.main.fragment_input.view.*
import kotlinx.android.synthetic.main.fragment_input.view.chooseDate
import kotlinx.android.synthetic.main.nav_header_main.*
import java.lang.StringBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.DateFormat
import java.util.*



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun getAuth(): FirebaseAuth {
        return auth
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()
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
                R.id.nav_tools, R.id.nav_share, R.id.nav_export, R.id.nav_login, R.id.nav_create_account
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        val login: LoginFragment = LoginFragment()
        login.updateUI(currentUser)
    }
    // Notification----------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------






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

}

private fun Any.replace(navHostFragmentContainer: Int, selectedFragment: Fragment) {

}


