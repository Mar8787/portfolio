/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.zaltor.gui;

import com.zaltor.juegoRol.*;
import com.zaltor.logica.*;
import com.zaltor.dao.*;

/**
 * Clase principal de la interfaz gráfica.
 *
 * @author Mar
 */
public class VentanaJuego extends javax.swing.JFrame {

    // Para los case de las fases del juego.
    private static final int faseInicio = 0;
    private static final int faseEleccionNombre = 1;
    private static final int faseEstadisticas = 2;
    private static final int faseEleccionPersonaje = 3;

    private static final int faseCombate = 5;
    private static final int faseFinal = 6;

    private int fase = faseInicio;
    private static String mensaje = "";
    public String nombreJugador;
    public long tiempoInicioCombate;

    private Enemigo enemigo;
    private Personaje jugador;
    private Juego j = new Juego(this);
    private Logica l = new Logica(this, j, jugador);
    public VentanaNombre vn;
    EnemigoDAO enemigoDAO = new EnemigoDAO();

    /**
     * Creates new form VentanaJuego Las imágenes, botones y etiquetas
     * permanecen ocultas hasta que se necesiten.
     */
    public VentanaJuego() {
        initComponents();
        jlGuerrera.setVisible(false);
        jlChaman.setVisible(false);
        jlNinja.setVisible(false);

        jlLobo.setVisible(false);
        jlGuerreroOscuro.setVisible(false);
        jlNoMuerto.setVisible(false);
        jlRey.setVisible(false);

        jlNombreJugador.setVisible(false);
        jlNombreGuerreroOscuro.setVisible(false);
        jlNombreLobo.setVisible(false);
        jlNombreNoMuerto.setVisible(false);
        jlNombreRey.setVisible(false);
        
        barrasVisibles(false);
        habilitarBotones(false);
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     *
     * @return devuelve el tiempo de combate
     */
    public long getTiempoInicioCombate() {
        return tiempoInicioCombate;
    }

    /**
     *
     * @param vida Para que se vea la vida del jugador en su barra.
     */
    public void setJpbVidaJugador(int vida) {
        jpbVidaJugador.setValue(vida);
        jpbVidaJugador.setString(vida + " / " + jpbVidaJugador.getMaximum());
    }

    /**
     *
     * Gestiona el flujo del juego
     *
     * @param comando variable que se corresponde con un número.
     */
    public void gestionarFase(int comando) {
        System.out.println("gestionarFase() " + comando);
        switch (fase) {
            case faseInicio:
                mostrarMensaje("Bienvenida a Las Tierras de Zaltor...");
                mostrarMensaje("Intrépida aventurera, ¿con qué nombre deseas ser conocida?");

                fase = faseEleccionNombre;
                gestionarFase(fase);
                break;
            case faseEleccionNombre:
                VentanaNombre vn = new VentanaNombre(this, true);
                vn.setLocationRelativeTo(this);
                vn.setVisible(true);

                nombreJugador = vn.getNombreJugador();
                if (nombreJugador != null && !nombreJugador.isBlank()) {
                    mostrarMensaje("\nHas elegido llamarte " + nombreJugador + ".");
                    pintarNombreJugador();
                    jlNombreJugador.setVisible(true);

                    fase = faseEstadisticas;
                    gestionarFase(fase);
                }

                break;
            case faseEstadisticas:
                mostrarMensaje("\nAntes de elegir un personaje, puedes ver las estadísticas pulsando el botón con su nombre.");

                VentanaEstadisticas ve = new VentanaEstadisticas(this, true);
                ve.setLocationRelativeTo(this);
                ve.setVisible(true);

                fase = faseEleccionPersonaje;
                gestionarFase(fase);
                break;
            case faseEleccionPersonaje:
                mostrarMensaje("\nElije qué personaje quieres ser pulsando el botón con su nombre.");

                VentanaEleccionPersonaje selector = new VentanaEleccionPersonaje(this, true);
                selector.setLocationRelativeTo(this);
                selector.setVisible(true);

                String eleccion = selector.getPersonajeSeleccionado();

                if (eleccion != null) {
                    PersonajeDAO dao = new PersonajeDAO();
                    jugador = dao.obtenerPersonajePorNombre(nombreJugador);

                    if (jugador == null) {
                        // Si no existe en la db, se crea
                        switch (eleccion) {
                            case "Guerrera":
                                jugador = new Guerrero(nombreJugador);
                                jlGuerrera.setVisible(true);
                                break;
                            case "Chaman":
                                jugador = new Chaman(nombreJugador);
                                jlChaman.setVisible(true);
                                break;
                            case "Ninja":
                                jugador = new Ninja(nombreJugador);
                                jlNinja.setVisible(true);
                                break;
                        }

                        // Se guarda en la db
                        dao.insertarPersonaje(jugador);
                        mostrarMensaje("\nNuevo personaje creado y guardado.");
                    } else {
                        // Si ya existe, se carga
                        switch (jugador.getClass().getSimpleName()) {
                            case "Guerrero":
                                jlGuerrera.setVisible(true);
                                break;
                            case "Chaman":
                                jlChaman.setVisible(true);
                                break;
                            case "Ninja":
                                jlNinja.setVisible(true);
                                break;
                        }
                        mostrarMensaje("\nBienvenida de nuevo " + jugador.getNombrePersonaje() + ".");
                    }

                }

                fase = faseCombate;
            case faseCombate:
                barrasVisibles(true);
                habilitarBotones(true);
                enemigo = l.generarEnemigoAleatorio();

                // Para calcular el tiempo de combate.
                tiempoInicioCombate = System.currentTimeMillis();

                // Se iguala el nivel de los enemigos al jugador.
                enemigo.setNivel(jugador.getNivel());

                // Para que la vida y demás d elos enemigos suba también al subir de nivel.
                int nivelJugador = jugador.getNivel();
                int bonusPorNivel = (nivelJugador - 1) * 5;
                enemigo.setVida(enemigo.getVida() + (nivelJugador - 1) * 50);
                enemigo.setAtaque(enemigo.getAtaque() + bonusPorNivel);
                enemigo.setDefensa(enemigo.getDefensa() + bonusPorNivel);

                // Guarda el enemigo en su tabla de la db.
                enemigoDAO.insertarEnemigo(enemigo);

                iniciarBarras();
                refrescarBarras();

                mostrarMensaje("\n¡CUIDADO!\n¡" + enemigo.getNombrePersonaje() + " de nivel " + enemigo.getNivel() + " apareció!");

                mostrarMensaje("\nElige que acción realizar pulsando el botón que corresponda:");
                mostrarMensaje("Para atacar, pulsa el botón ATACAR.");
                mostrarMensaje("Para defender, pulsa el botón DEFENDER.");
                mostrarMensaje("Para usar tu habilidad especial, pulsa el botón HABILIDAD ESPECIAL.\n");

                // Hace visibles las fotos y nombres de enemigos cuando aparecen.
                if (enemigo instanceof GuerreroOscuro) {
                    jlGuerreroOscuro.setVisible(true);
                    jlLobo.setVisible(false);
                    jlNoMuerto.setVisible(false);

                    jlNombreGuerreroOscuro.setVisible(true);
                    jlNombreNoMuerto.setVisible(false);
                    jlNombreLobo.setVisible(false);
                }
                if (enemigo instanceof NoMuerto) {
                    jlNoMuerto.setVisible(true);
                    jlGuerreroOscuro.setVisible(false);
                    jlLobo.setVisible(false);

                    jlNombreNoMuerto.setVisible(true);
                    jlNombreGuerreroOscuro.setVisible(false);
                    jlNombreLobo.setVisible(false);
                }
                if (enemigo instanceof LoboSalvaje) {
                    jlLobo.setVisible(true);
                    jlNoMuerto.setVisible(false);
                    jlGuerreroOscuro.setVisible(false);

                    jlNombreLobo.setVisible(true);
                    jlNombreGuerreroOscuro.setVisible(false);
                    jlNombreNoMuerto.setVisible(false);

                }
                break;
            case faseFinal:
                habilitarBotones(true);

                mostrarMensaje("\n¡¡Has alcanzado el nivel final!!");
                mostrarMensaje("\nEl Rey Oscuro Morghan ha aparecido");
                enemigo = new Morghan("Morghan");

                // Guarda en la db al Rey Oscuro.
                enemigoDAO.insertarEnemigo(enemigo);

                iniciarBarras();
                refrescarBarras();

                // aparece el Rey Oscuro
                jlRey.setVisible(true);
                jlGuerreroOscuro.setVisible(false);
                jlNoMuerto.setVisible(false);
                jlLobo.setVisible(false);

                jlNombreRey.setVisible(true);
                jlNombreLobo.setVisible(false);
                jlNombreGuerreroOscuro.setVisible(false);
                jlNombreNoMuerto.setVisible(false);
                break;

        }

    }

    /**
     *
     * Setea el mensaje que se quiere mostrar por pantalla.
     *
     * @param texto
     */
    public void mostrarMensaje(String texto) {
        mensaje += "\n" + texto;
        jtTexto.setText(mensaje);
    }

    /**
     * Muestra el nombre del jugador.
     */
    public void pintarNombreJugador() {
        jlNombreJugador.setText(nombreJugador);
    }

    /**
     * PAra habilitar/deshabilitar los botones.
     *
     * @param habilitado
     */
    public void habilitarBotones(boolean habilitado) {
        jbAtacar.setEnabled(habilitado);
        jbHabilidadEspecial.setEnabled(habilitado);
        jbDefender.setEnabled(habilitado);
    }
    /**
     * 
     * @param visible recibe true o false para veo o no las barras 
     */
    public void barrasVisibles(boolean visible) {
        jpbVidaJugador.setVisible(visible);
        jpbEnergia.setVisible(visible);
        jpbNivelJugador.setVisible(visible);
        jpbExperienciaJugador.setVisible(visible);
        
        jlTextoEnergia.setVisible(visible);
        jlTextoExp.setVisible(visible);
        jlTextoNivel.setVisible(visible);
        jlTextoVidaJugador.setVisible(visible);
                
        jpbVidaEnemigo.setVisible(visible);
        jlTextoVidaEnemigo.setVisible(visible);
    }

    /**
     * Las barras cogen sus datos.
     */
    public void iniciarBarras() {
        jpbVidaEnemigo.setMaximum(enemigo.getVida());
        jpbVidaEnemigo.setMinimum(0);
        jpbVidaEnemigo.setStringPainted(true);

        jpbVidaJugador.setMaximum(jugador.getVida());
        jpbVidaJugador.setMinimum(0);
        jpbVidaJugador.setStringPainted(true);

        jpbExperienciaJugador.setMaximum(150);
        jpbExperienciaJugador.setMinimum(0);
        jpbExperienciaJugador.setStringPainted(true);

        jpbNivelJugador.setMaximum(10);
        jpbNivelJugador.setMinimum(1);
        jpbNivelJugador.setStringPainted(true);

        jpbEnergia.setMaximum(jugador.getEnergia());
        jpbEnergia.setMinimum(0);
        jpbEnergia.setStringPainted(true);
    }

    /**
     * Se resetean las barras.
     */
    public void refrescarBarras() {
        jpbVidaEnemigo.setValue(enemigo.getVida());
        jpbVidaEnemigo.setString(enemigo.getVida() + "/" + jpbVidaEnemigo.getMaximum());

        jpbVidaJugador.setValue(jugador.getVida());
        jpbVidaJugador.setString(jugador.getVida() + "/" + jpbVidaJugador.getMaximum());

        jpbExperienciaJugador.setValue(jugador.getExperiencia());
        jpbExperienciaJugador.setString(jugador.getExperiencia() + "/" + jpbExperienciaJugador.getMaximum());

        jpbNivelJugador.setValue(jugador.getNivel());
        jpbNivelJugador.setString(jugador.getNivel() + "/" + jpbNivelJugador.getMaximum());

        jpbEnergia.setValue(jugador.getEnergia());
        jpbEnergia.setString(jugador.getEnergia() + "/" + jpbEnergia.getMaximum());

    }

    /**
     * Cuando vences a un enemigo sale una ventana emergente, le das a continuar
     * y te sale otro enemigo.
     */
    public void venciEnemigo() {
        jugador.ganarExperiencia(enemigo);

        // Calcula el tiempo del conbate.
        long tiempoFinCombate = System.currentTimeMillis();
        int duracionSegundos = (int) ((tiempoFinCombate - tiempoInicioCombate) / 1000);

        // Obtener los IDs de la db.
        PersonajeDAO personajeDAO = new PersonajeDAO();
        EnemigoDAO enemigoDAO = new EnemigoDAO();
        CombateDAO combateDAO = new CombateDAO();

        int idPersonaje = personajeDAO.obtenerIdPorNombre(jugador.getNombrePersonaje());
        int idEnemigo = enemigoDAO.obtenerIdPorTipo(enemigo.getNombrePersonaje());

        // Guarda el combate en su tabla de la db si gana jugador.
        combateDAO.registrarCombate(idPersonaje, idEnemigo, "Victoria", enemigo.getExpOtorgada(), duracionSegundos);

        habilitarBotones(false);

        mostrarMensaje("=======Combate finalizado=======");
        mostrarMensaje("\n¡¡ENHORABUENA!! Has vencido a " + enemigo.getNombrePersonaje() + ".");
        mostrarMensaje("Recuperas 30 puntos de vida");

        // Sube vida al jugador.
        jugador.setVida(jugador.getVida() + 30);
        jdContinuar.setLocationRelativeTo(this);
        jdContinuar.pack();
        jdContinuar.setVisible(true);

        // Para llamar a la actualización de la db.
        personajeDAO.actualizarPersonaje(jugador);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdContinuar = new javax.swing.JDialog();
        jlbTextoContinuar = new javax.swing.JLabel();
        jbContinuar = new javax.swing.JButton();
        jdFin = new javax.swing.JDialog();
        jpPanelFin = new javax.swing.JPanel();
        jlTextoFin = new javax.swing.JLabel();
        jbFin = new javax.swing.JButton();
        jdMuerte = new javax.swing.JDialog();
        jpPanelMuerte = new javax.swing.JPanel();
        jlTextoMuerte = new javax.swing.JLabel();
        jbMuerteFin = new javax.swing.JButton();
        jpPrincipal = new javax.swing.JPanel();
        jpEnemigo = new javax.swing.JPanel();
        jlLobo = new javax.swing.JLabel();
        jlGuerreroOscuro = new javax.swing.JLabel();
        jlNoMuerto = new javax.swing.JLabel();
        jpbVidaEnemigo = new javax.swing.JProgressBar();
        jlTextoVidaEnemigo = new javax.swing.JLabel();
        jlNombreLobo = new javax.swing.JLabel();
        jlNombreGuerreroOscuro = new javax.swing.JLabel();
        jlNombreNoMuerto = new javax.swing.JLabel();
        jlNombreRey = new javax.swing.JLabel();
        jlRey = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jpJugador = new javax.swing.JPanel();
        jlNinja = new javax.swing.JLabel();
        jlChaman = new javax.swing.JLabel();
        jlGuerrera = new javax.swing.JLabel();
        jpbVidaJugador = new javax.swing.JProgressBar();
        jpbExperienciaJugador = new javax.swing.JProgressBar();
        jpbNivelJugador = new javax.swing.JProgressBar();
        jlTextoExp = new javax.swing.JLabel();
        jlTextoNivel = new javax.swing.JLabel();
        jlTextoVidaJugador = new javax.swing.JLabel();
        jpbEnergia = new javax.swing.JProgressBar();
        jlTextoEnergia = new javax.swing.JLabel();
        jlNombreJugador = new javax.swing.JLabel();
        jsConsola = new javax.swing.JScrollPane();
        jtTexto = new javax.swing.JTextArea();
        jpBotones = new javax.swing.JPanel();
        jbAtacar = new javax.swing.JButton();
        jbHabilidadEspecial = new javax.swing.JButton();
        jbDefender = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jlbTextoContinuar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTextoContinuar.setText("<html>¡Enhorabuena! Has vencido a tu oponente. <br> Para continuar, pulsa el botón CONTINUAR</html>");

        jbContinuar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbContinuar.setText("Continuar");
        jbContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbContinuarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jdContinuarLayout = new javax.swing.GroupLayout(jdContinuar.getContentPane());
        jdContinuar.getContentPane().setLayout(jdContinuarLayout);
        jdContinuarLayout.setHorizontalGroup(
            jdContinuarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdContinuarLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jlbTextoContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdContinuarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbContinuar)
                .addGap(118, 118, 118))
        );
        jdContinuarLayout.setVerticalGroup(
            jdContinuarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdContinuarLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jlbTextoContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jbContinuar)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jlTextoFin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoFin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTextoFin.setText("<html>¡¡¡ENHORABUENA!!! <br>Has vencido al Rey Oscuro Morghan y has devuelto la paz a las tierras de Zaltor. <br>Pulsa FIN para finalizar el juego.</html>");

