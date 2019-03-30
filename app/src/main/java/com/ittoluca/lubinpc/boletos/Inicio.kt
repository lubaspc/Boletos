package com.ittoluca.lubinpc.boletos

import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.ittoluca.lubinpc.boletos.SQLite.Bus
import com.ittoluca.lubinpc.boletos.SQLite.Ciudad
import com.ittoluca.lubinpc.boletos.SQLite.Ciudades
import com.ittoluca.lubinpc.boletos.SQLite.Viaje
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_inicio.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class Inicio : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inicio)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        // Set up the user interaction to manually show or hide the system UI.
        fullscreen_content.setOnClickListener {  hide() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        Loger.setOnClickListener {
            val prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            if (!prefs.getBoolean("bandera",false)) {
                insertarValores()
            }
            if(prefs.getString("login","")== users.text.toString()&& prefs.getString("pass", "")==contra.text.toString()){
                editor.putBoolean("bandera", true)
                editor.putBoolean("sesion", true)
                editor.putInt("DescuentoMenores", 40)
                editor.putInt("DescuentoMayores", 70)
                editor.commit()
                finish()
            }

        }

    }

    private fun insertarValores() {
        val crud=CRUD(this)
        crud.insertarCiudades(Ciudad("Toluca","Mexico"))
        crud.insertarCiudades(Ciudad("Texcaltitlan","Mexico"))
        crud.insertarCiudades(Ciudad("Jilotepec","Mexico"))
        crud.insertarCiudades(Ciudad("Zacualpan","Mexico"))
        crud.insertarCiudades(Ciudad("Valle de bravo","Mexico"))
        crud.insertarCiudades(Ciudad("Tenango","Mexico"))
        crud.insertarCiudades(Ciudad("Tetlatlauca","Tlaxala"))
        crud.insertarCiudades(Ciudad("Puebla","Puebla"))

        crud.insertarBuses(Bus("Zinabus",1,2,120.0,50.0,42))
        crud.insertarBuses(Bus("Zinabus",2,1,120.0,50.0,42))
        crud.insertarBuses(Bus("Exelencia",1,2,120.0,50.0,42))
        crud.insertarBuses(Bus("Exelencia",2,1,120.0,50.0,42))
        crud.insertarBuses(Bus("FlechaRoja",1,2,120.0,50.0,42))
        crud.insertarBuses(Bus("FlechaRoja",2,1,120.0,50.0,42))
        crud.insertarBuses(Bus("Exelencia",1,3,150.0,90.0,42))
        crud.insertarBuses(Bus("Exelencia",3,1,150.0,90.0,42))
        crud.insertarBuses(Bus("FlechaRoja",1,4,180.0,130.0,42))
        crud.insertarBuses(Bus("FlechaRoja",4,1,180.0,130.0,42))
        crud.insertarBuses(Bus("Futura",1,5,60.0,50.0,42))
        crud.insertarBuses(Bus("Futura",5,1,60.0,50.0,42))
        crud.insertarBuses(Bus("FlechaAzul",1,6,90.0,20.0,42))
        crud.insertarBuses(Bus("FlechaAzul",6,1,90.0,20.0,42))
        crud.insertarBuses(Bus("Zinabus",1,7,240.0,450.0,42))
        crud.insertarBuses(Bus("Zinabus",7,1,240.0,250.0,42))
        crud.insertarBuses(Bus("Futura",1,8,300.0,500.0,42))
        crud.insertarBuses(Bus("Futura",8,1,300.0,500.0,42))
        crud.insertarBuses(Bus("FlechaAzul",1,8,300.0,500.0,42))
        crud.insertarBuses(Bus("FlechaAzul",8,1,300.0,500.0,42))


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }


    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }

    private val permisoCamara=android.Manifest.permission.CAMERA

    private fun ValidarPermisos(): Boolean {
        return ActivityCompat.checkSelfPermission(this, permisoCamara) == PackageManager.PERMISSION_GRANTED
    }

    override fun onStart() {
        super.onStart()
        if(ValidarPermisos()) {

        } else {
            requestPermissions(arrayOf(permisoCamara),1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1-> {
                var permiso=true
                for(i in grantResults)
                    permiso=(i==PackageManager.PERMISSION_GRANTED)&&permiso
                if(grantResults.size>0&& permiso){

                }
                else {
                    Toast.makeText(this, "Es nesesrio que des permiso", Toast.LENGTH_LONG).show()
                    finishAffinity()
                }
            }
        }
    }
}
