package Model;
import Model.Interface.MetodoPago;

public class PagoTransferencia implements MetodoPago {

    @Override
    public void pagar(double monto) {
        System.out.println("Pagando por transferencia: " + monto);
    }
}
