package com.ittoluca.lubinpc.boletos.SQLite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView

import com.ittoluca.lubinpc.boletos.R
import com.ittoluca.lubinpc.guiatpu.AdapCustom
import com.ittoluca.lubinpc.guiatpu.AdaptadorCustom
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ListaBuses : Fragment() {

    private var vieww: View?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww=inflater.inflate(R.layout.fragment_lista_buses, container, false)

        var array= CRUD(vieww!!.context).consultarLineasFull()
        val list=vieww!!.findViewById<ListView>(R.id.listasdd)
        list.adapter=ArrayAdapter<String>(vieww!!.context,android.R.layout.simple_expandable_list_item_1,array)
        list.onItemClickListener= object :AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val ar=CRUD(vieww!!.context).consultarBusesxlinea(array[position])
                var modeldialog = AlertDialog.Builder(vieww!!.context)
                val Dialogvista = layoutInflater.inflate(R.layout.listado, null)
                val adaptador= AdapCustom(vieww!!.context, ar)
                val list=Dialogvista.findViewById<ListView>(R.id.liss)
                list.adapter=adaptador
                modeldialog.setView(Dialogvista)
                var dialogo = modeldialog.create()
                dialogo.show()
            }

        }
        return vieww
    }


}
