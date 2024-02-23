package com.piximongameAPI.Entidades;

import com.fasterxml.jackson.annotation.*;
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
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombreJugador;

    @Column
    private String iconoJugador;

    @Column
    private double dineroJugador;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partida_id", referencedColumnName = "id") //partida_id es el campo de la tabla jugador que hace referencia al id de la tabla partida
    @JsonBackReference("jugador-partida")
    private Partida partida;

    @OneToOne(mappedBy = "jugador", cascade = CascadeType.ALL)
    @JsonIgnore
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonManagedReference("jugador-combate")
    private Combate combate;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario", referencedColumnName = "id") //hace referencia al id de la tabla usuario
    @JsonBackReference("jugador-usuario")
    private Usuario usuario;


    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("carta-jugador")
    private List<Carta> cartas = new ArrayList<>();




    /*public Jugador() {
    }*/

    public Jugador(String nombreJugador, String iconoJugador, double dineroJugador) {
        this.nombreJugador = nombreJugador;
        this.iconoJugador = iconoJugador;
        this.dineroJugador = dineroJugador;
    }

    public Jugador(String nombreJugador, String iconoJugador, double dineroJugador, Usuario usuario) {
        this.nombreJugador = nombreJugador;
        this.iconoJugador = iconoJugador;
        this.dineroJugador = dineroJugador;
        this.usuario = usuario;
    }

    public Jugador(String nombreJugador, String iconoJugador, double dineroJugador, Partida partida, Usuario usuario) {
        this.nombreJugador = nombreJugador;
        this.iconoJugador = iconoJugador;
        this.dineroJugador = dineroJugador;
        this.partida = partida;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getIconoJugador() {
        return iconoJugador;
    }

    public double getDineroJugador() {
        return dineroJugador;
    }

    public Partida getPartida() {
        return partida;
    }

    /*public Combate getCombate() {
        return combate;
    }*/

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Carta> getCartas() {
        return cartas;
    }
}
