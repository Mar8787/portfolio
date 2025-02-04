/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mar;

import java.util.Scanner;

/**
 *
 * @author Mar
 */
public class recursividad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Para calcular el factorial de un número, escribe a continuación un número entero positivo:");
        factorialRecursivo(); //Llamamos al método que controla la entrada por teclado
    }
    //Método que calcula el factorial
    public static int factorial(int numero) {
        if (numero == 1) { //Cuando el numero valga 1, dejará de llamar a la función
            return numero;
        } else {
            return numero * factorial(numero - 1); //Llama a la misma función
        }
    }
    //Método que controla las entradas por teclado
    public static void factorialRecursivo(){
      try {
            Scanner leer = new Scanner(System.in);
            int numero = leer.nextInt(); 
            if (numero > 0) { //Para que el numero sea positivo
                System.out.println("El factorial de " + numero + " es: " + factorial(numero)); //Llama al método que calcula el factorial
            } else {
                System.out.println("El número introducido debe ser mayor que cero. \nInténtelo de nuevo:");
                factorialRecursivo(); //Llamamos al mismo método para que introduzca el numero bien
            }
            leer.close();
        } catch (Exception e) { //Si salta este error, porque no escriban un entero, se activa el catch
            System.out.println("No se aceptan letras, decimales o símbolos. \nIntroduzca un número positivo válido, por favor:");
            factorialRecursivo(); //Llamamos al mismo método, hasta que introduzca un numero
        }
    }
}
