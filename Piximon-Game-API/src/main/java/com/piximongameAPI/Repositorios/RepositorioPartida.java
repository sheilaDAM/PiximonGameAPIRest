package com.piximongameAPI.Repositorios;


import com.piximongameAPI.Entidades.Jugador;
import com.piximongameAPI.Entidades.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioPartida extends JpaRepository<Partida, Integer> {

    //Método para obtener la partida por id concreto usando repositorio Jpa
    @Query(value = "SELECT * FROM partida WHERE id = :id", nativeQuery = true)
    Partida findById(int id);

   //Método para obtener la partida actual, que será siempre la última que se crea con el login del usuario
     @Query(value = "SELECT p.id FROM partidas p ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
     Partida obtenerPartidaActual();



}
