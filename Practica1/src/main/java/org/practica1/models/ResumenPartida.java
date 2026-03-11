package org.practica1.models;

import java.time.LocalDateTime;

public class ResumenPartida {

    private int partidaId;
    private LocalDateTime fechaInicio;
    private int nivel;
    private int puntaje;
    private int pedidosAtendidos;
    private LocalDateTime fechaFin;

    public ResumenPartida(int partidaId, LocalDateTime fechaInicio, LocalDateTime fechaFin, int nivel, int puntaje, int pedidosAtendidos) {
        this.partidaId = partidaId;
        this.fechaInicio = fechaInicio;
        this.nivel = nivel;
        this.puntaje = puntaje;
        this.pedidosAtendidos = pedidosAtendidos;
        this.fechaFin = fechaFin;
    }

    // Getters
    public int getPartidaId() {
        return partidaId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getPedidosAtendidos() {
        return pedidosAtendidos;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
}
