package com.ittoluca.lubinpc.boletos.SQLite

class Buses(Id_bus:Int,Linea:String,COrigen:Int,CDestino:Int, Tiempo:Double, Costo:Double, CapacidadT:Int) {
    var id_bus:Int=0
    var COrigen=0
    var Linea=""
    var CDestino=0
    var Tiempo=0.0
    var Costo=0.0
    var CapacidadT=0

    init {
        this.Linea=Linea
        this.id_bus=Id_bus
        this.COrigen=COrigen
        this.CDestino=CDestino
        this.Tiempo=Tiempo
        this.Costo=Costo
        this.CapacidadT=CapacidadT
    }
}

class Bus(Linea:String,COrigen:Int,CDestino:Int, Tiempo:Double, Costo:Double, CapacidadT:Int) {

    var COrigen=0
    var CDestino=0
    var Linea=""
    var Tiempo=0.0
    var Costo=0.0
    var CapacidadT=0

    init {
        this.Linea=Linea
        this.COrigen=COrigen
        this.CDestino=CDestino
        this.Tiempo=Tiempo
        this.Costo=Costo
        this.CapacidadT=CapacidadT
    }
}