package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.*;
import com.piximongameAPI.Repositorios.*;
import com.piximongameAPI.api.IAPIServiceDigimon;
import com.piximongameAPI.api.RestClientDigimon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Callback;

import java.util.*;

@Service
public class GenerarDatos {
    @Autowired
    private RepositorioCarta repositorioCarta;
    /*@Autowired
    private RepositorioCombate repositorioCombate;*/
    @Autowired
    private RepositorioJugador repositorioJugador;
    @Autowired
    private RepositorioPartida repositorioPartida;
    /*@Autowired
    private RepositorioRonda repositorioRonda;*/
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioAlineacion repositorioAlineacion;
    @Autowired
    private RepositorioDigimon repositorioDigimon;

    public Usuario crearUsuario(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }

    public void generarAlineaciones() {
        if (repositorioAlineacion.findAll().size() == 3) {

        } else {
            for (int i = 1; i < 4; i++) {
                repositorioAlineacion.save(new Alineacion("Zona " + i));
            }
        }
    }

    @Transactional
    public Partida crearJugadores(Jugador jugador) {
        //creamos la partida e insertamos este id a los jugadores que creemos
        Partida partidaActual = new Partida();
        partidaActual = repositorioPartida.save(partidaActual);
        //obtenemos el usuario que está registrado
        Usuario jugadorUsuario = repositorioUsuario.obtenerUsuarioPorNombre(jugador.getNombreJugador());
        //Usuario usuarioEnPartida = repositorioUsuario.obtenerUsuarioEnPartidaActual(partidaActual.getId());
        // Primero guardamos el jugador principal (usuario que juega), y el fk_usuario estará rellenado con el id del usuario, el resto de jugadores será null
        List<Jugador> jugadores = new ArrayList<>();
        jugador.setPartida(partidaActual);
        jugador.setUsuario(jugadorUsuario);
        jugadores.add(jugador);
        Random random = new Random();
        //Creamos dos colecciones para guardar las fotos (avatares) y nombres aleatorios disponibles para los jugadores aleatorios
        //ya que al generarse aleatoriamente no queremos que se repitan entres sí al crearse
        List<String> avataresDisponibles = new ArrayList<>(rutaimagenAvatar());
        List<String> nombresDisponibles = new ArrayList<>(obtenerNombresOponentes());

        // Crearmos 4 jugadores aleatoriamente
        for (int i = 0; i < 4; i++) {
            // Seleccionamos un nombre aleatorio que no se haya utilizado
            String nombreAleatorio = nombresDisponibles.remove(random.nextInt(nombresDisponibles.size()));

            // Seleccionamos un avatar aleatorio que no sea el elegido por el jugador principal (usuario)
            String avatarAleatorio = null;
            do {
                // fotoAvatarAleatorio = listaAvatares.get(random.nextInt(listaAvatares.size())).getImagenAvatar();
                // Seleccionamos un avatar aleatorio que no se haya utilizado
                avatarAleatorio = avataresDisponibles.remove(random.nextInt(avataresDisponibles.size()));

            } while (avatarAleatorio.equals(jugador.getIconoJugador()));

            // Creamos el jugador aleatorio y lo añadimos a la lista
            Jugador jugadorAleatorio = new Jugador(nombreAleatorio, avatarAleatorio, 1500000, partidaActual, null);
            jugadores.add(jugadorAleatorio);

            //insertar cartas a jugadores
        }
        repositorioJugador.saveAll(jugadores);
        //crearCartas(partidaActual);

        //llamamos al método del servicioCarta para crear las cartas y le pasamos le idPartda


        //aquí recibimos el jugador que es el usuario que está registrado, ahora mismo
        //solamente tiene el nombre y el avatar, que es lo que se ha mandado desde la vista
        //le añadiremos el dinero de manera aleatoria y lo añadiremos a una List<Jugador>
        //creamos los 4 bots con datos aleatorios completamente y los añadimos a la lista
        // con esto: return repositorioJugador.saveAll("array list de jugadores") guardaremos toda la array
        // return jugadores;

        return partidaActual;
    }

