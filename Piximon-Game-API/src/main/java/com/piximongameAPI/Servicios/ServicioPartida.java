package com.piximongameAPI.Servicios;

import com.piximongameAPI.Entidades.Jugador;
//import com.piximongameAPI.Entidades.Combate;
import com.piximongameAPI.Entidades.Partida;

import java.util.List;

public interface ServicioPartida {

    List<Partida> obtenerTodas();

    Partida obtenerPartidaPorId(int id);

    Partida obtenerPartidaActual();

    Partida crearPartida(Partida partida);

}
