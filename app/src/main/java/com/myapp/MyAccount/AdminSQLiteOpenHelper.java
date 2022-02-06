package com.myapp.MyAccount;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//clase para la creacion de la base de datos
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    //
    public AdminSQLiteOpenHelper( Context Context,  String name,  SQLiteDatabase.CursorFactory factory, int version){
        super(Context, name, factory, version);

}

    @Override
    public void onCreate(SQLiteDatabase basededatos){
        basededatos.execSQL(utilidades.CREAR_TABLA_PRODUCTO);


    }

    @Override

    public void onUpgrade(SQLiteDatabase basededatos, int oldversion, int newVersion){
        basededatos.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(basededatos);
    }

}


