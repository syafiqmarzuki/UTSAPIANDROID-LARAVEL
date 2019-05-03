package com.syafiqmarzuki21.msyafiqmarzuki.sepakbola

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Fragment.FragmentBola
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Fragment.FragmentSearch
import com.syafiqmarzuki21.msyafiqmarzuki.sepakbola.Fragment.FragmentShow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        private var openFirst = true
        private var navStatus = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initComp()
        if(savedInstanceState == null){
            openFirst = true
            val item = nav_view.getMenu().getItem(0).setChecked(true)
            onNavigationItemSelected(item)
        }


        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var fragment : Fragment? = null
        when (item.itemId) {
            R.id.nav_new -> {
                if(navStatus == 0 && !openFirst){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    navStatus = 0
                    openFirst = false
                    fragment = FragmentBola()
                }
            }
            R.id.nav_show -> {
                if(navStatus == 1 && !openFirst){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    openFirst = false
                    navStatus = 1
                    fragment = FragmentShow()
                }
            }
            R.id.nav_search -> {
                if(navStatus == 2 && !openFirst){
                    drawer_layout.closeDrawer(GravityCompat.START)
                }else{
                    openFirst = false
                    navStatus = 2
                    fragment = FragmentSearch()
                }
            }

            else -> {
                openFirst = false
                navStatus = 0
                fragment = FragmentShow()
            }
        }

        if(fragment != null){
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.container_fragment, fragment)
            ft.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initComp(){
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }
}
