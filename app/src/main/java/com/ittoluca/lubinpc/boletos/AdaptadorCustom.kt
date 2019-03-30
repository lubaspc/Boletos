package com.ittoluca.lubinpc.guiatpu

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ittoluca.lubinpc.boletos.R
import com.ittoluca.lubinpc.boletos.SQLite.Viajes
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import kotlinx.android.synthetic.main.lainfo.view.*


class AdaptadorCustom(var context:Context,item:ArrayList<Viajes>,acivity:FragmentActivity):BaseAdapter() {

    var item:ArrayList<Viajes>?=null
    var activity:FragmentActivity?=null
    init {
        this.item=item
        this.activity=acivity
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder?=null
        var vista:View?=convertView
        if (vista==null){
            vista=LayoutInflater.from(context).inflate(R.layout.item,null)
            holder=ViewHolder(vista)
            vista.tag=holder
        }else{
            holder=vista.tag as? ViewHolder
        }
        var items=getItem(position)as Viajes
        holder?.Bcolo!!.setImageBitmap(items.foto)
        holder?.TNom!!.text=items.nombre
        holder?.TFecha!!.text=items.fechaS
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
    private class ViewHolder(vista:View) {
        var Bcolo: ImageView? = null
        var TNom: TextView? = null
        var TFecha: TextView? = null

        init {
            Bcolo = vista.findViewById(R.id.IMGItem)
            TNom = vista.findViewById(R.id.itemNombre)
            TFecha = vista.findViewById(R.id.itemfecha)

        }

    }

}
