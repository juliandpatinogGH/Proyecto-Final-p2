package Model;

import Model.Interfaces.EstadoCompraInterface;

public class EstadoIncidencia implements EstadoCompraInterface {
    @Override public void pagar(Compra compra) {
        System.out.println("No se puede pagar, la compra tiene una incidencia.");
    }
    @Override public void cancelar(Compra compra) {
        System.out.println("Resuelva la incidencia antes de cancelar.");
    }
    @Override public void confirmar(Compra compra) {
        System.out.println("No se puede confirmar, la compra tiene una incidencia.");
    }
    @Override public String getNombreEstado() { return "INCIDENCIA"; }
}