    public List<Carta> crearCartas(Partida partida) {
        //Guardamos todos los digimons en un arraylist
        List<Carta> cartas = new ArrayList<>();
        //Los obtenemos de la bbdd
        //digimons = repositorioDigimon.findAll();
        //Creamos un arraylist con los digimons disponibles (los que se asignen dejarán de estar disponibles
        List<Digimon> digimonsDisponibles = repositorioDigimon.findAll();
        Collections.shuffle(digimonsDisponibles);

        for (int i = 0; i < 120; i++) {
            //Creamos un número aleatorio para seleccionar un digimon de la lista
            //int random = (int) (Math.random() * digimonsDisponibles.size());
            //Seleccionamos el digimon en la posición random obtenida y lo guardamos en una variable
            Digimon digimon = digimonsDisponibles.get(i);
            //Eliminamos el digimon de la lista de disponibles
            //digimonsDisponibles.remove(i);
            //Creamos una carta con los datos del digimon seleccionado
            Carta carta = new Carta();
            carta.setNombreCarta(digimon.getName());
            carta.setImgCarta(digimon.getImg());
            //Asignamos un valor (dinero) aleatorio entre 100 y 5000
            carta.setValorCarta((int) (Math.random() * (5000 - 100 + 1)) + 100);
            //Asignamos un nivel inicial de 1
            carta.setNivelCarta(1);
            //Asignamos un valor de vida aleatorio entre 10 y 100 ambos incluidos
            carta.setVidaCarta(String.valueOf((int) (Math.random() * (100 - 10 + 1)) + 10) + "%");
            //Añadimos la carta creada al arraylist de cartas
            cartas.add(carta);
        }
        //Una vez creadas las 150 cartas las guardamos en la bbdd
        repositorioCarta.saveAll(cartas);
        //Recuperamos los jugadores que pertenezcan a la partida actual y les asignamos 20 cartas aleatorias a cada uno
        //List<Jugador> jugadoresEnPartida = new ArrayList<>();
        //jugadoresEnPartida = repositorioJugador.findJugadoresByPartidaId(partida.getId());
        //Por cada jugador en la partida le asignamos 20 cartas aleatorias
        /*for (Jugador jugador : jugadoresEnPartida){
            //Nos aseguramos de que las cartas no tengan jugador asignado
            List<Carta> cartasParaJugador = repositorioCarta.recuperar20CartasSinJugador(20);
            //asignarCartasAJugadores(cartasParaJugador, jugador);
        }*/

        return cartas;
    }


    public List<Jugador> findJugadoresByPartidaId(int id) {
        return repositorioJugador.findJugadoresByPartidaId(id);
    }


    public List<String> rutaimagenAvatar() {
        List<String> avatares = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String rutaImagen = "personaje" + i;
            avatares.add(rutaImagen);
        }
        return avatares;
    }

    public List<String> obtenerNombresOponentes() {
        List<String> nombresContrincantes = Arrays.asList("Noa", "Alex", "Noor", "Andrea", "Carmen");
        return nombresContrincantes;
    }

    public void alinearCartas() {
        List<Jugador> jugadoresEnPartida = repositorioJugador.findAll();
        List<Alineacion> alineaciones = repositorioAlineacion.findAll();
        List<Integer> cartasIds = new ArrayList<>();

        for (Jugador jugador : jugadoresEnPartida) {
            List<Carta> zona1 = new ArrayList<>();
            List<Carta> zona2 = new ArrayList<>();
            List<Carta> zona3 = new ArrayList<>();
            List<Carta> cartasAlineadas = new ArrayList<>();

            int idJugador = jugador.getId();
            List<Carta> cartasDelJugador = jugador.getCartas();
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

    public void asignarCartasAJugadores(List<Jugador> jugadores) {


        //List<Carta> cartas = repositorioCarta.recuperar20CartasSinJugador(20);
        //En la tabla Cartas habrá un campo jugador que guardará el id del jugador al que se le asigna la carta
        for (Jugador jugador : jugadores) {
            System.out.println("NOMBRE DEL JUGADOR " + jugador.getNombreJugador());
            List<Carta> cartas = repositorioCarta.recuperar20CartasSinJugador(20);
            for (Carta carta : cartas) {
                carta.setJugador(jugador);
                repositorioCarta.save(carta);
            }
            jugador.setCartas(cartas);
            repositorioJugador.save(jugador);
        }
    }

    public void recuperarDigimons() {
        IAPIServiceDigimon apiService = RestClientDigimon.getApiServiceInstance();
        apiService.obtenerDigimons().enqueue(new Callback<List<Digimon>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Digimon>> call, retrofit2.Response<List<Digimon>> response) {
                List<Digimon> digimons = (List<Digimon>) response.body();
                System.out.println("Número de Digimons obtenidos: " + digimons.size());
                repositorioDigimon.saveAll(digimons);
            }

            @Override
            public void onFailure(retrofit2.Call<List<Digimon>> call, Throwable throwable) {
                System.out.println("Error al recuperar los digimons");
            }
        });
    }


}
