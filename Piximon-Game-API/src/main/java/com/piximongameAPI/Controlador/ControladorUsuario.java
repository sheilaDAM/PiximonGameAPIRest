package com.piximongameAPI.Controlador;

import com.piximongameAPI.Entidades.Jugador;

import com.piximongameAPI.Entidades.ResponseStatus;
import com.piximongameAPI.Entidades.Usuario;
import com.piximongameAPI.Repositorios.RepositorioUsuario;
import com.piximongameAPI.Servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private ServicioUsuario servicioUsuario;

    //MÉTODO PARA OBTENER TODOS LOS USUARIOS DE LA BBDD
    @GetMapping("/getUsuarios")
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        return usuarios;
    }

    //MÉTODO PARA REGISTRAR UN USUARIO EN LA BBDD
    @PostMapping("/registrar")
    public ResponseStatus addUsuario(@RequestBody Usuario usuario) {
        System.out.println(usuario.toString());
        try {
            //comprobar si el usuario insertado ya está registrado
            //Si existe en la bbdd devolvemos un mensaje de error
            if (servicioUsuario.comprobarSiExisteUsuario(usuario.getNombre()) != null) {
                return new ResponseStatus(ResponseStatus.TipoCodigo.YA_EXITE);
            //si no existe en la bbdd lo insertamos
            }else {
                Usuario usuarioInsertado = usuario;
                servicioUsuario.crearUsuario(usuario);
                return new ResponseStatus(ResponseStatus.TipoCodigo.REGISTRADO);
            }

        } catch (Exception e) {
            System.out.println("addUsuario ERROR:" + e.getMessage());
            return new ResponseStatus(ResponseStatus.TipoCodigo.ERROR);
        }
    }

    //MÉTODO PARA COMPROBAR EL LOGIN DE UN USUARIO Y SABER SI EXISTE EN LA BBDD Y SI LA CONTRASEÑA ES CORRECTA
    @PostMapping("/comprobarLogin")
    public ResponseStatus login(@RequestBody Usuario usuario) {
        Usuario usuarioEncontrado = repositorioUsuario.login(usuario.getNombre(), usuario.getPassword());
        System.out.println(usuario.toString());

        if (usuarioEncontrado != null) {
            // Credenciales válidas
            return new ResponseStatus(ResponseStatus.TipoCodigo.LOGIN_CORRECTO);
        } else {
            // Credenciales inválidas
            return new ResponseStatus(ResponseStatus.TipoCodigo.LOGIN_INCORRECTO);
        }
    }

}
