/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionvehiculos;

/**
 * @author Mar
 *
 * Creo la clase Cooche que hereda de Vehículo.
 */
public class Coche extends Vehiculo {

    // Atributo propio de la clace Coche.
    String puertas;

    // Constructor con todos los atributos.
    public Coche(String marca, String modelo, String año, String puertas) {
        super(marca, modelo, año); // Lo heredado.
        this.puertas = puertas; // Lo nuevo.
    }

    // Creamos el getter y setter.
    public String getPuertas() {
        return puertas;
    }

    public void setPuertas(String puertas) {
        this.puertas = puertas;
    }

    @Override // Implemento el método mostrarInfo.
    void mostrarInfo() {
        System.out.println("Información del coche: "
                + "\n ID: " + id
                + "\n Marca: " + marca
                + "\n Modelo: " + modelo
                + "\n Año: " + año
                + "\n Número de puertas: " + puertas + "\n");
    }

    @Override // Creo el método toString.
    public String toString() {
        return "Coche {"
                + "\n Marca: " + marca
                + "\n Modelo: " + modelo
                + "\n Año: " + año
                + "\n Número de puertas: " + puertas
                + "\n ID. " + id + "\n}\n";
    }

}
