package Model;

import Model.Interface.Observable;
import Model.Interface.Observador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private GestionEventos() { cargarDatosPrueba(); }

    public static GestionEventos getInstancia() {
        if (instancia == null) instancia = new GestionEventos();
        return instancia;
    }

    private Date fecha(int diasDesdeHoy) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, diasDesdeHoy);
        return c.getTime();
    }

    private void cargarDatosPrueba() {

        // ── USUARIOS ──────────────────────────────────────────
        usuarios.add(new Usuario("U1", "Oscar Moreno",   "oscar@gmail.com",  "3001234567"));
        usuarios.add(new Usuario("U2", "Ana García",     "ana@gmail.com",    "3109876543"));
        usuarios.add(new Usuario("U3", "Carlos Perez",   "carlos@gmail.com", "3205551234"));
        usuarios.add(new Usuario("U4", "Laura Ramirez",  "laura@gmail.com",  "3114445566"));

        // ── RECINTOS ──────────────────────────────────────────
        Recinto r1 = new Recinto("R1", "Estadio Atanasio",   "Cra 74",        "Medellín");
        Recinto r2 = new Recinto("R2", "El Campín",          "Av. NQS",       "Bogotá");
        Recinto r3 = new Recinto("R3", "Estadio Pascual",    "Calle 5",       "Cali");
        Recinto r4 = new Recinto("R4", "Teatro Metropolitano","Calle 41",     "Medellín");
        Recinto r5 = new Recinto("R5", "Movistar Arena",     "Calle 26",      "Bogotá");
        Recinto r6 = new Recinto("R6", "Centro de Eventos",  "Vía 40",        "Barranquilla");

        // Zonas r1
        Zona z1a = new Zona("Z1A", "General",     500, 80000,  "Occidental");
        Zona z1b = new Zona("Z1B", "VIP",          80, 200000, "Central");
        Zona z1c = new Zona("Z1C", "Preferencial", 120, 130000,"Oriental");
        r1.agregarZona(z1a); r1.agregarZona(z1b); r1.agregarZona(z1c);

        // Zonas r2
        Zona z2a = new Zona("Z2A", "General",     400, 60000,  "Norte");
        Zona z2b = new Zona("Z2B", "VIP",          60, 180000, "Central");
        r2.agregarZona(z2a); r2.agregarZona(z2b);

        // Zonas r3
        Zona z3a = new Zona("Z3A", "General",     300, 50000,  "Sur");
        Zona z3b = new Zona("Z3B", "VIP",          50, 150000, "Central");
        r3.agregarZona(z3a); r3.agregarZona(z3b);

        // Zonas r4
        Zona z4a = new Zona("Z4A", "Platea",      200, 120000, "Principal");
        Zona z4b = new Zona("Z4B", "Balcón",      100, 70000,  "Alto");
        r4.agregarZona(z4a); r4.agregarZona(z4b);

        // Zonas r5
        Zona z5a = new Zona("Z5A", "General",     600, 90000,  "Piso");
        Zona z5b = new Zona("Z5B", "VIP",         100, 220000, "Palco");
        r5.agregarZona(z5a); r5.agregarZona(z5b);

        // Zonas r6
        Zona z6a = new Zona("Z6A", "General",     350, 55000,  "Principal");
        Zona z6b = new Zona("Z6B", "VIP",          70, 160000, "Especial");
        r6.agregarZona(z6a); r6.agregarZona(z6b);

        recintos.add(r1); recintos.add(r2); recintos.add(r3);
        recintos.add(r4); recintos.add(r5); recintos.add(r6);

        // ── EVENTOS ───────────────────────────────────────────
        // Música
        Evento e1 = new Evento("E1", "Concierto Rock",      "Música",    "Gran noche de rock en vivo",         "Medellín",     fecha(5),  fecha(5),  500, 80000,  "No mascotas");
        Evento e2 = new Evento("E2", "Festival Jazz",       "Música",    "Jazz internacional con artistas top", "Bogotá",      fecha(10), fecha(10), 300, 50000,  "Solo mayores");
        Evento e3 = new Evento("E3", "Noche de Salsa",      "Música",    "Los mejores salseros del país",       "Cali",        fecha(7),  fecha(7),  400, 45000,  "Ninguna");
        Evento e4 = new Evento("E4", "Festival Vallenato",  "Música",    "Celebración del folclor colombiano",  "Barranquilla",fecha(15), fecha(15), 600, 35000,  "Ninguna");
        Evento e5 = new Evento("E5", "Concierto Pop",       "Música",    "Los mejores éxitos del pop actual",   "Bogotá",      fecha(20), fecha(20), 700, 90000,  "No menores");
        Evento e6 = new Evento("E6", "Reggaeton Night",     "Música",    "La mejor noche urbana",               "Medellín",    fecha(3),  fecha(3),  500, 70000,  "Ninguna");

        // Deportes
        Evento e7  = new Evento("E7",  "Clásico Nacional",     "Deportes", "Nacional vs Millonarios",            "Medellín",    fecha(6),  fecha(6),  45000, 60000, "Solo hinchas");
        Evento e8  = new Evento("E8",  "Final Copa Colombia",   "Deportes", "Gran final del torneo nacional",     "Bogotá",      fecha(12), fecha(12), 40000, 55000, "Ninguna");
        Evento e9  = new Evento("E9",  "Deportivo vs América",  "Deportes", "Clásico vallecaucano",               "Cali",        fecha(8),  fecha(8),  35000, 40000, "Ninguna");
        Evento e10 = new Evento("E10", "Atletismo Nacional",    "Deportes", "Campeonato nacional de atletismo",   "Barranquilla",fecha(18), fecha(18), 5000,  25000, "Ninguna");

        // Teatro
        Evento e11 = new Evento("E11", "Hamlet",             "Teatro",   "La obra clásica de Shakespeare",      "Medellín",    fecha(9),  fecha(9),  300, 120000, "Ninguna");
        Evento e12 = new Evento("E12", "El Avaro",           "Teatro",   "Comedia clásica de Molière",          "Bogotá",      fecha(14), fecha(14), 250, 90000,  "Ninguna");
        Evento e13 = new Evento("E13", "Bodas de Sangre",    "Teatro",   "Drama de García Lorca",               "Cali",        fecha(11), fecha(11), 200, 80000,  "Ninguna");

        // Conferencias
        Evento e14 = new Evento("E14", "Summit Tech 2025",   "Conferencia","Líderes mundiales de tecnología",   "Bogotá",      fecha(25), fecha(25), 1000, 150000,"Registro previo");
        Evento e15 = new Evento("E15", "Foro Emprendimiento","Conferencia","Emprendedores e inversores",         "Medellín",    fecha(22), fecha(22), 500,  80000, "Ninguna");

        // Asignar recintos
        e1.setRecinto(r1);  e2.setRecinto(r2);  e3.setRecinto(r3);
        e4.setRecinto(r6);  e5.setRecinto(r2);  e6.setRecinto(r1);
        e7.setRecinto(r1);  e8.setRecinto(r2);  e9.setRecinto(r3);
        e10.setRecinto(r6); e11.setRecinto(r4); e12.setRecinto(r5);
        e13.setRecinto(r3); e14.setRecinto(r5); e15.setRecinto(r1);

        aforo.add(e1);  aforo.add(e2);  aforo.add(e3);
        aforo.add(e4);  aforo.add(e5);  aforo.add(e6);
        aforo.add(e7);  aforo.add(e8);  aforo.add(e9);
        aforo.add(e10); aforo.add(e11); aforo.add(e12);
        aforo.add(e13); aforo.add(e14); aforo.add(e15);
    }

    @Override public void agregarObservador(Observador o) { observadores.add(o); }
    @Override public void notificarObservadores() { for (Observador o : observadores) o.actualizar(); }

    public List<Evento> getEventos()               { return aforo; }
    public void agregarEvento(Evento e)            { aforo.add(e); notificarObservadores(); }
    public void eliminarEvento(Evento e)           { aforo.remove(e); }

    public List<Usuario> getUsuarios()             { return usuarios; }
    public void agregarUsuario(Usuario u)          { usuarios.add(u); }
    public void eliminarUsuario(Usuario u)         { usuarios.remove(u); }

    public List<Recinto> getRecintos()             { return recintos; }
    public void agregarRecinto(Recinto r)          { recintos.add(r); }
    public void eliminarRecinto(Recinto r)         { recintos.remove(r); }

    public List<Compra> getCompras()               { return compras; }
    public void agregarCompra(Compra c)            { compras.add(c); }
    public List<Compra> getComprasPorUsuario(Usuario u) {
        return compras.stream()
                .filter(c -> c.getUsuario().equals(u))
                .collect(Collectors.toList());
    }

    public List<Incidencia> getIncidencias()       { return incidencias; }
    public void agregarIncidencia(Incidencia i)    { incidencias.add(i); }

    public Usuario getUsuarioActual()              { return usuarioActual; }
    public void setUsuarioActual(Usuario u)        { this.usuarioActual = u; notificarObservadores(); }

    public Administrador getAdministradorActual()  { return administradorActual; }
    public void setAdministradorActual(Administrador a) { this.administradorActual = a; }
}