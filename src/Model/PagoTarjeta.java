package Model;

import Model.Interfaces.MetodoPago;

public class PagoTarjeta implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Model.Pago con tarjeta procesado por: $" + monto);
    }
}
