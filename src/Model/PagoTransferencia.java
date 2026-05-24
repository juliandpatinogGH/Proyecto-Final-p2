package Model;

public class PagoTransferencia implements MetodoPago {

    @Override
    public void pagar(double monto) {
        System.out.println("Pagando por transferencia: " + monto);
    }
}