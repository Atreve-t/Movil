public class Usuario {
    private String email;
    private String tipo;

    public Usuario(){}

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

    // Otros campos según sea necesario

    public Usuario(String email, String tipo) {
        this.email = email;
        this.tipo = tipo;
    }

    // Getters y setters
    // ...
}