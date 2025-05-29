/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logs.Logger;
import com.zaltor.excepciones.EnergiaInsuficienteException;
import com.zaltor.excepciones.ZonaBloqueadaException;
import java.util.Scanner;

/**
 * Clase que controla la creación de personaje, flujo del juego e interacción con la jugadora.
 * Main.
 * @author Mar
 */
public class Juego {
    
    /**
     * 
     * @param args Creo el main, donde se presentará el juego, las estadísticas de los personajes y se gestionarán los ataques del jugador
     */
    public static void main(String[] args) {
        Logger.registrarEvento("Comienza el juego.");
        Scanner sc = new Scanner(System.in);
        String verPersonaje;
        
        /**
         * Creo instancias de clases para llamar a distintos métodos.
         */
        Mundo m = new Mundo();
        Juego j = new Juego(); 
        
        Guerrero g = new Guerrero(" ", 0, 0, 0, 0, 0, 0, 0, false);
        Chaman c = new Chaman(" ", 0, 0, 0, 0, 0, 0, 0);
        Ninja n = new Ninja(" ", 0, 0, 0, 0, 0, 0, 0, false);
        Enemigo ene = new Enemigo(" ", 0, 0, 0, 0, 0, 0, 0, 0);
        
        /**
         * Bienvenida e introducción al juego.
         */
        System.out.println("Bienvenida a Las Tierras de Zaltor...");
        System.out.println("Para comenzar el juego, debes elegir un tipo de personaje.");
        System.out.println("Para ver las estadísticas de cada personaje, pulsa su número:");
        
        /**
         * Muestra las estadísticas de los personajes, eligiendo cual ver mediante un switch.
         */
        do {            
            System.out.println("\nPulsa 1 para ver las estadísticas de Guerrero.");
            System.out.println("Pulsa 2 para ver las estadísticas de Chaman.");
            System.out.println("Pulsa 3 para ver las estadísticas de Ninja.");
            System.out.println("Pulsa 0 para salir de la vista de personajes.");
            verPersonaje = sc.nextLine();
            
            switch (verPersonaje) {
                case "1":
                    g.mostrarInfo();
                    break;
                case "2":
                    c.mostrarInfo();
                    break;
                case "3":
                    n.mostrarInfo();
                    break;
                case "0":
                    System.out.println("\nSaliendo de la vista de personajes...");
                    break;
                default:
                    System.err.println("\nPersonaje no encontrado, por favor, introduce el número del personaje que deseas ver.");
            }
            
        } while (!verPersonaje.equals("0"));
        
        /**
         * Llamo a los métodos en el main para que se ejecuten.
         */
        Personaje p = j.seleccionarClase(sc);
        
        // Este método maneja una excepcion
        try {
            m.flujoJuego(p, ene, sc);
        } catch (ZonaBloqueadaException e) {
        }
        // Cierro el Scanner.
        sc.close();
        
    }

