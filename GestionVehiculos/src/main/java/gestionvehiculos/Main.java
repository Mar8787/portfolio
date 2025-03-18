/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionvehiculos;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Mar
 *
 * En esta clase, creo un menú interactivo con distintas opciones.
 */
public class Main {

    public static void main(String[] args) {

        String opcion;
        Scanner leer = new Scanner(System.in);
        VehiculoCRUD crud = new VehiculoCRUD();

        crud.cargarVehiculo(); // Cargo el documento .txt con info sobre Coches y Motos y lo agrego a mi ArrayList.

        do { // Menú que se ejecutará mientras no pulses 6.
            System.out.println("\nEscribe el número de la opción que deseas ejecutar: "
                    + "\n 1. Agregar un coche."
                    + "\n 2. Agregar una moto."
                    + "\n 3. Listar todos los vehículos."
                    + "\n 4. Eliminar un vehículo por ID."
                    + "\n 5. Filtrar vehículos por tipo (coche o moto)."
                    + "\n 6. Salir del programa.\n");
            opcion = leer.nextLine().trim(); // Con trim se borran los espacios que puedan escribir.

            switch (opcion.toLowerCase()) { // Con el formato cadena de texto se evitan errores por entradas no válidas.
                case "1": // Opción para agregar un coche, te pide cada parte del constructor que necesita Coche.
                    System.out.println("Elegiste agregar un coche.\n"
                            + "\nIntroduce la marca: ");
                    String marcaC = leer.nextLine();

                    System.out.println("Introduce el modelo: ");
                    String modeloC = leer.nextLine();

                    System.out.println("Introduce el año: ");
                    String yearC = leer.nextLine();

                    System.out.println("Introduce el nº de puertas: ");
                    String puertasC = leer.nextLine();

                    // Llamo al método para agregar el coche a la ArrayList.
                    crud.agregarVehiculo(new Coche(marcaC, modeloC, yearC, puertasC));
                    break;
                case "2": // Opción para agregar una moto, te pide lo que necesita el constructor de Moto.
                    System.out.println("Elegiste agregar una moto.\n"
                            + "\nIntroduce la marca: ");
                    String marcaM = leer.nextLine();

                    System.out.println("Introduce el modelo: ");
                    String modeloM = leer.nextLine();

                    System.out.println("Introduce el año: ");
                    String yearM = leer.nextLine();

                    // Valido la entrada del booleano para evitar errores.
                    boolean sidecar = false;
                    boolean entradaValida = false;
                    while (!entradaValida) {
                        System.out.println("¿Tiene sidecar? Responde con true/false: ");
                        try {
                            sidecar = leer.nextBoolean();
                            entradaValida = true;
                        } catch (InputMismatchException e) {
                            System.err.println("Entrada inválida, \nPor favor, responde únicamente con true o false");
                            leer.nextLine();
                        }
                    }
                    leer.nextLine(); // Limpia el buffer.
                    // Llamo al método para agregar la moto a la ArrayList.
                    crud.agregarVehiculo(new Moto(marcaM, modeloM, yearM, sidecar));
                    break;
                case "3": // Opción que llama al método para imprimir por pantalla todos los vehículos.
                    System.out.println("Elegiste listar todos los vehículos.\n");
                    crud.listarVehiculo();
                    break;
                case "4": // Opción para eliminar un vehículo escribiendo la ID.
                    System.out.println("Elegiste eliminar un vehículo por su ID.\n");
                    System.out.println("Introduce la ID del vehículo que quieras eliminar: ");
                    // Llamo al método para eliminar vehículo.
                    crud.eliminarVehiculo(leer.next());

                    leer.nextLine(); // Limpia el buffer.
                    break;
                case "5": // Opción que llama a listar por tipo.
                    System.out.println("Elegiste listar por tipo.\n");

                    System.out.println("\nEstos son los coches:");
                    crud.listarPorTipo("Coche");

                    System.out.println("\nEstas son las motos:");
                    crud.listarPorTipo("Moto");
                    break;
                case "6": // Opción para salir del bucle.
                    System.out.println("Saliste del programa.\n");
                    break;
                default: // Si esctibe algo que no sean los números del 1-6, salta este error y vuelve a pedir que marques una opción.
                    System.err.println("Opción no encontrada."
                            + "\nPor favor, marque un número que corresponda a una opción.");
                    break;
            }

        } while (!opcion.equals("6"));

        // Llamo al método para guardar las entradas en el .txt.
        crud.guardarVehiculo();

        leer.close();
    }

}