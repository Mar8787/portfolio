/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionvehiculos;

/**
 * @author Mar
 *
 * Creo la clase Moto que hereda de Vehiculo.
 */
public class Moto extends Vehiculo {

    // Atributo propio de Moto.
    boolean tieneSidecar;

    // Constructor con todos los atributos.
    public Moto(String marca, String modelo, String año, boolean tieneSidecar) {
        super(marca, modelo, año); // Los heredados.
        this.tieneSidecar = tieneSidecar; // El nuevo.
    }

    // Creamos el getter y setter.
    public boolean isTieneSidecar() {
        return tieneSidecar;
    }

    public void setTieneSidecar(boolean tieneSidecar) {
        this.tieneSidecar = tieneSidecar;
    }

    @Override // Implemento el método mostrarInfo.
    void mostrarInfo() {
        System.out.println("Información de la moto: "
                + "\n ID: " + id
                + "\n Marca: " + marca
                + "\n Modelo: " + modelo
                + "\n Año: " + año
                + "\n Con sidecar: " + tieneSidecar + "\n");
    }

    @Override // Creo el toString.
    public String toString() {
        return "Moto {"
                + "\n Marca: " + marca
                + "\n Modelo: " + modelo
                + "\n Año: " + año
                + "\n Tiene Sidecar: " + tieneSidecar
                + "\n ID. " + id + "\n}\n";
    }

}
