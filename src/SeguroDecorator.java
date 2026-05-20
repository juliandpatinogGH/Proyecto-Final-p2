public class SeguroDecorator extends EntradaDecorator {
    private static final double COSTO_SEGURO = 10.0;

    public SeguroDecorator(Entrada entrada) {
        super(entrada);
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + COSTO_SEGURO;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Seguro de Cancelación";
    }
}
