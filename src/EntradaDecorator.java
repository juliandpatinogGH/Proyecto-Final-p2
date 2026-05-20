public abstract class EntradaDecorator extends Entrada {
    protected Entrada entrada;

    public EntradaDecorator(Entrada entrada) {
        super(entrada.getIdEntrada(), entrada.getZona(), entrada.getPrecioBase(), entrada.getEstadoEntrada());
        this.entrada = entrada;
    }

    @Override
    public abstract double getCosto();

    @Override
    public abstract String getDescripcion();
}
