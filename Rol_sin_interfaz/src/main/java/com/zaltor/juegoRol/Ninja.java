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
     * 
     * @param nombrePersonaje Atributos heredados.
     * @param vida
     * @param ataque
     * @param defensa
     * @param velocidad
     * @param nivel
     * @param experiencia
     * @param energia
     * @param ataqueSigiloso Atributo propio.
     */
    public Ninja(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, boolean ataqueSigiloso) {
        super("nombrePersonaje", 100, 15, 8, 15, nivel, experiencia, energia);
        this.ataqueSigiloso = false;
    }

    /**
     * Sobrescribo los métodos mostrarInfo y habilidadEspecial.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("\nNinja: Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", velocidad: " + velocidad + ", nivel: " + nivel + ", experiencia; " + experiencia + ", habilidad especial: Ataque Sigiloso.");
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
                
        System.out.println("\n " + nombrePersonaje + " prepara Ataque Sigiloso...");
        System.out.println(" " + nombrePersonaje + " consume 5 puntos de su energía");
        System.out.println(" La energía actual de" + nombrePersonaje + " es de: " + jugador.getEnergia());
                
        atacar(enemigo, defensaActiva, defensaActiva, ataqueSigiloso);
    }

}
