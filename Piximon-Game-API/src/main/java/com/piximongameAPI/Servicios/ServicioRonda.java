package com.piximongameAPI.Servicios;


import com.piximongameAPI.Entidades.Ronda;

import java.util.List;
import java.util.Optional;

public interface ServicioRonda {

    List<Ronda> obtenerRondas();

    Optional<Ronda> obtenerRondaPorId(int id);

    Ronda crearRondas(Ronda ronda);
}
