package com.ittoluca.lubinpc.boletos.SQLite

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.ittoluca.lubinpc.boletos.R
import com.ittoluca.lubinpc.guiatpu.AdaptadorCustom
import com.ittoluca.lubinpc.guiatpu.SQLite.CRUD
import java.text.SimpleDateFormat
import java.util.*


class Historial : Fragment() {

    var vieww:View?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww=inflater.inflate(R.layout.fragment_historial, container, false)
        var array= CRUD(vieww!!.context).ConsutaViajes()
        val adaptador= AdaptadorCustom(vieww!!.context, array,activity!!    )
        val list=vieww!!.findViewById<ListView>(R.id.historial)
        list.adapter=adaptador
        return vieww
    }


}
