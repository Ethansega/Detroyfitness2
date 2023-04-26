package com.example.detroyfitness2;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);

        //inputs declarados
        EditText introduceUsername_registrar = findViewById(R.id.IntroduceUsername_registrar);
        EditText introducePWD_registrar = findViewById(R.id.IntroducePWD_registrar);
        EditText introduceconfirmPWD = findViewById(R.id.IntroduceconfirmPWD);
        EditText introduceEmail = findViewById(R.id.IntroduceEmail);

        //botones declarados
        Button b_crearCuenta = findViewById(R.id.B_crearCuenta);
        Button buttonvolver = findViewById(R.id.buttonvolver_login);

        buttonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Registrarse.this, MainActivity.class);
                startActivity(intent);
                // Aquí puedes agregar el código para navegar a la pantalla de inicio de sesión
            }
        });

        b_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //guardar variables
                String email = introduceEmail.getText().toString().trim();
                String password = introducePWD_registrar.getText().toString().trim();
                String confirmpassword = introduceconfirmPWD.getText().toString().trim();
                String username = introduceUsername_registrar.getText().toString();

                //funcion para introducion info en la database
                crearusuario( email,password,confirmpassword, username);

            }
        });




    }
    public void crearusuario(String email, String password, String confirmpassword, String username){


        if(password.equals(confirmpassword) ) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // El registro se realizó con éxito
                                FirebaseUser user = mAuth.getCurrentUser();

                                //Creamos documento e insertamos usuario

                                // Se crea un objeto Map con el valor del username
                                Map<String, Object> data = new HashMap<>();
                                data.put("username", username);
                                data.put("survey", false);

                                // Se inserta el Map en Firestore
                                db.collection("usuarios").document(user.getEmail().toString()).set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // La operación de inserción fue exitosa

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println(e);
                                                // Ocurrió un error al intentar insertar los datos
                                            }
                                        });

                                Intent intent = new Intent(Registrarse.this, Questionario1.class);
                                startActivity(intent);
                                // Puedes guardar los datos del usuario en Firestore aquí


                            } else {
                                // El registro falló
                                Toast.makeText(Registrarse.this, "Error al registrar el usuario: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{

        }


    }



}
