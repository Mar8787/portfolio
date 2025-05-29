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
     * 
     * @param nombrePersonaje Atributos heredados
     * @param vida
     * @param ataque
     * @param defensa
     * @param velocidad
     * @param nivel
     * @param experiencia
     * @param energia
     * @param expOtorgada 
     */
    public NoMuerto(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, int expOtorgada) {
        super("No-Muerto", 100, 15, 8, velocidad, nivel, experiencia, energia, 40);
    }

    /**
     * Sobrescribo los métodos mostrarInfo y habilidadEspecial.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("\nNo Muerto: Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", experiencia que otorga: " + expOtorgada + ", habilidad especial: Regeneración.");
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
        System.out.println(" No-Muerto usó Regeneración, su vida actual es de " + vida + " puntos.");
    }
    
}
