package org.practica1.models;

import java.time.LocalDateTime;

public class ReporteEstadistica {

    private int partidaId;
    private String nombreJugador;
    private String nombreSucursal;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int nivel;
    private int puntaje;

    public ReporteEstadistica(int partidaId, String nombreJugador, String nombreSucursal, LocalDateTime fechaInicio, LocalDateTime fechaFin, int nivel, int puntaje) {
        this.partidaId = partidaId;
        this.nombreJugador = nombreJugador;
        this.nombreSucursal = nombreSucursal;
        this.fechaInicio = fechaInicio;
        this.nivel = nivel;
        this.puntaje = puntaje;
        this.fechaFin = fechaFin;
    }

    public int getPartidaId() {
        return partidaId;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntaje() {
        return puntaje;
    }

}
