package Model;

import java.util.Date;

/**
 * Clase principal que demuestra el uso de todos los patrones
 * implementados en el sistema de gestión de eventos.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE EVENTOS");
        System.out.println("========================================\n");

        // ── 1. SINGLETON: GestionEventos ─────────────────────────
        System.out.println("--- [Singleton] GestionEventos ---");
        GestionEventos gestion = GestionEventos.getInstancia();
        GestionEventos gestion2 = GestionEventos.getInstancia();
        System.out.println("Misma instancia: " + (gestion == gestion2)); // true

        // ── 2. OBSERVER: Usuarios que observan eventos ────────────
        System.out.println("\n--- [Observer] Registrar observadores ---");
        Usuario usuario1 = new Usuario("U001", "Ana García", "ana@mail.com", "3001234567");
        Usuario usuario2 = new Usuario("U002", "Carlos López", "carlos@mail.com", "3109876543");
        gestion.agregarObservador(usuario1);
        gestion.agregarObservador(usuario2);

        Evento evento = new Evento(
            "E001", "Concierto Rock", "Música", "Gran concierto de rock nacional",
            "Bogotá", new Date(), new Date(), 1000, 120000.0, "No mascotas"
        );
        gestion.agregarEvento(evento); // dispara notificación a observadores

        // ── 3. DECORATOR: Construir entrada con servicios ─────────
        System.out.println("\n--- [Decorator] Entrada con servicios adicionales ---");
        Zona zona = new Zona("Z001", "Palco", 200, 80000.0, "fila-columna");
        EntradaBase entradaBase = new EntradaBase("ENT001", zona, 80000.0, EstadoEntrada.ACTIVA);

        Entrada entradaConVip       = new VipDecorator(entradaBase);
        Entrada entradaConSeguro    = new SeguroDecorator(entradaConVip);
        Entrada entradaConParqueo   = new ParqueaderoDecorator(entradaConSeguro);

        System.out.println("Descripción: " + entradaConParqueo.getDescripcion());
        System.out.println("Costo total: $" + entradaConParqueo.getCosto());

        // ── 4. BUILDER: Construir una Compra paso a paso ──────────
        System.out.println("\n--- [Builder] Crear compra ---");
        Compra compra = new Compra.CompraBuilder()
            .idCompra("C001")
            .usuario(usuario1)
            .evento(evento)
            .fechaCreacion(new Date())
            .agregarEntrada(entradaConParqueo)
            .tipoCompra(TipoCompra.VIP)
            .build();

        System.out.println("Compra creada. Total: $" + compra.getTotal());

        // ── 5. STATE: Ciclo de vida de la compra ──────────────────
        System.out.println("\n--- [State] Ciclo de vida de la compra ---");
        compra.pagar();                          // Estado: CREADA → pagar
        compra.setEstado(new EstadoPagada());
        compra.confirmar();                      // Estado: PAGADA → confirmar
        compra.setEstado(new EstadoConfirmada());
        compra.cancelar();                       // Estado: CONFIRMADA → cancelar

        // ── 6. STRATEGY: Elegir método de pago ───────────────────
        System.out.println("\n--- [Strategy] Métodos de pago ---");
        MetodoPago pagoTarjeta      = new PagoTarjeta();
        MetodoPago pagoTransferencia= new PagoTransferencia();
        MetodoPago pagoEfectivo     = new PagoEfectivo();
        MetodoPago pagoLinea        = new PagoLinea();

        pagoTarjeta.pagar(compra.getTotal());
        pagoTransferencia.pagar(50000.0);

        // ── 7. FACADE: Proceso de pago simplificado ───────────────
        System.out.println("\n--- [Facade] ProcesoPagoFacade ---");
        Compra compra2 = new Compra.CompraBuilder()
            .idCompra("C002")
            .usuario(usuario2)
            .evento(evento)
            .fechaCreacion(new Date())
            .agregarEntrada(new PreferencialDecorator(entradaBase))
            .build();

        ProcesoPagoFacade facade = new ProcesoPagoFacade(compra2, pagoLinea);
        facade.procesarPago();

        // ── 8. PROTOTYPE: Clonar una compra ───────────────────────
        System.out.println("\n--- [Prototype] Clonar compra ---");
        Compra compraClonada = compra2.clone();
        System.out.println("Compra original  id: " + compra2.getIdCompra());
        System.out.println("Compra clonada   id: " + compraClonada.getIdCompra());
        System.out.println("Son objetos distintos: " + (compra2 != compraClonada));

        // ── 9. PROXY: Control de acceso al sistema ────────────────
        System.out.println("\n--- [Proxy] Control de acceso ---");
        SistemaEventos proxyAdmin   = new SistemaProxy("ADMIN");
        SistemaEventos proxyUsuario = new SistemaProxy("USUARIO");

        proxyAdmin.gestionarEventos();   // permitido
        proxyAdmin.verMetricas();        // permitido
        proxyUsuario.gestionarEventos(); // denegado
        proxyUsuario.comprar();          // permitido

        System.out.println("\n========================================");
        System.out.println("  FIN DE DEMOSTRACIÓN");
        System.out.println("========================================");
    }
}
