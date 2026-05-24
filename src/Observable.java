public interface Observable {
    void agregarObservador(Observador o);
    void notificarObservadores();
}
