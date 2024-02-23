package com.piximongameAPI.Servicios.Implementacion;

import com.piximongameAPI.Entidades.Combate;
import com.piximongameAPI.Repositorios.RepositorioCarta;
import com.piximongameAPI.Repositorios.RepositorioCombate;
import com.piximongameAPI.Servicios.ServicioCarta;
import com.piximongameAPI.Servicios.ServicioCombate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCombateImpl implements ServicioCombate {

    @Autowired
    private RepositorioCombate repositorioCombate;
    @Override
    public List<Combate> obtenerCombates() {
        return repositorioCombate.findAll();
    }

    @Override
    public Combate obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Combate> crearCombate(Combate combate) {
        return null;
    }
}
