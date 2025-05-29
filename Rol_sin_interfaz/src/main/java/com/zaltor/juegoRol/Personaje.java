/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.juegoRol;
import com.zaltor.logs.Logger;
import com.zaltor.excepciones.EnergiaInsuficienteException;
import com.zaltor.excepciones.JuegoException;

/**
 * Creo la clase abstracta padre Personaje, con atributos comunes de los personajes y métodos.
 *
 * @author Mar
 */
abstract class Personaje {
    
    protected String nombrePersonaje;
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int nivel;
    protected int experiencia;
    protected int energia;
    protected boolean defensaActiva;
    
    /**
     * Constructor.
     * @param nombrePersonaje Atributo de la clase padre. Serán heredados y modificados por las clases hijas.
     * @param vida Atributo de la clase padre.
     * @param ataque Atributo de la clase padre.
     * @param defensa Atributo de la clase padre.
     * @param velocidad Atributo de la clase padre.
     * @param nivel Atributo de la clase padre.
     * @param experiencia Atributo de la clase padre.
     * @param energia Atributo de la clase padre.
     */
    public Personaje(String nombrePersonaje, int vida, int ataque, int defensa, int velocidad, int nivel, int experiencia, int energia) {
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.nivel = 1;
        this.experiencia = 0;
        this.defensaActiva = false;
        this.nombrePersonaje = nombrePersonaje;
        this.energia = 50; // Energía inicial
    }
    /**
     * Getters y Setters de la clase padre.
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
    
    /**
     * 
     * @param nombrePersonaje Necesito este setter para que el jugador cambie el nombre del personaje en la clase juego.
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
        
    
    /**
     * Creo los métodos, que podrán usar las clases hijas.
     */
    public abstract void mostrarInfo();
    
    // Cuando un personaje use en su turno defensa, se llama a este método.
    public void activarDefensa() {
        defensaActiva = true;
    }
    // Para que no se sumen estos puntos a la defensa siempre, se desactiva al usarla.
    public void desactivarDefensa() {
        defensaActiva = false;
    }
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param habilidadEspecial Declaro el método habilidadEspecial que usará cada personaje con sus variantes.
     */
    public abstract void habilidadEspecial(Personaje jugador, Enemigo enemigo, boolean habilidadEspecial);
    
    /**
     * 
     * @param jugador
     * @param enemigo
     * @param habilidadEspecial Agrego el método usarHabilidadEspecial para poner un límite a la habilidadEspecial y gestionarla.
     * @throws EnergiaInsuficienteException Lanza una excepción cuando la energía no es suficiente para usar la habilidad especial.
     */
    public void usarHabilidadEspecial (Personaje jugador, Enemigo enemigo, boolean habilidadEspecial) throws EnergiaInsuficienteException {
        if (energia <= 0) {
            throw new EnergiaInsuficienteException("");
        } else {
            habilidadEspecial(jugador, enemigo, false);
        }
    }
    
    
    /**
     * Método recibirDanio: Enemigo ataca a personaje, personaje recibe daño, se resta a su vida el valor del ataque.
     * 
     * @param jugador
     * @param enemigo
     * @param defensaActiva Si personaje activó su defensa en el turno anterior, bloquea el ataque o recibe menos daño.
     * @param mordidaRapida Booleano de la habilidad especial del lobo.
     * @param furiaMaldita  Booleano de la habilidad especial del guerrero oscuro.
     */
    public void recibirDanio(Personaje jugador, Enemigo enemigo, boolean defensaActiva, boolean mordidaRapida, boolean furiaMaldita) {
        int diferenciaAtaqueDefensa = 0;
        int ataqueAuxiliar = enemigo.getAtaque();
        String mensajeBloqueo = "Ataque bloqueado por ";
        
        // Se activa la habilidad especial de LoboSalvaje. 
        if (mordidaRapida) {
            ataqueAuxiliar += 2;
            mordidaRapida = false;
        }
        
        // Se activa la habilidad especial de GuerreroOscuro
        if (furiaMaldita) {
            ataqueAuxiliar += 5;
            furiaMaldita = false;
        }
        
        // Durante el turno en que la defense esté activa se suman los puntos de defensa a la vida.
        if (defensaActiva) {
            diferenciaAtaqueDefensa = ataqueAuxiliar - jugador.getDefensa();
            
            // Si el ataque gana en puntos a la defensa, esa diferencia de puntos repercuten en la vida del personaje.
            if (diferenciaAtaqueDefensa > 0) {
                jugador.setVida(Math.max(0, jugador.getVida() - diferenciaAtaqueDefensa));
                                
                System.out.println("\n" + enemigo.getNombrePersonaje() + " atacó a " + jugador.getNombrePersonaje() + ".");
            } else { // Si no, no resta puntos a la vida
                System.out.println("\n" + mensajeBloqueo + jugador.getNombrePersonaje() + ".");
                Logger.registrarEvento(mensajeBloqueo + jugador.getNombrePersonaje() + ".");
            }
            // Se desactiva la defensa.
            desactivarDefensa();
        } else { // Si no está activa la defensa, se le restan los puntos del ataque del enemigo a la vida normal del personaje.
            jugador.setVida(Math.max(0, jugador.getVida() - ataqueAuxiliar));
                
            System.out.println("\n" + enemigo.getNombrePersonaje() + " atacó a " + jugador.getNombrePersonaje() + ".");
            
            
        }
        // Si el jugador está vivo, se muestra su vida
        if (jugador.getVida() > 0) {
            System.out.println(jugador.getNombrePersonaje() + " tiene " + jugador.getVida() + " puntos de vida restantes.");
                        
        }

        
    }
    
