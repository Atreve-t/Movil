package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

class Automovil {

    private String marca;
    private String modelo;
    private double alto;
    private double ancho;
    private double largo;
    public Automovil(){}
    public Automovil(String propietarioId, String marca, String modelo, double alto, double ancho, double largo) {
        this.propietarioId = propietarioId;
        this.marca = marca;
        this.modelo = modelo;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }
// Otros campos según sea necesario

}