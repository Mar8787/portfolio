/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 * Creo la clase hijo Guerrero,
 * hereda de Personaje.
 * @author Mar
 */
public class Guerrero extends Personaje {
    protected boolean golpeDevastador;
    
    /**
     * Constructor
     * @param nombrePersonaje 
     */
    public Guerrero(String nombrePersonaje) {
        super(TipoPersonaje.Guerrera, nombrePersonaje, 150, 18, 12, 6, 1, 0, 50, "Golpe Devastador");
        this.golpeDevastador = false;
    }
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param golpeDevastador Sobrescribo el método habilidadEspecial con las cualidades de golpe devastador, que dobla el ataque.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean golpeDevastador) {
        jugador.setEnergia(jugador.getEnergia() - 5);
        golpeDevastador = true;
        atacar(enemigo, golpeDevastador, defensaActiva);
    }
    
    
    
    
    
}
