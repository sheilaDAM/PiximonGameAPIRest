package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.Ronda;
import com.piximongameAPI.Repositorios.RepositorioRonda;
import com.piximongameAPI.Servicios.ServicioRonda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioRondaImpl implements ServicioRonda {

    @Autowired
    private RepositorioRonda repositorioRonda;

    @Override
    public List<Ronda> obtenerRondas() {
        return repositorioRonda.findAll();
    }

    @Override
    public Optional<Ronda> obtenerRondaPorId(int id) {
        return repositorioRonda.findById(id);
    }

    @Override
    public Ronda crearRondas(Ronda ronda) {
        return repositorioRonda.save(ronda);
    }
}
