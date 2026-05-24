package Model;

public class PagoLinea implements MetodoPago {

    @Override
    public void pagar(double monto) {
        System.out.println("Pagando en línea: " + monto);
    }
}