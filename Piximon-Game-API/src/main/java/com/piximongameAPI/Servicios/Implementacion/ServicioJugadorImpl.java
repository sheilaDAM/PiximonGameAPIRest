package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.Jugador;
import com.piximongameAPI.Entidades.Partida;
import com.piximongameAPI.Entidades.Usuario;
import com.piximongameAPI.Repositorios.RepositorioCarta;
import com.piximongameAPI.Repositorios.RepositorioJugador;
import com.piximongameAPI.Repositorios.RepositorioPartida;
import com.piximongameAPI.Repositorios.RepositorioUsuario;
import com.piximongameAPI.Servicios.ServicioJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
public class ServicioJugadorImpl implements ServicioJugador {

    /* TEORÍA */
    //La implementación del servicio es la que se encarga de hacer uso de los métodos del repositorio
    //como el repositorio extiende a JpaRepository, ya tiene métodos implementados para hacer operaciones CRUD
    //aquí creamos nuestro propios métodos y hacemos uso de los métodos heredados de JpaRepository
    //y también podemos crear nuestros propios métodos personalizados sin el uso de los métodos heredados
    //de JpaRepository, los tendríamos creados en el repositorio y con su inyección de dependencias
    //podríamos hacer uso de ellos en esta clase, aunque primero definiríamos la firma del método
    // en la interfaz del servicio y su implementación aquí
    //La firma del método significa que definimos el nombre del método, los parámetros que recibe y el tipo de dato que devuelve
    //Las querys personalizadas se pueden hacer con JPQL o con SQL nativo,
    // en el repositorio se pueden hacer con JPQL que es el lenguaje de consultas de JPA
    //y con el uso de la anotación @Query

    // ----------------- Inyección de dependencias -----------------
    @Autowired //Inyección de dependencias, para hacer uso de los métodos creados en nuestro repositorio personal, el servicio nos dará acceso a ellos
    private RepositorioJugador repositorioJugador;

    @Autowired
    private RepositorioPartida repositorioPartida;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioCarta repositorioCarta;

    @Autowired
    private ServicioCartaImpl servicioCarta;

   // ----------------- Métodos del servicio -----------------


    @Override
    public List<Jugador> obtenerTodosLosJugadores(){
    return repositorioJugador.obtenerTodosLosJugadores();
    }

    @Override
    public Jugador obtenerPorId(int id) {
        return repositorioJugador.findById(id);
    }

    @Override
    public List<Jugador> crearJugadores(Jugador jugador) {
        Partida partidaActual = new Partida();
        partidaActual = repositorioPartida.save(partidaActual);
        //Combate combate = new Combate();
        //Combate partidaActual = repositorioPartida.save(partida);
        Usuario jugadorUsuario = repositorioUsuario.obtenerUsuarioPorNombre(jugador.getNombreJugador());

       // partidaActual.setUsuario(jugadorUsuario);

        //creamos la partida e insertamos este id a los jugadores que creemos

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
        servicioCarta.crearCartas(partidaActual);

        //llamamos al método del servicioCarta para crear las cartas y le pasamos le idPartda


        //aquí recibimos el jugador que es el usuario que está registrado, ahora mismo
        //solamente tiene el nombre y el avatar, que es lo que se ha mandado desde la vista
        //le añadiremos el dinero de manera aleatoria y lo añadiremos a una List<Jugador>
        //creamos los 4 bots con datos aleatorios completamente y los añadimos a la lista
        // con esto: return repositorioJugador.saveAll("array list de jugadores") guardaremos toda la array
        return jugadores;

    }

    @Override
    public List<Jugador> findJugadoresByPartidaId(int id) {
        return repositorioJugador.findJugadoresByPartidaId(id);
    }

    @Override
    public Jugador comprobarSiExisteJugador(String nombre, int id_partida) {
        return repositorioJugador.comprobarSiExisteJugador(nombre, id_partida);
    }

    @Override
    public List<Jugador> obtenerJugadoresAleatoriosEnPartida(int id) {
        return repositorioJugador.obtenerJugadoresAleatoriosEnPartida(id);
    }

    @Override
    public Jugador obtenerJugadorUsuarioEnPartida(int id) {
        return repositorioJugador.obtenerJugadorUsuarioEnPartida(id);
    }

    @Override
    public Jugador obtenerJugadorPorNombre(String nombre) {
        return repositorioJugador.obtenerJugadorPorNombre(nombre);
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



}
