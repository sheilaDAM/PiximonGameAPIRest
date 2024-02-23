package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.*;
import com.piximongameAPI.Repositorios.*;
import com.piximongameAPI.Servicios.ServicioCarta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServicioCartaImpl implements ServicioCarta {


    @Autowired
    private RepositorioCarta repositorioCarta;
    @Autowired
    private RepositorioDigimon repositorioDigimon;
    @Autowired
    private RepositorioJugador repositorioJugador;

    @Autowired
    private RepositorioPartida repositorioPartida;

    @Autowired
    private RepositorioAlineacion repositorioAlineacion;

    @Override
    public List<Carta> obtenerCartas() {
        return repositorioCarta.findAll();
    }

    @Override
    public List<Carta> obtenerCartasJugador(int id) {
        return repositorioCarta.obtenerCartasJugador(id);
    }

    @Override
    public Carta obtenerPorId(int id) {
        return repositorioCarta.findById(id).orElse(null);
    }

    @Override
    public List<Carta> crearCartas(Partida partida) {
        //Guardamos todos los digimons en un arraylist
      List<Digimon> digimons = new ArrayList<>();
      List<Carta> cartas = new ArrayList<>();
      //Los obtenemos de la bbdd
      digimons = repositorioDigimon.findAll();
        //Creamos un arraylist con los digimons disponibles (los que se asignen dejarán de estar disponibles
      List<Digimon> digimonsDisponibles = new ArrayList<>(digimons);

      for (int i = 0;  i<150; i++) {
          //Creamos un número aleatorio para seleccionar un digimon de la lista
            int random = (int) (Math.random() * digimonsDisponibles.size());
            //Seleccionamos el digimon en la posición random obtenida y lo guardamos en una variable
            Digimon digimon = digimonsDisponibles.get(random);
            //Eliminamos el digimon de la lista de disponibles
            digimonsDisponibles.remove(random);
            //Creamos una carta con los datos del digimon seleccionado
            Carta carta = new Carta();
            carta.setNombreCarta(digimon.getName());
            carta.setImgCarta(digimon.getImg());
            //Asignamos un valor (dinero) aleatorio entre 100 y 5000
            carta.setValorCarta((int) (Math.random() * (5000 - 100 + 1)) + 100);
            //Asignamos un nivel inicial de 1
            carta.setNivelCarta(1);
            //Asignamos un valor de vida aleatorio entre 10 y 100 ambos incluidos
            carta.setVidaCarta(String.valueOf((int)(Math.random() * (100 - 10 + 1)) + 10) +"%");
            //Añadimos la carta creada al arraylist de cartas
            cartas.add(carta);
      }
      //Una vez creadas las 150 cartas las guardamos en la bbdd
        repositorioCarta.saveAll(cartas);
        //Recuperamos los jugadores que pertenezcan a la partida actual y les asignamos 20 cartas aleatorias a cada uno
        List<Jugador> jugadoresEnPartida = new ArrayList<>();
        jugadoresEnPartida = repositorioJugador.findJugadoresByPartidaId(partida.getId());
        //Por cada jugador en la partida le asignamos 20 cartas aleatorias
        for (Jugador jugador : jugadoresEnPartida){
            //Nos aseguramos de que las cartas no tengan jugador asignado
            List<Carta> cartasParaJugador = repositorioCarta.recuperar20CartasSinJugador(20);
            asignarCartasAJugadores(cartasParaJugador, jugador);
        }

     return cartas;
    }

    @Override
    public List<Carta> obtenerCartasSinAsignar() {
        return repositorioCarta.obtenerCartasSinAsignar();
    }

    @Override
    public List<Carta> recuperarCartasAlineadasDeJugador(int id) {
        return repositorioCarta.recuperarCartasAlineadasDeJugador(id);
    }

    public void alinearCartas() {
        List<Jugador> jugadoresEnPartida = repositorioJugador.findAll();
        List<Alineacion> alineaciones = repositorioAlineacion.findAll();
        List<Integer> cartasIds = new ArrayList<>();
        List<Carta> zona1 = new ArrayList<>();
        List<Carta> zona2 = new ArrayList<>();
        List<Carta> zona3 = new ArrayList<>();
        List<Carta> cartasAlineadas = new ArrayList<>();
        List<Carta> cartasDelJugador;
        int idJugador;
        for (Jugador jugador : jugadoresEnPartida) {
            idJugador = jugador.getId();
            cartasDelJugador = jugador.getCartas();
            Collections.shuffle(cartasDelJugador);
            for (Alineacion alineacion : alineaciones) {
                for (Carta carta : cartasDelJugador) {
                    if (!cartasIds.contains(carta.getId())) {
                        switch (alineacion.getId()) {
                            case 1:
                                if (zona1.size() < 3) {
                                    zona1.add(carta);
                                    cartasIds.add(carta.getId());
                                    carta.setAlineacion(alineacion);
                                }
                                break;
                            case 2:
                                if (zona2.size() < 3) {
                                    zona2.add(carta);
                                    cartasIds.add(carta.getId());
                                    carta.setAlineacion(alineacion);
                                }
                                break;
                            case 3:
                                if (zona3.size() < 3) {
                                    zona3.add(carta);
                                    cartasIds.add(carta.getId());
                                    carta.setAlineacion(alineacion);
                                }
                                break;
                        }
                    }
                }

            }
            cartasAlineadas.addAll(zona1);
            cartasAlineadas.addAll(zona2);
            cartasAlineadas.addAll(zona3);
            repositorioCarta.saveAll(cartasAlineadas);
            repositorioJugador.save(repositorioJugador.findById(idJugador));
        }
    }

    private List<Carta> recuperar20CartasSinJugador(int limit) {
        //Recuperamos 20 cartas que no tengan jugador asignado, su campo jugador será null
        return repositorioCarta.recuperar20CartasSinJugador(limit);
    }

    private void asignarCartasAJugadores(List<Carta> cartas, Jugador jugador){
        //En la tabla Cartas habrá un campo jugador que guardará el id del jugador al que se le asigna la carta
        for (Carta carta : cartas){
            carta.setJugador(jugador);
            repositorioCarta.save(carta);

        }
        alinearCartas();
    }
}
