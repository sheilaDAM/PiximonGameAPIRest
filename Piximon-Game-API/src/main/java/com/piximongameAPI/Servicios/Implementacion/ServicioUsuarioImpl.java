package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.Usuario;
import com.piximongameAPI.Repositorios.RepositorioUsuario;
import com.piximongameAPI.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    public List<Usuario> obtenerTodos() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Usuario obtenerPorId(int id) {
        return repositorioUsuario.findById(id).orElse(null);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }

    @Override
    public Usuario login(String nombre, String password) {
        return repositorioUsuario.login(nombre, password); //Si el usuario existe, retorna true
    }

    @Override
    public Usuario comprobarSiExisteUsuario(String nombre) {
        return repositorioUsuario.comprobarSiExisteUsuario(nombre);
    }

    @Override
    public int obtenerIdUsuarioPorNombre(String nombre) {
        return repositorioUsuario.obtenerIdUsuarioPorNombre(nombre);
    }

    @Override
    public Usuario obtenerUsuarioEnPartidaActual(int id) {
        return repositorioUsuario.obtenerUsuarioEnPartidaActual(id);
    }


}
