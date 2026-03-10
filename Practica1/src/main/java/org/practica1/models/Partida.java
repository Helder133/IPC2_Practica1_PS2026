package org.practica1.models;

import java.time.LocalDateTime;

public class Partida {
    private int partidaId;
    private int usuarioId;
    private int sucursalId;
    private int nivel;
    private int puntaje;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Partida(int usuarioId, int sucursalId) {
        this.usuarioId = usuarioId;
        this.sucursalId = sucursalId;
        this.nivel = 1;
        this.puntaje = 0;
        this.fechaInicio = LocalDateTime.now();
    }

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}

