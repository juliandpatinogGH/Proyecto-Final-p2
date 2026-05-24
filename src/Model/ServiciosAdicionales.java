package Model;

public class ServiciosAdicionales {
    private String tipo;
    private double costo;
    private String idServicio;
    public ServiciosAdicionales(String tipo, double costo, String idServicio) {
        this.tipo = tipo;
        this.costo = costo;
        this.idServicio = idServicio;
    }

    public String getTipo()  { return tipo; }
    public double getCosto() { return costo; }
    public String getIdServicio() { return idServicio; }
}