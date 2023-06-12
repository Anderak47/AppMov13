package com.example.practica;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practica.Entitis.Pokemon;
import com.example.practica.R;
import com.example.practica.Service.PokemonService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearPokemonActivity extends AppCompatActivity {
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private static final int REQUEST_CAMERA = 1;
    String urlImage = "";

    Pokemon pokemon = new Pokemon();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pokemon);

        EditText ediname = findViewById(R.id.ediNombre);
        EditText ediTipo = findViewById(R.id.ediTipo);
        Button crear = findViewById(R.id.btnCrear);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://647788dc9233e82dd53bd0e9.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PokemonService service = retrofit.create(PokemonService.class);

                pokemon.nombre = String.valueOf(ediname.getText());
                pokemon.tipo = String.valueOf(ediTipo.getText());
                String url = "https://demo-upn.bit2bittest.com/" + urlImage;
                pokemon.foto = url;
                // Llamar al servicio para guardar el nuevo usuario

                Call<Pokemon> call = service.create(pokemon);
                call.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        if (response.isSuccessful()) {


                        } else {
                            // Manejar el error en caso de que no se haya podido guardar el usuario
                        }
                    }
                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        // Manejar el error de la llamada al servicio
                    }
                });
                Toast.makeText(getApplicationContext(), "Pokemon Guardado", Toast.LENGTH_SHORT).show();
                //limpiar datos
                ediname.setText("");
                ediTipo.setText("");
            }
        });
        Button tomarFoto = findViewById(R.id.btnCamara);
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleOpenCamera();
            }
        });

        Button fotoGaleria = findViewById(R.id.btnGaleria);
        fotoGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                else {
                    String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 2000);
                }
            }
        });
    }
    private void handleOpenCamera() {
        if(checkSelfPermission(android.Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED)
        {
            // abrir camara
            Log.i("MAIN_APP", "Tiene permisos para abrir la camara");
            abrirCamara();
        } else {
            // solicitar el permiso
            Log.i("MAIN_APP", "No tiene permisos para abrir la camara, solicitando");
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);
            handleOpenCamera();
        }
    }
    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            // Convierte el bitmap a una cadena Base64
            String base64Image = convertBitmapToBase64(bitmap);

            // Resto del código para enviar y guardar la imagen
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://demo-upn.bit2bittest.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PokemonService services = retrofit.create(PokemonService.class);
            Call<PokemonService.ImageResponse> call = services.saveImage(new PokemonService.ImageToSave(base64Image));
            call.enqueue(new Callback<PokemonService.ImageResponse>() {
                @Override
                public void onResponse(Call<PokemonService.ImageResponse> call, Response<PokemonService.ImageResponse> response) {
                    if (response.isSuccessful()) {
                        PokemonService.ImageResponse imageResponse = response.body();
                        urlImage = imageResponse.getUrl();
                        Log.e("APPURL", urlImage);
                    } else {
                        Log.e("Error cargar imagen", response.toString());
                    }
                }

                @Override
                public void onFailure(Call<PokemonService.ImageResponse> call, Throwable t) {
                    // Error de red o de la API
                    Log.i("Respuesta inactiva", "");
                }
            });
        }
        //galeria
        if(requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK && data !=null){
            Uri selectedImageUri = data.getData();

            // Obtener la ruta del archivo de imagen a partir de la URI
            String imagePath = getPathFromUri(selectedImageUri);
            if (imagePath != null) {
                // Cargar la imagen desde el archivo en un objeto Bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                if (bitmap != null) {
                    // Convierte el bitmap a una cadena Base64
                    String base64Image = convertBitmapToBase64(bitmap);
                    // Resto del código para enviar y guardar la imagen
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://demo-upn.bit2bittest.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    PokemonService services = retrofit.create(PokemonService.class);
                    Call<PokemonService.ImageResponse> call = services.saveImage(new PokemonService.ImageToSave(base64Image));
                    call.enqueue(new Callback<PokemonService.ImageResponse>() {
                        @Override
                        public void onResponse(Call<PokemonService.ImageResponse> call, Response<PokemonService.ImageResponse> response) {
                            if (response.isSuccessful()) {
                                PokemonService.ImageResponse imageResponse = response.body();
                                urlImage = imageResponse.getUrl();
                                Log.e("NewImageUrl", urlImage);

                                // Después de obtener la nueva URL, puedes continuar con el proceso de guardar el usuario en el mock API
                                // Aquí puedes llamar al servicio mock API y enviar la nueva URL como parte de los datos del usuario
                                pokemon.foto = urlImage;
                                // Resto del código para guardar el usuario en el mock API
                            } else {
                                Log.e("Error cargar imagen", response.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<PokemonService.ImageResponse> call, Throwable t) {
                            // Error de red o de la API
                            Log.e("API Failure", t.getMessage());
                        }
                    });
                }
            }
        }

    }
    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST);
    }
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return null;
    }
}