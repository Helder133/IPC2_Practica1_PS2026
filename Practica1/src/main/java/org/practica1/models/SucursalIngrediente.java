package org.practica1.models;

public class SucursalIngrediente {
    private int sucursalId;
    private int ingredienteId;
    private boolean estado;
    private String nombreIngrediente;

    public SucursalIngrediente(int sucursalId, int ingredienteId, boolean estado) {
        this.sucursalId = sucursalId;
        this.ingredienteId = ingredienteId;
        this.estado = estado;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public int getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(int ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }
}
