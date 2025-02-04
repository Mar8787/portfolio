package com.mar.cuentasbancarias;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Mar
 * 
 * Ejemplo uso del constructor Cuenta
 */
public class GestionCuentas {
    public static void main(String[] args) {
        ArrayList<Cuenta> cuentasBank = new ArrayList<>();
        
        cuentasBank.add(new Cuenta("001", 1500));
        cuentasBank.add(new Cuenta("002", 1000));
        cuentasBank.add(new Cuenta("003", 500));
        cuentasBank.add(new Cuenta("004", 2000));
        
        System.out.println("Estado inicial:");
        for (Cuenta unaCuenta : cuentasBank) { //Para que se impriman todas las cuentas
            System.out.println("Cuenta " + unaCuenta.getNumeroCuenta() + ": " + unaCuenta.getSaldo() + " €");
        }
        //Declaro una variable donde almaceno el saldo que modificaré en el método1
        int saldoInicialCuenta1 = cuentasBank.get(0).getSaldo(); 
        modificarSaldoPorValor(saldoInicialCuenta1); //Le paso ese saldo por parámetros
        System.out.println("Saldo de la cuenta " + cuentasBank.get(0).getNumeroCuenta() + " después del método: " + saldoInicialCuenta1 + "€"); //Imprimo ese saldo y compruebo que no ha cambiado
        
        //Declaro otra variable para pasarle una cuenta completa al método2
        Cuenta unaCuenta2 = cuentasBank.get(1); //Es la cuenta 002
        modificarSaldoPorReferencia(unaCuenta2);
        //Imprimo para comprobar que SÍ se ha cambiado el saldo de esta cuenta
        System.out.println("Saldo de la cuenta " + unaCuenta2.getNumeroCuenta() + " después del método: " + unaCuenta2.getSaldo() +"€");
        
        //Para el método3
        Cuenta unaCuenta3 = cuentasBank.get(2); //Es la cuenta 003
        Cuenta unaCuenta4 = cuentasBank.get(3); //Es la cuenta 004
        int cantidad = 500; //Este valor lo puedes modificar a más de 500 para ver si salta el aviso de saldo insuficiente
        realizarTransferencia(unaCuenta3, unaCuenta4, cantidad);
        
        //Para el método4
        System.out.println("Estado final:");
        mostrarCuentas(cuentasBank);
        
    }    
        //Método 1 cambio de saldo dentro del método
        public static void modificarSaldoPorValor(int saldo) { //Cambio el saldo solo en el método
            saldo = saldo + 500;
            System.out.println("\nSaldo dentro del método (local): " + saldo + "€");
        }
        //Método 2 modificación del saldo
        public static void modificarSaldoPorReferencia(Cuenta cuenta) { //Cambio el saldo dentro y afecta fuera del método
            int saldoActual = cuenta.getSaldo(); //Llamo al saldo de la cuenta que metí en el método2
            cuenta.setSaldo(saldoActual + 500); //Lo modifico
            //Imprimo para ver que se ha modificado
            System.out.println("\nSaldo dentro del método " + cuenta.getSaldo() + "€");
        
        }
        //Método 3 realiza transferencias
        public static void realizarTransferencia(Cuenta origen, Cuenta destino, int cantidad) {
            if (origen.getSaldo()<cantidad) { //Si el saldo es menor de la cantidad, no hace transferencia
                System.out.println("\nFondos insuficientes en la cuenta de origen\n");
            } else {
                int saldoOrigen = origen.getSaldo();
                origen.setSaldo(saldoOrigen - cantidad); //Le resto la cantidad a la cuenta de origen
                
                int saldoDestino = destino.getSaldo();
                destino.setSaldo(saldoDestino + cantidad); //Le sumo la cantidad a la cuenta de destino
                
                System.out.println("\nTransferencia realizada. \nCuenta " + origen.getNumeroCuenta() + ": " + origen.getSaldo() + " €");
                System.out.println("Cuenta " + destino.getNumeroCuenta() + ": " + destino.getSaldo() + " €\n");} 
            }
        //Método 4 Muestra las cuentas
        public static void mostrarCuentas(ArrayList<Cuenta> cuentas) {
            for (Cuenta unaCuenta : cuentas) {
                System.out.println("Cuenta " + unaCuenta.getNumeroCuenta() + ": " + unaCuenta.getSaldo() );
            }
        }
}