package com.piximongameAPI.Controlador;

import com.piximongameAPI.Entidades.Jugador;
import com.piximongameAPI.Entidades.ResponseStatus;
import com.piximongameAPI.Repositorios.RepositorioJugador;
import com.piximongameAPI.Servicios.Implementacion.ServicioAlineacionImpl;
import com.piximongameAPI.Servicios.Implementacion.ServicioCartaImpl;
import com.piximongameAPI.Servicios.ServicioAlineacion;
import com.piximongameAPI.Servicios.ServicioJugador;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/jugadores")
public class ControladorJugador {
    @Autowired
    private ServicioJugador servicioJugador;

    @Autowired
    private ServicioCartaImpl servicioCarta;

    @Autowired
    private RepositorioJugador repositorioJugador;

    @Autowired
    private ServicioAlineacionImpl servicioAlineacion;

    @GetMapping("/getJugadores")
    public List<Jugador> listarJugadores() {
        try {
            List<Jugador> jugadores = servicioJugador.obtenerTodosLosJugadores();
            for(Jugador jugador : jugadores){
                System.out.println(jugador.getNombreJugador());
            }
            return servicioJugador.obtenerTodosLosJugadores();
        } catch (Exception e) {
            // Manejar la excepción aquí
            e.printStackTrace(); // Imprime el rastreo de la pila, puedes cambiar esto según tus necesidades
            // Puedes lanzar una excepción personalizada o devolver una respuesta de error si es necesario
            return Collections.emptyList(); // Devuelve una lista vacía en caso de error
        }
    }

    @GetMapping("/obtenerJugadorUsuarioEnPartida/{id}")
    public Jugador obtenerUsuarioJugador(@PathVariable int id) {
        try {
            System.out.println("partida id: " + id);
            Jugador jugador = servicioJugador.obtenerJugadorUsuarioEnPartida(id);
            System.out.println(jugador.getNombreJugador());
            return servicioJugador.obtenerJugadorUsuarioEnPartida(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve una lista vacía en caso de error
        }
    }

    @GetMapping("/obtenerJugadoresPorPartidaId/{id}")
    public List<Jugador> obtenerJugadoresPorPartidaId(@PathVariable int id) {
        try {
            List<Jugador> jugadores = servicioJugador.findJugadoresByPartidaId(id);
            for(Jugador jugador : jugadores){
                System.out.println(jugador.getNombreJugador());
            }
            return servicioJugador.findJugadoresByPartidaId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Devuelve una lista vacía en caso de error
        }
    }

    @GetMapping("/obtenerJugadoresAleatoriosEnPartida/{id}")
    public List<Jugador> obtenerJugadoresAleatoriosEnPartida(@PathVariable int id) {
        try {
            List<Jugador> jugadores = servicioJugador.obtenerJugadoresAleatoriosEnPartida(id);
            for(Jugador jugador : jugadores){
                System.out.println(jugador.getNombreJugador());
            }
            return servicioJugador.obtenerJugadoresAleatoriosEnPartida(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    @PostMapping("/addJugadores")
    public ResponseStatus addJugador(@RequestBody Jugador jugador) {
        System.out.println(jugador.toString());
        try {
            Jugador jugadorInsertado = jugador;
            System.out.println(jugadorInsertado.toString());
            //repositorioJugador.save(jugadorInsertado);
            servicioAlineacion.generarAlineaciones();
            servicioJugador.crearJugadores(jugador);
            //servicioCarta.alinearCartas();


            return new ResponseStatus(ResponseStatus.TipoCodigo.INSERT_OK);
        } catch (Exception e) {
            System.out.println("addJugador ERROR:" + e.getMessage());
            return new ResponseStatus(ResponseStatus.TipoCodigo.ERROR);
        }
    }
}