    /**
     * Método atacar:
     * @param enemigo Jugador ataca a enemigo, enemigo recibe daño, se resta a su vida el valor del ataque.
     * @param defensaActiva Si enemigo activó su defensa en el turno anterior, bloquea el ataque o recibe menos daño.
     * @param golpeDevastador Si golpeDevastador está activo, Guerrero duplica su ataque.
     * @param ataqueSigiloso Si ataqueSigiloso está activo, se suman 5 puntos al ataque.
     */
    public void atacar(Enemigo enemigo, boolean defensaActiva, boolean golpeDevastador, boolean ataqueSigiloso) {
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

                System.out.println("\n" + nombrePersonaje + " atacó a " + enemigo.getNombrePersonaje() + ".");
            } else { // Si no, no se resta vida al enemigo.
                System.out.println("\nEl ataque de " + nombrePersonaje +" ha sido bloqueado por " + enemigo.getNombrePersonaje() + ".");
                Logger.registrarEvento("El ataque de " + nombrePersonaje +" ha sido bloqueado por " + enemigo.getNombrePersonaje() + "." );
            }
            // Se desactiva la defensa del enemigo al terminar el turno.
            enemigo.desactivarDefensa();
        } else { // Si no está activada la defensa, se le restan los puntos del ataque del personaje a la vida del enemigo.
            enemigo.setVida(Math.max(0, enemigo.getVida() - ataqueAuxiliar));
            System.out.println("\nAtaque de " + nombrePersonaje + ".");
        }
        
        // Si el enemigo tiene menos de 1 punto de vida, gana la batalla el jugador.
        if (enemigo.getVida() < 0) {
            System.out.println("\nCombate finalizado.");
            System.out.println("\nLa vencedora de la batalla es " + nombrePersonaje + ".");
        } else {
            System.out.println("Vida de " + enemigo.getNombrePersonaje() + " bajó a: " + enemigo.getVida() + ".");  
        }
       
    }

    /**
     * 
     * @return Método que devuelve true si está vivo el personaje leyendo la vida.
     * @throws JuegoException Gestiona una excepción si el jugador muere.
     */
    public boolean estaVivo() throws JuegoException  {
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
     * @param enemigo Método que mejora los atributos de los personajes al llegar a cierto nivel.
     */
    public void subirNivel(Enemigo enemigo) {
                        
        int valorDeSubida = 5;
        int valorSubidaEnemigos = 3;
        
        nivel ++;
        vida += 100;
        ataque += valorDeSubida;
        defensa += valorDeSubida;
        velocidad += valorDeSubida;
        energia += 50;
        
        enemigo.setNivel(enemigo.getNivel() + 1);
        enemigo.setVida(enemigo.getVida() + valorSubidaEnemigos);
        enemigo.setAtaque(enemigo.getAtaque()+ valorSubidaEnemigos);
        enemigo.setDefensa(enemigo.getDefensa()+ valorSubidaEnemigos);
        enemigo.setVelocidad(enemigo.getVelocidad()+ valorSubidaEnemigos);
        
        experiencia = 0;
        System.out.println("¡¡Enhorabuena!! Subiste al nivel " + nivel);
        System.out.println("Estas son tus nuevas estadísticas: ");
        mostrarInfo();
        Logger.registrarEvento(nombrePersonaje + " ha subido al nivel " + nivel + ".");
    }
    
    /**
     * 
     * @param enemigo Método que aumenta los puntos de experiencia de Jugador que obtiene de Enemigo y revisa si es suficiente exp para subir de nivel.
     */
    public void ganarExperiencia(Enemigo enemigo) {
        int expNecesaria = 150;
        
        
        if (enemigo.vida <=0) {
            experiencia += enemigo.getExpOtorgada();
            
            System.out.println("\n" + nombrePersonaje + " ha adquirido " + enemigo.getExpOtorgada() + " puntos de experiencia.");
            Logger.registrarEvento(nombrePersonaje + " ha adquirido " + enemigo.getExpOtorgada() + " exp. tiene " + experiencia + " exp.");
        }
        
        if (experiencia >= expNecesaria) {
            subirNivel(enemigo);
            
        } else {
            System.out.println("Puntos de experiencia totales " + experiencia + ".");
            System.out.println("Está a " + (expNecesaria - experiencia) + " puntos del nivel " + (nivel + 1) + ".");
            
        }
    }
    
    
    
    
    
    
    
    
    
    

}