        jbFin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jbFin.setText("Fin");
        jbFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPanelFinLayout = new javax.swing.GroupLayout(jpPanelFin);
        jpPanelFin.setLayout(jpPanelFinLayout);
        jpPanelFinLayout.setHorizontalGroup(
            jpPanelFinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelFinLayout.createSequentialGroup()
                .addGroup(jpPanelFinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPanelFinLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jlTextoFin, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpPanelFinLayout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(jbFin)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jpPanelFinLayout.setVerticalGroup(
            jpPanelFinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelFinLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jlTextoFin, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jbFin)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdFinLayout = new javax.swing.GroupLayout(jdFin.getContentPane());
        jdFin.getContentPane().setLayout(jdFinLayout);
        jdFinLayout.setHorizontalGroup(
            jdFinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPanelFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdFinLayout.setVerticalGroup(
            jdFinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPanelFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jlTextoMuerte.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoMuerte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTextoMuerte.setText("<html><br><p style='text-align:center;'>¡¡¡PERDISTE!!!</p><br><p style='text-align:center;'> Has sido derrotada, tu aventura en las Tierras de Zaltor llega a su fin.</p><br><br><p style='text-align:center;'>Pulsa FIN para cerrar el juego</p><br></html>");

        jbMuerteFin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jbMuerteFin.setText("FIN");
        jbMuerteFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMuerteFinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPanelMuerteLayout = new javax.swing.GroupLayout(jpPanelMuerte);
        jpPanelMuerte.setLayout(jpPanelMuerteLayout);
        jpPanelMuerteLayout.setHorizontalGroup(
            jpPanelMuerteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelMuerteLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(jlTextoMuerte, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(jpPanelMuerteLayout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(jbMuerteFin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpPanelMuerteLayout.setVerticalGroup(
            jpPanelMuerteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelMuerteLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jlTextoMuerte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jbMuerteFin)
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout jdMuerteLayout = new javax.swing.GroupLayout(jdMuerte.getContentPane());
        jdMuerte.getContentPane().setLayout(jdMuerteLayout);
        jdMuerteLayout.setHorizontalGroup(
            jdMuerteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPanelMuerte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdMuerteLayout.setVerticalGroup(
            jdMuerteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPanelMuerte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpEnemigo.setBackground(new java.awt.Color(255, 102, 102));
        jpEnemigo.setBorder(new javax.swing.border.MatteBorder(null));
        jpEnemigo.setLayout(null);

        jlLobo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lobo_co_218x246.jpg"))); // NOI18N
        jlLobo.setText("jLabel1");
        jlLobo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpEnemigo.add(jlLobo);
        jlLobo.setBounds(1070, 10, 218, 250);

        jlGuerreroOscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/guerreroOscuro_co_218x246.jpg"))); // NOI18N
        jlGuerreroOscuro.setText("jLabel1");
        jlGuerreroOscuro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpEnemigo.add(jlGuerreroOscuro);
        jlGuerreroOscuro.setBounds(1070, 10, 218, 250);

        jlNoMuerto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/noMuerto_co_218x246.jpg"))); // NOI18N
        jlNoMuerto.setText("jLabel2");
        jlNoMuerto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpEnemigo.add(jlNoMuerto);
        jlNoMuerto.setBounds(1070, 10, 220, 250);

        jpbVidaEnemigo.setBackground(new java.awt.Color(255, 0, 0));
        jpbVidaEnemigo.setForeground(new java.awt.Color(0, 153, 0));
        jpbVidaEnemigo.setMaximum(130);
        jpbVidaEnemigo.setString("");
        jpbVidaEnemigo.setStringPainted(true);
        jpEnemigo.add(jpbVidaEnemigo);
        jpbVidaEnemigo.setBounds(490, 20, 550, 20);

        jlTextoVidaEnemigo.setBackground(new java.awt.Color(204, 204, 204));
        jlTextoVidaEnemigo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoVidaEnemigo.setText("Vida");
        jpEnemigo.add(jlTextoVidaEnemigo);
        jlTextoVidaEnemigo.setBounds(440, 10, 40, 40);

        jlNombreLobo.setBackground(new java.awt.Color(204, 204, 204));
        jlNombreLobo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlNombreLobo.setForeground(new java.awt.Color(0, 0, 0));
        jlNombreLobo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNombreLobo.setText("Lobo Salvaje");
        jlNombreLobo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jlNombreLobo.setOpaque(true);
        jpEnemigo.add(jlNombreLobo);
        jlNombreLobo.setBounds(870, 70, 170, 40);

        jlNombreGuerreroOscuro.setBackground(new java.awt.Color(204, 204, 204));
        jlNombreGuerreroOscuro.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlNombreGuerreroOscuro.setForeground(new java.awt.Color(0, 0, 0));
        jlNombreGuerreroOscuro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNombreGuerreroOscuro.setText("Guerrero Oscuro");
        jlNombreGuerreroOscuro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jlNombreGuerreroOscuro.setOpaque(true);
        jpEnemigo.add(jlNombreGuerreroOscuro);
        jlNombreGuerreroOscuro.setBounds(820, 70, 220, 40);

        jlNombreNoMuerto.setBackground(new java.awt.Color(204, 204, 204));
        jlNombreNoMuerto.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlNombreNoMuerto.setForeground(new java.awt.Color(0, 0, 0));
        jlNombreNoMuerto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNombreNoMuerto.setText("No-Muerto");
        jlNombreNoMuerto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpEnemigo.add(jlNombreNoMuerto);
        jlNombreNoMuerto.setBounds(870, 70, 170, 40);

        jlNombreRey.setBackground(new java.awt.Color(204, 204, 204));
        jlNombreRey.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlNombreRey.setForeground(new java.awt.Color(0, 0, 0));
        jlNombreRey.setText("Rey Oscuro Morghan");
        jlNombreRey.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jlNombreRey.setOpaque(true);
        jpEnemigo.add(jlNombreRey);
        jlNombreRey.setBounds(810, 60, 240, 70);

        jlRey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/morghan_co_218x246.jpg"))); // NOI18N
        jlRey.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpEnemigo.add(jlRey);
        jlRey.setBounds(1070, 10, 220, 250);

        jScrollPane1.setBackground(new java.awt.Color(153, 255, 255));

        jpJugador.setBackground(new java.awt.Color(0, 102, 102));
        jpJugador.setBorder(new javax.swing.border.MatteBorder(null));
        jpJugador.setLayout(null);

        jlNinja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ninja_co_218x246.jpg"))); // NOI18N
        jlNinja.setText("lbNinja");
        jlNinja.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpJugador.add(jlNinja);
        jlNinja.setBounds(10, 30, 216, 240);

        jlChaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chaman_co_218x246.jpg"))); // NOI18N
        jlChaman.setText("lbChaman");
        jlChaman.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpJugador.add(jlChaman);
        jlChaman.setBounds(10, 20, 216, 250);

        jlGuerrera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/guerrera_co_218x246.jpg"))); // NOI18N
        jlGuerrera.setText("lbGuerreraa");
        jlGuerrera.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpJugador.add(jlGuerrera);
        jlGuerrera.setBounds(10, 20, 216, 250);

        jpbVidaJugador.setBackground(new java.awt.Color(255, 0, 0));
        jpbVidaJugador.setForeground(new java.awt.Color(0, 153, 0));
        jpbVidaJugador.setMaximum(99999999);
        jpbVidaJugador.setName(""); // NOI18N
        jpbVidaJugador.setString("");
        jpbVidaJugador.setStringPainted(true);
        jpJugador.add(jpbVidaJugador);
        jpbVidaJugador.setBounds(730, 20, 550, 20);

        jpbExperienciaJugador.setBackground(new java.awt.Color(255, 255, 255));
        jpbExperienciaJugador.setForeground(new java.awt.Color(204, 102, 0));
        jpbExperienciaJugador.setMaximum(150);
        jpbExperienciaJugador.setString("");
        jpbExperienciaJugador.setStringPainted(true);
        jpJugador.add(jpbExperienciaJugador);
        jpbExperienciaJugador.setBounds(730, 100, 550, 20);

        jpbNivelJugador.setBackground(new java.awt.Color(255, 255, 255));
        jpbNivelJugador.setForeground(new java.awt.Color(0, 0, 153));
        jpbNivelJugador.setMaximum(10);
        jpbNivelJugador.setString("");
        jpbNivelJugador.setStringPainted(true);
        jpJugador.add(jpbNivelJugador);
        jpbNivelJugador.setBounds(730, 60, 550, 20);

        jlTextoExp.setBackground(new java.awt.Color(255, 255, 255));
        jlTextoExp.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoExp.setForeground(new java.awt.Color(0, 0, 0));
        jlTextoExp.setText("Exp.");
        jpJugador.add(jlTextoExp);
        jlTextoExp.setBounds(680, 90, 40, 40);

        jlTextoNivel.setBackground(new java.awt.Color(255, 255, 255));
        jlTextoNivel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoNivel.setForeground(new java.awt.Color(0, 0, 0));
        jlTextoNivel.setText("Nivel");
        jpJugador.add(jlTextoNivel);
        jlTextoNivel.setBounds(670, 50, 50, 40);

        jlTextoVidaJugador.setBackground(new java.awt.Color(255, 255, 255));
        jlTextoVidaJugador.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoVidaJugador.setForeground(new java.awt.Color(0, 0, 0));
        jlTextoVidaJugador.setText("Vida");
        jpJugador.add(jlTextoVidaJugador);
        jlTextoVidaJugador.setBounds(680, 10, 40, 40);

        jpbEnergia.setBackground(new java.awt.Color(255, 255, 255));
        jpbEnergia.setForeground(new java.awt.Color(153, 0, 153));
        jpbEnergia.setString("");
        jpbEnergia.setStringPainted(true);
        jpJugador.add(jpbEnergia);
        jpbEnergia.setBounds(730, 140, 550, 20);

        jlTextoEnergia.setBackground(new java.awt.Color(255, 255, 255));
        jlTextoEnergia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlTextoEnergia.setForeground(new java.awt.Color(0, 0, 0));
        jlTextoEnergia.setText("Energía");
        jpJugador.add(jlTextoEnergia);
        jlTextoEnergia.setBounds(650, 130, 60, 30);

        jlNombreJugador.setBackground(new java.awt.Color(204, 204, 204));
        jlNombreJugador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlNombreJugador.setForeground(new java.awt.Color(0, 0, 0));
        jlNombreJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNombreJugador.setText("Jugadora");
        jlNombreJugador.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jlNombreJugador.setOpaque(true);
        jpJugador.add(jlNombreJugador);
        jlNombreJugador.setBounds(250, 40, 130, 50);

        jScrollPane1.setViewportView(jpJugador);

        jtTexto.setEditable(false);
        jtTexto.setBackground(new java.awt.Color(0, 51, 102));
        jtTexto.setColumns(20);
        jtTexto.setForeground(new java.awt.Color(255, 255, 255));
        jtTexto.setRows(5);
        jtTexto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jsConsola.setViewportView(jtTexto);

        jpBotones.setBackground(new java.awt.Color(102, 102, 102));

        jbAtacar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbAtacar.setText("Atacar");
        jbAtacar.setToolTipText("");
        jbAtacar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbAtacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtacarActionPerformed(evt);
            }
        });

        jbHabilidadEspecial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbHabilidadEspecial.setText("Habilidad Especial");
        jbHabilidadEspecial.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbHabilidadEspecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHabilidadEspecialActionPerformed(evt);
            }
        });

        jbDefender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbDefender.setText("Defender");
        jbDefender.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbDefender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDefenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBotonesLayout = new javax.swing.GroupLayout(jpBotones);
        jpBotones.setLayout(jpBotonesLayout);
        jpBotonesLayout.setHorizontalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jbAtacar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jbDefender, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jbHabilidadEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBotonesLayout.setVerticalGroup(
            jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBotonesLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jpBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAtacar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbDefender, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jbHabilidadEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Las tierras de Zaltor");
        jLabel3.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addComponent(jsConsola, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpEnemigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpEnemigo, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsConsola, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Funciones de los botones.
     *
     * @param evt
     */
    private void jbAtacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtacarActionPerformed
        mostrarMensaje("\n-----------TU TURNO-----------");
        j.realizarAccionJugador(jugador, enemigo, Accion.Atacar);

        refrescarBarras();

        if (enemigo.getVida() <= 0) {
            venciEnemigo();
        } else {
            mostrarMensaje("\n-----------TURNO " + enemigo.getNombrePersonaje() + "-----------");
            l.accionEnemigo(enemigo, jugador);

        }
    }//GEN-LAST:event_jbAtacarActionPerformed

    private void jbHabilidadEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHabilidadEspecialActionPerformed
        mostrarMensaje("\n-----------TU TURNO-----------");
        j.realizarAccionJugador(jugador, enemigo, Accion.HabilidadEspecial);

        refrescarBarras();

        if (enemigo.getVida() <= 0) {
            venciEnemigo();
        } else {
            mostrarMensaje("\n-----------TURNO " + enemigo.getNombrePersonaje() + "-----------");
            l.accionEnemigo(enemigo, jugador);
        }
    }//GEN-LAST:event_jbHabilidadEspecialActionPerformed

    private void jbDefenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDefenderActionPerformed
        mostrarMensaje("\n-----------TU TURNO-----------");
        j.realizarAccionJugador(jugador, enemigo, Accion.Defender);

        mostrarMensaje("\n-----------TURNO " + enemigo.getNombrePersonaje() + "-----------");
        l.accionEnemigo(enemigo, jugador);
    }//GEN-LAST:event_jbDefenderActionPerformed

    private void jbContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbContinuarActionPerformed
        System.out.println("Pulsado continuar. Nivel: " + jugador.getNivel());
        if (jugador.getNivel() == 10) {
            fase = faseFinal;
            gestionarFase(faseFinal);

        } else if (jugador.getNivel() > 10) {
            jdFin.setLocationRelativeTo(this);
            jdFin.pack();
            jdFin.setVisible(true);
        } else {
            gestionarFase(faseCombate);
        }
        jdContinuar.dispose();
    }//GEN-LAST:event_jbContinuarActionPerformed

    private void jbFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFinActionPerformed
        jdFin.dispose();
        dispose();
    }//GEN-LAST:event_jbFinActionPerformed

    private void jbMuerteFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMuerteFinActionPerformed
        jdMuerte.dispose();
        this.dispose();
    }//GEN-LAST:event_jbMuerteFinActionPerformed

