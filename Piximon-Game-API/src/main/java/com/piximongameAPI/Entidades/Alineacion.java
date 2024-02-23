package com.piximongameAPI.Entidades;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "alineaciones")
public class Alineacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String zona;

    @OneToMany(mappedBy = "alineacion", cascade = CascadeType.ALL)
    @JsonManagedReference("cartas-alineacion")
    private List<Carta> cartas = new ArrayList<>();

    public Alineacion(String zona) {
        this.zona = zona;
    }
}
