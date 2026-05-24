package Model;

import Model.Interfaces.MetodoPago;

public class PagoEfectivo implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Model.Pago en efectivo procesado por: $" + monto);
    }
}
