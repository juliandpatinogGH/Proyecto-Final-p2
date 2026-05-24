import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestionEventos implements Observable {

    private static GestionEventos instancia;

    private List<Evento> aforo = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Recinto> recintos = new ArrayList<>();
    private List<Observador> observadores = new ArrayList<>();
    private Usuario usuarioActual;
    private Administrador administradorActual;

    private GestionEventos() {
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        Date hoy = new Date();

        usuarios.add(new Usuario("U1", "Oscar Moreno", "oscar@gmail.com", "3001234567"));
        usuarios.add(new Usuario("U2", "Ana García", "ana@gmail.com", "3109876543"));

        Recinto r1 = new Recinto("R1", "Estadio Atanasio", "Cra 74", "Medellín");
        Zona z1 = new Zona("Z1", "General", 200, 80000, "Occidental");
        Zona z2 = new Zona("Z2", "VIP", 50, 150000, "Central");
        r1.agregarZona(z1);
        r1.agregarZona(z2);
        recintos.add(r1);

        Evento e1 = new Evento("E1", "Concierto Rock", "Música", "Gran noche de rock", "Medellín", hoy, hoy, 500, 80000, "No mascotas");
        Evento e2 = new Evento("E2", "Festival Jazz", "Música", "Jazz internacional", "Bogotá", hoy, hoy, 300, 50000, "Solo mayores");
        e1.setRecinto(r1);
        e2.setRecinto(r1);
        aforo.add(e1);
        aforo.add(e2);
    }

    public static GestionEventos getInstancia() {
        if (instancia == null) instancia = new GestionEventos();
        return instancia;
    }

    @Override
    public void agregarObservador(Observador o) { observadores.add(o); }

    @Override
    public void notificarObservadores() {
        for (Observador o : observadores) o.actualizar();
    }

    public List<Evento> getAforo() { return aforo; }
    public List<Evento> getEventos() { return aforo; }
    public void agregarEvento(Evento e) { aforo.add(e); notificarObservadores(); }
    public void eliminarEvento(Evento e) { aforo.remove(e); }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void agregarUsuario(Usuario u) { usuarios.add(u); }
    public void eliminarUsuario(Usuario u) { usuarios.remove(u); }

    public List<Recinto> getRecintos() { return recintos; }
    public void agregarRecinto(Recinto r) { recintos.add(r); }
    public void eliminarRecinto(Recinto r) { recintos.remove(r); }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario u) { this.usuarioActual = u; notificarObservadores(); }

    public Administrador getAdministradorActual() { return administradorActual; }
    public void setAdministradorActual(Administrador a) { this.administradorActual = a; }
}