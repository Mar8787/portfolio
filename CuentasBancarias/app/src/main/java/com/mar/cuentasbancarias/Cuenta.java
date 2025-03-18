/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mar.cuentasbancarias;

/**
 *
 * @author Mar
 * 
 * Constructor de cuentas corrientes
 */
public class Cuenta {
    private String numeroCuenta;
    private int saldo;
    
    public Cuenta(String numeroCuenta, int saldo){
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public int getSaldo() {
        return saldo;
    }
    
    public void setSaldo(int nuevoSaldo) {
        this.saldo= nuevoSaldo;
    }
    
}
