package com.example.detroyfitness2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Nutricion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutricion);

        //Botones header
        ImageButton boton_atras = findViewById(R.id.flecha_atras);

        //Botones main
        ImageButton boton_evaluacioncorporal = findViewById(R.id.B_evaluacionCoporal);
        ImageButton boton_recetas = findViewById(R.id.B_recetas);
        ImageButton boton_plansemanal = findViewById(R.id.B_plansemanal);
        ImageButton boton_alimentos = findViewById(R.id.B_alimentos);

        //botones declarados toolbar
        Button botontoolbar_perfil = findViewById(R.id.B_perfil);
        Button botontoolbar_entrenamiento = findViewById(R.id.B_entrenamiento);
        Button botontoolbar_nutricion = findViewById(R.id.B_nutricion);

        //ejecucion de los botones
        botontoolbar_perfil.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de perfil
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nutricion.this, Perfil.class);
                startActivity(intent);
            }
        });

        botontoolbar_nutricion.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de nutricion
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nutricion.this, Nutricion.class);
                startActivity(intent);
            }
        });

        botontoolbar_entrenamiento.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de entrenamiento
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nutricion.this, Entrenamiento.class);
                startActivity(intent);
            }
        });

        boton_atras.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de atras
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nutricion.this, Pagina_principal.class);
                startActivity(intent);
            }
        });

        //botones de main para cambiar pantalla
        boton_evaluacioncorporal.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de evaluacio corporal
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nutricion.this, Questionario1.class);
                startActivity(intent);
            }
        });

        boton_recetas.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de recetas
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nutricion.this, Recetas.class);
                startActivity(intent);
            }
        });


    }
}
