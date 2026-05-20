public class Tarifa {
    private String idTarifa;
    private Zona zona;
    private TipoCompra tipoCompra;
    private double precio;

    public Tarifa(String idTarifa, Zona zona, TipoCompra tipoCompra, double precio) {
        this.idTarifa = idTarifa;
        this.zona = zona;
        this.tipoCompra = tipoCompra;
        this.precio = precio;
    }

    public String getIdTarifa() { return idTarifa; }
    public Zona getZona() { return zona; }
    public TipoCompra getTipoCompra() { return tipoCompra; }
    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }
}
