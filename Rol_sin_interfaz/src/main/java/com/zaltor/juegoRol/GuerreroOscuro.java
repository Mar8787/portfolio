/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 *  Clase heredera de Enemigo.
 * @author Mar
 */
public class GuerreroOscuro extends Enemigo {
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
     * @param expOtorgada 
     */
    public GuerreroOscuro(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, int expOtorgada) {
        super("Guerrero Oscuro", 130, 20, 10, velocidad, nivel, experiencia, energia, 50);
    }
   
    /**
     * Sobrescribo los métodos mostrarInfo y habilidadEspecial.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("\nGuerrero Oscuro: Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", experiencia que otorga: " + expOtorgada + ", habilidad especial: Furia Maldita.");
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
        System.out.println("\n Guerrero Oscuro prepara Furia Maldita...");
        
        recibirDanio(jugador, enemigo, defensaActiva, furiaMaldita, furiaMaldita); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
