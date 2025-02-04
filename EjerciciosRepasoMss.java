/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.salvador.repaso;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mss7
 * 
 * Juego de adivinar en número aleatorio.
 */
public class EjerciciosRepasoMss {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int contador = 0; 
        ArrayList<Integer> intentos = new ArrayList<>(); //Declaro la ArrayList en el main 
        contador = adivinaElNumero(intentos); 
        mostrarEstadisticas(contador, intentos); // Esto es lo que va a usar mostrarEstadísticas de la función adivinaElNumero
    
    }

    // Generador de número aleatorio
    public static int adivinaElNumero(ArrayList<Integer> intentos){
        int elegido;
        int numero;
        int contador = 1; 
        Scanner leer;
        
        elegido = (int) (Math.random()*16); // Este es el número que hay que acertar
      //  System.out.println("El número random es: " + elegido); // Quite las barras de comentario para comprobar si funciona el random
        
        leer = new Scanner (System.in);
        System.out.print("Adivina el número del 0 al 15.\nPrueba un número: "); // La primera vez, le pido el número fuera del bucle
        numero = leer.nextInt(); 
                
        while (numero != elegido){
            intentos.add(numero); // Los números que va intentando el usuario se guardan en la ArrayList intentos
            contador++;
                        
            System.out.print("Prueba otro número: ");
            numero = leer.nextInt(); 
        }
        
        System.out.println("\n¡Enhorabuena, has adivinado el número " + elegido + "!");
        intentos.add(elegido);
                
        return contador; //Devuelve el número total de intentos del usuario
    }

    // Estadísticas del juego
    public static void mostrarEstadisticas(int contador, ArrayList<Integer> intentos){
        int suma = 0;
        double media;
        
        // Nº total de intentos
        System.out.println("\nLo has intentado: "+contador+" veces."); 
        
        // Promedio de los intentos
        for (int intento : intentos){ // Primero los sumo todos con el for each, que recorre la lista entera
            suma = suma + intento; 
        }
        
        media = (double) suma / intentos.size(); // Y luego lo divido entre lo larga que sea la lista
        System.out.println("El promedio de tus intentos es: "+media);
        
        // Intento más cercano al nº objetivo
        int elegido;
        int cercano;
        
        elegido = intentos.get(contador - 1); // Esto me da el nº random elegido
        
        if (intentos.get(0) == elegido){
            System.out.println("¡Has hacertado al primer intento!");
        } else{
            cercano = intentos.get(0); // Obtenemos el primer nº de la lista para empezar a comparar
        
            for (int intento : intentos) {
                if (intento != elegido && Math.abs(intento - elegido) < Math.abs(cercano - elegido)) {
                    cercano = intento;
                } 
            }
            System.out.println("El intento más cercano a " + elegido+" es: " + cercano);
        }
    }
}
