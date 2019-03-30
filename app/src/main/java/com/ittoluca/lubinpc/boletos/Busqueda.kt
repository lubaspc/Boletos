package com.ittoluca.lubinpc.boletos


import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ittoluca.lubinpc.boletos.SQLite.Ciudades
import com.ittoluca.lubinpc.boletos.SQLite.Viaje
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import kotlinx.android.synthetic.main.fragment_busqueda.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class Busqueda : Fragment() {

    var vieww:View?=null
    var id_bus=""
    var Hora1=""
    var Hora2=""
    var fecha= arrayListOf<Int>()
    var fech2= arrayListOf<Int>()
    var nombres= ""
    var edads=0
    var foto:Bitmap?=null
    var iteraciones=0


    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww=inflater.inflate(R.layout.fragment_busqueda, container, false)
        val SOEstado=vieww!!.findViewById<Spinner>(R.id.SOEstado)
        val SOLugar=vieww!!.findViewById<Spinner>(R.id.SONombre)
        val SDEstado=vieww!!.findViewById<Spinner>(R.id.SDEstado)
        val SDLugar=vieww!!.findViewById<Spinner>(R.id.SDNombre)
        val SDLinea=vieww!!.findViewById<Spinner>(R.id.SPLinea)

        val crud=CRUD(vieww!!.context)
        val ciudades=crud.consultarEstadosJoin()
        var Estado=""
        var Estado2=""
        var Nombre=""

        SOEstado.adapter= ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,ciudades)
        SOEstado.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Estado=ciudades[position]
                val lugares= crud.consultarCiudadesGroupnombre(Estado)

                SOLugar.adapter= ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,lugares)
                SOLugar.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        Nombre=lugares[position]
                        var id_origen=crud.consultarIDciudades(Estado,Nombre)
                        val ciu=crud.consultarEstadosJoin2(id_origen)
                        SDEstado.adapter = ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,ciu)
                        SDEstado.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                 Estado2=ciu[position]
                                val lu=crud.consultarEstadosJoin2xestado(id_origen,Estado2)
                                SDLugar.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,lu)
                                SDLugar.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                      var id_destino=crud.consultarIDciudades(Estado2,lu[position])
                                        var Lineas=crud.consultarLineas(id_origen,id_destino)
                                        SDLinea.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,Lineas[0])
                                        SDLinea.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                id_bus=Lineas[1][position]
                                                COstito.setText("$"+crud.consultarBusesxID(id_bus)[0].Costo.toString())
                                            }
                                        }

                                    }

                                }
                            }

                        }
                    }
                }

            }
        }

        val cantidad=vieww!!.findViewById<Spinner>(R.id.SPCantidad)
        val opsiones=getResources().getStringArray(R.array.Cantidad)
        cantidad.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,opsiones)
        cantidad.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              iteraciones=opsiones[position].toInt()
            }
        }
        val boton=vieww!!.findViewById<Button>(R.id.BBuscar)
        boton.setOnClickListener {
                    fechas(iteraciones)
        }
        return vieww
    }

    @SuppressLint("NewApi")
    fun fechas(cant:Int){
        var modeldialog = AlertDialog.Builder(vieww!!.context)
        val Dialogvista = layoutInflater.inflate(R.layout.compras, null)
        val check=Dialogvista.findViewById<CheckBox>(R.id.Redondo)
        modeldialog.setView(Dialogvista)
        modeldialog.setPositiveButton("Aceptar",DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            if(check.isChecked){
                if (fecha.isEmpty() && fech2.isEmpty()){
                    Toast.makeText(vieww!!.context,"Tienes que poner ambas fechas",Toast.LENGTH_SHORT).show()
                }else{
                    for (i in 0..cant-1)
                        boletos()
                }
            }else{
                if (fecha.isEmpty() ){
                    Toast.makeText(vieww!!.context,"Tienes que poner la fecha",Toast.LENGTH_SHORT).show()
                }else{
                    for (i in 0..cant-1)
                        boletos()
                }
            }

        })
        modeldialog.setNegativeButton("Cancelar",DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        var dialogo = modeldialog.create()
        var Cal=Calendar.getInstance();

        val fecha1=Dialogvista.findViewById<TextView>(R.id.Fecha1)
        val SH1=Dialogvista.findViewById<Spinner>(R.id.SHora)
        var fecha2=Dialogvista.findViewById<TextView>(R.id.Fecha2)
        val SH2=Dialogvista.findViewById<Spinner>(R.id.SHora2)
        fecha2.visibility=View.INVISIBLE
        SH2.visibility=View.INVISIBLE

        val opsiones=getResources().getStringArray(R.array.Horas)
        SH1.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,opsiones)
        SH1.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Hora1= opsiones[position]
            }
        }
        var long:Long?=null

        fecha1!!.setOnClickListener{
            var calendario=DatePickerDialog(vieww!!.context,android.R.style.ThemeOverlay_Material_Dialog_Alert,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    fecha.add(year)
                    fecha.add(month+1)
                    fecha.add(dayOfMonth)
                    val Cale=Calendar.getInstance()
                    Cale.set(year,month,dayOfMonth)
                    long=Cale.timeInMillis
                    fecha1.setText(""+dayOfMonth+"-"+(month+1)+"-"+year)
                }
                ,Cal.get(Calendar.YEAR),Cal.get(Calendar.MONTH),Cal.get(Calendar.DAY_OF_MONTH))
            //calendario.window.setBackgroundDrawable(resources.getDrawable(R.color.transparente))
            val can=Calendar.getInstance()
            can.add(Calendar.DAY_OF_YEAR,0)
            calendario.datePicker.minDate=can.timeInMillis
            calendario.show()
        }
        SH2.adapter=ArrayAdapter(vieww!!.context,R.layout.support_simple_spinner_dropdown_item,opsiones)
        SH2.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Hora2=opsiones[position]
            }

        }
        fecha2!!.setOnClickListener {
            if(long!=null) {
                var calendario = DatePickerDialog(vieww!!.context, android.R.style.ThemeOverlay_Material_Dialog_Alert,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        fech2.add(year)
                        fech2.add(month + 1)
                        fech2.add(dayOfMonth)
                        fecha2.setText("" + dayOfMonth + "-" + (month + 1) + "-" + year)
                    }
                    , Cal.get(Calendar.YEAR), Cal.get(Calendar.MONTH), Cal.get(Calendar.DAY_OF_MONTH))
                //calendario.window.setBackgroundDrawable(resources.getDrawable(R.color.transparente))
                calendario.datePicker.minDate = long!!
                calendario.show()
            }
        }
        check.setOnClickListener {
            if(check.isChecked){
                fecha2.visibility=View.VISIBLE
                SH2.visibility=View.VISIBLE

            }else{
                fecha2.visibility=View.INVISIBLE
                SH2.visibility=View.INVISIBLE
            }
        }

        dialogo.setCancelable(false)
        dialogo.setCanceledOnTouchOutside(false)

        dialogo.show()
    }


    fun boletos(){
        var modeldialog = AlertDialog.Builder(vieww!!.context)
        val Dialogvista = layoutInflater.inflate(R.layout.peronales, null)
        modeldialog.setView(Dialogvista)

        val nombre=Dialogvista.findViewById<EditText>(R.id.Nombre)
        val edad=Dialogvista.findViewById<EditText>(R.id.Edad)

        modeldialog.setPositiveButton("Siguiente",DialogInterface.OnClickListener { dialog, which ->

            if(nombre.text.toString()=="" && nombre.text.isEmpty() && edad.text.toString()!=="" && edad.text.isEmpty()){
                Toast.makeText(vieww!!.context,"Llena los campos",Toast.LENGTH_SHORT).show()
            }else {
                nombres = nombre.text.toString()
                try {

                    edads = edad.text.toString().toInt()
                    val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent,1)

                } catch (e: Exception) {
                    Toast.makeText(vieww!!.context, "Coloca una edad valida", Toast.LENGTH_SHORT).show()
                }
            }

            modeldialog.setNegativeButton("Cancelar",DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        })
        var dialogo = modeldialog.create()
        dialogo.setCancelable(false)
        dialogo.setCanceledOnTouchOutside(false)
        dialogo.show()
    }

    private fun inserquery() {
        Toast.makeText(vieww!!.context,"Compra Completa",Toast.LENGTH_LONG).show()
        var id=0
        try {
           id=id_bus.toInt()
        }catch (e:Exception){}
        val B=CRUD(vieww!!.context).consultarBusesxID(id_bus)
        var costo=0.0
        for (b in  B){
            costo=b.Costo
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var fehca1=""+fecha[0]+"-"+fecha[1]+"-"+fecha[2]+" "+Hora1+":00"
        var fehca2=""
        if(!fech2.isEmpty()){
            fehca2=""+fech2[0]+"-"+fech2[1]+"-"+fech2[2]+" "+Hora2+":00"
        }else{
            fehca2=fehca1
        }
        val prefs = context!!.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        val DMe= prefs.getInt("DescuentoMenores", 0)
        val DMa = prefs.getInt("DescuentoMayores", 0)
        if(edads>=50){
            costo=costo-(costo*(DMa.toDouble()/100.0))
        }else if (edads<=12){
            costo=costo-(costo*(DMe.toDouble()/100.0))
        }

        val date = Date()
        val v=Viaje(id,fehca1,fehca2,dateFormat.format(date),costo,nombres,foto!!,edads)
        CRUD(vieww!!.context).insertarViajes(v)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 ->{
                if(resultCode== Activity.RESULT_OK) {
                    val extras= data?.extras
                     foto= extras!!.get("data") as Bitmap
                        inserquery()
                }
                if(resultCode==Activity.RESULT_CANCELED){
                    Toast.makeText(vieww!!.context,"Es nesesaria la foto tomala",Toast.LENGTH_SHORT).show()
                    //val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    //startActivityForResult(intent,1)
                }
            }
        }
    }


}
