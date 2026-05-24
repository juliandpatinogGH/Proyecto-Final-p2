package Model;

import Model.Interfaces.MetodoPago;

public class PagoPSE implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago con PSE procesado por: $" + monto);
    }
}