package com.ittoluca.lubinpc.boletos


import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ittoluca.lubinpc.boletos.SQLite.Buses
import com.ittoluca.lubinpc.boletos.SQLite.Viaje
import com.ittoluca.lubinpc.boletos.SQLite.Viajes
import com.ittoluca.lubinpc.guiatpu.AdaptadorCustom
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import kotlinx.android.synthetic.main.lainfo.*
import kotlinx.android.synthetic.main.lainfo.view.*
import kotlinx.android.synthetic.main.peronales.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class Bpendientes : Fragment() {
    var vieww:View?=null
    var Hora1=""
    var Hora2=""
    var foto:Bitmap?=null
    var IMG:ImageButton?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww=inflater.inflate(R.layout.fragment_bpendientes, container, false)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date()
        var array=CRUD(vieww!!.context).ConsutaViajesxfecha(dateFormat.format(date))
        for (i in array){
            if(!dateFormat.parse(i.fechaS).after(date)){
                array.remove(i)
            }
        }
        val adaptador= AdaptadorCustom(vieww!!.context, array,activity!!)
        val list=vieww!!.findViewById<ListView>(R.id.listpendietes)
        list.adapter=adaptador
        list.onItemClickListener=object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ventana(array[position])
                onCreateView(inflater,container,savedInstanceState)
            }


        }
        return vieww
    }


    @SuppressLint("NewApi")
    fun ventana(items:Viajes){
        var modeldialog = AlertDialog.Builder(vieww!!.context)
        val Dialogvista = activity!!.layoutInflater.inflate(R.layout.lainfo, null)
        var bus=CRUD(vieww!!.context).consultarBusesxID(items.Id_bus.toString())
        var cid=CRUD(vieww!!.context).consultarCiudadesxID(bus[0].COrigen.toString())
        var cid2=CRUD(vieww!!.context).consultarCiudadesxID(bus[0].CDestino.toString())
        foto=items.foto
        Dialogvista.idd.setText("ID de vaije: "+items.Id_Viaje)
        IMG=Dialogvista.findViewById(R.id.IMG2)
        IMG!!.setImageBitmap(items.foto)
        IMG!!.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 1)
        }
        Dialogvista.Noombre.setText(""+items.nombre)
        Dialogvista.Edadd.setText(""+items.edad)
        Dialogvista.FechaIDA.setText("Fecha de ida: "+items.fechaS)
        val textfecha1=Dialogvista.findViewById<TextView>(R.id.FechaIDA)
        val sP=Dialogvista.findViewById<Spinner>(R.id.spinner)
        val opsiones=getResources().getStringArray(R.array.Horas)
        sP.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,opsiones)
        sP.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Hora1 = opsiones[position]
            }
        }
        var long:Long?=null
        var Cal=Calendar.getInstance()
        textfecha1.setOnClickListener {
            var calendario= DatePickerDialog(vieww!!.context,android.R.style.ThemeOverlay_Material_Dialog_Alert,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    fecha.add(year)
                    fecha.add(month+1)
                    fecha.add(dayOfMonth)
                    val Cale= Calendar.getInstance()
                    Cale.set(year,month,dayOfMonth)
                    long=Cale.timeInMillis
                    textfecha1.setText(""+dayOfMonth+"-"+(month+1)+"-"+year)
                }
                ,Cal.get(Calendar.YEAR),Cal.get(Calendar.MONTH),Cal.get(Calendar.DAY_OF_MONTH))
            //calendario.window.setBackgroundDrawable(resources.getDrawable(R.color.transparente))
            val can= Calendar.getInstance()
            can.add(Calendar.DAY_OF_YEAR,0)
            calendario.datePicker.minDate=can.timeInMillis
            calendario.show()
        }
        val Sp=Dialogvista.findViewById<Spinner>(R.id.spinner2)
        val fechatex2=Dialogvista.findViewById<TextView>(R.id.FechaRetorno)
        if (items.fechaLl==items.fechaS){
            fechatex2.visibility=View.INVISIBLE
            Sp.visibility=View.INVISIBLE

        }else{

            Sp.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,opsiones)
            Sp.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Hora2 = opsiones[position]
                }
            }
           fechatex2.setText("Fecha de retorno: "+items.fechaLl)
            fechatex2.setOnClickListener {
                var calendario = DatePickerDialog(vieww!!.context, android.R.style.ThemeOverlay_Material_Dialog_Alert,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        fech2.add(year)
                        fech2.add(month + 1)
                        fech2.add(dayOfMonth)
                        fechatex2.setText("" + dayOfMonth + "-" + (month + 1) + "-" + year)
                    }
                    , Cal.get(Calendar.YEAR), Cal.get(Calendar.MONTH), Cal.get(Calendar.DAY_OF_MONTH))
                //calendario.window.setBackgroundDrawable(resources.getDrawable(R.color.transparente))
                calendario.datePicker.minDate = long!!
                calendario.show()
            }
        }
        Dialogvista.FechaCompra.setText("Fecha de compra: "+items.fechaC)
        Dialogvista.CiudadS.setText("Ciudad de salida: "+cid[0].Estado+", "+cid[0].Nombre)
        Dialogvista.CiudadR.setText("Ciudad de llegada: "+cid2[0].Estado+", "+cid2[0].Nombre)
        var costo=0.0
        var ids=0
        Dialogvista.CiudadR.setOnClickListener {
           val bus= CRUD(vieww!!.context).consultarBusesxOrigen(cid[0].id_ciudad.toString())
            val aString= arrayListOf<String>()
            val sCosto= arrayListOf<Double>()
            val rID= arrayListOf<Int>()
            for (i in bus){
                val cid=CRUD(vieww!!.context).consultarCiudadesxID(i.CDestino.toString())
                aString.add(cid[0].Nombre+", "+cid[0].Estado+": "+i.Linea)
                sCosto.add(i.Costo)
                rID.add(bus[0].id_bus)
            }
            val SS=Dialogvista.findViewById<Spinner>(R.id.spinner3)

            SS.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,aString)
            SS.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Dialogvista.CiudadR.setText(aString[position])
                    costo=sCosto[position]
                    ids=rID[position]
                    Dialogvista.Costo.setText("Costo $"+sCosto[position])
                }
            }

        }
        costo=items.Costo
        Dialogvista.Costo.setText("Costo $"+items.Costo)

        modeldialog.setPositiveButton("Guardar", DialogInterface.OnClickListener { dialog, which ->
            var fehca1=""
            if(!fecha.isEmpty()){
               fehca1=""+fecha[0]+"-"+fecha[1]+"-"+fecha[2]+" "+Hora1+":00"
            }else{
                fehca1=items.fechaS
            }
            var fehca2=""
            if(!fech2.isEmpty()){
                fehca2=""+fech2[0]+"-"+fech2[1]+"-"+fech2[2]+" "+Hora2+":00"
            }else{
                fehca2=fehca1
            }
            val prefs = context!!.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            val DMe= prefs.getInt("DescuentoMenores", 0)
            val DMa = prefs.getInt("DescuentoMayores", 0)
            var edadd=0
            try {
               edadd= Dialogvista.Edadd.text.toString().toInt()
            }catch (e:Exception){ }
            if(costo==0.0){
                costo=items.Costo
            }

            if(edadd>=50){
                costo=costo-(costo*(DMa.toDouble()/100.0))
            }else if (edadd<=12){
                costo=costo-(costo*(DMe.toDouble()/100.0))
            }
            if(ids==0){
                ids=items.Id_bus
            }

            val viajes= Viajes(items.Id_Viaje,ids,fehca1,fehca2,items.fechaC,costo,Dialogvista.Noombre.text.toString(),foto!!,edadd)
            CRUD(vieww!!.context).UpdateViajes(items.Id_Viaje.toString(),viajes)
        })
        modeldialog.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        modeldialog.setNeutralButton("Eliminar", DialogInterface.OnClickListener { dialog, which ->
            CRUD(vieww!!.context).EliminarViajes(items.Id_Viaje.toString())

        })
        modeldialog.setView(Dialogvista)
        var dialogo = modeldialog.create()
        dialogo.show()

    }
    var fecha= arrayListOf<Int>()
    var fech2= arrayListOf<Int>()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 ->{
                if(resultCode== Activity.RESULT_OK) {
                    val extras= data?.extras
                    foto=extras!!.get("data") as Bitmap
                    IMG!!.setImageBitmap(foto)
                }
                if(resultCode== Activity.RESULT_CANCELED){
                    Toast.makeText(vieww!!.context,"Es nesesaria la foto tomala", Toast.LENGTH_SHORT).show()
                    //val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    //startActivityForResult(intent,1)
                }
            }
        }
    }


}
