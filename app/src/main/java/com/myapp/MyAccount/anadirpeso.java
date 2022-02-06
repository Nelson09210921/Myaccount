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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class anadirpeso extends AppCompatActivity {

    Snackbar snackbar2;
    private EditText et_nombre, pesokilo, pesoneto;
    private boolean instalada;
    AdminSQLiteOpenHelper conn;
    Dialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadirpeso);

        et_nombre = (EditText) findViewById(R.id.nombrepeso);
        pesokilo = (EditText) findViewById(R.id.pesokilomod);
        pesoneto = (EditText) findViewById(R.id.pesonetomod);

        SharedPreferences instaladas = getSharedPreferences("instaladas4", Context.MODE_PRIVATE);
        instalada = instaladas.getBoolean("numero", false);

        if(!instalada){
            new MaterialTapTargetPrompt.Builder(this)
                    .setTarget(pesokilo)
                    .setPrimaryText("Peso por kilo")
                    .setSecondaryText("Aqui pondras el precio por kilo, (Es el que tiene el supermercado).\n\n\nOK.")
                    .setPromptFocal(new RectanglePromptFocal())
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                            if(state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED){
                                new MaterialTapTargetPrompt.Builder(anadirpeso.this)
                                        .setTarget(pesoneto)
                                        .setPrimaryText("Peso neto")
                                        .setSecondaryText("Y en este lugar podras colocar el peso del producto que deseas llevar, recuerda ponerlo en gramos.\n\n\nOK.")
                                        .setPromptFocal(new RectanglePromptFocal())
                                        .show();
                                SharedPreferences instaladaaa = getSharedPreferences("instaladas4", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = instaladaaa.edit();
                                editor.putBoolean("numero", true);
                                editor.commit();
                            }
                        }
                    }).show();
        }
    }

    public void agregarpeso(View view){
        int contador = getIntent().getIntExtra("contadorpremium", 0);
        //Toast.makeText(this, String.valueOf(contador), Toast.LENGTH_SHORT).show();

        String nombre_string = et_nombre.getText().toString();
        String pesokilo_string = pesokilo.getText().toString();
        String pesoneto_string = pesoneto.getText().toString();

        if(!nombre_string.isEmpty() && !pesokilo_string.isEmpty() && !pesoneto_string.isEmpty()){
            if(contador <30){


                conn = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
                SQLiteDatabase data = conn.getReadableDatabase();
                String[] parametros = {nombre_string};
                String[] campos = {utilidades.CAMPO_CANTIDAD};

                try{
                    Cursor cursor = data.query(utilidades.TABLA_PRODUCTO, campos, utilidades.CAMPO_NOMBRE+"=?", parametros, null, null, null);
                    if(cursor.moveToFirst()){
                        Toast.makeText(this, "Este producto ya existe, no puede añadirlo de nuevo", Toast.LENGTH_SHORT).show();
                    }else{

                        Float pesokilo_float = Float.parseFloat(pesokilo_string);
                        Float pesoneto_float = Float.parseFloat(pesoneto_string);
                        int pesoneto_int = Integer.parseInt(pesoneto_string);



                        Float preciotot_float = pesokilo_float * (pesoneto_float/1000);
                        int preciotot_int = Math.round(preciotot_float);
                        int pesokilo_int = Math.round(pesokilo_float);

                        //Se abre base de datos
                        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
                        SQLiteDatabase basededatos = db.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(utilidades.CAMPO_NOMBRE, nombre_string);
                        values.put(utilidades.CAMPO_CANTIDAD, pesoneto_int);
                        values.put(utilidades.CAMPO_PRECIOUND, pesokilo_int);
                        values.put(utilidades.CAMPO_PRECIOTOT, preciotot_int);
                        values.put(utilidades.CAMPO_EVALUADOR, 1);

                        basededatos.insert(utilidades.TABLA_PRODUCTO, null, values);
                        basededatos.close();
                        Toast.makeText(this,nombre_string + " agregado", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(anadirpeso.this, Listaprincipal.class);
                        intent.putExtra("dato4", preciotot_int);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(this, "no existe", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Para poder agregar mas productos es necesario instalar la version completa", Toast.LENGTH_SHORT).show();
                mdialog = new Dialog(this);
                mdialog.setContentView(R.layout.popup);
                mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mdialog.show();
            }


        }else{
            View view2 = findViewById(R.id.aviso_informacionn);
            snackbar2 = Snackbar.make(view2, "Debes llenar todos los campos", Snackbar.LENGTH_LONG);
            snackbar2.setAction("Ok", new View.OnClickListener(){
                @Override
                public void onClick(View view){

                }
            }).setActionTextColor(Color.YELLOW);
            snackbar2.setDuration(3500);
            snackbar2.show();

        }
    }
}