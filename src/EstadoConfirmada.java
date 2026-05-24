public class EstadoConfirmada implements EstadoCompraInterface {
    @Override public void pagar()    { System.out.println("La compra ya fue confirmada."); }
    @Override public void cancelar() { System.out.println("Compra confirmada → CANCELADA."); }
    @Override public void confirmar(){ System.out.println("La compra ya está confirmada."); }
}
