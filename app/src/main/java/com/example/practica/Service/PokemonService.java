package com.example.practica.Service;

import com.example.practica.Entitis.Pokemon;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PokemonService {
    @GET("pokemon")
    Call<List<Pokemon>> getAllUser();

    // /users/1
    @GET("pokemon/{id}")
    Call<Pokemon> findUser(@Path("id") int id);
    // users
    @POST("pokemon")
    Call<Pokemon> create(@Body Pokemon pokemon);

    @PUT("pokemon/{id}")
    Call<Pokemon> update(@Path("id") int id, @Body Pokemon pokemon);

    @DELETE("pokemon/{id}")
    Call<Void> delete(@Path("id") int id);

    @POST("image")
    Call<ImageResponse> saveImage(@Body ImageToSave image);

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
