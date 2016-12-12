package unicauca.movil.gegan.models;

import java.util.Date;

/**
 * Created by jlbel on 11/12/2016.
 */

public class Animal {

    private long id, id_finca;
    private String nombre, raza, sexo, tipo, imagen;
    private float peso, peso_al_nacer, litros_diarios, ganancia;
    private Date nacimiento;

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getPeso_al_nacer() {
        return peso_al_nacer;
    }

    public void setPeso_al_nacer(float peso_al_nacer) {
        this.peso_al_nacer = peso_al_nacer;
    }

    public float getLitros_diarios() {
        return litros_diarios;
    }

    public void setLitros_diarios(float litros_diarios) {
        this.litros_diarios = litros_diarios;
    }

    public float getGanancia() {
        return ganancia;
    }

    public void setGanancia(float ganancia) {
        this.ganancia = ganancia;
    }
}
