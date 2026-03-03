package org.practica1.models;

import org.apache.commons.lang3.StringUtils;

public class Producto {
    private int productoID;
    private String nombre;
    private int timepoBaseDePreparacion;

    public Producto(int timepoBaseDePreparacion, String nombre) {
        this.timepoBaseDePreparacion = timepoBaseDePreparacion;
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

    public int getTimepoBaseDePreparacion() {
        return timepoBaseDePreparacion;
    }

    public void setTimepoBaseDePreparacion(int timepoBaseDePreparacion) {
        this.timepoBaseDePreparacion = timepoBaseDePreparacion;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre);
    }
}
