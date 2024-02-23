package com.piximongameAPI.Controlador;

import com.piximongameAPI.Entidades.Carta;
import com.piximongameAPI.Repositorios.RepositorioCarta;
import com.piximongameAPI.Servicios.ServicioCarta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartas")
public class ControladorCarta {

    @Autowired
    private ServicioCarta servicioCarta;

    //Método para obtener todas las cartas de la bbdd
    @GetMapping("/getCartas")
    public List<Carta> listarCartas() {
        List<Carta> cartas = servicioCarta.obtenerCartas();
        return cartas;
    }

    //Método para obtener las cartas de un jugador concreto
    @GetMapping("/obtenerCartasJugador/{id}")
    public List<Carta> obtenerCartasJugador(@PathVariable int id) {
        System.out.println("ENTRÓ EN LA LLAMADA AL ENDPONT OBTENER CARTAS JUGADOR");
    List<Carta> cartas = servicioCarta.obtenerCartasJugador(id);
    return cartas;
    }

   

    //Método para obtener las cartaas no asignadas a ningún jugador (para el mercado)
    @GetMapping("/obtenerCartasSinAsignar")
    List<Carta> obtenerCartasSinAsignar() {
        return servicioCarta.obtenerCartasSinAsignar();
    }

    @GetMapping("/obtenerCartasAlineadasDeUnJugador/{id}")
    public List<Carta> obtenerCartasEnAlineacion(@PathVariable int id) {
        List<Carta> cartas = servicioCarta.recuperarCartasAlineadasDeJugador(id);
        return cartas;
    }
}
