package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

public class Garage {
    private String propietarioId;
    private String direccion;
    private double latitud;
    private double longitud;
    private Integer alto;
    private Integer ancho;
    private Integer largo;
    private String estado;
    private String dia;
    private String inicio;
    private String fin;


    public Garage(){}

    public String getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(String propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    // Otros campos según sea necesario
    public Garage(String propietarioId, String direccion, double latitud, double longitud, Integer alto, Integer ancho, Integer largo, String estado) {
        this.propietarioId = propietarioId;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
        this.estado = estado;
    }

}