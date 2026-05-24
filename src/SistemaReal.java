public class SistemaReal implements SistemaEventos {
    @Override
    public void gestionarEventos() {
        System.out.println("SistemaReal: gestionando eventos...");
    }

    @Override
    public void comprar() {
        System.out.println("SistemaReal: procesando compra...");
    }

    @Override
    public void verMetricas() {
        System.out.println("SistemaReal: mostrando métricas...");
    }
}
