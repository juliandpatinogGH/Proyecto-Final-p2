public class Zona {
    private String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private String configAsientos;

    public Zona(String idZona, String nombre, int capacidad, double precioBase, String configAsientos) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.configAsientos = configAsientos;
    }

    public String getIdZona() { return idZona; }
    public void setIdZona(String idZona) { this.idZona = idZona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }

    public String getConfigAsientos() { return configAsientos; }
    public void setConfigAsientos(String configAsientos) { this.configAsientos = configAsientos; }
}
