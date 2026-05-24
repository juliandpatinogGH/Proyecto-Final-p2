package Model;

public class EstadoPagada implements EstadoCompraInterface {
    @Override
    public void pagar(Compra compra) {
        System.out.println("La compra ya fue pagada.");
    }
    @Override
    public void cancelar(Compra compra) {
        System.out.println("Model.Compra pagada --> REMBOLSADA.");
        compra.setEstado(new EstadoReembolsada());
    }
    @Override
    public void confirmar(Compra compra) {
        System.out.println("Model.Compra pagada - -> CONFIRMADA.");
        compra.setEstado(new EstadoConfirmada());
    }
}