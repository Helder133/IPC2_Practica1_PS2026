package org.practica1.models;

public class Configuracion {

    private int configuracion_id;
    private int timpo_preparacion;
    private int dificultad_nivel; //tiempo que se le va reduciendo con forme sube de nivel
    private int punteo_minimo;
    private int completo;
    private int completo_optimo;
    private double completo_eficiente;
    private int cancelado;
    private int no_entregado;

    public Configuracion(int timpo_preparacion, int dificultad_nivel, int punteo_minimo, int completo, int completo_optimo, double completo_eficiente, int no_entregado, int cancelado) {
        this.timpo_preparacion = timpo_preparacion;
        this.dificultad_nivel = dificultad_nivel;
        this.punteo_minimo = punteo_minimo;
        this.completo = completo;
        this.completo_optimo = completo_optimo;
        this.completo_eficiente = completo_eficiente;
        this.no_entregado = no_entregado;
        this.cancelado = cancelado;
    }

    public int getConfiguracion_id() {
        return configuracion_id;
    }

    public void setConfiguracion_id(int configuracion_id) {
        this.configuracion_id = configuracion_id;
    }

    public int getTimpo_preparacion() {
        return timpo_preparacion;
    }

    public void setTimpo_preparacion(int timpo_preparacion) {
        this.timpo_preparacion = timpo_preparacion;
    }

    public int getDificultad_nivel() {
        return dificultad_nivel;
    }

    public void setDificultad_nivel(int dificultad_nivel) {
        this.dificultad_nivel = dificultad_nivel;
    }

    public int getPunteo_minimo() {
        return punteo_minimo;
    }

    public void setPunteo_minimo(int punteo_minimo) {
        this.punteo_minimo = punteo_minimo;
    }

    public int getCompleto() {
        return completo;
    }

    public void setCompleto(int completo) {
        this.completo = completo;
    }

    public int getCompleto_optimo() {
        return completo_optimo;
    }

    public void setCompleto_optimo(int completo_optimo) {
        this.completo_optimo = completo_optimo;
    }

    public double getCompleto_eficiente() {
        return completo_eficiente;
    }

    public void setCompleto_eficiente(double completo_eficiente) {
        this.completo_eficiente = completo_eficiente;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    public int getNo_entregado() {
        return no_entregado;
    }

    public void setNo_entregado(int no_entregado) {
        this.no_entregado = no_entregado;
    }
}
