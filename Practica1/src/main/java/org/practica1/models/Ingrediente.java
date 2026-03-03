package org.practica1.models;

import org.apache.commons.lang3.StringUtils;

public class Ingrediente {
    private int ingredienteId;
    private String nombre;

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    public int getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(int ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre);
    }
}