    /**
     * 
     * @param sc Scanner para elegir personaje.
     * @return Retorna el personaje elegido
     */
    public Personaje seleccionarClase(Scanner sc) {
        String eleccionPersonaje;
        Personaje p = null; // p se "llena" con la elección del personaje que hagas.

        System.out.println("\nViajera de Las Tierras de Zaltor, antes de comenzar tu aventura, debes elegir un personaje y bautizarlo con la sangre de un dragón.");
        System.out.println("¿Qué personaje deseas ser?");
        System.out.println("\nPulsa el número del personaje que elijas:");
        
        do {
            System.out.println("Pulsa 1 para elegir al Guerrero.");
            System.out.println("Pulsa 2 para elegir al Chaman.");
            System.out.println("Pulsa 3 para elegir al Ninja.");
            eleccionPersonaje = sc.nextLine();

            // Switch para elegir personaje jugable.
            switch (eleccionPersonaje) {
                case "1":
                    p = new Guerrero(eleccionPersonaje, 0, 0, 0, 0, 0, 0, 0, false);
                 
                    System.out.println("\n¡Enhorabuena! Has elegido al Guerrero.");
                    System.out.println("¿Estás segura de tu elección? Si es así pulsa 0, pero si deseas cambiar de personaje, pulsa su número:\n");
                    Logger.registrarEvento("La jugadora elige al Guerrero");
                    break;
                case "2":
                    p = new Chaman(eleccionPersonaje, 0, 0, 0, 0, 0, 0, 0);
                 
                    System.out.println("\n¡Enhorabuena! Has elegido al Chaman.");
                    System.out.println("¿Estás segura de tu elección? Si es así pulsa 0, pero si deseas cambiar de personaje, pulsa su número:\n");
                    Logger.registrarEvento("La jugadora elige al Chaman");
                    break;
                case "3":
                    p = new Ninja(eleccionPersonaje, 0, 0, 0, 0, 0, 0, 0, false);
                
                    System.out.println("\n¡Enhorabuena! Has elegido al Ninja.");
                    System.out.println("¿Estás segura de tu elección? Si es así pulsa 0, pero si deseas cambiar de personaje, pulsa su número:\n");
                    Logger.registrarEvento("La jugadora elige al Ninja");
                    break;
                case "0":
                    
                    break;
                default:
                    System.err.println("\nPersonaje no encontrado, por favor, introduce el número del personaje que deseas elegir.");
            }
                    
        } while (!eleccionPersonaje.equals("0"));
        
        // Elección de nombre para el personaje.
        System.out.println("\nIntrépida aventurera, ¿con qué nombre deseas ser conocida en Las Tierras de Zaltor?");
        
        p.setNombrePersonaje(sc.nextLine());
        
        System.out.println("\nDe acuerdo, desde hoy serás conocida como " + p.getNombrePersonaje() + ".");
        Logger.registrarEvento("La jugadora nombra a su personaje como: " + p.getNombrePersonaje() + ".");
        System.out.println("La aventura da comienzo...");
                       
        return p;
    }
    
    /**
     * 
     * @param jugador Jugador que realiza la acción.
     * @param enemigo Enemigo que recibe la acción del jugador.
     * @param sc Scanner para elegir la acción.
     */
    public void realizarAccion(Personaje jugador, Enemigo enemigo, Scanner sc) {
        String accion;
                        
            System.out.println("Puntos de vida de " + jugador.nombrePersonaje + ": " + jugador.getVida() + ".");
            System.out.println("Elige que acción realizar pulsando el número que corresponda:");
            
            System.out.println("\nPara ATACAR pulsa 1.");
            System.out.println("Para DEFENDERTE pulsa 2.");
            System.out.println("Para usar HABILIDAD ESPECIAL pulsa 3.");
            
            accion = sc.nextLine();
            
            switch (accion) {
                case "1":
                    jugador.atacar(enemigo, false, false, false);
                    Logger.registrarEvento(jugador.getNombrePersonaje() + " ataca a " + enemigo.getNombrePersonaje());
                    break;
                case "2":
                    jugador.activarDefensa();
                    System.out.println("\nLa defensa de " + jugador.nombrePersonaje + " está activada.");
                    Logger.registrarEvento(jugador.getNombrePersonaje() + " activa la defensa.");
                    break;
                case "3":
                    try {
                        jugador.usarHabilidadEspecial(jugador, enemigo, false);
                        Logger.registrarEvento(jugador.getNombrePersonaje() + " usa Habilidad Especial.");
                    } catch (EnergiaInsuficienteException e) { // Maneja la excepción y lanza un mensaje
                        System.out.println("\nERROR -->" + e.getMessage()); 
                        Logger.registrarEvento("ERROR --> " + e.getMessage());
                    }
                    break;
                
                default:
                    System.err.println("\nAcción no encontrada, por favor, pulsa el número de la acción que deseas ejecutar.");
            }
    }
        
        
        
        
       
        
        
        
        
        
        
    
    
}
