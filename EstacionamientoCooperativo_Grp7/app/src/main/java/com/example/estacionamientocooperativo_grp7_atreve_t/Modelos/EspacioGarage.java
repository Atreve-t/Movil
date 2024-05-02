package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

public class EspacioGarage {
    private String garageId;
    private Integer largo;
    private Integer ancho;

    public EspacioGarage(String garageId, Integer alto, Integer ancho) {
        this.garageId = garageId;
        this.largo = alto;
        this.ancho = ancho;
    }
    public String getGarageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }
}
