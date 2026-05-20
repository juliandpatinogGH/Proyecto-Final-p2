import java.util.ArrayList;
import java.util.List;

public class GestionEventos implements Observable {
    // Singleton
    private static GestionEventos instancia;

    private List<Evento> aforo;
    private Usuario usuarioActual;
    private Administrador administradorActual;
    private List<Observador> observadores;

    private GestionEventos() {
        this.aforo = new ArrayList<>();
        this.observadores = new ArrayList<>();
    }

    public static GestionEventos getInstancia() {
        if (instancia == null) {
            instancia = new GestionEventos();
        }
        return instancia;
    }

    // Observer
    @Override
    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observador o : observadores) {
            o.actualizar();
        }
    }

    // Getters / Setters
    public List<Evento> getAforo() { return aforo; }
    public void setAforo(List<Evento> aforo) { this.aforo = aforo; }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        notificarObservadores();
    }

    public Administrador getAdministradorActual() { return administradorActual; }
    public void setAdministradorActual(Administrador administradorActual) {
        this.administradorActual = administradorActual;
    }

    public void agregarEvento(Evento evento) {
        aforo.add(evento);
        notificarObservadores();
    }
}
