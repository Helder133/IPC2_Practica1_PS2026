package org.practica1.models;

import java.time.LocalDateTime;

public class HistorialPedido {
    private int historialId;
    private int pedidoId;
    private LocalDateTime fecha;
    private EnumPedido estado;

    public HistorialPedido(int pedidoId) {
        this.pedidoId = pedidoId;
        this.fecha = LocalDateTime.now();
    }

    public int getHistorialId() {
        return historialId;
    }

    public void setHistorialId(int historialId) {
        this.historialId = historialId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EnumPedido getEstado() {
        return estado;
    }

    public void setEstado(EnumPedido estado) {
        this.estado = estado;
    }
}
