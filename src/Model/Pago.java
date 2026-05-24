package Model;

import java.util.Date;

public class Pago {
    private String idPago;
    private double monto;
    private Date fecha;
    private String estado;
    private MetodoPago metodoPago;

    public Pago(String idPago, double monto, Date fecha, String estado, MetodoPago metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    public String getIdPago() { return idPago; }
    public double getMonto() { return monto; }
    public Date getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public MetodoPago getMetodoPago() { return metodoPago; }

    public void setEstado(String estado) { this.estado = estado; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
}
