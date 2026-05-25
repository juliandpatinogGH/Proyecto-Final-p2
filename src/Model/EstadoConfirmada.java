package Model;

import Model.Interface.EstadoCompraInterface;

public class EstadoConfirmada implements EstadoCompraInterface {
    @Override public void pagar(Compra compra) {
        System.out.println("La compra ya fue confirmada.");
    }
    @Override public void cancelar(Compra compra) {
        System.out.println("Compra confirmada → REEMBOLSADA.");
        compra.setEstado((EstadoCompraInterface) new EstadoReembolsada());
    }
    @Override public void confirmar(Compra compra) {
        System.out.println("La compra ya fue confirmada.");
    }
    @Override public String getNombreEstado() { return "CONFIRMADA"; }
}