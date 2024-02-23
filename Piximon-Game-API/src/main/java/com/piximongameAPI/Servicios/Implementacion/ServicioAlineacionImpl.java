package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.Alineacion;
import com.piximongameAPI.Repositorios.RepositorioAlineacion;
import com.piximongameAPI.Servicios.ServicioAlineacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioAlineacionImpl implements ServicioAlineacion {

    @Autowired
    private RepositorioAlineacion repositorioAlineacion;
    @Override
    public List<Alineacion> obtenerTodas() {
        return repositorioAlineacion.findAll();
    }

    @Override
    public Optional<Alineacion> obtenerAlineacionPorId(int id) {
        return repositorioAlineacion.findById(id);
    }

    @Override
    public Alineacion crearAlineacion(Alineacion alineacion) {
        return repositorioAlineacion.save(alineacion);
    }

    @Override
    public int comprobarSiHayAlineaciones() {
        return repositorioAlineacion.comprobarSiHayAlineaciones();
    }

    public void generarAlineaciones(){
        for(int i = 1; i<4; i++){
            repositorioAlineacion.save(new Alineacion("Zona " + i));
        }
    }


}
