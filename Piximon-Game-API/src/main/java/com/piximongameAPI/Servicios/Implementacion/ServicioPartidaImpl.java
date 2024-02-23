package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.Jugador;
//import com.piximongameAPI.Entidades.Combate;
import com.piximongameAPI.Entidades.Partida;
import com.piximongameAPI.Repositorios.RepositorioPartida;
import com.piximongameAPI.Servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPartidaImpl implements ServicioPartida {

    @Autowired
    private RepositorioPartida repositorioPartida;

    @Override
    public List<Partida> obtenerTodas() {
        return repositorioPartida.findAll();
    }

    @Override
    public Partida obtenerPartidaPorId(int id) {
        return repositorioPartida.findById(id);
    }

    @Override
    public Partida obtenerPartidaActual() {
        return repositorioPartida.obtenerPartidaActual();
    }

    @Override
    public Partida crearPartida(Partida partida) {
        return repositorioPartida.findById(repositorioPartida.save(partida).getId());
    }

}
