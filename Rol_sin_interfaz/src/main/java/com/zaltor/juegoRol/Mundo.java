/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logs.Logger;
import com.zaltor.excepciones.ZonaBloqueadaException;
import com.zaltor.excepciones.JuegoException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que controla la exploración del jugador, la generación de enemigos, el combate y el flujo del juego.
 * @author Mar
 */
public class Mundo {
    protected int contadorEnemigos = 0;
    // Esta variable se puede cambiar si quieres hacer más corto el juego.
    protected int nivelFinal = 10;
    
    /**
     * @return Retorna el enemigo aleatorio.
     */
    
    // Método para elegir aleatoriamente un enemigo.
    public Enemigo generarEnemigoAleatorio() {
        int indiceEnemigoAleatorio;
        Enemigo enemigoAleatorio;
        ArrayList<Enemigo> enemigos = new ArrayList<>();

        LoboSalvaje ls = new LoboSalvaje(" ", 0, 0, 0, 0, 0, 0, 0, 0);
        GuerreroOscuro go = new GuerreroOscuro(" ", 0, 0, 0, 0, 0, 0, 0, 0);
        NoMuerto no = new NoMuerto(" ", 0, 0, 0, 0, 0, 0, 0, 0);

        enemigos.add(ls);
        enemigos.add(go);
        enemigos.add(no);

        indiceEnemigoAleatorio = (int) (Math.random() * enemigos.size());
        enemigoAleatorio = enemigos.get(indiceEnemigoAleatorio);
        
        contadorEnemigos ++;
        
        return enemigoAleatorio;
    }

    /**
     * 
     * @param enemigo Implemento las opciones de acción de los enemigos.
     * @param jugador Jugador recibe las acciones de Enemigo.
     */ 
    public void accionEnemigo(Enemigo enemigo, Personaje jugador) {
        int accion = (int) (Math.random() * 3);
        
        System.out.println("Puntos de vida de " + enemigo.nombrePersonaje + ": " + enemigo.getVida() + ".");
                
        switch (accion) {
            
            case 0:
                jugador.recibirDanio(jugador, enemigo, false, false, false);
                Logger.registrarEvento(enemigo.getNombrePersonaje() + " ataca a " + jugador.getNombrePersonaje() +".");
                break;
            case 1:
                enemigo.activarDefensa();
                System.out.println("\nLa defensa de " + enemigo.nombrePersonaje + " está activada.");
                Logger.registrarEvento(enemigo.getNombrePersonaje() + " activa defensa.");
                break;
            case 2:
                enemigo.habilidadEspecial(jugador, enemigo, false);
                Logger.registrarEvento(enemigo.getNombrePersonaje() + " usa Habilidad Especial.");
                break;
            default:

        }
    }
    
