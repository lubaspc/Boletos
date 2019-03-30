package com.ittoluca.lubinpc.boletos.SQLite

class Ciudades(id_ciudad:Int,Nombre:String,Estado:String) {
    var id_ciudad:Int?=null
    var Nombre=""
    var Estado=""

    init {
        this.id_ciudad=id_ciudad
        this.Nombre=Nombre
        this.Estado=Estado
    }
}

class Ciudad(Nombre:String,Estado:String) {
    var Nombre=""
    var Estado=""

    init {
        this.Nombre=Nombre
        this.Estado=Estado
    }
}