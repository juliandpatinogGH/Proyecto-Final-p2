package Model.Interface;

import Model.Compra;

public interface EstadoCompraInterface {
    void pagar(Compra compra);
    void cancelar(Compra compra);
    void confirmar(Compra compra);
    String getNombreEstado();
}