package com.piximongameAPI.Servicios;

import com.piximongameAPI.Entidades.Carta;
import com.piximongameAPI.Entidades.Combate;

import java.util.List;

public interface ServicioCombate {

    List<Combate> obtenerCombates();

    Combate obtenerPorId(int id);

    List<Combate> crearCombate(Combate combate);

}
