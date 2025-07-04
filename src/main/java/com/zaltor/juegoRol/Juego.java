/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logs.*;
import com.zaltor.excepciones.*;
import com.zaltor.gui.*;

/**
 * Clase que controla la acción de jugador.
 * @author Mar
 */
public class Juego {
    
    private VentanaJuego vj;
            
    public Juego(VentanaJuego vj) {
        this.vj = vj;
    }
    
    /**
     * 
     * @param jugador Realiza la acción.
     * @param enemigo La recibe.
     * @param accion Enumeración de la clase Accion.
     */
    public void realizarAccionJugador(Personaje jugador, Enemigo enemigo, Accion accion) {
     
        switch (accion) {
            case Atacar:
                jugador.atacar(enemigo, false, false);
                vj.mostrarMensaje(jugador.getNombrePersonaje() + " ataca a " + enemigo.getNombrePersonaje());
                Logger.registrarEvento(jugador.getNombrePersonaje() + " ataca a " + enemigo.getNombrePersonaje());
                break;
            case Defender:
                jugador.activarDefensa();
                vj.mostrarMensaje("La defensa de " + jugador.nombrePersonaje + " está activada.");
                Logger.registrarEvento(jugador.getNombrePersonaje() + " activa la defensa.");
                break;
            case HabilidadEspecial:
                try {
                    jugador.usarHabilidadEspecial(jugador, enemigo, false);
                    vj.mostrarMensaje(jugador.getNombrePersonaje() + " usa Habilidad Especial. \nConsume 5 puntos de energía.");
                    Logger.registrarEvento(jugador.getNombrePersonaje() + " usa Habilidad Especial.");
                } catch (EnergiaInsuficienteException e) { // Maneja la excepción y lanza un mensaje
                    vj.mostrarMensaje("\nERROR -->" + e.getMessage());
                    Logger.registrarEvento("ERROR --> " + e.getMessage());
                }
                break;
        }
         
    }
    
}