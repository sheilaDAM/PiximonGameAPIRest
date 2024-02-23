package com.piximongameAPI.Servicios;

import com.piximongameAPI.Entidades.Jugador;

import java.util.List;

public interface ServicioJugador {

    //--------- Métodos de tipo CRUD personalizados (Create, Read, Update, Delete) ---------
    //List<Jugador> obtenerTodos();
    List<Jugador> obtenerTodosLosJugadores();

    Jugador obtenerPorId(int id);

    List<Jugador> crearJugadores(Jugador jugador);

    List<Jugador> findJugadoresByPartidaId(int id);

    Jugador comprobarSiExisteJugador(String nombre, int id_partida);
    List<Jugador> obtenerJugadoresAleatoriosEnPartida(int id);
    Jugador obtenerJugadorUsuarioEnPartida(int id);

    Jugador obtenerJugadorPorNombre(String nombre);


}
