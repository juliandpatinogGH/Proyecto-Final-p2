package Model;

public class EstadoReembolsada implements EstadoCompraInterface {
    @Override public void pagar(Compra compra) {
        System.out.println("No se puede pagar una compra reembolsada.");
    }
    @Override public void cancelar(Compra compra) {
        System.out.println("La compra ya fue reembolsada.");
    }
    @Override public void confirmar(Compra compra) {
        System.out.println("No se puede confirmar una compra reembolsada.");
    }
    @Override public String getNombreEstado() { return "REEMBOLSADA"; }
}