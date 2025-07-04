/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.zaltor.iniciarJuego;

import com.zaltor.gui.VentanaJuego;

/**
 * Clase que inicia el juego de rol.
 *
 * @author mss7
 */
public class Rol {

    /**
     * El método llama a ventanaJuego para que se ejecute de forma seguraen el
     * hilo de eventos de Swing, que gestiona las interfaces gráfica. Hace
     * visible ventanaJuego yu llama al método que gestiona las fases del juego.
     *
     * @param args
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VentanaJuego ventanaJuego = new VentanaJuego();
            ventanaJuego.setVisible(true);
            ventanaJuego.gestionarFase(0);
        });
    }

}
