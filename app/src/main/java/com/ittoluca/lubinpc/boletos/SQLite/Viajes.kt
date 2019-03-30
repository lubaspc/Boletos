package com.ittoluca.lubinpc.boletos.SQLite

import android.graphics.Bitmap

class Viajes(Id_Viaje:Int,Id_bus:Int, fechaS:String,
             fechaLl:String,fechaC:String, Costo:Double,nombre:String,
             foto:Bitmap, edad:Int) {
    var Id_Viaje=0
    var Id_bus=0
    var fechaS=""
    var fechaLl=""
    var fechaC=""
    var Costo=0.0
    var nombre=""
    var foto:Bitmap?=null
    var edad=0

    init {
        this.Id_Viaje=Id_Viaje
        this.Id_bus=Id_bus
        this.fechaS=fechaS
        this.fechaLl=fechaLl
        this.fechaC=fechaC
        this.Costo=Costo
        this.nombre=nombre
        this.foto=foto
        this.edad=edad
    }
}

class Viaje(Id_bus:Int, fechaS:String,
             fechaLl:String,fechaC:String, Costo:Double,nombre:String,
             foto:Bitmap, edad:Int) {
    var Id_bus = 0
    var fechaS = ""
    var fechaLl = ""
    var fechaC = ""
    var Costo = 0.0
    var nombre = ""
    var foto: Bitmap? = null
    var edad = 0

    init {
        this.Id_bus = Id_bus
        this.fechaS = fechaS
        this.fechaLl = fechaLl
        this.fechaC = fechaC
        this.Costo = Costo
        this.nombre = nombre
        this.foto = foto
        this.edad = edad
    }
}