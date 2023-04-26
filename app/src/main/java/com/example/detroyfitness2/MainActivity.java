package com.example.detroyfitness2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.detroyfitness2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MainActivity extends AppCompatActivity {
    //Declaracion de los objetos
    public RelativeLayout relativeLayout;
    public EditText introduceUsuario;
    public EditText introducePWD;
    public Button bLogin;
    public Button bRegistrar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);


        //layaot principal
        relativeLayout = findViewById(R.id.relativeLayout);

        //inputs del usuario
        introduceUsuario = findViewById(R.id.IntroduceUsuario);
        introducePWD = findViewById(R.id.IntroducePWD);

        //botones de la pantalla
        bLogin = findViewById(R.id.B_login);
        bRegistrar = findViewById(R.id.B_Registrar);

        //ejecucion de los botones
        bRegistrar.setOnClickListener(new View.OnClickListener() { //ejecucion del botón crear cuenta
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() { //ejecucion del botón login
            @Override
            public void onClick(View view) {

                String email = introduceUsuario.getText().toString().trim();
                String password = introducePWD.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    introduceUsuario.setError("Ingrese su correo electrónico");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    introducePWD.setError("Ingrese su contraseña");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //COMPROBACION DE SURVEY(SI HA HECHO EL QUESTIONARIO O NO)
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    DocumentReference docRef = db.collection("usuarios").document(user.getEmail());

                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                Boolean resul= documentSnapshot.getBoolean("survey"); // Almacenamos el username en el array
                                                if (!resul){
                                                    Intent intent = new Intent(MainActivity.this, Questionario1.class);
                                                    startActivity(intent);
                                                }else{
                                                    Intent intent = new Intent(MainActivity.this, Pagina_principal.class);
                                                    startActivity(intent);
                                                }
                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        }
                                    });
                                    //se cierra login
                                    finish();
                                } else {
                                    Toast.makeText( MainActivity.this, "No se pudo iniciar sesión, compruebe sus datos y vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });




    }







}
