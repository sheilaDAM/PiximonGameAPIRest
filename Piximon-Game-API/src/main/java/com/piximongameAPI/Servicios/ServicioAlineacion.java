package com.piximongameAPI.Servicios;

import com.piximongameAPI.Entidades.Alineacion;

import java.util.List;
import java.util.Optional;

public interface ServicioAlineacion {

    List<Alineacion> obtenerTodas();

    Optional<Alineacion> obtenerAlineacionPorId(int id);

    Alineacion crearAlineacion(Alineacion alineacion);

    int comprobarSiHayAlineaciones();









}
