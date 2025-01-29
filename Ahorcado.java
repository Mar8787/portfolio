/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mar.ejercicioglobal;

import java.util.Scanner;
import java.util.Arrays;

/**
 * @author mss7
 */
public class Ahorcado {

    /**
     * Juego del Ahorcado
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\n¡Bienvenida al juego del ahorcado!\nDebe adivinar la palabra oculta antes de cometer 6 fallos.\n\nNormas del ahorcado:\n" +
                " -Debe escribir con el teclado la letra que piense que pueda estar en la palabra que debe adivinar y pulsar intro para comprobar si es correcta.\n" +
                " -Si escribe una palabra, contará la primera letra de esa palabra como intento.\n -Si repite una letra, no contará como intento.\n" + 
                " -Cada vez que le pidamos una letra, podrá ver el progreso que lleve de las letras acertadas y las letras falladas.\n");
        String palabraElegida = palabraAleatoria();
        letrasProbadas(palabraElegida);
    }
        /**
         * En este método tengo un array con las palabras del juego. 
         * En el índice del array usamos Math.random para que extraiga una palabra aleatoria.
         * En palabraElegida se almacena esa palabra, así que se retorna al main.
         * @return
         */
        public static String palabraAleatoria() {
            String palabraElegida;
            String [] listaPalabras = {"joya", "murcielago", "abuelito", "perreo", "esquirol", "okupa", "payaso", "jerezana", "contratiempo", "miccionar", "repechar", "salvador", "mentecata", "transistor"}; //Creo el array con las palabras para jugar
           
            palabraElegida = listaPalabras[(int) (Math.random()*listaPalabras.length)];
            System.out.println("La palabra que debe adivinar tiene "+palabraElegida.length()+" letras.\n");
            
            return palabraElegida;
        }
        /**
         * En este metodo se recogen las letras probadas por el usuario y se comprueba que están en la palabra elegida
         * Se crea un array con _ que se vaya sustituyendo por las letras en sus índices correctos
         * Se repetirá durante 6 errores o hasta que complete la palabra
         * @param palabraElegida
         */
        public static void letrasProbadas(String palabraElegida) {
            char letraUsuario;
            String escritoUsuario;
            int indicePalabra = 0;
            int indiceSubPalabra = 0;
            int contadorErrores = 0;
            int erroresPermitidos = 6;
            String subPalabraElegida;
            boolean letraEncontrada;
            Scanner leer = new Scanner(System.in);
            char [] aciertos = new char[palabraElegida.length()];
            char [] letrasFalladas = new char[6];
           
            for (int i =0; i < aciertos.length; i++) {
                aciertos[i] = '_'; // Se llena el array de char que en un principio tendra _ en sus índices
            }
            
            while (contadorErrores < erroresPermitidos && new String(aciertos).contains("_")) { //Transformamos el array char en String para usar .contains y ver si quedan _
                letraEncontrada = false; //Iniciamos el booleano como false cada vez que pasa por el bucle
                
                System.out.print("Escriba una letra que piense que pueda estar en la palabra y pulse intro: ");
                escritoUsuario = leer.nextLine().toLowerCase();
                
                if (escritoUsuario.isEmpty()) { //Para que no de error cuando pulsen solo intro
                    System.out.println("\nDebe escribir una letra para jugar, inténtelo de nuevo:");
                    continue; //Para que vuelva al inicio del bucle
                }
                
                letraUsuario = escritoUsuario.charAt(0); //Cogemos el primer caracter
                if (!Character.isLetter(letraUsuario)) { //Comprobamos si es una letra
                    System.out.println("\nHa introducido un caracter no válido, por favor, inténtelo de nuevo.\n");
                    continue;
                } 
                                
                subPalabraElegida = palabraElegida; //Iniciamos la subcadena
                indicePalabra = subPalabraElegida.indexOf(letraUsuario);
                               
                while (indicePalabra != -1) {
                    letraEncontrada = true;
                    indiceSubPalabra = palabraElegida.length() - subPalabraElegida.length() + indicePalabra;
                    aciertos[indiceSubPalabra] = letraUsuario;
                    
                    subPalabraElegida = subPalabraElegida.substring(indicePalabra +1); //Cortamos la palabra desde donde hemos encontrado la letra 
                    indicePalabra = subPalabraElegida.indexOf(letraUsuario); //Buscamos si vuelve a aparecer la misma letra
                }
            
                if (!letraEncontrada) { //Si este booleano es falso, entramos en el bucle
                    if (new String(letrasFalladas).indexOf(letraUsuario) == -1) { //Para que no de como fallo una letra ya fallada que repitas
                        contadorErrores++;
                        System.out.println("\nLetra incorrecta. Le quedan "+(erroresPermitidos - contadorErrores)+" intentos.");
                        letrasFalladas[contadorErrores-1] = letraUsuario;
                    } else {
                        System.out.println("\n¡Ya probaste esta letra! Inténtalo de nuevo con otra.");
                    }
                } else {
                    System.out.println("¡Letra acertada!");
                }
                System.out.println("\nPapabra a adivinar: "+new String(aciertos)); //Pasamos la cadena de caracteres a String
                System.out.println("Ha probado y fallado estas letras: "+Arrays.toString(letrasFalladas));
            }
            
            if (new String(aciertos).indexOf('_') == -1) {
                System.out.println("\n¡ENHORABUENA!\n¡Ha adivinado la palabra!");
            } else {
                System.out.println("\nLo sentimos, ha cometido 6 fallos, por lo tanto, ha perdido la partida. \nLa palabra era: "+palabraElegida);  
            }
            leer.close();
        }
}