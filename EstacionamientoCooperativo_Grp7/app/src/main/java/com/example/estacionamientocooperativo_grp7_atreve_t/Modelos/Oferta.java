package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

public class Oferta {
    private String garajeId;
    private String clientId;
    private String automovilId;
    private double ofertaActual;
    private double ofertaAnterior;
    private String estado;
    private HorarioGarage periodo;

    public Oferta() {}

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

    public HorarioGarage getPeriodo() {
        return periodo;
    }

    public void setPeriodo(HorarioGarage periodo) {
        this.periodo = periodo;
    }

    public Oferta(String garajeId, String clientId, String automovilId, double ofertaActual, double ofertaAnterior, String estado, HorarioGarage periodo) {
        this.garajeId = garajeId;
        this.clientId = clientId;
        this.automovilId = automovilId;
        this.ofertaActual = ofertaActual;
        this.ofertaAnterior = ofertaAnterior;
        this.estado = estado;
        this.periodo = periodo;
    }
}
