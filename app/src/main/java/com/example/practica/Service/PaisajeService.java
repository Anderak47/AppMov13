package com.example.practica.Service;

import com.example.practica.Entitis.Coordenadas;
import com.example.practica.Entitis.Paisaje;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaisajeService {
    @GET("pokemon")
    Call<List<Paisaje>> getAllUser();

    // /users/1
    @GET("pokemon/{id}")
    Call<Paisaje> findUser(@Path("id") int id);
    // users
    @POST("pokemon")
    Call<Paisaje> create(@Body Paisaje paisaje);

    @PUT("pokemon/{id}")
    Call<Paisaje> update(@Path("id") int id, @Body Paisaje paisaje);

    @DELETE("pokemon/{id}")
    Call<Void> delete(@Path("id") int id);

    @POST("image")
    Call<ImageResponse> saveImage(@Body ImageToSave image);

    //para coordenadas
    @GET("pokemon/{id}")
    Call<Coordenadas> getCoordenadas (@Path("id") int id);

    class ImageResponse {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }
    class ImageToSave {
        String base64Image;

        public ImageToSave(String base64Image) {
            this.base64Image = base64Image;
        }
    }
}
