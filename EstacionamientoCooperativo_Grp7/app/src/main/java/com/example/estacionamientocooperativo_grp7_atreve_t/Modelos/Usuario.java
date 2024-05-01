package com.example.estacionamientocooperativo_grp7_atreve_t.Modelos;

public class Usuario {
    private static Usuario instance;
    private String email;
    private String password;
    private String tipo;

    public Usuario(){}

    public static Usuario getInstance() {
        if (instance == null) {
            instance = new Usuario();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Otros campos según sea necesario

    public Usuario(String email,String password, String tipo) {
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }



    // Getters y setters
    // ...
}