package Model;

import Model.Interfaces.Observador;

public class Usuario extends Persona implements Observador {
    private String idUsuario;

    public Usuario(String idUsuario, String nombre, String correoElectronico, String telefono) {
        super(idUsuario, nombre, telefono, correoElectronico);
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public void actualizar() {
        System.out.println("Model.Usuario " + nombre + " notificado de cambios.");
    }
}
