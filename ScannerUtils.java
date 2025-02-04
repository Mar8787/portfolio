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
        System.out.println(mensaje); //Va a imprimir el mensaje que escriba en el .java donde importe esta librería
        int numero = scanner.nextInt();
        scanner.nextLine(); //Esto sirve para consumir el salto de línea y que no lo lea el sacanner de texto e imprima un espacio en blanco
        return numero;
    }
    //Declaro el método para leer un decimal
    public static  double leerDecimal(String mensaje) {
        System.out.println(mensaje);
        double decimal = scanner.nextDouble();
        scanner.nextLine();       
        return decimal;
    }
    //Declaro el método para leer una cadena de texto
    public static  String leerCadena(String mensaje) {
        System.out.println(mensaje);
        String texto = scanner.nextLine();
        return texto;
    }
    //Declaro el método para leer un booleano
    public static  boolean leerBooleano(String mensaje) {
        System.out.println(mensaje);
        boolean respuesta = scanner.nextBoolean();
        return respuesta;
    }
 
}
