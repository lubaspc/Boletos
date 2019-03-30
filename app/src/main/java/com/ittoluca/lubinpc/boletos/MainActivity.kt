package com.ittoluca.lubinpc.boletos

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ittoluca.lubinpc.boletos.SQLite.Historial
import com.ittoluca.lubinpc.boletos.SQLite.ListaBuses
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        val bool=prefs.getBoolean("bandera",false)
        val editor = prefs.edit()
        editor.putString("login", "LJCDE")
        editor.putString("pass", "DesMov")
        editor.commit()
        if (!bool || !prefs.getBoolean("sesion",false)){
            val int=Intent(this,Inicio::class.java)
            startActivity(int)
        }
        nav_view.setNavigationItemSelectedListener(this)


        supportFragmentManager.beginTransaction().replace(R.id.cont, ListaBuses()).commit()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
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
        when (item.itemId) {
            R.id.acerca -> {
                var modeldialog= AlertDialog.Builder(this)
                val Dialogvista=layoutInflater.inflate(R.layout.acercami,null)
                modeldialog.setView(Dialogvista)
                var dialogo=modeldialog.create()
                dialogo.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> supportFragmentManager.beginTransaction().replace(R.id.cont, Busqueda()).commit()
            R.id.nav_gallery -> supportFragmentManager.beginTransaction().replace(R.id.cont, Bpendientes()).commit()
            R.id.nav_slideshow -> supportFragmentManager.beginTransaction().replace(R.id.cont, Historial()).commit()
            R.id.Lineasad ->supportFragmentManager.beginTransaction().replace(R.id.cont, ListaBuses()).commit()
            R.id.nav_manage -> finishAffinity()
            R.id.nav_share -> {
                val prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("sesion", false)
                editor.commit()
                val Inten=Intent(this,Inicio::class.java)
                startActivity(Inten)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
