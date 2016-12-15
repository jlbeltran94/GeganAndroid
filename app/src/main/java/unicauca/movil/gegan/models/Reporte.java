package unicauca.movil.gegan.models;

import java.util.Date;

/**
 * Created by jlbel on 11/12/2016.
 */

public class Reporte {
    private long id, id_finca;
    private double valor;
    private String tipo, comentario;
    private Date fecha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_finca() {
        return id_finca;
    }

    public void setId_finca(long id_finca) {
        this.id_finca = id_finca;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
