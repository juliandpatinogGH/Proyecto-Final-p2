package Model;

import Model.Enums.EntidadAfectada;

import java.util.Date;

public class Incidencia {
    private String idIncidencia;
    private String tipo;
    private String descripcion;
    private Date fecha;
    private EntidadAfectada entidadAfectada;

    public Incidencia(String idIncidencia, String tipo, String descripcion,
                      Date fecha, EntidadAfectada entidadAfectada) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;
    }

    public String getIdIncidencia() { return idIncidencia; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public Date getFecha() { return fecha; }
    public EntidadAfectada getEntidadAfectada() { return entidadAfectada; }
}
