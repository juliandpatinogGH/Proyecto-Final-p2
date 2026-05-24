package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;
import java.util.List;

public class UsuarioController {

    @FXML private ComboBox<Usuario> comboUsuario;
    @FXML private TextField txtNombrePerfil, txtCorreoPerfil, txtTelefonoPerfil;
    @FXML private TextField txtNombreRegistro, txtCorreoRegistro, txtTelefonoRegistro;

    @FXML private TextField txtFiltroCiudad, txtFiltroCategoria, txtFiltroPrecio;
    @FXML private DatePicker filtroFecha;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colEventoNombre, colEventoCategoria, colEventoCiudad, colEventoFecha, colEventoDesde;
    @FXML private TextArea txtDetalleEvento;

    @FXML private ComboBox<Zona> comboZona;
    @FXML private ComboBox<Asiento> comboAsiento;
    @FXML private ComboBox<String> comboPago;
    @FXML private CheckBox chkVip, chkSeguro, chkMerch, chkParqueadero;
    @FXML private Label lblTotalCompra;

    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraId, colCompraEvento, colCompraEstado, colCompraTotal, colCompraFecha;
    @FXML private Label lblMensajeUsuario;

    private GestionEventos gestion = GestionEventos.getInstancia();
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        cargarUsuarios();
        cargarEventos();
        cargarMetodosPago();
        configurarTablaEventos();
        configurarTablaCompras();

        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> {
            if (nuevo != null) mostrarDetalleEvento(nuevo);
        });

        comboUsuario.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> {
            if (nuevo != null) cargarPerfilUsuario(nuevo);
        });

        comboZona.getSelectionModel().selectedItemProperty().addListener((obs, old, zona) -> {
            if (zona != null) cargarAsientos(zona);
        });
    }

    private void cargarUsuarios() {
        comboUsuario.setItems(FXCollections.observableArrayList(gestion.getUsuarios()));
        comboUsuario.setConverter(new javafx.util.StringConverter<Usuario>() {
            @Override
            public String toString(Usuario u) {
                return u == null ? "" : u.getNombre();
            }
            @Override
            public Usuario fromString(String s) { return null; }
        });
    }
    private void cargarEventos() {
        tablaEventos.setItems(FXCollections.observableArrayList(gestion.getEventos()));
    }

    private void cargarMetodosPago() {
        comboPago.setItems(FXCollections.observableArrayList("Tarjeta", "Efectivo", "PSE", "Nequi"));
    }

    private void configurarTablaEventos() {
        colEventoNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
        colEventoCategoria.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCategoria()));
        colEventoCiudad.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCiudad()));
        colEventoFecha.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFecha().toString()));
        colEventoDesde.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + d.getValue().getPrecio()));
    }

    private void configurarTablaCompras() {
        colCompraId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdCompra()));
        colCompraEvento.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraEstado.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEstado().getNombreEstado()));
        colCompraTotal.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + d.getValue().getTotal()));
        colCompraFecha.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFechaCreacion().toString()));
    }

    private void cargarPerfilUsuario(Usuario u) {
        usuarioActual = u;
        txtNombrePerfil.setText(u.getNombre());
        txtCorreoPerfil.setText(u.getCorreoElectronico());
        txtTelefonoPerfil.setText(u.getTelefono());
        cargarComprasUsuario();
        cargarZonasEvento();
    }

    private void mostrarDetalleEvento(Evento e) {
        txtDetalleEvento.setText(
                "Nombre: " + e.getNombre() + "\n" +
                        "Categoría: " + e.getCategoria() + "\n" +
                        "Ciudad: " + e.getCiudad() + "\n" +
                        "Fecha: " + e.getFecha() + "\n" +
                        "Descripción: " + e.getDescripcion() + "\n" +
                        "Precio base: $" + e.getPrecio()
        );
        cargarZonasEvento();
    }

    private void cargarZonasEvento() {
        Evento eventoSel = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSel != null && eventoSel.getRecinto() != null) {
            comboZona.setItems(FXCollections.observableArrayList(eventoSel.getRecinto().getZonas()));
        }
    }

    private void cargarAsientos(Zona zona) {
        comboAsiento.setItems(FXCollections.observableArrayList(zona.getAsientos()));
    }

    private void cargarComprasUsuario() {
        if (usuarioActual == null) return;
        List<Compra> compras = gestion.getComprasPorUsuario(usuarioActual);
        tablaCompras.setItems(FXCollections.observableArrayList(compras));
    }

    @FXML
    private void aplicarPerfil() {
        if (usuarioActual == null) { mostrarMensaje("Selecciona un usuario."); return; }
        usuarioActual.setNombre(txtNombrePerfil.getText());
        usuarioActual.setCorreoElectronico(txtCorreoPerfil.getText());
        usuarioActual.setTelefono(txtTelefonoPerfil.getText());
        mostrarMensaje("Perfil actualizado.");
    }

    @FXML
    private void registrarUsuario() {
        String nombre = txtNombreRegistro.getText();
        String correo = txtCorreoRegistro.getText();
        String telefono = txtTelefonoRegistro.getText();
        if (nombre.isEmpty() || correo.isEmpty()) { mostrarMensaje("Nombre y correo son obligatorios."); return; }
        String id = "U" + (gestion.getUsuarios().size() + 1);
        Usuario nuevo = new Usuario(id, nombre, correo, telefono);
        gestion.agregarUsuario(nuevo);
        cargarUsuarios();
        txtNombreRegistro.clear(); txtCorreoRegistro.clear(); txtTelefonoRegistro.clear();
        mostrarMensaje("Usuario registrado: " + nombre);
    }

    @FXML
    private void aplicarFiltros() {
        List<Evento> todos = gestion.getEventos();
        String ciudad = txtFiltroCiudad.getText().toLowerCase();
        String categoria = txtFiltroCategoria.getText().toLowerCase();
        String precioStr = txtFiltroPrecio.getText();

        List<Evento> filtrados = todos.stream()
                .filter(e -> ciudad.isEmpty() || e.getCiudad().toLowerCase().contains(ciudad))
                .filter(e -> categoria.isEmpty() || e.getCategoria().toLowerCase().contains(categoria))
                .filter(e -> precioStr.isEmpty() || e.getPrecio() <= Double.parseDouble(precioStr))
                .toList();

        tablaEventos.setItems(FXCollections.observableArrayList(filtrados));
    }

    @FXML
    private void limpiarFiltros() {
        txtFiltroCiudad.clear(); txtFiltroCategoria.clear(); txtFiltroPrecio.clear(); filtroFecha.setValue(null);
        cargarEventos();
    }

    @FXML
    private void crearCompra() {
        if (usuarioActual == null) { mostrarMensaje("Selecciona un usuario."); return; }
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        if (evento == null) { mostrarMensaje("Selecciona un evento."); return; }
        Zona zona = comboZona.getValue();
        if (zona == null) { mostrarMensaje("Selecciona una zona."); return; }

        String idCompra = "C" + (gestion.getCompras().size() + 1);
        Compra.CompraBuilder builder = new Compra.CompraBuilder()
                .idCompra(idCompra)
                .usuario(usuarioActual)
                .evento(evento)
                .fechaCreacion(new Date());

        Asiento asiento = comboAsiento.getValue();
        EntradaBase entradaBase = new EntradaBase("E" + idCompra, zona, zona.getPrecioBase(), EstadoEntrada.ACTIVA);

        Entrada entrada = entradaBase;
        if (chkVip.isSelected())          entrada = new VipDecorator(entrada);
        if (chkSeguro.isSelected())       entrada = new SeguroDecorator(entrada);
        if (chkMerch.isSelected())        entrada = new PreferencialDecorator(entrada);
        if (chkParqueadero.isSelected())  entrada = new ParqueaderoDecorator(entrada);

        builder.agregarEntrada(entrada);

        String metodoPago = comboPago.getValue();
        MetodoPago pago = switch (metodoPago != null ? metodoPago : "") {
            case "Tarjeta"  -> new PagoTarjeta();
            case "Efectivo" -> new PagoEfectivo();
            case "PSE"      -> new PagoPSE();
            case "Nequi"    -> new PagoNequi();
            default         -> new PagoTarjeta();
        };

        Compra compra = builder.build();
        pago.pagar(compra.getTotal());
        compra.pagar();

        gestion.agregarCompra(compra);
        cargarComprasUsuario();
        lblTotalCompra.setText("Total: $" + compra.getTotal());
        mostrarMensaje("Compra creada. Total: $" + compra.getTotal());
    }

    @FXML
    private void confirmarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) { mostrarMensaje("Selecciona una compra."); return; }
        compra.confirmar();
        tablaCompras.refresh();
        mostrarMensaje("Compra confirmada.");
    }

    @FXML
    private void cancelarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) { mostrarMensaje("Selecciona una compra."); return; }
        compra.cancelar();
        tablaCompras.refresh();
        mostrarMensaje("Compra cancelada.");
    }

    @FXML
    private void exportarCSV() {
        mostrarMensaje("Exportando CSV...");
    }

    @FXML
    private void exportarPDF() {
        mostrarMensaje("Exportando PDF...");
    }

    private void mostrarMensaje(String msg) {
        lblMensajeUsuario.setText(msg);
    }
}