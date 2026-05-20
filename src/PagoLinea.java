public class PagoLinea implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago en línea procesado por: $" + monto);
    }
}
