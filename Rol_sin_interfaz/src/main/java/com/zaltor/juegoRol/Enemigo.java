/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

/**
 * Creo la clase hija Enemigo, que hereda de Personaje,
 * a su vez esta clase heredará o otras clases hijas de Enemigo.
 * @author Mar
 */
public class Enemigo extends Personaje {
    protected int expOtorgada;

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
    public Enemigo(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, int expOtorgada) {
        super(nombrePersonaje, vida, ataque, defensa, velocidad, nivel, experiencia, energia);
        this.expOtorgada = expOtorgada;
    }
    
    /**
     * 
     * @return Creo el getter del atributo distinto a la clase padre.
     */
    public int getExpOtorgada() {
        return expOtorgada;
    }

    /**
     * Sobrescribo el método mostrarInfo y habilidadEspecial,
     * pero los dejo vacíos para sobrescribirlos en cada enemigo.
     */
    @Override
    public void mostrarInfo() {
        
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
                
        System.out.println("\n " + enemigo.getNombrePersonaje() + " prepara Golpe Devastador...");
        System.out.println(" " + enemigo.getNombrePersonaje() + " consume 5 puntos de su energía");
        System.out.println(" La energía actual de" + enemigo.getNombrePersonaje() + " es de: " + jugador.getEnergia());
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
