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
    private List<Entrada> serviciosAdicionales;

    // Constructor privado para Builder
    private Compra() {
        this.serviciosAdicionales = new ArrayList<>();
        this.estado = new EstadoCreada();
    }

    // State pattern methods
    public void pagar()    { estado.pagar(); }
    public void cancelar() { estado.cancelar(); }
    public void confirmar(){ estado.confirmar(); }

    public void setEstado(EstadoCompraInterface estado) { this.estado = estado; }

    public String getIdCompra() { return idCompra; }
    public Usuario getUsuario() { return usuario; }
    public Evento getEvento() { return evento; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public double getTotal() { return total; }
    public List<Entrada> getServiciosAdicionales() { return serviciosAdicionales; }

    @Override
    public Compra clone() {
        try {
            Compra clon = (Compra) super.clone();
            clon.serviciosAdicionales = new ArrayList<>(this.serviciosAdicionales);
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // ─── Builder ──────────────────────────────────────────
    public static class CompraBuilder {
        private final Compra compra = new Compra();

        public CompraBuilder idCompra(String id) { compra.idCompra = id; return this; }
        public CompraBuilder usuario(Usuario u)  { compra.usuario = u; return this; }
        public CompraBuilder evento(Evento e)    { compra.evento = e; return this; }
        public CompraBuilder fechaCreacion(Date d){ compra.fechaCreacion = d; return this; }
        public CompraBuilder total(double t)     { compra.total = t; return this; }

        public CompraBuilder agregarEntrada(Entrada entrada) {
            compra.serviciosAdicionales.add(entrada);
            compra.total += entrada.getCosto();
            return this;
        }

        public CompraBuilder agregarServicio(Entrada servicio) {
            compra.serviciosAdicionales.add(servicio);
            return this;
        }

        public CompraBuilder tipoCompra(TipoCompra tipo) {
            // lógica según tipo si se necesita
            return this;
        }

        public Compra build() { return compra; }
    }
}
