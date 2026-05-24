package Model;

public class EntradaBase extends Entrada implements Cloneable {

    public EntradaBase(String idEntrada, Zona zona, double precioBase, EstadoEntrada estadoEntrada) {
        super(idEntrada, zona, precioBase, estadoEntrada);
    }

    @Override
    public double getCosto() {
        return precioBase;
    }

    @Override
    public String getDescripcion() {
        return "Model.Entrada General";
    }

    @Override
    public EntradaBase clone() {
        try {
            return (EntradaBase) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
