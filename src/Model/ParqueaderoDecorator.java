package Model;

public class ParqueaderoDecorator extends EntradaDecorator {
    private static final double COSTO_PARQUEADERO = 15.0;

    public ParqueaderoDecorator(Entrada entrada) {
        super(entrada);
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + COSTO_PARQUEADERO;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Parqueadero";
    }
}
