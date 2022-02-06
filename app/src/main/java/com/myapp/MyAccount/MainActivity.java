package com.myapp.MyAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class MainActivity extends AppCompatActivity {

    private boolean instalada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences instaladas = getSharedPreferences("instaladas1", Context.MODE_PRIVATE);
        instalada = instaladas.getBoolean("numero", false);

        if(instalada == false){
            SharedPreferences instaladass = getSharedPreferences("instaladas1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = instaladass.edit();
            editor.putBoolean("numero", true);
            editor.commit();

            new MaterialTapTargetPrompt.Builder(this)
                    .setTarget(R.id.button)
                    .setPrimaryText("Bienvenido ")
                    .setSecondaryText("pulsa (Mi lista) para empezar, te recomendamos leer estas notas para aprender a usar esta app correctamente.")
                    .setPromptFocal(new RectanglePromptFocal())
                    .show();
        }

    }
    //Implementacion iniciar lista
    public void milista(View view){
        Intent lista = new Intent(this, Listaprincipal.class);
        startActivity(lista);
    }
}
