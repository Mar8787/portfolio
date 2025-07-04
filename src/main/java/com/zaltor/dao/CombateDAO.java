/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaltor.dao;

import com.zaltor.dataBase.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para registrar en la tabla combate de la db los datos del combate.
 * @author mss7
 */
public class CombateDAO {
    public void registrarCombate(int idPersonaje, int idEnemigo, String resultado, int experienciaGanada, int tiempoSegundos) {
        String sql = "INSERT INTO combates (id_personaje, id_enemigo, resultado, experiencia_ganada, tiempo_segundos) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPersonaje);
            ps.setInt(2, idEnemigo);
            ps.setString(3, resultado);
            ps.setInt(4, experienciaGanada);
            ps.setInt(5, tiempoSegundos);

            // Para actualizar la tabla
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
