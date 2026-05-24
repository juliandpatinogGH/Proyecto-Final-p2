package Model;

public class EstadoPagada implements EstadoCompraInterface {
    @Override public void pagar(Compra compra) {
        System.out.println("La compra ya fue pagada.");
    }
    @Override public void cancelar(Compra compra) {
        System.out.println("Compra pagada → REEMBOLSADA.");
        compra.setEstado(new EstadoReembolsada());
    }
    @Override public void confirmar(Compra compra) {
        System.out.println("Compra pagada → CONFIRMADA.");
        compra.setEstado(new EstadoConfirmada());
    }
    @Override public String getNombreEstado() { return "PAGADA"; }
}