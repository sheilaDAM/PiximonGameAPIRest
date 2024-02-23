package com.piximongameAPI.Servicios;


import com.piximongameAPI.Entidades.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ServicioUsuario {

    List<Usuario> obtenerTodos();

    Usuario obtenerPorId(int id);

    Usuario crearUsuario(Usuario usuario);

    Usuario login(String nombre, String password);

    Usuario comprobarSiExisteUsuario(String nombre);

    int obtenerIdUsuarioPorNombre(String nombre);

    Usuario obtenerUsuarioEnPartidaActual(int id);
}
