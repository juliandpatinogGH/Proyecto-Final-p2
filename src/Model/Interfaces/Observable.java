package Model.Interfaces;

public interface Observable {
    void agregarObservador(Observador o);
    void notificarObservadores();
}
