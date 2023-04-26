package com.example.detroyfitness2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Perfil extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageRef = storage.getReference();
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ImageButton subirFotoPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);

        //Botones Header
        ImageButton boton_atras = findViewById(R.id.flecha_atras);

        //Botones main
        Button boton_cerrarsesion = findViewById(R.id.Cerrar_sesion);
         subirFotoPerfil = findViewById(R.id.Imagen_perfl);

        //botones declarados toolbar
        Button botontoolbar_perfil = findViewById(R.id.B_perfil);
        Button botontoolbar_entrenamiento = findViewById(R.id.B_entrenamiento);
        Button botontoolbar_nutricion = findViewById(R.id.B_nutricion);

        //ejecucion de los botones

        subirFotoPerfil.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de subir foto para cambiar perfil
            @Override
            public void onClick(View view) {
               SubirImg();


            }
        });


        botontoolbar_perfil.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de perfil
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Perfil.class);
                startActivity(intent);
            }
        });

        botontoolbar_nutricion.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de nutricion
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Nutricion.class);
                startActivity(intent);
            }
        });

        botontoolbar_entrenamiento.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de entrenamiento
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Entrenamiento.class);
                startActivity(intent);
            }
        });

        boton_atras.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de atras
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Pagina_principal.class);
                startActivity(intent);
            }
        });

        boton_cerrarsesion.setOnClickListener(new View.OnClickListener() { //ejecucion del botón de cerrar sesion
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Perfil.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void SubirImg(){
        // Crear una referencia a la imagen que se va a subir
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            // Subir la imagen al Firebase Storage
            StorageReference fileReference = mStorageRef.child("img_perfil/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // La imagen se ha subido correctamente
                    Toast.makeText(Perfil.this, "Imagen subida correctamente", Toast.LENGTH_SHORT).show();

                    // Obtener la URL de la imagen subida
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageUrl = uri.toString();

                            //Bitmap obtener_imagen = get_imagen(imageUrl);
                            //subirFotoPerfil.setImageBitmap(obtener_imagen);
                            // Actualizar la foto de perfil del usuario en la base de datos de Firebase

                        }
                    });
                }
            });
        }
        }

    }




