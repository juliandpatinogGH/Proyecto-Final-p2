public class EstadoCreada implements EstadoCompraInterface {
    @Override public void pagar()    { System.out.println("Compra creada → pasando a PAGADA."); }
    @Override public void cancelar() { System.out.println("Compra creada → CANCELADA."); }
    @Override public void confirmar(){ System.out.println("Compra creada → no se puede confirmar directamente."); }
}
