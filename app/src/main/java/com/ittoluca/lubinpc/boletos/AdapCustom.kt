package com.ittoluca.lubinpc.guiatpu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ittoluca.lubinpc.boletos.R
import com.ittoluca.lubinpc.boletos.SQLite.Buses
import com.ittoluca.lubinpc.boletos.SQLite.Viajes
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import kotlinx.android.synthetic.main.lainfo.view.*


class AdapCustom(var context:Context, item:ArrayList<Buses>):BaseAdapter() {

    var item:ArrayList<Buses>?=null

    init {
        this.item=item

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder?=null
        var vista:View?=convertView
        if (vista==null){
            vista=LayoutInflater.from(context).inflate(R.layout.lisinfo,null)
            holder=ViewHolder(vista)
            vista.tag=holder
        }else{
            holder=vista.tag as? ViewHolder
        }
        var items=getItem(position)as Buses
        var b1=CRUD(context).consultarCiudadesxID(items.COrigen.toString())
        var b2=CRUD(context).consultarCiudadesxID(items.CDestino.toString())
        holder?.Origen!!.text="Salida "+b1[0].Nombre+", "+b1[0].Estado
        holder?.Destino!!.text="Llegada "+b2[0].Nombre+", "+b2[0].Estado
        holder?.Tiempo!!.text="Duracion "+items.Tiempo.toString()
        holder?.Costo!!.text="Costo "+items.Costo.toString()

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return item?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return item!!.count()
    }
    private class ViewHolder(vista:View){
        var Origen:TextView?=null
        var Destino:TextView?=null
        var Tiempo:TextView?=null
        var Costo:TextView?=null


        init {
            Origen=vista.findViewById(R.id.Origenn)
            Destino=vista.findViewById(R.id.Destinoo)
            Tiempo=vista.findViewById(R.id.Tiempoo)
            Costo=vista.findViewById(R.id.COstoo)
        }

    }
}
