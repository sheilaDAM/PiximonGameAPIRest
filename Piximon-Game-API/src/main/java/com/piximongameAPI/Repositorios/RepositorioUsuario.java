package com.piximongameAPI.Repositorios;

import com.piximongameAPI.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {

    //Método para buscar si un usuario existe en la base de datos y sus datos (nombre y password) son correctos para iniciar sesión
    @Query (value = "SELECT * FROM usuarios WHERE nombre =:nombre and password =:password", nativeQuery = true)
    Usuario login(String nombre, String password);

    //Método para buscar si un usuario ya existe en la base de datos por su nombre
    @Query (value = "SELECT * FROM usuarios WHERE nombre =:nombre", nativeQuery = true)
    Usuario comprobarSiExisteUsuario(String nombre);

    //Método para obtener el id de un usuario concreto por nombre
    @Query (value = "SELECT id FROM usuarios WHERE nombre =:nombre", nativeQuery = true)
    int obtenerIdUsuarioPorNombre(String nombre);

    //Método para obtener el Usuario de un usuario concreto por nombre
    @Query (value = "SELECT * FROM usuarios WHERE nombre =:nombre", nativeQuery = true)
    Usuario obtenerUsuarioPorNombre(String nombre);

    @Query (value = "SELECT u.* FROM jugadores j INNER JOIN usuarios u ON u.id = j.usuario WHERE j.partida_id =:id AND j.usuario IS NOT NULL", nativeQuery = true)
    Usuario obtenerUsuarioEnPartidaActual(int id);


}
