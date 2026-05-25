package Model;

import Model.Enums.EstadoEvento;

import java.util.Date;

public class Evento {
    private String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String ciudad;
    private Date fecha;
    private Date hora;
    private Recinto recinto;
    private EstadoEvento estado = EstadoEvento.BORRADOR;
    private int aforo;
    private double precio;
    private String reglas;

    public Evento(String idEvento, String nombre, String categoria, String descripcion,
                  String ciudad, Date fecha, Date hora, Object o, int aforo, double precio, String reglas) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.hora = hora;
        this.recinto = recinto;
        this.aforo = aforo;
        this.precio = precio;
        this.reglas = reglas;
    }

    public String getIdEvento() { return idEvento; }
    public void setIdEvento(String idEvento) { this.idEvento = idEvento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Date getHora() { return hora; }
    public void setHora(Date hora) { this.hora = hora; }

    public Recinto getRecinto() { return recinto; }
    public void setRecinto(Recinto r) { this.recinto = r; }

    public EstadoEvento getEstado() { return estado; }
    public void setEstado(EstadoEvento estado) { this.estado = estado; }

    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getReglas() { return reglas; }
    public void setReglas(String reglas) { this.reglas = reglas; }
}