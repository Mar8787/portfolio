/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logs.Logger;

/**
 * Creo la clase hija Enemigo, que hereda de Personaje,
 * a su vez esta clase heredará o otras clases hijas de Enemigo.
 * @author Mar
 */
public class Enemigo extends Personaje {
    protected int expOtorgada;
    
    /**
     * Constructor
     * @param tipo
     * @param nombrePersonaje
     * @param vida
     * @param ataque
     * @param defensa
     * @param velocidad
     * @param nivel
     * @param experiencia
     * @param energia
     * @param expOtorgada 
     */
    public Enemigo(TipoPersonaje tipo, String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, int expOtorgada, String habilidadEspecial) {
        super(tipo, nombrePersonaje, vida , ataque, defensa, velocidad, nivel, experiencia, energia, habilidadEspecial);
        this.expOtorgada = expOtorgada;
        this.habilidadEspecial = habilidadEspecial;
    }
    
    /**
     * 
     * @return Creo el getter del atributo distinto a la clase padre.
     */
    public int getExpOtorgada() {
        return expOtorgada;
    }
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param habilidadEspecial Booleano que activa o desactiva la habilidad especial
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean habilidadEspecial) {
        jugador.setEnergia(enemigo.getEnergia() - 5);
    }
    
    /**
     * Método que gestiona el ataque del enemigo
     * @param jugador
     * @param enemigo
     * @param defensaActiva booleano que activa/desactiva la defensa.
     * @param mordidaRapida booleano que activa habilidad especial de lobo.
     * @param furiaMaldita booleano que activa habilidad especial de Guerrero Oscuro.
     */
    public void infligirDanio(Personaje jugador, Enemigo enemigo, boolean defensaActiva, boolean mordidaRapida, boolean furiaMaldita) {
        int diferenciaAtaqueDefensa = 0;
        int ataqueAuxiliar = enemigo.getAtaque();
        
        // Se activa la habilidad especial de LoboSalvaje. 
        if (mordidaRapida) {
            ataqueAuxiliar += 2;
            mordidaRapida = false;
        }
        
        // Se activa la habilidad especial de GuerreroOscuro
        if (furiaMaldita) {
            ataqueAuxiliar += 5;
            furiaMaldita = false;
        }
        
        // Durante el turno en que la defense esté activa se suman los puntos de defensa a la vida.
        if (defensaActiva) {
            diferenciaAtaqueDefensa = ataqueAuxiliar - jugador.getDefensa();
            
            // Si el ataque gana en puntos a la defensa, esa diferencia de puntos repercuten en la vida del personaje.
            if (diferenciaAtaqueDefensa > 0) {
                jugador.setVida(Math.max(0, jugador.getVida() - diferenciaAtaqueDefensa));
            } else { // Si no, no resta puntos a la vida
                Logger.registrarEvento("Ataque bloqueado por " + jugador.getNombrePersonaje() + ".");
            }
            // Se desactiva la defensa.
            jugador.desactivarDefensa();
        } else { // Si no está activa la defensa, se le restan los puntos del ataque del enemigo a la vida normal del personaje.
            jugador.setVida(Math.max(0, jugador.getVida() - ataqueAuxiliar));
        }
    }
    
}
