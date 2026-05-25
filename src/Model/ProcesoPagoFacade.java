package Model;

import Model.Interface.MetodoPago;

public class ProcesoPagoFacade {
    private Compra compra;
    private MetodoPago metodoPago;

    public ProcesoPagoFacade(Compra compra, PagoTarjeta metodoPago) {
        this.compra = compra;
        this.metodoPago = metodoPago;
    }

    public void procesarPago() {
        System.out.println("Facade: iniciando proceso de pago...");
        metodoPago.pagar(compra.getTotal());
        compra.pagar();
        System.out.println("Facade: pago procesado exitosamente.");
    }
}
