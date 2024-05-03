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
    private HorarioGarage horarios;
    private Double precio;
    private Double estrellas;
    private String infoAdicional;

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

    public HorarioGarage getHorarios() {
        return horarios;
    }

    public void setHorarios(HorarioGarage horarios) {
        this.horarios = horarios;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getEstrellas() {return estrellas; }

    public void setEstrellas(Double estrellas) {this.estrellas = estrellas;}

    public String getInfoAdicional() {return infoAdicional;}

    public void setInfoAdicional(String infoAdicional) { this.infoAdicional = infoAdicional;}

    public Garage(String propietarioId, String direccion, double latitud, double longitud, Integer alto, Integer ancho, Integer largo, String estado, HorarioGarage horarios, Double precio, Double estrellas, String infoAdicional) {
        this.propietarioId = propietarioId;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
        this.estado = estado;
        this.horarios = horarios;
        this.precio = precio;
        this.estrellas = estrellas;
        this.infoAdicional = infoAdicional;
    }

}