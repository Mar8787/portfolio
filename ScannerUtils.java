/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miempresa.utils;

import java.util.Scanner;
/**
 *
 * @author Mar
 */
public class ScannerUtils {
    private static final Scanner scanner = new Scanner(System.in); //Declaro el atributo scanner
    
    //Declaro el método para leer un entero
    public static  int leerEntero(String mensaje) {
        int numero = 0; //Declaro la variable, le doy un valor para iniciarla y que no me de error más tarde
        boolean entradaCorrecta; //Declaro este booleano para el bucle, cuando se cumpla, saldrá del bucle
        do { 
            entradaCorrecta = true; //Le damos el valor verdadero de entrada
            try {
                System.out.println(mensaje); //Va a imprimir el mensaje que escriba en el .java donde importe esta librería
                numero = scanner.nextInt();
                scanner.nextLine(); //Limpia el buffer y hace que leerCadena no imprima un espacio en blanco
            } catch (Exception e) {
                System.out.println("No se aceptan letras u otros símbolos. Por favor, pruebe de nuevo.");
                scanner.nextLine(); //Limpia el buffer y evita que entre en bucle infinito
                entradaCorrecta = false; //Si captura la excepción, vuelve la variable falsa y entra en el bucle
            }
        } while (!entradaCorrecta); //Mientras la variable sea falsa, seguirá el bucle
        return numero;
    }
    //Declaro el método para leer un decimal
    public static  double leerDecimal(String mensaje) {
        double decimal = 0.0;
        boolean entradaCorrecta;
        do {
            entradaCorrecta = true;
            try { //Usamos try...catch para manejar las excepciones
                System.out.println(mensaje);
                decimal = scanner.nextDouble();
                scanner.nextLine(); //Limpia el buffer   
            } catch (Exception e) {
                System.out.println("No se aceptan letras u otros símbolos.\nUse ',' como coma. Por favor, pruebe de nuevo.");
                scanner.nextLine(); //Limpia el buffer
                entradaCorrecta = false;
            }
        } while (!entradaCorrecta);
        return decimal;
    }
    //Declaro el método para leer una cadena de texto
    public static  String leerCadena(String mensaje) { //Al ser cadena de texto, admite cualquier símbolo o número, no hay que manejar excepciones
        System.out.println(mensaje);
        String texto = scanner.nextLine();
        return texto;
    }
    //Declaro el método para leer un booleano
    public static  boolean leerBooleano(String mensaje) {
        boolean respuesta = true;
        boolean entradaCorrecta;
        do {
            entradaCorrecta = true;
            try {
                System.out.println(mensaje);
                respuesta = scanner.nextBoolean();
            } catch (Exception e) {
                System.out.println("Se aceptan como entradas válidas: true o false. Por favor, pruebe de nuevo.");
                scanner.nextLine(); //Limpia el buffer
                entradaCorrecta = false;
            }
        } while (!entradaCorrecta);
        return respuesta;
    }
}
