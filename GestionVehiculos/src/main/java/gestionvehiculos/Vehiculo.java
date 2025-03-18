/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionvehiculos;

import java.util.UUID;

/**
 * @author Mar
 *
 * Creo la clase abstracta.
 */
abstract class Vehiculo {

    // Atributos
    String marca;
    String modelo;
    String año;
    UUID id;

    // Constructor.
    public Vehiculo(String marca, String modelo, String año) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.id = UUID.randomUUID(); // Genero el id con UUID de forma aleatoria.
    }

    // Los getters:
    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getAño() {
        return año;
    }

    public UUID getId() {
        return id;
    }

    // Los setters:
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // Método abstracto.
    abstract void mostrarInfo();

}
