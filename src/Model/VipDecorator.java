package Model;

public class VipDecorator extends EntradaDecorator {
    private static final double COSTO_VIP = 50.0;

    public VipDecorator(Entrada entrada) {
        super(entrada);
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + COSTO_VIP;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Acceso VIP";
    }
}
