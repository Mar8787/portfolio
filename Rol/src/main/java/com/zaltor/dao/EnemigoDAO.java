/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.dao;

import com.zaltor.dataBase.*;
import com.zaltor.juegoRol.Enemigo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para guardar y obtener datos en la tabla enemigos de la db.
 *
 * @author mss7
 */
public class EnemigoDAO {

    /**
     *
     * @param enemigo Inserta un enemigo en su tabla de la db.
     */
    public void insertarEnemigo(Enemigo enemigo) {
        String sql = "INSERT INTO enemigos (tipo, vida, ataque, defensa, habilidad_especial) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, enemigo.getNombrePersonaje());
            ps.setInt(2, enemigo.getVida());
            ps.setInt(3, enemigo.getAtaque());
            ps.setInt(4, enemigo.getDefensa());
            ps.setString(5, enemigo.getHabilidadEspecial());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param tipo Recibe el tipo de enemigo y obtiene su id
     * @return Devuelve la id del enemigo o -1 si no encuentra el tipo
     */
    public int obtenerIdPorTipo(String tipo) {
        String sql = "SELECT id FROM enemigos WHERE tipo = ?";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tipo);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
