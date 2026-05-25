package Model;

import Model.Enums.TipoCompra;
import Model.Interface.EstadoCompraInterface;

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

    // Constructor privado para Builder
    private Compra() {
        this.entradas = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
        this.estado = new EstadoCreada();
        this.tipoCompra = TipoCompra.GENERAL;
        this.fechaCreacion = new Date();
    }

    // =========================
    // MÉTODOS STATE
    // =========================

    public void pagar() {
        estado.pagar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void confirmar() {
        estado.confirmar(this);
    }

    public void setEstado(EstadoCompraInterface estado) {
        this.estado = estado;
    }

    // =========================
    // GESTIÓN DE ENTRADAS
    // =========================

    public void agregarEntrada(Entrada entrada) {
        entradas.add(entrada);
        calcularTotal();
    }

    public void eliminarEntrada(String idEntrada) {
        entradas.removeIf(e -> e.getIdEntrada().equals(idEntrada));
        calcularTotal();
    }

    // =========================
    // GESTIÓN DE SERVICIOS
    // =========================

    public void agregarServicio(ServiciosAdicionales servicio) {
        serviciosAdicionales.add(servicio);
        calcularTotal();
    }

    public void eliminarServicio(String idServicio) {
        serviciosAdicionales.removeIf(s -> s.getIdServicio().equals(idServicio));
        calcularTotal();
    }

    // =========================
    // CÁLCULO TOTAL
    // =========================

    public void calcularTotal() {
        total = 0;

        for (Entrada entrada : entradas) {
            total += entrada.getCosto();
        }

        for (ServiciosAdicionales servicio : serviciosAdicionales) {
            total += servicio.getCosto();
        }
    }

    // =========================
    // GETTERS
    // =========================

    public String getIdCompra() {
        return idCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public double getTotal() {
        return total;
    }

    public EstadoCompraInterface getEstado() {
        return estado;
    }

    public List<ServiciosAdicionales> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public TipoCompra getTipoCompra() {
        return tipoCompra;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    // =========================
    // PROTOTYPE
    // =========================

    @Override
    public Compra clone() {
        try {

            Compra clon = (Compra) super.clone();

            // Clonado de listas
            clon.entradas = new ArrayList<>(this.entradas);
            clon.serviciosAdicionales = new ArrayList<>(this.serviciosAdicionales);

            return clon;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar la compra", e);
        }
    }

    // =========================
    // BUILDER
    // =========================

    public static class CompraBuilder {

        private final Compra compra;

        public CompraBuilder() {
            compra = new Compra();
        }

        public CompraBuilder idCompra(String idCompra) {
            compra.idCompra = idCompra;
            return this;
        }

        public CompraBuilder usuario(Usuario usuario) {
            compra.usuario = usuario;
            return this;
        }

        public CompraBuilder evento(Evento evento) {
            compra.evento = evento;
            return this;
        }

        public CompraBuilder tipoCompra(TipoCompra tipoCompra) {
            compra.tipoCompra = tipoCompra;
            return this;
        }

        public CompraBuilder agregarEntrada(Entrada entrada) {
            compra.entradas.add(entrada);
            return this;
        }

        public CompraBuilder agregarServicio(ServiciosAdicionales servicio) {
            compra.serviciosAdicionales.add(servicio);
            return this;
        }

        public CompraBuilder fechaCreacion(Date fechaCreacion) {
            compra.fechaCreacion = fechaCreacion;
            return this;
        }

        public Compra build() {

            if (compra.usuario == null) {
                throw new IllegalStateException("El usuario es obligatorio");
            }

            if (compra.evento == null) {
                throw new IllegalStateException("El evento es obligatorio");
            }

            compra.calcularTotal();

            return compra;
        }
    }
}