package Model;

public class Administrador extends Persona {
    private String idAdmin;

    public Administrador(String idAdmin, String nombre, String correoElectronico) {
        super(idAdmin, nombre, null, correoElectronico);
        this.idAdmin = idAdmin;
    }

    public String getIdAdmin() { return idAdmin; }
    public void setIdAdmin(String idAdmin) { this.idAdmin = idAdmin; }
}
