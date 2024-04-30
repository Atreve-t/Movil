class Calificacion {
    private String ofertaId;
    private String usuarioCalificadoId;
    private String tipo;
    private int puntuacion;
    private String comentario;

    public Calificacion(){}

    public String getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(String ofertaId) {
        this.ofertaId = ofertaId;
    }

    public String getUsuarioCalificadoId() {
        return usuarioCalificadoId;
    }

    public void setUsuarioCalificadoId(String usuarioCalificadoId) {
        this.usuarioCalificadoId = usuarioCalificadoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    // Otros campos según sea necesario

    public Calificacion(String ofertaId, String usuarioCalificadoId, String tipo, int puntuacion, String comentario) {
        this.ofertaId = ofertaId;
        this.usuarioCalificadoId = usuarioCalificadoId;
        this.tipo = tipo;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }
}