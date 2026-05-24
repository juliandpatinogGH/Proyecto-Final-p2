public class EstadoCancelada implements EstadoCompraInterface {
    @Override public void pagar()    { System.out.println("La compra está cancelada, no se puede pagar."); }
    @Override public void cancelar() { System.out.println("La compra ya está cancelada."); }
    @Override public void confirmar(){ System.out.println("La compra está cancelada, no se puede confirmar."); }
}
