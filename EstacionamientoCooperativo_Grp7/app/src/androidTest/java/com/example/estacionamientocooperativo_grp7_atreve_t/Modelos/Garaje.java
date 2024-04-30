class Garaje {
    private String propietarioId;
    private String direccion;
    private double latitud;
    private double longitud;
    private double alto;
    private double ancho;
    private double largo;
    private String estado;
    private String dia;
    private String inicio;
    private String fin;


    public Garaje(){}

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
    public Garaje(String propietarioId, String direccion, double latitud, double longitud, double alto, double ancho, double largo, String estado, List<Horario> horarios) {
        this.propietarioId = propietarioId;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
        this.estado = estado;
        this.horarios = horarios;
    }

}