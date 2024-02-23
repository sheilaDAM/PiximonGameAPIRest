package com.piximongameAPI.api;

import com.piximongameAPI.Entidades.Digimon;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

//Esta interfaz define los métodos que se utilizarán para hacer las peticiones a la API
public interface IAPIServiceDigimon {

    @GET("digimon")
    public Call<List<Digimon>> obtenerDigimons();

}
