package com.ittoluca.lubinpc.guiatpu.SQLite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHerlper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_CIUDAD)
        db?.execSQL(CREATE_TABLE_BUSES)
        db?.execSQL(CREATE_TABLE_VIAJES )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_CIUDAD)
        db?.execSQL(DROP_TABLE_BUSES)
        db?.execSQL(DROP_TABLE_VIAJES)
        onCreate(db)
    }

    companion object {
        val CREATE_TABLE_CIUDAD="CREATE TABLE IF NOT EXISTS \"Ciudad\" (\n" +
                "\t\"Id_Ciudad\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"Nombre\"\tVARCHAR(30) NOT NULL,\n"+
                "\t\"Estado\"\tVARCHAR(30) NOT NULL );"

        var CREATE_TABLE_BUSES="CREATE TABLE IF NOT EXISTS \"Buses\" (\n" +
                "\t\"Id_bus\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"Linea\"\tTEXT NOT NULL,\n" +
                "\t\"COrigen\"\tINTEGER NOT NULL,\n" +
                "\t\"CDestino\"\tINTEGER NOT NULL,\n" +
                "\t\"Tiempo\"\tDOUBLE(4 , 100) NOT NULL,\n" +
                "\t\"Costo\"\tDOUBLE(4 , 100) NOT NULL,\n" +
                "\t\"CapacidadT\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"COrigen\") REFERENCES \"Ciudad\"(\"Id_Ciudad\"),\n" +
                "\tFOREIGN KEY(\"CDestino\") REFERENCES \"Ciudad\"(\"Id_Ciudad\")\n" +
                ");"
        var CREATE_TABLE_VIAJES="CREATE TABLE IF NOT EXISTS \"Viajes\" (\n" +
                "\t\"Id_viaje\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"Id_bus\"\tINTEGER NOT NULL,\n" +
                "\t\"fechaS\"\tDATE NOT NULL,\n" +
                "\t\"fechaLl\"\tDATE NOT NULL,\n" +
                "\t\"fechaC\"\tDATE NOT NULL,\n" +
                "\t\"Costo\"\tDOUBLE(4 , 100) NOT NULL,\n" +
                "\t\"nombre\"\tTEXT NOT NULL,\n" +
                "\t\"foto\"\tBLOB NOT NULL,\n" +
                "\t\"edad\"\tINTERGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"Id_bus\") REFERENCES \"Buses\"(\"Id_bus\")\n" +
                ");"

        var DROP_TABLE_CIUDAD="DROP TABLE IF EXISTS Ciudad"
        var DROP_TABLE_BUSES="DROP TABLE IF EXISTS Buses"
        var DROP_TABLE_VIAJES="DROP TABLE IF EXISTS Viajes"
        private val DATABASE_NAME = "Buses"
        private val DATABASE_VERSION = 1
    }

}