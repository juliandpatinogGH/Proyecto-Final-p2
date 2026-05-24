package Model;

public class EstadoCreada implements EstadoCompraInterface {
    @Override
    public void pagar(Compra compra) {
        System.out.println("Model.Compra creada - > PAGADA.");
        compra.setEstado(new EstadoPagada());
    }
    @Override
    public void cancelar(Compra compra) {
        System.out.println("Model.Compra cancelada sin cargo.");
        compra.setEstado(new EstadoCancelada());
    }
    @Override
    public void confirmar(Compra compra) {
        System.out.println("No se puede confirmar sin pagar.");
    }
}