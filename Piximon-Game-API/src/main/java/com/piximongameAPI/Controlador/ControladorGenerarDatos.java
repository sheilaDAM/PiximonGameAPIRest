package com.piximongameAPI.Controlador;

import com.piximongameAPI.Entidades.Jugador;
import com.piximongameAPI.Entidades.Partida;
import com.piximongameAPI.Entidades.ResponseStatus;
import com.piximongameAPI.Servicios.Implementacion.GenerarDatos;
import com.piximongameAPI.Servicios.Implementacion.ServicioJugadorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inicializador")
public class ControladorGenerarDatos {

    @Autowired
    private GenerarDatos generarDatos;

    @Autowired
    private ServicioJugadorImpl servicioJugador;

    @PostMapping("/generarDatos")
    public ResponseStatus generarDatos(@RequestBody Jugador jugador){
        try {
        //generarDatos.recuperarDigimons();
        Partida partidaActual = generarDatos.crearJugadores(jugador);
        generarDatos.crearCartas(partidaActual);
        generarDatos.asignarCartasAJugadores(servicioJugador.findJugadoresByPartidaId(partidaActual.getId()));
        generarDatos.generarAlineaciones();
        generarDatos.alinearCartas();
        return new ResponseStatus(ResponseStatus.TipoCodigo.INSERT_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseStatus(ResponseStatus.TipoCodigo.ERROR);
        }
    }
}
