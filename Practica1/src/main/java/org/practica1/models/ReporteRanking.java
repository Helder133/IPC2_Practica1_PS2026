package org.practica1.models;

public class ReporteRanking {

    private String nombreJugador;
    private String nombreSucursal;
    private int nivelMaximo;
    private int puntajeTotal;

    public ReporteRanking(String nombreJugador, String nombreSucursal, int nivelMaximo, int puntajeTotal) {
        this.nombreJugador = nombreJugador;
        this.nombreSucursal = nombreSucursal;
        this.nivelMaximo = nivelMaximo;
        this.puntajeTotal = puntajeTotal;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public int getNivelMaximo() {
        return nivelMaximo;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

}
