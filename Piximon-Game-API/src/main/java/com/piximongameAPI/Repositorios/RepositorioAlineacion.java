package com.piximongameAPI.Repositorios;

import com.piximongameAPI.Entidades.Alineacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioAlineacion extends JpaRepository<Alineacion, Integer> {

    //Método para saber si hay alineaciones (zonas creadas)
    @Query(value = "SELECT COUNT(id) FROM alineaciones", nativeQuery = true)
    int comprobarSiHayAlineaciones();
}
