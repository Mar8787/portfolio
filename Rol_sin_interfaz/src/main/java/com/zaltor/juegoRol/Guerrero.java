/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 * Creo la clase hijo Guerrero,
 * hereda de Personaje.
 * @author Mar
 */
public class Guerrero extends Personaje {
    protected boolean golpeDevastador;
    
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
     * @param golpeDevastador Atributo propio de esta clase.
     */
    public Guerrero(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, boolean golpeDevastador) {
        super("nombrePersonaje", 150, 18, 12, 6, nivel, experiencia, energia);
        this.golpeDevastador = false;
    }

    /**
     * Sobrescribo los métodos mostrarInfo y habilidadEspecial.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("\nGuerrero: Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", velocidad: " + velocidad + ", nivel: " + nivel + ", experiencia; " + experiencia + ", habilidad especial: Golpe Devastador.");
    }
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param golpeDevastador Sobrescribo el método habilidadEspecial con las cualidades de golpe devastador, que dobla el ataque.
     */
    @Override
    public void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean golpeDevastador) {
        jugador.setEnergia(jugador.getEnergia() - 5);
        golpeDevastador = true;
        
        System.out.println("\n " + nombrePersonaje + " prepara Golpe Devastador...");
        System.out.println(" " + nombrePersonaje + " consume 5 puntos de su energía");
        System.out.println(" La energía actual de " + nombrePersonaje + " es de: " + jugador.getEnergia());
        
        
        atacar(enemigo, defensaActiva, golpeDevastador, defensaActiva);
    }
    
    
    
    
    
}
