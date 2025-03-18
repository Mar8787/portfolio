/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionvehiculos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Mar Creo métodos para listar, eliminar vehículos, listar por tipo
 * moto/coche, guardar en un .txt, cargar el .txt en la ArrayList de almecén de
 * vehículos.
 */
public class VehiculoCRUD {

    // Creo una ArrayList para almacenar los vehículsos.
    static ArrayList<Vehiculo> almacenVehiculos = new ArrayList<>(); // Pongo estática la arrayList para poder usarla luego en el public static void cargarVehiculo.

    /* 
    * Creo los distintos métodos que me pide:
     */
    void agregarVehiculo(Vehiculo v) {
        almacenVehiculos.add(v);
    }

    // Muestra todos los vehículos.
    void listarVehiculo() {
        // Itero mi ArrayList para imprimir todos los vehículos.
        try {
            for (Vehiculo vehiculo : almacenVehiculos) {
                vehiculo.mostrarInfo();
            }
        } catch (Exception e) {
            System.out.println("No existen vehículos en la base de datos.");
        }

    }

    // Elimina un vehículo pidiendo su id.
    void eliminarVehiculo(String id) {
        boolean borrado = false;
        // Creo un objeto Coche, porque vehículo es abstracto, que luego se machaca con el vehículo que quiera borrar.
        Vehiculo vehiculo_copia = new Coche("", "", "", "");
        // Itero mi ArrayList para igualar la ID y borrar el vehículo.        
        for (Vehiculo vehiculo : almacenVehiculos) {
            if (id.equals(vehiculo.getId().toString())) {
                System.out.println("\nVehículo borrado con éxito.");
                vehiculo_copia = vehiculo; // Copio el vehículo concreto...
                borrado = true;
            }
        }
        // ...y lo borro fuera del bucle.
        almacenVehiculos.remove(vehiculo_copia);
        if (!borrado) {
            System.err.println("\nVehículo no encontrado.");
        }
    }

    // Muestra los vehículos por tipo.
    void listarPorTipo(String tipo) {
        // Hago un for each para que me recorra el ArrayList.
        for (Vehiculo vehiculo : almacenVehiculos) {
            switch (tipo) {
                case "Coche": // Cuando encuentra Coche entra en este case.
                    if (vehiculo instanceof Coche) { // Con instanceof, confirma que es Coche.
                        vehiculo.mostrarInfo(); // Llamando a mostrarInfo, me lo imprime.
                    }
                    break;
                case "Moto": // Con Moto actúa igual.
                    if (vehiculo instanceof Moto) {
                        vehiculo.mostrarInfo();
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    // Creo el método para generar un documento de texto con mi ArrayList de vehículos.
    public static void guardarVehiculo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("vehiculos.txt", true))) {
            bw.flush(); // Limpia el buffer.
            for (Vehiculo vehiculo : almacenVehiculos) {
                bw.write(vehiculo.toString());
            }
        } catch (IOException e) {
            System.out.println("Error de escritura en el archivo");
        }
    }

    // Creo un método para leer ese documento y llenar mi ArrayList con él.
    public static void cargarVehiculo() {
        try (BufferedReader br = new BufferedReader(new FileReader("vehiculos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) { // Mientas la linea no sea nula (final del .txt) lo lee.
                linea = linea.trim(); // Le quita espacios a la linea que lee del .txt.

                if (linea.startsWith("Moto")) { // Si la linea empieza por Moto, entra aquí.
                    boolean tieneSidecar = false;
                    String marca = " ";
                    String modelo = " ";
                    String year = " ";
                    // Mientras la linea no sea nula y no sea } sigue leyendo el .txt.
                    while ((linea = br.readLine()) != null && !linea.trim().equals("}")) {
                        linea = linea.trim();
                        if (linea.startsWith("Tiene Sidecar:")) { // Coge el texto después de : y lo transforma en booleano.
                            tieneSidecar = Boolean.parseBoolean(linea.split(":")[1].trim());
                        } else if (linea.startsWith("Marca:")) { // Para que no de error si encuentra un registro vacío,
                            marca = linea.split(":", 2)[1].trim(); // le digo que extraiga 2 posiciones y borre los espacios.
                        } else if (linea.startsWith("Modelo:")) {
                            modelo = linea.split(":", 2)[1].trim();
                        } else if (linea.startsWith("Año:")) {
                            year = linea.split(":", 2)[1].trim();
                        }

                    } // Se añade a mi ArrayList
                    almacenVehiculos.add(new Moto(marca, modelo, year, tieneSidecar));
                } else if (linea.startsWith("Coche")) { // Coche actua igual
                    String puertas = " ";
                    String marca = " ";
                    String modelo = " ";
                    String year = " ";

                    while ((linea = br.readLine()) != null && !linea.trim().equals("}")) {
                        linea = linea.trim();
                        if (linea.startsWith("Número de puertas:")) {
                            puertas = linea.split(":", 2)[1].trim();
                        } else if (linea.startsWith("Marca:")) {
                            marca = linea.split(":", 2)[1].trim();
                        } else if (linea.startsWith("Modelo:")) {
                            modelo = linea.split(":", 2)[1].trim();
                        } else if (linea.startsWith("Año:")) {
                            year = linea.split(":", 2)[1].trim();
                        }
                    }
                    almacenVehiculos.add(new Coche(marca, modelo, year, puertas));
                }
            }
        } catch (IOException e) { // Captura las excepciones
            System.out.println("Error al cargar vehiculos.txt: ");
        }
    }
}
