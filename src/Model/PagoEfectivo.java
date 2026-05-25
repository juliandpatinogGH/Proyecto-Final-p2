package Model;
import Model.Interface.MetodoPago;

import Model.Interface.MetodoPago;

public class PagoEfectivo implements MetodoPago {
    @Override
public void pagar(double monto) {
    System.out.println("Pago en efectivo: $" + monto);
}
}
