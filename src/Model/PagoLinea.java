package Model;
import Model.Interface.MetodoPago;

import Model.Interface.MetodoPago;

public class PagoLinea implements MetodoPago {

    @Override
    public void pagar(double monto) {
        System.out.println("Pagando en línea: " + monto);
    }
}
