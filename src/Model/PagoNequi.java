package Model;
import Model.Interface.MetodoPago;  

public class PagoNequi implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago por Nequi: $" + monto);
    }
}