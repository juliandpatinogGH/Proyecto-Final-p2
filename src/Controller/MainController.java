package Controller;

import Model.GestionEventos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

    public class MainController {

        @FXML private Label resumenLabel;

        private GestionEventos gestion = GestionEventos.getInstancia();

        @FXML
        public void initialize() {
            actualizarResumen();
        }

        public void actualizarResumen() {
            int eventos = gestion.getEventos().size();
            int usuarios = gestion.getUsuarios().size();
            int compras = gestion.getCompras().size();
            resumenLabel.setText("Eventos: " + eventos + "  |  Usuarios: " + usuarios + "  |  Compras: " + compras);
        }
    }
