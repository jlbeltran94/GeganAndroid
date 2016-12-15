package unicauca.movil.gegan.models;

/**
 * Created by jlbel on 11/12/2016.
 */

public class Finca {

    private long id, idusr;
    private String nombre, direccion, imagen;

    public long getIdusr() {
        return idusr;
    }

    public void setIdusr(long idusr) {
        this.idusr = idusr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
