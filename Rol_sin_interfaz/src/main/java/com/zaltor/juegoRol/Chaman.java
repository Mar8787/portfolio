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
     * 
     * @param nombrePersonaje Atributos heredados
     * @param vida
     * @param ataque
     * @param defensa
     * @param velocidad
     * @param nivel
     * @param experiencia
     * @param energia 
     */
    public Chaman(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia) {
        super("nombrePersonaje", 120 , 12, 10, 10, nivel, experiencia, energia);
    }

    /**
     * Sobrescribo los métodos mostrarInfo y habilidadEspecial.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("\nChaman: Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", velocidad: " + velocidad + ", nivel: " + nivel + ", experiencia; " + experiencia + ", habilidad especial: Curación Mística.");
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
                
        System.out.println("\n " + nombrePersonaje + " prepara Golpe Devastador...");
        System.out.println(" " + nombrePersonaje + " consume 5 puntos de su energía");
        System.out.println(" La energía actual de" + nombrePersonaje + " es de: " + jugador.getEnergia());
        
        System.out.println("\nPuntos de vida de " + nombrePersonaje + ": " + vida);
        vida += 20;
        System.out.println("\n " + nombrePersonaje + " usó Curación Mística, su vida actual es de " + vida + " puntos.");
    }
    
    
    
    
    
    
    
    
    
}
