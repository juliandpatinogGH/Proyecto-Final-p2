package Model;

import Model.Interfaces.Observable;
import Model.Interfaces.Observador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionEventos implements Observable {

    private static GestionEventos instancia;

    private List<Evento> aforo = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Recinto> recintos = new ArrayList<>();
    private List<Compra> compras = new ArrayList<>();
    private List<Incidencia> incidencias = new ArrayList<>();
    private Usuario usuarioActual;
    private Administrador administradorActual;
    private List<Observador> observadores = new ArrayList<>();

    private GestionEventos() {}

    public static GestionEventos getInstancia() {
        if (instancia == null) instancia = new GestionEventos();
        return instancia;
    }

    // Observer
    @Override
    public void agregarObservador(Observador o) { observadores.add(o); }

    @Override
    public void notificarObservadores() {
        for (Observador o : observadores) o.actualizar();
    }

    // Eventos
    public List<Evento> getEventos() { return aforo; }
    public void agregarEvento(Evento e) { aforo.add(e); notificarObservadores(); }
    public void eliminarEvento(Evento e) { aforo.remove(e); }

    // Usuarios
    public List<Usuario> getUsuarios() { return usuarios; }
    public void agregarUsuario(Usuario u) { usuarios.add(u); }
    public void eliminarUsuario(Usuario u) { usuarios.remove(u); }

    // Recintos
    public List<Recinto> getRecintos() { return recintos; }
    public void agregarRecinto(Recinto r) { recintos.add(r); }
    public void eliminarRecinto(Recinto r) { recintos.remove(r); }

    // Compras
    public List<Compra> getCompras() { return compras; }
    public void agregarCompra(Compra c) { compras.add(c); }
    public List<Compra> getComprasPorUsuario(Usuario u) {
        return compras.stream()
                .filter(c -> c.getUsuario().equals(u))
                .collect(Collectors.toList());
    }

    // Incidencias
    public List<Incidencia> getIncidencias() { return incidencias; }
    public void agregarIncidencia(Incidencia i) { incidencias.add(i); }

    // Sesión
    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario u) { this.usuarioActual = u; notificarObservadores(); }

    public Administrador getAdministradorActual() { return administradorActual; }
    public void setAdministradorActual(Administrador a) { this.administradorActual = a; }
}