package com.myapp.MyAccount;
/*
Jueves 13 de mayo
Nelson Andres Barboza Landine<
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;



import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class Listaprincipal extends AppCompatActivity {
    //Declaracion de variable necesarias
    private int costeacumulado_float, costeparcial_float, dato_float;
    private FloatingActionButton add;
    private TextView tv_costo;
    private String dato_string;
    RecyclerView recycler_view;
    ArrayList<Producto> listaproducto;
    AdminSQLiteOpenHelper conn;
    Producto nombreproducto, evaluador;
    private FloatingActionMenu actionmenu;
    boolean instalada;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaprincipal);

        SharedPreferences instaladas = getSharedPreferences("instaladas2", Context.MODE_PRIVATE);
        instalada = instaladas.getBoolean("numero", false);


        //Cuando viene de la activity agregar
        String datoforaneo = getIntent().getStringExtra("dato");
        //condicional para saber si es la primera vez que se añaden datos
        if (datoforaneo != null) {
            costeparcial_float = Integer.parseInt(datoforaneo);

            SharedPreferences text0 = getSharedPreferences("datos", Context.MODE_PRIVATE);
            costeacumulado_float = text0.getInt("data", 0);

            //suma de datos
            costeacumulado_float = costeacumulado_float + costeparcial_float;
            //Almacenando coste acumulado para que no se borre al cerrar app
            SharedPreferences texto = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = texto.edit();
            editor.putInt("data", costeacumulado_float);
            editor.commit();

        }
        //Recepcion de variables foraneas
        int valorrestar = getIntent().getIntExtra("dato2", 0);
        int valorsumar = getIntent().getIntExtra("dato3", 0);

        //Evaluar si hay un cambio, modificar o eliminar dato por und
        if (valorrestar != 0 || valorsumar != 0) {
            //Se guarda en coste acumulado lo que tenga almacenado el share preferees
            SharedPreferences text0 = getSharedPreferences("datos", Context.MODE_PRIVATE);
            costeacumulado_float = text0.getInt("data", 0);

            //Se hacen cambios respectivos y se guarda nuevamente
            costeacumulado_float = (costeacumulado_float - valorrestar) + valorsumar;
            SharedPreferences texto = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = texto.edit();
            editor.putInt("data", costeacumulado_float);
            editor.commit();
        }


        //Agregar por peso
        int preciopeso = getIntent().getIntExtra("dato4", 0);

        if (preciopeso != 0) {
            //Guardar lo almacenado en costeacumulado
            SharedPreferences text = getSharedPreferences("datos", Context.MODE_PRIVATE);
            costeacumulado_float = text.getInt("data", 0);

            //cambios y guardar nuevas variables
            costeacumulado_float = costeacumulado_float + preciopeso;
            SharedPreferences texto = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = texto.edit();
            editor.putInt("data", costeacumulado_float);
            editor.commit();
        }


        //Eliminar y modificar por peso
        int valorrestarpeso = getIntent().getIntExtra("dato5", 0);
        int valorsumarpeso = getIntent().getIntExtra("dato6", 0);

        if (valorrestarpeso != 0 || valorsumarpeso != 0) {
            SharedPreferences textoo = getSharedPreferences("datos", Context.MODE_PRIVATE);
            costeacumulado_float = textoo.getInt("data", 0);
            //cambios y guardar nuevas variables
            costeacumulado_float = (costeacumulado_float - valorrestarpeso) + valorsumarpeso;
            SharedPreferences textoooo = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = textoooo.edit();
            editor.putInt("data", costeacumulado_float);
            editor.commit();
        }


        //Se muestra el coste de productos
        tv_costo = (TextView) findViewById(R.id.tw_costetotal);
        SharedPreferences text = getSharedPreferences("datos", Context.MODE_PRIVATE);
        dato_float = text.getInt("data", 0);
        dato_string = String.valueOf(dato_float);
        tv_costo.setText(dato_string);


        actionmenu = (FloatingActionMenu) findViewById(R.id.principal);
        actionmenu.setClosedOnTouchOutside(true);

        if (instalada == false) {


            new MaterialTapTargetPrompt.Builder(this)
                    .setTarget(tv_costo)
                    .setPrimaryText("Coste total")
                    .setSecondaryText("Este numero te indicara cual es el valor de tu cuenta, según lo que hayas agregado.")
                    .setPromptFocal(new RectanglePromptFocal())
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                                new MaterialTapTargetPrompt.Builder(Listaprincipal.this)
                                        .setTarget(R.id.submenu2)
                                        .setPrimaryText("Añadir por unidades")
                                        .setSecondaryText("Este boton te servira para agregar productos, por unidades.(Arroz, aceite, jabon, etc.)")
                                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                            @Override
                                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHED) {
                                                    new MaterialTapTargetPrompt.Builder(Listaprincipal.this)
                                                            .setTarget(R.id.submenu1)
                                                            .setPrimaryText("Añadir por peso")
                                                            .setSecondaryText("Al presionar este podras agregar productos los cuales necesitan ser pesados.(Naranja, tomate, papa, etc).")
                                                            .show();
                                                    SharedPreferences instaladaaa = getSharedPreferences("instaladas2", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = instaladaaa.edit();
                                                    editor.putBoolean("numero", true);
                                                    editor.commit();
                                                }
                                            }
                                        }).show();
                            }
                        }
                    })
                    .show();


        }




        /*
        //Relacion e implementacion boton flotante añadir
        add = (FloatingActionButton)findViewById(R.id.flotanteadd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent anadir = new Intent(Listaprincipal.this, anadir.class);
                    startActivity(anadir);
                    finish();

            }
        });

         */

        //Se abre base de datos para mostrarlos con recycler view
        conn = new AdminSQLiteOpenHelper(getApplicationContext(), "productos", null, 1);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        listaproducto = new ArrayList<>();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        consultarlistapersonar();


        //Modificar o eliminar tocando el elemento
        Listaproductosadapter adapter = new Listaproductosadapter(listaproducto);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreproducto = listaproducto.get(recycler_view.getChildAdapterPosition(view));
                String nombre = nombreproducto.getNombre();
                int preciokilo = nombreproducto.getPreciound();
                int pesoneto = nombreproducto.getCantidad();
                int preciototal = nombreproducto.getPreciotot();

                evaluador = listaproducto.get(recycler_view.getChildAdapterPosition(view));
                int evaluadorof = evaluador.getEvaluador();


                if (evaluadorof == 0) {
                    //Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_SHORT).show();
                    Intent modificar = new Intent(Listaprincipal.this, Modificar.class);
                    modificar.putExtra("nombreforaneo", nombre);
                    startActivity(modificar);
                    finish();
                } else if (evaluadorof == 1) {
                    //enviar a nueva actividad
                    Intent enviar = new Intent(Listaprincipal.this, modificarpeso.class);
                    enviar.putExtra("nombreforaneo", nombre);
                    enviar.putExtra("preciokiloforaneo", preciokilo);
                    enviar.putExtra("pesonetoforaneo", pesoneto);
                    enviar.putExtra("totalforaneo", preciototal);
                    startActivity(enviar);
                    finish();
                }

            }
        });
        recycler_view.setAdapter(adapter);
        /*
        Listaproductosadapter adaptador = new Listaproductosadapter(listaproducto);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                listaproducto.remove(viewHolder.getAdapterPosition());
                adaptador.notifyItemRemoved(viewHolder.getAdapterPosition());
                nombreproducto = listaproducto.get(recycler_view.getChildAdapterPosition(viewHolder.itemView));
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycler_view);
        //falta eliminar de la base de datos y del coste


         */


    }


    //Metodo para consultar los productos existentes
    private void consultarlistapersonar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Producto producto = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + utilidades.TABLA_PRODUCTO, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                producto = new Producto();
                producto.setCantidad(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setPreciound(cursor.getInt(2));
                producto.setPreciotot(cursor.getInt(3));
                producto.setEvaluador(cursor.getInt(4));
                listaproducto.add(producto);

            }
            int contador = 0;
            contador = cursor.getCount();
            String contadorstring = String.valueOf(contador);
            //Toast.makeText(this, contadorstring, Toast.LENGTH_LONG).show();
            Intent enviar = new Intent(Listaprincipal.this, anadir.class);


        }

    }

    private int consultarlistapersonarretornar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Producto producto = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + utilidades.TABLA_PRODUCTO, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            while (cursor.moveToNext()) {
                producto = new Producto();
                producto.setCantidad(cursor.getInt(0));
                producto.setNombre(cursor.getString(1));
                producto.setPreciound(cursor.getInt(2));
                producto.setPreciotot(cursor.getInt(3));
                producto.setEvaluador(cursor.getInt(4));
                listaproducto.add(producto);

            }

            int contador = 0;
            contador = cursor.getCount();
            String contadorstring = String.valueOf(contador);
            //Toast.makeText(this, contadorstring, Toast.LENGTH_LONG).show();
            Intent enviar = new Intent(Listaprincipal.this, anadir.class);
            return contador;


        }

    }






    public void enviar1(View view) {
        Intent anadir = new Intent(Listaprincipal.this, anadir.class);
        int contador = consultarlistapersonarretornar();
        anadir.putExtra("contadorpremium", contador);
        startActivity(anadir);
        finish();

    }
    public void enviar2(View view){
        Intent anadir = new Intent(Listaprincipal.this, anadirpeso.class);
        int contador = consultarlistapersonarretornar();
        anadir.putExtra("contadorpremium", contador);
        startActivity(anadir);
        finish();
    }
}