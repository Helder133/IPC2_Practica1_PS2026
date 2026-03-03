package org.practica1.models;

import java.util.List;

public class ProductoIngrediente {
    private int productoId;
    private int ingredienteId;
    private String nombreProducto;
    private List<Ingrediente> ingredientes;

    public ProductoIngrediente(int productoId, int ingredienteId) {
        this.productoId = productoId;
        this.ingredienteId = ingredienteId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(int ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
