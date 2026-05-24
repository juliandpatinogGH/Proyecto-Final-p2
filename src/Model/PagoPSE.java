package Model;

public class PagoPSE implements MetodoPago {
    @Override
    public void pagar(double monto) {
        System.out.println("Pago con PSE procesado por: $" + monto);
    }
}