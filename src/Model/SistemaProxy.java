package Model;

import Model.Interfaces.SistemaEventos;

public class SistemaProxy implements SistemaEventos {
    private String perfil;
    private SistemaReal sistema;
    private GestionEventos gestionarEvento;

    public SistemaProxy(String perfil) {
        this.perfil = perfil;
        this.sistema = new SistemaReal();
        this.gestionarEvento = GestionEventos.getInstancia();
    }

    @Override
    public void gestionarEventos() {
        if (perfil.equals("ADMIN")) {
            sistema.gestionarEventos();
        } else {
            System.out.println("Proxy: acceso denegado a gestionarEventos. Perfil: " + perfil);
        }
    }

    @Override
    public void comprar() {
        sistema.comprar();
    }

    @Override
    public void verMetricas() {
        if (perfil.equals("ADMIN")) {
            sistema.verMetricas();
        } else {
            System.out.println("Proxy: acceso denegado a verMetricas. Perfil: " + perfil);
        }
    }
}
