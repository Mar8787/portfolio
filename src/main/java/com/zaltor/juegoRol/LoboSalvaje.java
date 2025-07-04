/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logica.*;

/**
 * Clase hija LoboSalvaje que hereda de Enemigo, que a su vez, hereda de
 * Personaje.
 *
 * @author Mar
 */
public class LoboSalvaje extends Enemigo {
    
    /**
     * Constructor
     * @param nombrePersonaje 
     */
    public LoboSalvaje(String nombrePersonaje) {
        super(TipoPersonaje.LoboSalvaje, nombrePersonaje, 80, 12, 5, 10, 1, 0, 50, 30, "Mordida Rápida");
    }

    /**
     *
     * @param jugador
     * @param enemigo
     * @param mordidaRapida Sobrescribo el método habilidadEspecial con las cualidades de Mordida Rápida, +2 de ataque.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean mordidaRapida) {
        mordidaRapida = true;
        
        infligirDanio(jugador, enemigo, defensaActiva, mordidaRapida, mordidaRapida);
    }

}
