/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;

import com.zaltor.logs.*;
import com.zaltor.excepciones.*;
import com.zaltor.gui.*;

/**
 * Creo la clase abstracta padre Personaje, con atributos comunes de los
 * personajes y métodos que gestionan a los personajes de jugador. Uso la
 * enumeración para el tipo de personaje que se creará. Esta clase gestiona el
 * ataque del jugador
 *
 * @author Mar
 */
enum TipoPersonaje {
    Chaman, Ninja, Guerrera, Enemigo, NoMuerto, GuerreroOscuro, LoboSalvaje, Morghan
}

public abstract class Personaje {

    protected TipoPersonaje tipo;
    protected String nombrePersonaje;
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int nivel;
    protected int experiencia;
    protected int energia;
    protected boolean defensaActiva;
    protected String habilidadEspecial;

    /**
     * Constructor.
     *
     * @param tipo
     * @param nombrePersonaje
     * @param vida
     * @param ataque
     * @param defensa
     * @param velocidad
     * @param nivel
     * @param experiencia
     * @param energia
     */
    public Personaje(TipoPersonaje tipo, String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia, String habilidadEspecial) {
        this.tipo = tipo;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.nivel = 1;
        this.experiencia = 0;
        this.defensaActiva = false;
        this.nombrePersonaje = nombrePersonaje;
        this.energia = 50; // Energía 
        this.habilidadEspecial = habilidadEspecial;
    }

    /**
     * Getters y Setters de la clase padre.
     *
     * @return Retorna el nombre del personaje.
     */
    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getEnergia() {
        return energia;
    }

    public TipoPersonaje getTipo() {
        return tipo;
    }

    public String getHabilidadEspecial() {
        return habilidadEspecial;
    }

    /**
     *
     * @param nombrePersonaje Necesito este setter para que el jugador cambie el
     * nombre del personaje.
     */
    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setDefensaActiva(boolean defensaActiva) {
        this.defensaActiva = defensaActiva;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Creo los métodos, que podrán usar las clases hijas.
     */
    public String mostrarInfo() {
        return ("\n" + tipo.toString() + ": Vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa + ", velocidad: " + velocidad + ", nivel: " + nivel + ", experiencia; " + experiencia);
    }

    /**
     * Cuando un personaje use en su turno defensa, se llama a este método.
     */
    public void activarDefensa() {
        defensaActiva = true;
    }

    /**
     * Para que no se sumen estos puntos a la defensa siempre, se desactiva al
     * usarla.
     */
    public void desactivarDefensa() {
        defensaActiva = false;
    }

    /**
     *
     * @param jugador
     * @param enemigo
     * @param habilidadEspecial Declaro el método habilidadEspecial que usará
     * cada personaje con sus variantes.
     */
    public abstract void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean habilidadEspecial);

    /**
     *
     * @param jugador
     * @param enemigo
     * @param habilidadEspecial Agrego el método usarHabilidadEspecial para
     * poner un límite a la habilidadEspecial y gestionarla.
     * @throws EnergiaInsuficienteException Lanza una excepción cuando la
     * energía no es suficiente para usar la habilidad especial.
     */
    public void usarHabilidadEspecial(Personaje jugador, Enemigo enemigo, boolean habilidadEspecial) throws EnergiaInsuficienteException {
        if (energia <= 0) {
            throw new EnergiaInsuficienteException("");
        } else {
            habilidadEspecial(jugador, enemigo, false);
        }
    }

    /**
     * Método atacar del jugador:
     *
     * @param enemigo Jugador ataca a enemigo, enemigo recibe daño, se resta a
     * su vida el valor del ataque.
     * @param golpeDevastador Si golpeDevastador está activo, Guerrero duplica
     * su ataque.
     * @param ataqueSigiloso Si ataqueSigiloso está activo, se suman 5 puntos al
     * ataque.
     */
    public void atacar(Enemigo enemigo, boolean golpeDevastador, boolean ataqueSigiloso) {
        int diferenciaAtaqueDefensa = 0;
        // Creo una variable auxiliar para que no se guarden los cambios en el atributo.
        int ataqueAuxiliar = ataque;

        // Si Guerrero activa el golpeDevastador, se duplica el valor del ataque que se guarda en la variable.
        if (golpeDevastador) {
            ataqueAuxiliar *= 2;
            golpeDevastador = false;
        }

        // Si Ninja activa ataqueSigiloso, suma 5 al ataque que se guarda en la variable auxiliar.
        if (ataqueSigiloso) {
            ataqueAuxiliar += 5;
            ataqueSigiloso = false;
        }

        // Si el enemigo activó la defensa en su turno, se resta el valor de la defensa al ataque.
        if (defensaActiva) {
            diferenciaAtaqueDefensa = ataqueAuxiliar - enemigo.getDefensa();

            // Si el ataque tiene más puntos que la defensa del enemigo, la diferencia se le resta a la vida del enemigo.
            if (diferenciaAtaqueDefensa > 0) {
                enemigo.setVida(Math.max(0, enemigo.getVida() - diferenciaAtaqueDefensa));

            } else { // Si no, no se resta vida al enemigo.
                Logger.registrarEvento("El ataque de " + nombrePersonaje + " ha sido bloqueado por " + enemigo.getNombrePersonaje() + ".");
            }
            // Se desactiva la defensa del enemigo al terminar el turno.
            enemigo.desactivarDefensa();
        } else { // Si no está activada la defensa, se le restan los puntos del ataque del personaje a la vida del enemigo.
            enemigo.setVida(Math.max(0, enemigo.getVida() - ataqueAuxiliar));
        }
    }

    /**
     *
     * @return Método que devuelve true si está vivo el personaje leyendo la
     * vida.
     * @throws JuegoException Gestiona una excepción si el jugador muere.
     */
    public boolean estaVivo() throws JuegoException {
        boolean vivo = true;
        if (vida <= 0) {
            vivo = false;

            throw new JuegoException("¡¡ESTÁS MUERTA!!\nGAME OVER");
        } else {
            vivo = true;
        }

        return vivo;
    }

    /**
     *
     * @param enemigo Método que mejora los atributos del jugador al llegar a
     * cierto nivel.
     */
    public void subirNivel(Enemigo enemigo) {

        int valorDeSubida = 5;

        nivel++;
        vida += 100;
        ataque += valorDeSubida;
        defensa += valorDeSubida;
        velocidad += valorDeSubida;
        energia += 50;

        experiencia = 0;

        Logger.registrarEvento(nombrePersonaje + " ha subido al nivel " + nivel + ".");
    }

    /**
     *
     * @param enemigo Método que aumenta los puntos de experiencia de Jugador
     * que obtiene de Enemigo y revisa si es suficiente exp para subir de nivel.
     */
    public void ganarExperiencia(Enemigo enemigo) {
        int expNecesaria = 150;

        if (enemigo.vida <= 0) {
            experiencia += enemigo.getExpOtorgada();

            Logger.registrarEvento(nombrePersonaje + " ha adquirido " + enemigo.getExpOtorgada() + " exp. tiene " + experiencia + " exp.");
        }

        if (experiencia >= expNecesaria) {
            subirNivel(enemigo);

        }
    }

}
