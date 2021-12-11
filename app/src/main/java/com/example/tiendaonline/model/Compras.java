package com.example.tiendaonline.model;

import java.io.Serializable;

public class Compras implements Serializable {

    private String sku;
    private String nombre;
    private double monto;
    private int cantidad;

    public Compras(){

    }

    public Compras(String sku, String nombre, double monto, int cantidad) {
        this.sku = sku;
        this.nombre = nombre;
        this.monto = monto;
        this.cantidad = cantidad;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
