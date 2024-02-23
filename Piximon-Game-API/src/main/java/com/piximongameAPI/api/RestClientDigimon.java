package com.piximongameAPI.api;

import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
Vamos a consumir una api de Digimons que hay en internet.
Con esta clase se pretende realizar una consulta HTTP (una petición) a la API de Digimon
para obtener la información de los Digimons y almacenarla en la base de datos.
Esta información serán los nombres y las fotos todos de los digimons, que utilizaremos
para poder crear las cartas de los jugadores.
 */
@Service
public class RestClientDigimon {

    private static IAPIServiceDigimon apiInstance;
    private static final String BASE_URL = "https://digimon-api.vercel.app/api/"; //url de la api de digimons
    //para conectarnos a nuestra api y obtener los digimon http://localhost:8082/digimons/recuperar
    //para obtener el listado de digimons http://localhost:8082/digimons/listarDigimons

    /*
    Para insertar un objeto digimon en la base de datos desde postman
    Elegimos POST y raw
     {
        "idDigimon": 1,
        "name": "Koromon",
        "img": "https://digimon.shadowsmith.com/img/koromon.jpg"
    }
     */

    private RestClientDigimon() {
    }

    public synchronized static IAPIServiceDigimon getApiServiceInstance() {
        if (apiInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInstance = retrofit.create(IAPIServiceDigimon.class);
        }
        return apiInstance;
    }




}
