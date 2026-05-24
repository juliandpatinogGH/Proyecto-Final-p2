package Model;

public class PagoEfectivo implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Model.Pago en efectivo procesado por: $" + monto);
    }
}
