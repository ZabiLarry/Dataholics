package com.example.dataholics

import android.content.Intent
import android.os.Bundle
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
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.replace
import androidx.transition.Slide
import com.example.dataholics.ui.calendar.ToolsFragment
import com.example.dataholics.ui.data.DataFragment
import com.example.dataholics.ui.export.ExportFragment
import com.example.dataholics.ui.profile.ProfileFragment
import com.example.dataholics.ui.settings.SettingsFragment
import com.example.dataholics.ui.share.ShareFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        var headerImage: ImageView = headerView.findViewById(R.id.imageView)//here we can set the profile picture
        navView.setNavigationItemSelectedListener(this);
        /*headerImage.setOnClickListener {
            @Override
            fun onClick(view: View) {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    ProfileFragment()
                ).commit()
            }
        }*/

        val actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
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
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.nav_data ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    DataFragment()
                ).commit()
            R.id.nav_export ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    ExportFragment()
                ).commit()
            R.id.nav_settings ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    SettingsFragment()
                ).commit()
            R.id.nav_share ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    ShareFragment()
                ).commit()
            R.id.nav_tools ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    ToolsFragment()
                ).commit()
            //will be calendar^
        }
        drawer_layout.closeDrawer(GravityCompat.START)
    return true}

    fun openProfile(view: View) {
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment,
            ProfileFragment()
        ).commit()
    }
}


