package com.example.detroyfitness2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Questionario1 extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String username1 ;
    Map<String, Object> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionario);


        obtener_usuario();

        //Botones main
        Button boton_siguiente = findViewById(R.id.B_siguiente);

        //botones declarados toolbar
        Button botontoolbar_perfil = findViewById(R.id.B_perfil);
        Button botontoolbar_entrenamiento = findViewById(R.id.B_entrenamiento);
        Button botontoolbar_nutricion = findViewById(R.id.B_nutricion);

        //Opciones questionario spinner
        Spinner spinnerSexo = findViewById(R.id.spinner_sexo);
        Spinner spinnerActFis = findViewById(R.id.spinner_ActFis);

        //datos spinner genero
        String[] opcionesSexo = {"  ","Hombre", "Mujer", "Sin especificar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesSexo);
        spinnerSexo.setAdapter(adapter);


        String SexoSelected= "";
        String ACTFIS_Selected;

        //SPINNER SOBRE EL GENERO
        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               System.out.println("JKDNAWDNADNAW");
                data.put("sexo", (String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //DATOS SPINNER NIVEL ACTIVIDAD FISICA
        String[] opciones_ActFis = {"  ","Sedentario", "act ligera", "act moderada", "act intensa"};
        ArrayAdapter<String> adapter_ActFis = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones_ActFis);
        spinnerActFis.setAdapter(adapter_ActFis);

        //SPINNER SOBRE ACTIVIDAD FISICA
        spinnerActFis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data.put("ActFis", (String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("holiapkofawdoawiodnukawbnduiawhdhuawuh");
            }
        });


        //Declaración de los inputs
        EditText Introduce_fecha_nacimiento = findViewById(R.id.fecha_nacimiento);
        EditText Introduce_altura = findViewById(R.id.altura);
        EditText Introduce_peso = findViewById(R.id.peso);



        //ejecucion de los botones
        botontoolbar_perfil.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de perfil
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionario1.this, Perfil.class);
                startActivity(intent);
            }
        });

        botontoolbar_nutricion.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de nutricion
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionario1.this, Nutricion.class);
                startActivity(intent);
            }
        });

        botontoolbar_entrenamiento.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de entrenamiento
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Questionario1.this, Entrenamiento.class);
                startActivity(intent);
            }
        });

        boton_siguiente.setOnClickListener(new View.OnClickListener() { //ejecucion del botón siguiente
            @Override
            public void onClick(View view) {
                System.out.println(spinnerActFis);
                    //funcion donde meteremos toda la info que seleccione el usuario en el cuestionario a la base de datos
                    database_cuestionario(Introduce_fecha_nacimiento, Introduce_altura, Introduce_peso);
                    Intent intent = new Intent(Questionario1.this, Questionario2.class);
                    startActivity(intent);

            }
        });
    }
//----------------------------------------------------------------------------------------------------------------------------------
    public void obtener_usuario(){
        //textview declarado
        TextView username = findViewById(R.id.NombreUsuarioView);



        DocumentReference docRef = db.collection("usuarios").document(user.getEmail());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    username1= documentSnapshot.getString("username"); // Almacenamos el username en el array
                    username.setText(username1);
                    Log.d(TAG, "Username: " + username1);

                } else {
                    Log.d(TAG, "No such document");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error getting document: " + e.getMessage());
            }
        });

    }

//----------------------------------------------------------------------------------------------------------------------------------

    public void database_cuestionario(EditText Introduce_fechanacimiento, EditText Introduce_altura, EditText Introduce_peso){
        //EDIT TEXT PESO, ALTURA Y FECHA DE NACIMIENTO
        String fecha_nacimiento = Introduce_fechanacimiento.getText().toString().trim();
        String altura = Introduce_altura.getText().toString().trim();
        String peso = Introduce_peso.getText().toString().trim();

        //introducimos info

        data.put("fecha_nacimiento", fecha_nacimiento);
        data.put("altura", altura);
        data.put("peso", peso);

        // Se inserta el Map en Firestore
        db.collection("usuarios").document(user.getEmail().toString()).update(data);

    }
}
