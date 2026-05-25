package Model;

import Model.Enums.EstadoEntrada;

public abstract class Entrada {
    protected String idEntrada;
    protected Zona zona;
    protected double precioBase;
    protected EstadoEntrada estadoEntrada;

    public Entrada(String idEntrada, Zona zona, double precioBase, EstadoEntrada estadoEntrada) {
        this.idEntrada = idEntrada;
        this.zona = zona;
        this.precioBase = precioBase;
        this.estadoEntrada = estadoEntrada;
    }

    public abstract double getCosto();
    public abstract String getDescripcion();

    public String getIdEntrada() { return idEntrada; }
    public void setIdEntrada(String idEntrada) { this.idEntrada = idEntrada; }

    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }

    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }

    public EstadoEntrada getEstadoEntrada() { return estadoEntrada; }
    public void setEstadoEntrada(EstadoEntrada estadoEntrada) { this.estadoEntrada = estadoEntrada; }
}
