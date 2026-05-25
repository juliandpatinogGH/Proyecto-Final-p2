package Model;
import Model.Enums.EstadoAsiento;
import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private String configAsientos;
    private List<Asiento> asientos = new ArrayList<>();

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

    public List<Asiento> getAsientos() { return asientos; }
    public void agregarAsiento(Asiento a) { asientos.add(a); }
    public void eliminarAsiento(Asiento a) { asientos.remove(a); }

    public int getOcupacion() {
        if (asientos.isEmpty()) return 0;
        long vendidos = asientos.stream()
                .filter(a -> a.getEstado() == EstadoAsiento.VENDIDO)
                .count();
        return (int)(vendidos * 100 / asientos.size());
    }
}