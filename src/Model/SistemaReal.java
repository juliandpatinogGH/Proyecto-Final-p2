package Model;

public class SistemaReal implements SistemaEventos {
    @Override
    public void gestionarEventos() {
        System.out.println("Model.SistemaReal: gestionando eventos...");
    }

    @Override
    public void comprar() {
        System.out.println("Model.SistemaReal: procesando compra...");
    }

    @Override
    public void verMetricas() {
        System.out.println("Model.SistemaReal: mostrando métricas...");
    }
}
