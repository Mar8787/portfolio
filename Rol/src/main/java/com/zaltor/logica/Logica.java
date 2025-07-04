/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.logica;

import com.zaltor.juegoRol.*;
import com.zaltor.logs.*;
import com.zaltor.gui.*;
import com.zaltor.dao.*;

import java.util.ArrayList;

/**
 * Clase que controla gran parte de la lógica del juego.
 *
 * @author mss7
 */
public class Logica {

    private VentanaJuego vj;
    private Juego j;
    private Personaje jugador;

    /**
     * Constructor para poder usar métodos de otras clases sin instanciarlas.
     *
     * @param vj
     * @param j
     */
    public Logica(VentanaJuego vj, Juego j, Personaje jugador) {
        this.vj = vj;
        this.j = j;
        this.jugador = jugador;
    }

    /**
     * Métodos que controlan la acción del enemigo y su generación
     * aleatoriamente.
     * Incluye la llamada a método que guarda en la db el combate si pierde
     *
     * @param enemigo
     * @param jugador
     */
    public void accionEnemigo(Enemigo enemigo, Personaje jugador) {
        int accion = (int) (Math.random() * 3);

        switch (accion) {

            case 0:
                enemigo.infligirDanio(jugador, enemigo, false, false, false);
                Logger.registrarEvento(enemigo.getNombrePersonaje() + " ataca a " + jugador.getNombrePersonaje() + ".");
                vj.mostrarMensaje(enemigo.getNombrePersonaje() + " ataca a " + jugador.getNombrePersonaje());
                vj.setJpbVidaJugador(jugador.getVida());

                break;
            case 1:
                enemigo.activarDefensa();
                vj.mostrarMensaje("La defensa de " + enemigo.getNombrePersonaje() + " está activada.");
                Logger.registrarEvento(enemigo.getNombrePersonaje() + " activa defensa.");
                break;
            case 2:
                enemigo.habilidadEspecial(jugador, enemigo, false);
                vj.setJpbVidaJugador(jugador.getVida());
                vj.mostrarMensaje(enemigo.getNombrePersonaje() + " usa Habilidad Especial.");
                Logger.registrarEvento(enemigo.getNombrePersonaje() + " usa Habilidad Especial.");
                break;
            default:

        }

        if (jugador.getVida() <= 0) {
            vj.mostrarMensaje("\n¡¡MALDICIÓN!! Has muerto a manos de " + enemigo.getNombrePersonaje());
            vj.habilitarBotones(false);

            // Guarda la derrota en la db.
            PersonajeDAO personajeDAO = new PersonajeDAO();
            EnemigoDAO enemigoDAO = new EnemigoDAO();
            CombateDAO combateDAO = new CombateDAO();

            int idPersonaje = personajeDAO.obtenerIdPorNombre(jugador.getNombrePersonaje());
            int idEnemigo = enemigoDAO.obtenerIdPorTipo(enemigo.getNombrePersonaje());

            // Calcula los segundos que dura el combate
            long tiempoFinCombate = System.currentTimeMillis();
            int duracionSegundos = (int) ((tiempoFinCombate - vj.getTiempoInicioCombate()) / 1000);

            combateDAO.registrarCombate(idPersonaje, idEnemigo, "Derrota", 0, duracionSegundos);

            vj.mostrarDialogoMuerte();

        }
    }

    /**
     *
     * @return Método para elegir aleatoriamente un enemigo.
     */
    public Enemigo generarEnemigoAleatorio() {
        int indiceEnemigoAleatorio;
        Enemigo enemigoAleatorio;

        ArrayList<Enemigo> enemigos = new ArrayList<>();

        LoboSalvaje ls = new LoboSalvaje("Lobo Salvaje");
        GuerreroOscuro go = new GuerreroOscuro("Guerrero Oscuro");
        NoMuerto no = new NoMuerto("No-Muerto");

        enemigos.add(ls);
        enemigos.add(go);
        enemigos.add(no);

        indiceEnemigoAleatorio = (int) (Math.random() * enemigos.size());
        enemigoAleatorio = enemigos.get(indiceEnemigoAleatorio);

        return enemigoAleatorio;
    }

}
