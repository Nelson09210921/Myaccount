package com.myapp.MyAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

public class Modificar extends AppCompatActivity {

    //Se declaran variables
    private EditText et_nombre;
    private Spinner s_cantidad, s_miles, s_pesos;
    private Snackbar snackbar1;
    SQLiteOpenHelper con;
    private TextView canmod, preciomod;
    private Switch cincuenta;
    //private  TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        //Se enlaza parte logica y grafica
        et_nombre = (EditText) findViewById(R.id.et_nombremodificar);
        s_cantidad = (Spinner) findViewById(R.id.spinner_cantidad_mod);
        s_pesos = (Spinner) findViewById(R.id.spinner_pesos_mid);
        s_miles = (Spinner) findViewById(R.id.spinner_miles_mod);
        canmod = (TextView)findViewById(R.id.canmod);
        preciomod = (TextView)findViewById(R.id.preciomod);
        //tv = (TextView) findViewById(R.id.tv_milesmodificar);


        //Vectores para llenar spinners
        Integer[] cantidad = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item_cantidad, cantidad);
        s_cantidad.setAdapter(adapter1);

        Integer[] miles = {0, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000,
                20000, 210000, 22000, 23000, 24000, 25000, 26000, 27000, 28000, 29000, 30000, 40000, 41000, 42000, 43000, 44000, 45000, 46000, 47000, 48000, 49000, 50000};
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_miles, miles);
        s_miles.setAdapter(adapter2);

        Integer[] pesos = {0, 100, 200, 300, 400, 500, 600, 700, 800, 900};
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(this, R.layout.spiner_item_pesos, pesos);
        s_pesos.setAdapter(adapter3);

        //Se modifica edittext para predeterminar el nombre
        String nombreforaneo = getIntent().getStringExtra("nombreforaneo");
        con = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
        SQLiteDatabase db = con.getReadableDatabase();
        //Imprimir cantidades actuales, y precio actual
        String[] parametros={nombreforaneo};
        String[] campos={utilidades.CAMPO_CANTIDAD, utilidades.CAMPO_PRECIOUND};
        Cursor cursor = db.query(utilidades.TABLA_PRODUCTO, campos, utilidades.CAMPO_NOMBRE+"=?", parametros, null, null, null);
        cursor.moveToFirst();
        int unidadsettext = cursor.getInt(0);
        int precioundsextext = cursor.getInt(1);
        String unidadset_string = String.valueOf(unidadsettext);
        String precioset_string = String.valueOf(precioundsextext);

        canmod.setText(unidadset_string);
        preciomod.setText(precioset_string);

        et_nombre.setText(nombreforaneo);

    }


    //Metodo para el boton modificar
    public void modificar(View view){
        cincuenta = (Switch) findViewById(R.id.switch2);

        //Recepcion de datos ingresados
        String nombreforaneo = getIntent().getStringExtra("nombreforaneo");
        String nombre_string = et_nombre.getText().toString();
        String cantidad_string = s_cantidad.getSelectedItem().toString();
        String miles_string;
        miles_string = s_miles.getSelectedItem().toString();
        String pesos_string = s_pesos.getSelectedItem().toString();

        //Declaracion de variables adicionales necesarias
        int precio_und, totalproducto;
        int cantidad_int = Integer.parseInt(cantidad_string);
        int miles_int = Integer.parseInt(miles_string);
        int pesos_int = Integer.parseInt(pesos_string);

        //Calcular precios
        if(!cincuenta.isChecked()){
            precio_und = miles_int + pesos_int;
        }else{
            precio_und = miles_int + pesos_int + 50;
        }
        totalproducto = cantidad_int * precio_und;

         //Se evalua que el campo nombre no este vacio
         if(!nombre_string.isEmpty()){

             //Se abre db y se indica que se leera, esto se hace para buscar el elemento despues por su nombre
             con = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
             SQLiteDatabase db = con.getReadableDatabase();

             String[] parametros ={nombreforaneo};
             String [] campos ={utilidades.CAMPO_PRECIOTOT};
             //Se busca
             Cursor cursor = db.query(utilidades.TABLA_PRODUCTO, campos, utilidades.CAMPO_NOMBRE+"=?", parametros, null, null, null);
             cursor.moveToFirst();
             int precio_toteal_db = cursor.getInt(0);

            //Se abre nuevamente y se indica que esta vez sera para escritura
             AdminSQLiteOpenHelper con = new AdminSQLiteOpenHelper(this, utilidades.TABLA_PRODUCTO, null, 1);
             SQLiteDatabase basededatos = con.getWritableDatabase();
             ContentValues values = new ContentValues();
             values.put(utilidades.CAMPO_NOMBRE, nombre_string);
             values.put(utilidades.CAMPO_CANTIDAD, cantidad_int);
             values.put(utilidades.CAMPO_PRECIOUND, precio_und);
             values.put(utilidades.CAMPO_PRECIOTOT, totalproducto);
             String[] parametro = {nombreforaneo};
             //Se remplazan datos y se cierra db
             int cantidad = basededatos.update(utilidades.TABLA_PRODUCTO,values, utilidades.CAMPO_NOMBRE+"=?",parametro);
             basededatos.close();
             if(cantidad==1){
                 //Indicando que se modifico y de vuelta al activity pricipal
                 Toast.makeText(getApplicationContext(), "Se modifico correctamente", Toast.LENGTH_SHORT).show();
                 Intent devolver = new Intent(Modificar.this, Listaprincipal.class);
                 devolver.putExtra("dato2", precio_toteal_db);
                 devolver.putExtra("dato3", totalproducto);
                 startActivity(devolver);
                 finish();

             }
         }else{
             //Mensaje para solicitar llenar todos los campos
             View view1 = (View)findViewById(R.id.modificar);
             snackbar1 = Snackbar.make(view1, "Debes llenar todos los campos", Snackbar.LENGTH_SHORT);
             snackbar1.setAction("Ok", new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             }).setActionTextColor(Color.RED);
             snackbar1.setDuration(3000).show();

         }

    }

    public void eliminar(View view){
        //se abre la base de datos
        con = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
        SQLiteDatabase db = con.getReadableDatabase();
        //se recibe el nombre a buscar y ubicacion de cantidad
        String datoforaneo = getIntent().getStringExtra("nombreforaneo");
        String[] parametros ={datoforaneo};
        String [] campos ={utilidades.CAMPO_CANTIDAD, utilidades.CAMPO_PRECIOUND, utilidades.CAMPO_PRECIOTOT};
        //Se busca
        Cursor cursor = db.query(utilidades.TABLA_PRODUCTO, campos, utilidades.CAMPO_NOMBRE+"=?", parametros, null, null, null);
        cursor.moveToFirst();
        int cantidad_db= cursor.getInt(0);
        int precio_und_db = cursor.getInt(1);
        int precio_toteal_db = cursor.getInt(2);

        cursor.close();
        //convalidacion de cantidades
        String cantidad_string = s_cantidad.getSelectedItem().toString();
        int cantidad_int = Integer.parseInt(cantidad_string);
        int evaluador = cantidad_db -cantidad_int;
        int preciotot;

        //Dependiendo de las cantidades se ejecutan acciones diferentes
        if(evaluador > 0){
            //Se modifica sobreencribiendo y eliminando cantidades deseadas
            ContentValues values = new ContentValues();
            String[] parametro ={datoforaneo};

            preciotot = evaluador * precio_und_db;

            values.put(utilidades.CAMPO_CANTIDAD, evaluador);
            values.put(utilidades.CAMPO_PRECIOTOT, preciotot);
            db.update(utilidades.TABLA_PRODUCTO, values, utilidades.CAMPO_NOMBRE+"=?", parametro);
            Intent intent = new Intent(Modificar.this, Listaprincipal.class);
            intent.putExtra("dato2", precio_toteal_db);
            intent.putExtra("dato3", preciotot);
            startActivity(intent);
            finish();
        }else if(evaluador == 0){
            //Se calcula precio total para restar en db y se elimina elemento
            preciotot = evaluador * precio_und_db;
            int precioenviar = -(precio_toteal_db) + preciotot;
            SQLiteDatabase basededatos = con.getWritableDatabase();

            basededatos.delete(utilidades.TABLA_PRODUCTO, utilidades.CAMPO_NOMBRE+"=?", parametros);
            basededatos.close();
            Intent intent = new Intent(Modificar.this, Listaprincipal.class);
            intent.putExtra("dato2", precio_toteal_db);
            intent.putExtra("dato3", preciotot);
            startActivity(intent);
            finish();
        }else if(evaluador < 0){

            Toast.makeText(this, "Por favor revise la cantidad agregada", Toast.LENGTH_SHORT).show();
        }
    }



}