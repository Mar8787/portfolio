/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.dao;

import com.zaltor.dataBase.*;
import com.zaltor.juegoRol.Personaje;
import com.zaltor.juegoRol.Guerrero;
import com.zaltor.juegoRol.Chaman;
import com.zaltor.juegoRol.Ninja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que guarda, obtiene y actualiza datos en la tabla personajes de la db.
 *
 * @author mss7
 */
public class PersonajeDAO {

    /**
     *
     * @param personaje Recibe un personaje que insertará en la tabla de la db.
     */
    public void insertarPersonaje(Personaje personaje) {
        String sql = "INSERT INTO personajes (nombre, clase, vida, ataque, defensa, velocidad, nivel, experiencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, personaje.getNombrePersonaje());
            ps.setString(2, personaje.getClass().getSimpleName());
            ps.setInt(3, personaje.getVida());
            ps.setInt(4, personaje.getAtaque());
            ps.setInt(5, personaje.getDefensa());
            ps.setInt(6, personaje.getVelocidad());
            ps.setInt(7, personaje.getNivel());
            ps.setInt(8, personaje.getExperiencia());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param nombre Recibe el nombre del personaje y lo busca en la tabla.
     * @return Devuelve el personaje con sus datos o null.
     */
    public Personaje obtenerPersonajePorNombre(String nombre) {
        String sql = "SELECT * FROM personajes WHERE nombre = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String clase = rs.getString("clase");
                int vida = rs.getInt("vida");
                int ataque = rs.getInt("ataque");
                int defensa = rs.getInt("defensa");
                int velocidad = rs.getInt("velocidad");
                int nivel = rs.getInt("nivel");
                int experiencia = rs.getInt("experiencia");

                Personaje p = null;

                switch (clase) {
                    case "Guerrero":
                        p = new Guerrero(nombre);
                        break;
                    case "Chaman":
                        p = new Chaman(nombre);
                        break;
                    case "Ninja":
                        p = new Ninja(nombre);
                        break;
                    default:
                        return null;
                }

                p.setVida(vida);
                p.setAtaque(ataque);
                p.setDefensa(defensa);
                p.setVelocidad(velocidad);
                p.setNivel(nivel);
                p.setExperiencia(experiencia);

                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param nombre Recibe el nombre del personaje para buscar su id con él.
     * @return Devuelve el id o -1 si no lo encuentra.
     */
    public int obtenerIdPorNombre(String nombre) {
        String sql = "SELECT id FROM personajes WHERE nombre = ?";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     *
     * @param p Recibe el personaje para settear sus datos en la tabla de la db.
     */
    public void actualizarPersonaje(Personaje p) {
        String sql = "UPDATE personajes SET vida = ?, ataque = ?, defensa = ?, velocidad = ?, nivel = ?, experiencia = ? " + "WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getVida());
            ps.setInt(2, p.getAtaque());
            ps.setInt(3, p.getDefensa());
            ps.setInt(4, p.getVelocidad());
            ps.setInt(5, p.getNivel());
            ps.setInt(6, p.getExperiencia());

            int id = obtenerIdPorNombre(p.getNombrePersonaje());
            ps.setInt(7, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
