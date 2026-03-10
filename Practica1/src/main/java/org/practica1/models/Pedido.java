package org.practica1.models;

import java.time.LocalDateTime;

public class Pedido {
    private int pedidoId;
    private int partidaId;
    private int tiempoLimite;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;
    private EnumPedido estado;
    private int puntajeObtenido;

    public Pedido(int partidaId) {
        this.partidaId = partidaId;
        this.estado = EnumPedido.Recibido;
        this.fechaCreacion = LocalDateTime.now();
        this.puntajeObtenido = 0;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }

    public int getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(int tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EnumPedido getEstado() {
        return estado;
    }

    public void setEstado(EnumPedido estado) {
        this.estado = estado;
    }

    public int getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(int puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }
}
