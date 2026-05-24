package Model;

public class EstadoConfirmada implements EstadoCompraInterface {
    @Override
    public void pagar(Compra compra) {
        System.out.println("La compra ya fue confirmada.");
    }
    @Override
    public void cancelar(Compra compra) {
        System.out.println("Model.Compra confirmada --> REEMBOLSADA.");
        compra.setEstado(new EstadoReembolsada());
    }
    @Override
    public void confirmar(Compra compra) {
        System.out.println("La compra ya fue confirmada.");
    }
}