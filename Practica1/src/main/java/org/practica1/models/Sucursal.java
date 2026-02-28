package org.practica1.models;

import org.apache.commons.lang3.StringUtils;

public class Sucursal {
    private int sucursal_id;
    private String nombre;
    private String contacto;
    private String ubicacion;

    public Sucursal(String nombre, String contacto, String ubicacion) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.ubicacion = ubicacion;
    }

    public int getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre) &&
                StringUtils.isNotBlank(ubicacion) &&
                StringUtils.isNotBlank(contacto);
    }

    @Override
    public String toString() {
        return nombre + "-" + ubicacion;
    }
}
