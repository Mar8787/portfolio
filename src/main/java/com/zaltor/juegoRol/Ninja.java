/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 * Clase hija Ninja, hereda de Personaje.
 *
 * @author Mar
 */
public class Ninja extends Personaje {
    protected boolean ataqueSigiloso;
    
    /**
     * Constructor
     * @param nombrePersonaje 
     */
    public Ninja(String nombrePersonaje) {
        super(TipoPersonaje.Ninja, nombrePersonaje, 100, 15, 8, 15, 1, 0, 50, "Ataque Sigiloso");
        this.ataqueSigiloso = false;
    }
        
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param ataqueSigiloso Sobrescribo el método habilidadEspecial con las cualidades de Ataque Sigiloso, +5 de ataque.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean ataqueSigiloso) {
        jugador.setEnergia(jugador.getEnergia() - 5);
        ataqueSigiloso = true;
                 
        atacar(enemigo, true, ataqueSigiloso);
    }

}
