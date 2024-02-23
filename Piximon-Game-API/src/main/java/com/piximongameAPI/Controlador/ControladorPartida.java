package com.piximongameAPI.Controlador;

import com.piximongameAPI.Entidades.Partida;
import com.piximongameAPI.Servicios.ServicioJugador;
import com.piximongameAPI.Servicios.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partida")
public class ControladorPartida {

    @Autowired
    private ServicioPartida servicioPartida;

    /*@GetMapping("/obtenerPartidaActual/{nombre}")
    Partida obtenerPartidaActual(@PathVariable String nombre) {
        return servicioPartida.obtenerPartidaActual(nombre);
    }*/

    @GetMapping("/obtenerPartidaActual")
    Partida obtenerPartidaActual() {
        return servicioPartida.obtenerPartidaActual();
    }
}
