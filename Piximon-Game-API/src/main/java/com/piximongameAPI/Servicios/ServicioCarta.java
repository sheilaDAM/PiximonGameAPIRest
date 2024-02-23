package com.piximongameAPI.Servicios;

import com.piximongameAPI.Entidades.Carta;
//import com.piximongameAPI.Entidades.Combate;
import com.piximongameAPI.Entidades.Partida;

import java.util.List;

public interface ServicioCarta {

    List<Carta> obtenerCartas();

    List<Carta> obtenerCartasJugador(int id);

    Carta obtenerPorId(int id);

    List<Carta> crearCartas(Partida partida);

    List<Carta> obtenerCartasSinAsignar();
    List<Carta> recuperarCartasAlineadasDeJugador(int id);



}
