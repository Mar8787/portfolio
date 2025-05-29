/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 *
 * @author Alumno
 */
public class Morghan extends Enemigo {
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
    public Morghan(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, int expOtorgada) {
        super("Rey Oscuro Morghan", 1000, 30, 40, 70, 100, experiencia, energia, 1000);
    }
    
}
