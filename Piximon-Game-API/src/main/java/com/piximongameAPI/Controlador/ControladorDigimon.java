package com.piximongameAPI.Controlador;

import com.piximongameAPI.Entidades.Digimon;
import com.piximongameAPI.Entidades.ResponseStatus;
import com.piximongameAPI.Repositorios.RepositorioDigimon;
import com.piximongameAPI.api.IAPIServiceDigimon;
import com.piximongameAPI.api.RestClientDigimon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Callback;

import java.util.List;

@RestController
@RequestMapping("/digimons")
public class ControladorDigimon {
    @Autowired
    private RestClientDigimon apiRestClient;

    @Autowired
    private RepositorioDigimon repositorioDigimon;

    @GetMapping("/listarDigimons")
    public List<Digimon> listarDigimons() {
        List<Digimon> digimons = repositorioDigimon.findAll();
        return digimons;
    }

    //---------- Método para recuperar los digimons de la API y guardarlos en la base de datos -------
    @GetMapping("/recuperar")
    public void recuperarDigimons() {
        IAPIServiceDigimon apiService = RestClientDigimon.getApiServiceInstance();
        apiService.obtenerDigimons().enqueue(new Callback<List<Digimon>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Digimon>> call, retrofit2.Response<List<Digimon>> response) {
                List<Digimon> digimons = (List<Digimon>) response.body();
                System.out.println("Número de Digimons obtenidos: " + digimons.size());
                repositorioDigimon.saveAll(digimons);
            }

            @Override
            public void onFailure(retrofit2.Call<List<Digimon>> call, Throwable throwable) {
                System.out.println("Error al recuperar los digimons");
            }
        });
    }

    //MÉTODO PARA COMPROBAR SI YA HAY DIGIMONS EN LA BBDD, SI LOS HAY, NO SE VUELVEN A INSERATR Y SI NO HAY LOS RECUPERAMOS DE LA API
    @PostMapping("/comprobarDigimonsEnBBDD")
    public ResponseStatus comprobarDigimonsEnBBDD() {
        List<Digimon> digimonsEnBBDD = repositorioDigimon.findAll();
        if (digimonsEnBBDD.isEmpty()) {
            IAPIServiceDigimon apiService = RestClientDigimon.getApiServiceInstance();
            apiService.obtenerDigimons().enqueue(new Callback<List<Digimon>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Digimon>> call, retrofit2.Response<List<Digimon>> response) {
                    List<Digimon> digimons = (List<Digimon>) response.body();
                    System.out.println("Número de Digimons obtenidos: " + digimons.size());
                    repositorioDigimon.saveAll(digimons);
                }
                @Override
                public void onFailure(retrofit2.Call<List<Digimon>> call, Throwable throwable) {
                    System.out.println("Error al recuperar los digimons");
                }
            });
            return new ResponseStatus(ResponseStatus.TipoCodigo.DIGIMONS_INSERTADOS);
        } else {
            return new ResponseStatus(ResponseStatus.TipoCodigo.DIGIMONS_YA_EXISTEN);
        }
    }
}
