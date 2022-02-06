package com.myapp.MyAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class anadir extends AppCompatActivity {

    private boolean instalada;
    Snackbar snackbar1, snackbar2;
    private EditText et_nombre;
    private Spinner spinner_cantidad, spinner_miles, spinner_pesos;
    private Switch cincuenta;
    AdminSQLiteOpenHelper conn;
    Dialog mdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        //Relacion con la vista

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        spinner_cantidad = (Spinner) findViewById(R.id.spinner1);
        spinner_miles = (Spinner) findViewById(R.id.spinner2);
        spinner_pesos = (Spinner) findViewById(R.id.spinner3);
        View view1 = findViewById(R.id.aviso_informacion);
        cincuenta = findViewById(R.id.switch1);
        inicializacionspinner();
        snackbarinicador(view1);

        SharedPreferences instaladas = getSharedPreferences("instaladas3", Context.MODE_PRIVATE);
        instalada = instaladas.getBoolean("numero", false);

        if (instalada == false) {
            new MaterialTapTargetPrompt.Builder(this)
                    .setTarget(et_nombre)
                    .setPrimaryText("Ingresamos el nombre del producto\n\n\nOK.")
                    .setSecondaryText("")
                    .setPromptFocal(new RectanglePromptFocal())
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                                new MaterialTapTargetPrompt.Builder(anadir.this)
                                        .setTarget(spinner_cantidad)
                                        .setPrimaryText("Unidades a añadir")
                                        .setSecondaryText("Pondremos las unidades necesarias.\n\n\nOK.")
                                        .setPromptFocal(new RectanglePromptFocal())
                                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                            @Override
                                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                                                    new MaterialTapTargetPrompt.Builder(anadir.this)
                                                            .setTarget(spinner_miles)
                                                            .setPrimaryText("Milesimas")
                                                            .setSecondaryText("En caso para el ejemplo usaremos un producto que cuesta 3550, entonces aqui pondremos 3000. \n\n\nOK.")
                                                            .setPromptFocal(new RectanglePromptFocal())
                                                            .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                                                @Override
                                                                public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                                                                    if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                                                                        new MaterialTapTargetPrompt.Builder(anadir.this)
                                                                                .setTarget(spinner_pesos)
                                                                                .setPrimaryText("Centesimas")
                                                                                .setSecondaryText("Como el valor es 3550 aqui pondremos 500.\n\nOK.")
                                                                                .setPromptFocal(new RectanglePromptFocal())
                                                                                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                                                                    @Override
                                                                                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                                                                                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                                                                                            new MaterialTapTargetPrompt.Builder(anadir.this)
                                                                                                    .setTarget(cincuenta)
                                                                                                    .setPrimaryText("¿50?")
                                                                                                    .setSecondaryText("Siendo el valor 3550 tendriamos que dejar activado esta opcion para añadir los 50 pesos.\n\n\nOK.")
                                                                                                    .setPromptFocal(new RectanglePromptFocal())
                                                                                                    .show();
                                                                                            SharedPreferences instaladaaa = getSharedPreferences("instaladas3", Context.MODE_PRIVATE);
                                                                                            SharedPreferences.Editor editor = instaladaaa.edit();
                                                                                            editor.putBoolean("numero", true);
                                                                                            editor.commit();
                                                                                        }
                                                                                    }
                                                                                }).show();
                                                                    }
                                                                }
                                                            }).show();
                                                }
                                            }
                                        }).show();
                            }
                        }
                    }).show();
        }

    }

    private void inicializacionspinner() {
        //Inicializacion y diseño de spinners
        Integer[] cantidad = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(this, R.layout.spinner_item_cantidad, cantidad);
        spinner_cantidad.setAdapter(adapter1);

        Integer[] miles = {0, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000,
                20000, 210000, 22000, 23000, 24000, 25000, 26000, 27000, 28000, 29000, 30000, 40000, 41000, 42000, 43000, 44000, 45000, 46000, 47000, 48000, 49000, 50000};
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this, R.layout.spinner_item_miles, miles);
        spinner_miles.setAdapter(adapter2);

        Integer[] pesos = {0, 100, 200, 300, 400, 500, 600, 700, 800, 900};
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this, R.layout.spiner_item_pesos, pesos);
        spinner_pesos.setAdapter(adapter3);
    }


    private void snackbarinicador(View view1) {
        //Snackbar para indicar como se introducen los datos
        snackbar1 = Snackbar.make(view1, "Ingrese primero los miles y luego los pesos", Snackbar.LENGTH_LONG);
        snackbar1.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).setActionTextColor(Color.GREEN);
        snackbar1.setDuration(3000);
        snackbar1.show();
    }



    //Agregar productos a la base de datos
    public void agregar(View view) {

        int contador = getIntent().getIntExtra("contadorpremium", 0);
        //Toast.makeText(this, String.valueOf(contador), Toast.LENGTH_SHORT).show();
        if (contador < 30){


        cincuenta = findViewById(R.id.switch1);

        //Recuperando datos
        String nombre = et_nombre.getText().toString();
        String cantidad_string = spinner_cantidad.getSelectedItem().toString();
        String miles_string = spinner_miles.getSelectedItem().toString();
        String pesos_string = spinner_pesos.getSelectedItem().toString();
        //Se necesita saber si es un producto ya puesto



        conn = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametro={nombre};
        String[] campos = {utilidades.CAMPO_CANTIDAD};

        try {
            Cursor cursor = db.query(utilidades.TABLA_PRODUCTO,campos, utilidades.CAMPO_NOMBRE+"=?", parametro, null, null, null );
            if(cursor.moveToFirst()){
                Toast.makeText(this, "Este producto ya existe, no puede añadirlo de nuevo", Toast.LENGTH_SHORT).show();

            }else{
                //Toast.makeText(this, "no existe ese producto", Toast.LENGTH_SHORT).show();
                //Asignacion de variables  adicionales, necesarias
                int precio_und;
                int totalproducto;
                int cantidad_int = Integer.parseInt(cantidad_string);
                int miles_int = Integer.parseInt(miles_string);
                int pesos_int = Integer.parseInt(pesos_string);

                if (!cincuenta.isChecked()) {
                    precio_und = miles_int + pesos_int;
                } else {
                    precio_und = miles_int + pesos_int + 50;
                }
                totalproducto = precio_und * cantidad_int;

                //condicional evaluar datos ingresados
                if (!nombre.isEmpty()) {
                    //Abriendo base de datos
                    AdminSQLiteOpenHelper usuario = new AdminSQLiteOpenHelper(this, "productos", null, 1);
                    //Incicando que se usara para escribir
                    SQLiteDatabase basededatos1 = usuario.getWritableDatabase();
                    //Creacion del objeto que guardara los datos en la base de datos
                    ContentValues adicion = new ContentValues();
                    adicion.put(utilidades.CAMPO_NOMBRE, nombre);
                    adicion.put(utilidades.CAMPO_CANTIDAD, cantidad_int);
                    adicion.put(utilidades.CAMPO_PRECIOUND, precio_und);
                    adicion.put(utilidades.CAMPO_PRECIOTOT, totalproducto);
                    adicion.put(utilidades.CAMPO_EVALUADOR, 0);

                    //Insertando datos y cerrando base de datos
                    basededatos1.insert(utilidades.TABLA_PRODUCTO, null, adicion);
                    basededatos1.close();
                    Toast.makeText(this, nombre + " agregado", Toast.LENGTH_SHORT).show();

                    String total_string = String.valueOf(totalproducto);

                    // Enviando coste para calcular precio final
                    Intent miintent = new Intent(this, Listaprincipal.class);
                    miintent.putExtra("dato", total_string);
                    startActivity(miintent);
                    finish();
                } else {
                    //Snackbar si esta vaci el campo
                    View view2 = findViewById(R.id.aviso_informacion);
                    snackbar2 = Snackbar.make(view2, "Debes llenar todos los campos", Snackbar.LENGTH_LONG);
                    snackbar2.setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(Color.YELLOW);
                    snackbar2.setDuration(3500);
                    snackbar2.show();
                }
            }

            cursor.close();
        }catch (Exception e){
            Toast.makeText(this, "no existe", Toast.LENGTH_SHORT).show();
        }


        }else{
            Toast.makeText(this, "Para poder agregar mas productos es necesario instalar la version completa", Toast.LENGTH_LONG).show();
            mdialog = new Dialog(this);
            mdialog.setContentView(R.layout.popup);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mdialog.show();
        }


    }

}