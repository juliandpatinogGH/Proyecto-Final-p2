package Model;

import Model.Interface.EstadoCompraInterface;

public class EstadoPagada implements EstadoCompraInterface {
    @Override public void pagar(Compra compra) {
        System.out.println("La compra ya fue pagada.");
    }
    @Override public void cancelar(Compra compra) {
        System.out.println("Compra pagada → REEMBOLSADA.");
        compra.setEstado((EstadoCompraInterface) new EstadoReembolsada());
    }
    @Override public void confirmar(Compra compra) {
        System.out.println("Compra pagada → CONFIRMADA.");
        compra.setEstado(new EstadoConfirmada());
    }
    @Override public String getNombreEstado() { return "PAGADA"; }
}