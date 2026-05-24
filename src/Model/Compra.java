package Model;

import Model.Enums.TipoCompra;
import Model.Interfaces.EstadoCompraInterface;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Compra implements Cloneable {
    private String idCompra;
    private Usuario usuario;
    private Evento evento;
    private Date fechaCreacion;
    private double total;
    private EstadoCompraInterface estado;
    private List<ServiciosAdicionales> serviciosAdicionales;
    private TipoCompra tipoCompra;
    private List<Entrada> entradas;

    private Compra() {
        this.entradas = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
        this.estado = new EstadoCreada();
        this.tipoCompra = TipoCompra.GENERAL;
        this.fechaCreacion = new Date();
    }


    public void pagar()    { estado.pagar(this); }
    public void cancelar() { estado.cancelar(this); }
    public void confirmar(){ estado.confirmar(this); }

    public void setEstado(EstadoCompraInterface estado) { this.estado = estado; }

    // Agregar y eliminar entradas
    public void agregarEntrada(Entrada entrada) {
        entradas.add(entrada);
        calcularTotal();
    }

    public void eliminarEntrada(String idEntrada) {
        entradas.removeIf(e -> e.getIdEntrada().equals(idEntrada));
        calcularTotal();
    }

    // Agregar y eliminar servicios
    public void agregarServicio(ServiciosAdicionales servicio) {
        serviciosAdicionales.add(servicio);
        calcularTotal();
    }

    public void eliminarServicio(String idServicio) {
        serviciosAdicionales.removeIf(s -> s.getIdServicio().equals(idServicio));
        calcularTotal();
    }


    public void calcularTotal() {
        this.total = 0;
        for (Entrada e : entradas) {
            this.total += e.getCosto();
        }
        for (ServiciosAdicionales s : serviciosAdicionales) {
            this.total += s.getCosto();
        }
    }


    public String getIdCompra()                                  { return idCompra; }
    public Usuario getUsuario()                                  { return usuario; }
    public Evento getEvento()                                    { return evento; }
    public Date getFechaCreacion()                               { return fechaCreacion; }
    public double getTotal()                                     { return total; }
    public TipoCompra getTipoCompra()                            { return tipoCompra; }
    public List<Entrada> getEntradas()                           { return entradas; }
    public List<ServiciosAdicionales> getServiciosAdicionales()  { return serviciosAdicionales; }
    public EstadoCompraInterface getEstado()                     { return estado; }

    // Prototype
    @Override
    public Compra clone() {
        try {
            Compra clon = (Compra) super.clone();
            clon.entradas = new ArrayList<>(this.entradas);
            clon.serviciosAdicionales = new ArrayList<>(this.serviciosAdicionales);
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // Builder
    public static class CompraBuilder {
        private final Compra compra = new Compra();

        public CompraBuilder idCompra(String id)         { compra.idCompra = id; return this; }
        public CompraBuilder usuario(Usuario u)          { compra.usuario = u; return this; }
        public CompraBuilder evento(Evento e)            { compra.evento = e; return this; }
        public CompraBuilder tipoCompra(TipoCompra tipo) { compra.tipoCompra = tipo; return this; }

        public CompraBuilder agregarEntrada(Entrada entrada) {
            compra.entradas.add(entrada);
            return this;
        }

        public CompraBuilder agregarServicio(ServiciosAdicionales servicio) {
            compra.serviciosAdicionales.add(servicio);
            return this;
        }

        public CompraBuilder fechaCreacion(Date d) {
            compra.fechaCreacion = d;
            return this;
        }

        public Compra build() {
            if (compra.usuario == null || compra.evento == null) {
                throw new IllegalStateException("Model.Usuario y Model.Evento son obligatorios");
            }
            compra.calcularTotal();
            return compra;
        }
    }
}