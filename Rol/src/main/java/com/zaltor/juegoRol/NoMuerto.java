/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 *  Clase que hereda de Enemigo.
 * @author Mar
 */
public class NoMuerto extends Enemigo {
    
    /**
     * Constructor
     * @param nombrePersonaje 
     */
    public NoMuerto(String nombrePersonaje) {
        super(TipoPersonaje.NoMuerto, nombrePersonaje, 100, 15, 8, 10, 1, 0, 50, 40, "Regeneración");
    }
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param regeneración Sobrescribo el método habilidadEspecial con las cualidades de Regeneración, suma 10 de vida.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean regeneración) {
        vida += 10;
    }
    
}