    /**
     * 
     * @param jugador Se alternan las acciones de Jugador con las de Enemigo.
     * @param enemigo En su turno realiza acciones sobre Jugador y viceversa.
     * @param sc Es necesario porque se llama al método realizarAccion que necesita Scanner.
     */
    public void iniciarCombate(Personaje jugador, Enemigo enemigo, Scanner sc) {
        
        Juego j = new Juego();
        
        // Si Jugador llega al nivel marcado, se enfrenta a Morgah.
        if (jugador.getNivel() == nivelFinal) {
            Morghan m = new Morghan("", 0, 0, 0, 0, 0, 0, 0, 0);
            enemigo = m;
            System.out.println("El temible Rey Oscuro se presenta ante ti, el destino de las Tierras de Zaltor está en tus manos intrépida " + jugador.nombrePersonaje + ".");
            Logger.registrarEvento("Aparece el Rey Oscuro.");
        } else {
            enemigo = generarEnemigoAleatorio();
            System.out.println("\n¡¡CUIDADO!! " + enemigo.getNombrePersonaje() + " apareció.");
            System.out.println("Se interpone en tu camino, si quieres continuar tu viaje, debes vencerlo.");
            System.out.println("Te has enfrentado a " + (contadorEnemigos - 1) + " enemigos hasta ahora.");
        }
        
        
        /** En barbecho, se implementará próximamente.
        if (jugador.getVelocidad() >= enemigo.getVelocidad()) {
            System.out.println(jugador.getNombrePersonaje() + " es más rápida que " + enemigo.getNombrePersonaje() + ", ataca primero.");
        } else {
            System.out.println(enemigo.getNombrePersonaje() + " es más rápido que " + jugador.getNombrePersonaje() + ", ataca primero.");
        } */
        
        
                
        System.out.println("\n¡Comienza el combate entre " + jugador.getNombrePersonaje() + " y " + enemigo.getNombrePersonaje() + "!");
        System.out.println(jugador.getNombrePersonaje() + " posee " + jugador.getVida() + " puntos de vida.");
        System.out.println(enemigo.getNombrePersonaje() + " posee " + enemigo.getVida() + " puntos de vida.");
        
        Logger.registrarEvento("Combate iniciado: " + jugador.getNombrePersonaje() + " vs " + enemigo.getNombrePersonaje() + ".");
                
        boolean sigueCombate = false;
        
        do {            
            if (jugador.getVida() > 0) {
                System.out.println("\n|=====================Turno de " + jugador.getNombrePersonaje() + "=====================|");
                Logger.registrarEvento("Turno de--> " + jugador.getNombrePersonaje() + ".");
                
                j.realizarAccion(jugador, enemigo, sc);
                
            } 
            if (enemigo.getVida() > 0) {
                System.out.println("\n|=====================Turno de " + enemigo.getNombrePersonaje() + "=====================|");
                Logger.registrarEvento("Turno de--> " + enemigo.getNombrePersonaje() + ".");
                
                accionEnemigo(enemigo, jugador);
                System.out.println("");
                
            } else {
                System.out.println("\n¡¡VENCISTE!!");
                Logger.registrarEvento(jugador.nombrePersonaje + " VENCIÓ a " + enemigo.getNombrePersonaje() + ".");
            }
             // Condiciones para que se pare el bucle.
            sigueCombate = true;
            if (jugador.getVida() <= 0 || enemigo.getVida() <= 0) {
                sigueCombate = false;
            }
            
                    
        } while (sigueCombate);
        
        // Tras el combate se comprueba si el jugador tiene experiencia para subir de nivel, si está muerto, no.
        if(jugador.getVida() > 0) {
            jugador.ganarExperiencia(enemigo);
        }
        
        
        
    }
    
    /**
     * 
     * @param jugador Se necesita para iniar combate.
     * @param enemigo Se necesita para iniciar combate.
     * @param sc Scanner para que Jugador elija camino.
     * @throws ZonaBloqueadaException Lanzará una excepción cuando la zona donde Jugador quiera acceder esté bloqueada.
     */
    public void flujoJuego(Personaje jugador, Enemigo enemigo, Scanner sc) throws ZonaBloqueadaException {
        boolean sigueJuego = false;
        String eligeCamino;
                
        do { 
            System.out.println("\n¿Qué camino quieres elegir?");
            System.out.println("\nPulsa 2 para ir al CASTILLO OSCURO.");
            System.out.println("Pulsa 1 para seguir caminando.");
            eligeCamino = sc.nextLine();
            
            try {
                switch (eligeCamino) {
                case "1":
                    System.out.println("\nVas caminando alegremente por las Tierras de Zaltor cuando de repente...");
                    Logger.registrarEvento(jugador.getNombrePersonaje() + " continúa caminando.");
                    break;
                case "2": // Excepción.
                    throw new ZonaBloqueadaException(" ");
                    
                default:
                    System.out.println("Opción no encontrada.");
                }
            } catch (ZonaBloqueadaException e) {
                System.out.println(e.getMessage());
                Logger.registrarEvento("ERROR --> Intento de entrar en el Castillo Oscuro sin la llave.");
            }
                        
            iniciarCombate(jugador, enemigo, sc);
            
            // Condiciones para que pare el while.
            sigueJuego = true;
            if (jugador.getVida() <= 0) {
                sigueJuego = false;
            }
            if (jugador.getNivel() == nivelFinal) {
                sigueJuego = false;
            }
        
        } while (sigueJuego);
        
        if (jugador.getVida() > 0) {
            System.out.println("\n" + jugador.getNombrePersonaje() + " has alcanzado el nivel " + nivelFinal + " estás preparada para enfrentarte al temible Rey Caído, Morghan.");
            iniciarCombate(jugador, enemigo, sc);
        } else {
            // Manejo de la excepción.
            try {
              jugador.estaVivo();  
            } catch (JuegoException e) {
                System.out.println("\nVida de " + jugador.getNombrePersonaje() + ": " + jugador.getVida());
                System.out.println("Combate finalizado.");
                System.out.println(jugador.getNombrePersonaje() + " perdió la batalla...");
                System.out.println("\nERROR --> " + e.getMessage());
                Logger.registrarEvento(jugador.getNombrePersonaje() + " Ha muerto.");
            }
            
        }
        

        

    }
    
    
    
        
    
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
