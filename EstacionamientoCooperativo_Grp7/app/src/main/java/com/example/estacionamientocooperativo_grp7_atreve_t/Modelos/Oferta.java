package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

public class Oferta {
    private String garajeId;
    private String usuarioId;
    private String automovilId;
    private double monto;
    private String estado;
    private String fechaInicio;
    private String fechaFin;

    public Oferta(){}

    public String getGarajeId() {
        return garajeId;
    }

    public void setGarajeId(String garajeId) {
        this.garajeId = garajeId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAutomovilId() {
        return automovilId;
    }

    public void setAutomovilId(String automovilId) {
        this.automovilId = automovilId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Otros campos según sea necesario

    public Oferta(String garajeId, String usuarioId, String automovilId, double monto, String estado, String fechaInicio, String fechaFin) {
        this.garajeId = garajeId;
        this.usuarioId = usuarioId;
        this.automovilId = automovilId;
        this.monto = monto;
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