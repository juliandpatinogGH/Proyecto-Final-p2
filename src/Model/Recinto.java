package Model;

import java.util.List;
import java.util.ArrayList;

public class Recinto {
    private String idRecinto;
    private String lugar;
    private String ciudad;
    private String nombre;
    private String direccion;
    private List<Zona> zonas;

    public Recinto(String idRecinto, String lugar, String nombre, String direccion, String ciudad) {
        this.idRecinto = idRecinto;
        this.lugar = lugar;
        this.nombre = nombre;
        this.direccion = direccion;
        this.zonas = new ArrayList<>();
    }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getIdRecinto() { return idRecinto; }
    public void setIdRecinto(String idRecinto) { this.idRecinto = idRecinto; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Zona> getZonas() { return zonas; }
    public void setZonas(List<Zona> zonas) { this.zonas = zonas; }

    public void agregarZona(Zona zona) { this.zonas.add(zona); }

    public void eliminarZona(Zona z) { zonas.remove(z); }

    public int getCapacidadTotal() {
        return zonas.stream().mapToInt(Zona::getCapacidad).sum();
    }
}
