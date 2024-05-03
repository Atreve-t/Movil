package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;
import java.util.Calendar;

public class Oferta {
    private String garajeId;
    private String clientId;
    private String automovilId;
    private double ofertaActual;
    private double ofertaAnterior;
    private String estado;
    private Calendar fechaInicio;
    private Calendar fechaFin;

    public Oferta(){}

    public String getGarajeId() {
        return garajeId;
    }

    public void setGarajeId(String garajeId) {
        this.garajeId = garajeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAutomovilId() {
        return automovilId;
    }

    public void setAutomovilId(String automovilId) {
        this.automovilId = automovilId;
    }

    public double getOfertaActual() {
        return ofertaActual;
    }

    public void setOfertaActual(double ofertaActual) {
        this.ofertaActual = ofertaActual;
    }

    public double getOfertaAnterior() {
        return ofertaAnterior;
    }

    public void setOfertaAnterior(double ofertaAnterior) {
        this.ofertaAnterior = ofertaAnterior;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Oferta(String garajeId, String usuarioId, String automovilId, double montoActual, double montoAnterior, String estado, Calendar fechaInicio, Calendar fechaFin) {
        this.garajeId = garajeId;
        this.clientId = usuarioId;
        this.automovilId = automovilId;
        this.ofertaActual = montoActual;
        this.ofertaAnterior = montoAnterior;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Oferta(String garajeId, String estado, String fechaInicio, String fechaFin) {
        this.garajeId = garajeId;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}