/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.excepciones;

/**
 * Clase hija que hereda de JuegoException
 * @author mss7
 */
public class EnergiaInsuficienteException extends JuegoException{
    public EnergiaInsuficienteException(String nombrePersonaje) {
        super(nombrePersonaje + " no tiene suficiente energía para usar su habilidad especial, pierde el turno.");
    }
}
