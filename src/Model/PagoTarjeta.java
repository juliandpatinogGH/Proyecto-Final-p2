package Model;
import Model.Interface.MetodoPago;


import Model.Interface.MetodoPago;

public class PagoTarjeta implements MetodoPago {
    @Override
    public void pagar(double monto) {
    System.out.println("Pago con tarjeta: $" + monto);
}
}
