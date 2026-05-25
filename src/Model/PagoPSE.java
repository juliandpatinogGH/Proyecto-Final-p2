package Model;
import Model.Interface.MetodoPago;

public class PagoPSE implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago por PSE: $" + monto);
    }
}
