/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logica.*;

/**
 * Clase heredera de Enemigo.
 * @author Mar
 */
public class GuerreroOscuro extends Enemigo {
    
    /**
     * Constructor
     * @param nombrePersonaje 
     */
    public GuerreroOscuro(String nombrePersonaje) {
        super(TipoPersonaje.GuerreroOscuro, nombrePersonaje, 130, 20, 10, 19, 1, 0, 50, 50, "Furia Maldita");
    }
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param furiaMaldita Sobrescribo el método habilidadEspecial con las cualidades de Furia Maldita, +5 de ataque.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean furiaMaldita) {
        furiaMaldita = true;
        infligirDanio(jugador, enemigo, defensaActiva, furiaMaldita, furiaMaldita); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
