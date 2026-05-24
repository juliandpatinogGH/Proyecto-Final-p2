package Model;

public interface EstadoCompraInterface {
    void pagar(Compra compra);
    void cancelar(Compra compra);
    void confirmar(Compra compra);
}