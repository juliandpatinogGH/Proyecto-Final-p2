package Model;

public class PagoNequi implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago con Nequi procesado por: $" + monto);
    }
}