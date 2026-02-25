package org.practica1.models;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Usuario {
    private int usuario_id;
    private int sucursal_id;
    private String nombre;
    private String email;
    private String contrasena;
    private EnumUsuario rol;
    private boolean estado;

    public Usuario(String nombre, String email, String contrasena, EnumUsuario rol) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = incriptar(contrasena);
        this.rol = rol;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = incriptar(contrasena);
    }

    public EnumUsuario getRol() {
        return rol;
    }

    public void setRol(EnumUsuario rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isValid() {
        return StringUtils.isBlank(nombre) &&
                StringUtils.isBlank(email) &&
                StringUtils.isBlank(contrasena) &&
                rol == null &&
                usuario_id > 0;

    }

    private String incriptar(String Contrasena) {
        byte[] message = Contrasena.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(message);
    }
}
