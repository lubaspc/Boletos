package com.ittoluca.lubinpc.guiatpu.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import com.ittoluca.lubinpc.boletos.SQLite.*
import java.io.ByteArrayInputStream


class CRUD(context: Context) {
    var helper= SQLiteHerlper(context)
    var colCiudades = arrayOf("Id_Ciudad", "Nombre", "Estado")
    var colBuses = arrayOf("Id_bus","Linea", "COrigen", "CDestino", "Tiempo", "Costo", "CapacidadT")
    var colViajes = arrayOf("Id_viaje", "Id_bus", "fechaS", "fechaLl", "fechaC", "Costo", "nombre", "foto","edad")


    fun consultarCiudadesxID(id:String):ArrayList<Ciudades>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Ciudades> = ArrayList()
       var c = db.query("Ciudad", colCiudades, " Id_Ciudad = ?", arrayOf(id), null, null, null, null)
        while (c.moveToNext()) {
                Array.add(
                    Ciudades(
                    c.getInt(c.getColumnIndexOrThrow(colCiudades[0])),
                    c.getString(c.getColumnIndexOrThrow(colCiudades[1])),
                    c.getString(c.getColumnIndexOrThrow(colCiudades[2]))
                )
                )
        }
        db.close()
        return Array
    }
    fun consultarCiudades():ArrayList<Ciudades>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Ciudades> = ArrayList()
        var c = db.query("Ciudad", colCiudades, null,null, null, null, null, null)
        while (c.moveToNext()) {
            Array.add(Ciudades(
                c.getInt(c.getColumnIndexOrThrow(colCiudades[0])),
                c.getString(c.getColumnIndexOrThrow(colCiudades[1])),
                c.getString(c.getColumnIndexOrThrow(colCiudades[2]))
            ))
        }
        db.close()
        return Array
    }

    fun consultarCiudadesGroupnombre(estado:String): ArrayList<String> {
        val db: SQLiteDatabase =helper.readableDatabase
        var Array = arrayListOf<String>()
        var c = db.query("Ciudad", arrayOf(colCiudades[1]), " Estado = ?", arrayOf(estado),null, null, null, null)
        while (c.moveToNext()) {
            Array.add(c.getString(c.getColumnIndexOrThrow(colCiudades[1])))
        }
        db.close()
        return Array
    }

    fun consultarIDciudades(estado:String,lugar:String): String {
        val db: SQLiteDatabase =helper.readableDatabase
        var Array =" "
        var c = db.query("Ciudad", arrayOf(colCiudades[0]), " Estado = ? and Nombre= ?", arrayOf(estado,lugar),null, null, null, null)
        while (c.moveToNext()) {
            Array=c.getString(c.getColumnIndexOrThrow(colCiudades[0]))
        }
        db.close()
        return Array
    }

    fun consultarIDBus(id1:String,id2:String): String {
        val db: SQLiteDatabase =helper.readableDatabase
        var Array =""
        var c = db.query("Buses", arrayOf(colBuses[0]), " COrigen = ? and CDestino= ?", arrayOf(id1,id2),null, null, null, null)
        while (c.moveToNext()) {
            Array=c.getString(c.getColumnIndexOrThrow(colBuses[0]))
        }
        db.close()
        return Array
    }

    fun consultarLineas(id1:String,id2:String): ArrayList<ArrayList<String>> {
        val db: SQLiteDatabase =helper.readableDatabase
        var Array= arrayListOf<String>()
        var Array2= arrayListOf<String>()
        var c = db.query("Buses", arrayOf(colBuses[1],colBuses[0]), " COrigen = ? and CDestino= ?", arrayOf(id1,id2),colBuses[1], null, null, null)
        while (c.moveToNext()) {
            Array.add(c.getString(c.getColumnIndexOrThrow(colBuses[1])))
            Array2.add(c.getString(c.getColumnIndexOrThrow(colBuses[0])))
        }
        db.close()
        return arrayListOf(Array,Array2)
    }

    fun consultarLineasFull(): ArrayList<String>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array= arrayListOf<String>()
        var c = db.query("Buses", arrayOf(colBuses[1]), null, null,colBuses[1], null, null, null)
        while (c.moveToNext()) {
            Array.add(c.getString(c.getColumnIndexOrThrow(colBuses[1])))

        }
        db.close()
        return Array
    }


    fun consultarEstadosJoin(): ArrayList<String> {
        val db: SQLiteDatabase =helper.readableDatabase
        var Array = arrayListOf<String>()
        var c = db.rawQuery("select c.Estado from Ciudad c join Buses b on (c.Id_Ciudad=b.COrigen) group by c.Estado ",null)
        while (c.moveToNext()) {
            Array.add(c.getString(c.getColumnIndexOrThrow("Estado")))
        }
        db.close()
        return Array
    }

    fun consultarEstadosJoin2(id:String):ArrayList<String>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array = arrayListOf<String>()
        var c = db.rawQuery("select c.Estado from Ciudad c join Buses b on (c.Id_Ciudad=b.CDestino) where b.COrigen=? group by c.Estado ; ", arrayOf(id))
        while (c.moveToNext()) {
            Array.add(c.getString(c.getColumnIndexOrThrow("Estado")))
        }
        db.close()
        return Array
    }

    fun consultarEstadosJoin2xestado(id:String,Estado:String):ArrayList<String>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array = arrayListOf<String>()
        var c = db.rawQuery("select c.Nombre from Ciudad c join Buses b on (c.Id_Ciudad=b.CDestino) where b.COrigen=? and c.Estado=?;", arrayOf(id,Estado))
        while (c.moveToNext()) {
            Array.add(c.getString(c.getColumnIndexOrThrow("Nombre")))
        }
        db.close()
        return Array
    }



    fun consultarBusesxID(id:String):ArrayList<Buses>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Buses> = ArrayList()

        var c = db.query("Buses", colBuses, " Id_bus = ?", arrayOf(id),
                             null, null, null, null)

        while (c.moveToNext()) {
                Array.add(
                    Buses(
                    c.getInt(c.getColumnIndexOrThrow(colBuses[0])),
                    c.getString(c.getColumnIndexOrThrow(colBuses[1])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[2])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[3])),
                    c.getDouble(c.getColumnIndexOrThrow(colBuses[4])),
                    c.getDouble(c.getColumnIndexOrThrow(colBuses[5])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[6]))
                ))
        }
        db.close()
        return Array
    }
    fun consultarBusesxlinea(Linea:String):ArrayList<Buses>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Buses> = ArrayList()
        var c = db.query("Buses", colBuses, "Linea = ?", arrayOf(Linea),
            null, null, null, null)

        while (c.moveToNext()) {
            Array.add(
                Buses(
                    c.getInt(c.getColumnIndexOrThrow(colBuses[0])),
                    c.getString(c.getColumnIndexOrThrow(colBuses[1])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[2])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[3])),
                    c.getDouble(c.getColumnIndexOrThrow(colBuses[4])),
                    c.getDouble(c.getColumnIndexOrThrow(colBuses[5])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[6]))
                ))
        }
        db.close()
        return Array
    }

    fun consultarBusesxOrigen(Origen:String):ArrayList<Buses>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Buses> = ArrayList()
        var c = db.query("Buses", colBuses, "COrigen = ?", arrayOf(Origen),
            null, null, null, null)

        while (c.moveToNext()) {
            Array.add(
                Buses(
                    c.getInt(c.getColumnIndexOrThrow(colBuses[0])),
                    c.getString(c.getColumnIndexOrThrow(colBuses[1])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[2])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[3])),
                    c.getDouble(c.getColumnIndexOrThrow(colBuses[4])),
                    c.getDouble(c.getColumnIndexOrThrow(colBuses[5])),
                    c.getInt(c.getColumnIndexOrThrow(colBuses[6]))
                ))
        }
        db.close()
        return Array
    }

    fun ConsutaViajesxID(id:String):ArrayList<Viajes>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Viajes> = ArrayList()
        var c = db.query("Viajes", colViajes, " Id_ruta = ?", arrayOf(id), null, null, null, null)
        while (c.moveToNext()) {
                Array.add(
                    Viajes(
                    c.getInt(c.getColumnIndexOrThrow(colViajes[0])),
                    c.getInt(c.getColumnIndexOrThrow(colViajes[1])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[2])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[3])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[4])),
                    c.getDouble(c.getColumnIndexOrThrow(colViajes[5])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[6])),
                        BitmapFactory.decodeStream(ByteArrayInputStream(c.getBlob(7))),
                    c.getInt(c.getColumnIndexOrThrow(colViajes[8]))
                )
                )
        }
        db.close()
        return Array
    }

    fun ConsutaViajes():ArrayList<Viajes>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Viajes> = ArrayList()
        var c = db.query("Viajes", colViajes, null,null, null, null, null, null)
        while (c.moveToNext()) {
            Array.add(
                Viajes(
                    c.getInt(c.getColumnIndexOrThrow(colViajes[0])),
                    c.getInt(c.getColumnIndexOrThrow(colViajes[1])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[2])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[3])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[4])),
                    c.getDouble(c.getColumnIndexOrThrow(colViajes[5])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[6])),
                    BitmapFactory.decodeStream(ByteArrayInputStream(c.getBlob(7))),
                    c.getInt(c.getColumnIndexOrThrow(colViajes[8]))
                )
            )
        }
        db.close()
        return Array
    }

    fun ConsutaViajesxfecha(fecha:String):ArrayList<Viajes>{
        val db: SQLiteDatabase =helper.readableDatabase
        var Array :ArrayList<Viajes> = ArrayList()
        var c = db.query("Viajes", colViajes, "fechaS > ?", arrayOf(fecha), null, null, colViajes[2], null)
        while (c.moveToNext()) {
            Array.add(
                Viajes(
                    c.getInt(c.getColumnIndexOrThrow(colViajes[0])),
                    c.getInt(c.getColumnIndexOrThrow(colViajes[1])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[2])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[3])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[4])),
                    c.getDouble(c.getColumnIndexOrThrow(colViajes[5])),
                    c.getString(c.getColumnIndexOrThrow(colViajes[6])),
                    BitmapFactory.decodeStream(ByteArrayInputStream(c.getBlob(7))),
                    c.getInt(c.getColumnIndexOrThrow(colViajes[8]))
                )
            )
        }
        db.close()
        return Array
    }




    fun insertarCiudades(T: Ciudad){
        val db: SQLiteDatabase =helper.writableDatabase
        val values=ContentValues()
        values.put(colCiudades[1],T.Nombre)
        values.put(colCiudades[2],T.Estado)
        db.insert("Ciudad",null,values)
    }

    fun insertarViajes(T: Viaje){
        val db: SQLiteDatabase =helper.writableDatabase
        val values=ContentValues()
        values.put(colViajes[1],T.Id_bus)
        values.put(colViajes[2],T.fechaS)
        values.put(colViajes[3],T.fechaLl)
        values.put(colViajes[4],T.fechaC)
        values.put(colViajes[5],T.Costo)
        values.put(colViajes[6],T.nombre)
        val baos = ByteArrayOutputStream(20480);
        T.foto!!.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        var blob = baos.toByteArray()
        values.put(colViajes[7],blob)
        values.put(colViajes[8],T.edad)
        db.insert("Viajes",null,values)
    }

    fun UpdateViajes(id: String,T: Viajes){
        val db: SQLiteDatabase =helper.writableDatabase
        val values=ContentValues()
        values.put(colViajes[0],T.Id_Viaje)
        values.put(colViajes[1],T.Id_bus)
        values.put(colViajes[2],T.fechaS)
        values.put(colViajes[3],T.fechaLl)
        values.put(colViajes[4],T.fechaC)
        values.put(colViajes[5],T.Costo)
        values.put(colViajes[6],T.nombre)
        val baos = ByteArrayOutputStream(20480);
        T.foto!!.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        var blob = baos.toByteArray()
        values.put(colViajes[7],blob)
        values.put(colViajes[8],T.edad)
        db.update("Viajes",values,"Id_viaje = ? ", arrayOf(id))
    }

    fun EliminarViajes(id: String){
        val db: SQLiteDatabase =helper.writableDatabase
        db.delete("Viajes","Id_viaje = ? ", arrayOf(id))
    }

    fun insertarBuses(T:Bus){
        val db: SQLiteDatabase =helper.writableDatabase
        val values=ContentValues()
        values.put(colBuses[1],T.Linea)
        values.put(colBuses[2],T.COrigen)
        values.put(colBuses[3],T.CDestino)
        values.put(colBuses[4],T.Tiempo)
        values.put(colBuses[5],T.Costo)
        values.put(colBuses[6],T.CapacidadT)
        db.insert("Buses",null,values)
    }


}