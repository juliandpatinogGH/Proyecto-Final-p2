public class PreferencialDecorator extends EntradaDecorator {
    private static final double COSTO_PREFERENCIAL = 25.0;

    public PreferencialDecorator(Entrada entrada) {
        super(entrada);
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + COSTO_PREFERENCIAL;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Asiento Preferencial";
    }
}
