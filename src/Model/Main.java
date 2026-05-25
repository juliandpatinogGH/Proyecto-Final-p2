

package Model;

import Model.Enums.EstadoEntrada;
import Model.Enums.TipoCompra;
import Model.Interface.MetodoPago;
import Model.Interface.SistemaEventos;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE EVENTOS");
        System.out.println("========================================\n");

        // ── 1. SINGLETON: GestionEventos ─────────────────────────
        System.out.println("--- [Singleton] GestionEventos ---");
        GestionEventos gestion = GestionEventos.getInstancia();
        GestionEventos gestion2 = GestionEventos.getInstancia();
        System.out.println("Misma instancia: " + (gestion == gestion2));

        // ── 2. OBSERVER ───────────────────────────────────────────
        System.out.println("\n--- [Observer] Registrar observadores ---");
        Usuario usuario1 = new Usuario("U001", "Ana García", "ana@mail.com", "3001234567");
        Usuario usuario2 = new Usuario("U002", "Carlos López", "carlos@mail.com", "3109876543");

        gestion.agregarObservador(usuario1);
        gestion.agregarObservador(usuario2);

        Evento evento = new Evento("E001", "Concierto Rock", "Música",
                "Gran concierto de rock nacional", "Bogotá",
                new Date(), new Date(), 1000, 120000.0, "No mascotas");

        gestion.agregarEvento(evento);

        // ── 3. DECORATOR ──────────────────────────────────────────
        System.out.println("\n--- [Decorator] Entrada con servicios adicionales ---");
        Zona zona = new Zona("Z001", "Palco", 200, 80000.0, "fila-columna");
        EntradaBase entradaBase = new EntradaBase("ENT001", zona, 80000.0, EstadoEntrada.ACTIVA);

        Entrada entradaDecorada = new ParqueaderoDecorator(
                new SeguroDecorator(
                        new VipDecorator(entradaBase)));

        System.out.println("Descripción: " + entradaDecorada.getDescripcion());
        System.out.println("Costo total: $" + entradaDecorada.getCosto());

        // ── 4. BUILDER ────────────────────────────────────────────
        System.out.println("\n--- [Builder] Crear compra ---");
        Compra compra = new Compra.CompraBuilder()
                .idCompra("C001")
                .usuario(usuario1)
                .evento(evento)
                .fechaCreacion(new Date())
                .agregarEntrada(entradaDecorada)
                .tipoCompra(TipoCompra.VIP)
                .build();

        System.out.println("Compra creada. Total: $" + compra.getTotal());

        // ── 5. STATE ──────────────────────────────────────────────
        System.out.println("\n--- [State] Ciclo de vida de la compra ---");
        compra.pagar();
        compra.confirmar();
        compra.cancelar();

        // ── 6. STRATEGY ───────────────────────────────────────────
        System.out.println("\n--- [Strategy] Métodos de pago ---");
        MetodoPago pagoTarjeta = new PagoTarjeta();
        MetodoPago pagoTransferencia = (MetodoPago) new PagoTransferencia();

        pagoTarjeta.pagar(compra.getTotal());
        pagoTransferencia.pagar(50000.0);

        // ── 7. FACADE ─────────────────────────────────────────────
        System.out.println("\n--- [Facade] ProcesoPagoFacade ---");
        Compra compra2 = new Compra.CompraBuilder()
                .idCompra("C002")
                .usuario(usuario2)
                .evento(evento)
                .fechaCreacion(new Date())
                .agregarEntrada(new PreferencialDecorator(entradaBase))
                .build();

        ProcesoPagoFacade facade = new ProcesoPagoFacade(compra2, (PagoTarjeta) pagoTarjeta);
        facade.procesarPago();

        // ── 8. PROTOTYPE ──────────────────────────────────────────
        System.out.println("\n--- [Prototype] Clonar compra ---");
        Compra compraClonada = compra2.clone();
        System.out.println("Original ID : " + compra2.getIdCompra());
        System.out.println("Clonada ID  : " + compraClonada.getIdCompra());
        System.out.println("Son distintos: " + (compra2 != compraClonada));

        // ── 9. PROXY ──────────────────────────────────────────────
        System.out.println("\n--- [Proxy] Control de acceso ---");
        SistemaEventos proxyAdmin = new SistemaProxy("ADMIN");
        SistemaEventos proxyUser = new SistemaProxy("USUARIO");

        proxyAdmin.gestionarEventos();
        proxyAdmin.verMetricas();
        proxyUser.gestionarEventos();
        proxyUser.comprar();

        System.out.println("\n========================================");
        System.out.println("  FIN DE DEMOSTRACIÓN");
        System.out.println("========================================");
    }
}