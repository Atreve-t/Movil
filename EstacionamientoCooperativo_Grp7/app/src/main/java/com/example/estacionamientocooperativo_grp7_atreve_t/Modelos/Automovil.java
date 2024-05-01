package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

public class Automovil {
    private String patente;
    private String descripcion;
    private int alto;
    private int ancho;
    private int largo;
    public Automovil(){}
    public Automovil(String propietarioId, String patente, String descripcion, int alto, int ancho, int largo) {
        this.propietarioId = propietarioId;
        this.patente = patente;
        this.descripcion = descripcion;
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
    }
    private String propietarioId;

    public String getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(String propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String marca) {
        this.patente = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

}