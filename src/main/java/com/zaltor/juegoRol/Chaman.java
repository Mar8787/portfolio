/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 * Creo la clase hija,
 * hereda de Personaje.
 * @author Mar
 */
public class Chaman extends Personaje {
    
    /**
     * Constructor
     * @param nombrePersonaje 
     */
    public Chaman(String nombrePersonaje) {
        super(TipoPersonaje.Chaman, nombrePersonaje, 120 , 12, 10, 10, 1, 0, 50, "Curación Mística");
    }
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param curacionMistica Sobrescribo el método habilidadEspecial con las cualidades de Curación Mística, suma 20 puntos de vida.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean curacionMistica) {
        jugador.setEnergia(jugador.getEnergia() - 5);
        vida += 20;
    }
    
    
    
    
    
    
    
    
    
}
