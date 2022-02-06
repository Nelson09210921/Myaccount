package com.myapp.MyAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class modificarpeso extends AppCompatActivity {

    private EditText et_nombre, et_pesokilo, et_pesoneto;
    SQLiteOpenHelper conn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarpeso);

        et_nombre = (EditText)findViewById(R.id.nombrepesomod);
        et_pesokilo = (EditText)findViewById(R.id.pesokilomod);
        et_pesoneto = (EditText)findViewById(R.id.pesonetomod);

        String nombreforaneo = getIntent().getStringExtra("nombreforaneo");
        int pesokiloforaneo = getIntent().getIntExtra("preciokiloforaneo", 0);
        int pesonetoforaneo = getIntent().getIntExtra("pesonetoforaneo", 0);
        int pesototal = getIntent().getIntExtra("totalforaneo", 0);
        String pesokiloforaneostring = String.valueOf(pesokiloforaneo);
        String pesonetoforaneostring = String.valueOf(pesonetoforaneo);
        String[] parametro={nombreforaneo};



        et_nombre.setText(nombreforaneo);
        et_pesokilo.setText(pesokiloforaneostring);
        et_pesoneto.setText(pesonetoforaneostring);
    }

    public void modificarbotton(View view){
        String nombreforaneo = getIntent().getStringExtra("nombreforaneo");
        int pesokiloforaneo = getIntent().getIntExtra("preciokiloforaneo", 0);
        int pesonetoforaneo = getIntent().getIntExtra("pesonetoforaneo", 0);
        int pesototal = getIntent().getIntExtra("totalforaneo", 0);

        String[] parametro={nombreforaneo};



        String nombremod = et_nombre.getText().toString();
        String cantidadkilo = et_pesokilo.getText().toString();
        String pesonetomod = et_pesoneto.getText().toString();

        float cantidadkilomod = Float.parseFloat(cantidadkilo);
        float pesonetomod_float = Float.parseFloat(pesonetomod);
        int pesoneto_int = Integer.parseInt(pesonetomod);

        Float preciotot_float = cantidadkilomod * (pesonetomod_float/1000);

        int preciotot_int = Math.round(preciotot_float);
        int pesokilo_int = Math.round(cantidadkilomod);
        conn = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
        SQLiteDatabase basededatos = conn.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(utilidades.CAMPO_NOMBRE, nombremod);
        values.put(utilidades.CAMPO_CANTIDAD, pesoneto_int);
        values.put(utilidades.CAMPO_PRECIOUND, pesokilo_int);
        values.put(utilidades.CAMPO_PRECIOTOT, preciotot_int);
        int cantidad = 0;
        cantidad = basededatos.update(utilidades.TABLA_PRODUCTO,values, utilidades.CAMPO_NOMBRE+"=?",parametro);
        basededatos.close();
        if(cantidad ==1){
            Toast.makeText(getApplicationContext(), "Se modifico correctamente", Toast.LENGTH_SHORT).show();
            Intent devolver = new Intent(modificarpeso.this, Listaprincipal.class);
            devolver.putExtra("dato5", pesototal);
            devolver.putExtra("dato6", preciotot_int);
            startActivity(devolver);
            finish();
        }

    }


    public void eliminarpesobotton(View view){

        String nombreforaneo = getIntent().getStringExtra("nombreforaneo");
        int valorrestar = getIntent().getIntExtra("totalforaneo", 0);


        String[] parametro={nombreforaneo};

        conn = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
        SQLiteDatabase basededatos = conn.getWritableDatabase();

        int cant = basededatos.delete(utilidades.TABLA_PRODUCTO, utilidades.CAMPO_NOMBRE+"=?", parametro);
        basededatos.close();
        if(cant == 1){
            Toast.makeText(this, "Dato eliminado correctamente", Toast.LENGTH_SHORT).show();
        }
        Intent intentelipeso = new Intent(modificarpeso.this, Listaprincipal.class);
        intentelipeso.putExtra("dato5", valorrestar);

        startActivity(intentelipeso);
        finish();
    }

}