    /**
     * Método para poder llamar al método jdMuerte, que es el diálogo que sale
     * cuando mueres, desde Lógica.
     */
    public void mostrarDialogoMuerte() {
        jdMuerte.pack();
        jdMuerte.setLocationRelativeTo(this);
        jdMuerte.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAtacar;
    private javax.swing.JButton jbContinuar;
    private javax.swing.JButton jbDefender;
    private javax.swing.JButton jbFin;
    private javax.swing.JButton jbHabilidadEspecial;
    private javax.swing.JButton jbMuerteFin;
    private javax.swing.JDialog jdContinuar;
    private javax.swing.JDialog jdFin;
    private javax.swing.JDialog jdMuerte;
    private javax.swing.JLabel jlChaman;
    private javax.swing.JLabel jlGuerrera;
    private javax.swing.JLabel jlGuerreroOscuro;
    private javax.swing.JLabel jlLobo;
    private javax.swing.JLabel jlNinja;
    private javax.swing.JLabel jlNoMuerto;
    private javax.swing.JLabel jlNombreGuerreroOscuro;
    private javax.swing.JLabel jlNombreJugador;
    private javax.swing.JLabel jlNombreLobo;
    private javax.swing.JLabel jlNombreNoMuerto;
    private javax.swing.JLabel jlNombreRey;
    private javax.swing.JLabel jlRey;
    private javax.swing.JLabel jlTextoEnergia;
    private javax.swing.JLabel jlTextoExp;
    private javax.swing.JLabel jlTextoFin;
    private javax.swing.JLabel jlTextoMuerte;
    private javax.swing.JLabel jlTextoNivel;
    private javax.swing.JLabel jlTextoVidaEnemigo;
    private javax.swing.JLabel jlTextoVidaJugador;
    private javax.swing.JLabel jlbTextoContinuar;
    private javax.swing.JPanel jpBotones;
    private javax.swing.JPanel jpEnemigo;
    private javax.swing.JPanel jpJugador;
    private javax.swing.JPanel jpPanelFin;
    private javax.swing.JPanel jpPanelMuerte;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JProgressBar jpbEnergia;
    private javax.swing.JProgressBar jpbExperienciaJugador;
    private javax.swing.JProgressBar jpbNivelJugador;
    private javax.swing.JProgressBar jpbVidaEnemigo;
    private javax.swing.JProgressBar jpbVidaJugador;
    private javax.swing.JScrollPane jsConsola;
    private static javax.swing.JTextArea jtTexto;
    // End of variables declaration//GEN-END:variables
}
