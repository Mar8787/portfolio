/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 * Clase hija LoboSalvaje que hereda de Enemigo, que a su vez, hereda de Personaje.
 * @author Mar
 */
public class LoboSalvaje extends Enemigo {
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
    public LoboSalvaje(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, int expOtorgada) {
        super("Lobo Salvaje", 80, 12, 5, velocidad, nivel, experiencia, energia, 30);
    }

    /**
     * Sobrescribo los métodos mostrarInfo y habilidadEspecial.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("\nLobo Salvaje: Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", experiencia que otorga: " + expOtorgada + ", habilidad especial: Mordida Rápida.");
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
        System.out.println("\n Lobo Salvaje prepara Mordida Rápida...");
        
           recibirDanio(jugador, enemigo, defensaActiva, mordidaRapida, mordidaRapida); 
        
        
    }
 
    
    
    
    
}
