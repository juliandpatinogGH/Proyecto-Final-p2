public class EstadoPagada implements EstadoCompraInterface {
    @Override public void pagar()    { System.out.println("La compra ya fue pagada."); }
    @Override public void cancelar() { System.out.println("Compra pagada → CANCELADA (inicia reembolso)."); }
    @Override public void confirmar(){ System.out.println("Compra pagada → CONFIRMADA."); }
}
