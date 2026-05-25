package Model;

import Model.Interface.EstadoCompraInterface;

public class EstadoCancelada implements EstadoCompraInterface {
    @Override
    public void pagar(Compra compra) {
        System.out.println("No se puede pagar una compra cancelada.");
    }
    @Override
    public void cancelar(Compra compra) {
        System.out.println("La compra ya fue cancelada.");
    }
    @Override
    public void confirmar(Compra compra) {
        System.out.println("No se puede confirmar una compra cancelada.");
    }
    @Override public String getNombreEstado() { return "CANCELADA"; }
}