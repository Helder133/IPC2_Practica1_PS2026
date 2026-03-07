package org.practica1.models;

public class SucursalProducto {

    private int sucursalId;
    private int ProductoId;
    private boolean estado;
    private String nombreProducto;

    public SucursalProducto(int sucursalId, int productoId, boolean estado) {
        this.sucursalId = sucursalId;
        ProductoId = productoId;
        this.estado = estado;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
