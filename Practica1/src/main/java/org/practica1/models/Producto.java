package org.practica1.models;

import org.apache.commons.lang3.StringUtils;

public class Producto {
    private int productoID;
    private String nombre;
    private int tiempoBaseDePreparacion;

    public Producto(int tiempoBaseDePreparacion, String nombre) {
        this.tiempoBaseDePreparacion = tiempoBaseDePreparacion;
        this.nombre = nombre;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoBaseDePreparacion() {
        return tiempoBaseDePreparacion;
    }

    public void setTiempoBaseDePreparacion(int tiempoBaseDePreparacion) {
        this.tiempoBaseDePreparacion = tiempoBaseDePreparacion;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
