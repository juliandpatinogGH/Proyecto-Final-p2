package Controller;

import Model.*;
import Model.Enums.EstadoEntrada;
import Model.Interface.MetodoPago;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioController {
    // Arriba con los otros @FXML
    @FXML private ComboBox<Usuario> comboUsuarioCompra;
    @FXML private ComboBox<Usuario> comboUsuario;
    @FXML private TextField txtNombrePerfil, txtCorreoPerfil, txtTelefonoPerfil;
    @FXML private TextField txtNombreRegistro, txtCorreoRegistro, txtTelefonoRegistro;

    @FXML private ComboBox<String> txtFiltroCiudad, txtFiltroCategoria;
    @FXML private TextField txtFiltroPrecio;
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

    // Costos extra por servicio
    private static final double COSTO_VIP         = 50000;
    private static final double COSTO_SEGURO       = 15000;
    private static final double COSTO_MERCH        = 20000;
    private static final double COSTO_PARQUEADERO  = 10000;

    @FXML
    public void initialize() {
        cargarUsuarios();
        cargarEventos();
        cargarMetodosPago();
        cargarFiltros();
        configurarTablaEventos();
        configurarTablaCompras();

        // Actualizar total en tiempo real al cambiar zona o servicios
        comboZona.getSelectionModel().selectedItemProperty().addListener((obs, old, zona) -> {
            if (zona != null) { cargarAsientos(zona); actualizarTotal(); }
        });
        chkVip.setOnAction(e         -> actualizarTotal());
        chkSeguro.setOnAction(e      -> actualizarTotal());
        chkMerch.setOnAction(e       -> actualizarTotal());
        chkParqueadero.setOnAction(e -> actualizarTotal());

        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> {
            if (nuevo != null) { mostrarDetalleEvento(nuevo); actualizarTotal(); }
        });

        comboUsuario.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> {
            if (nuevo != null) cargarPerfilUsuario(nuevo);
        });
    }

    // ── CARGA INICIAL ────────────────────────────────────────

    private void cargarUsuarios() {
        comboUsuario.setItems(FXCollections.observableArrayList(gestion.getUsuarios()));
        comboUsuario.setConverter(new javafx.util.StringConverter<Usuario>() {
            @Override public String toString(Usuario u) { return u == null ? "" : u.getNombre(); }
            @Override public Usuario fromString(String s) { return null; }
        });

        // ← NUEVO: también cargar en el combo de compra
        comboUsuarioCompra.setItems(FXCollections.observableArrayList(gestion.getUsuarios()));
        comboUsuarioCompra.setConverter(new javafx.util.StringConverter<Usuario>() {
            @Override public String toString(Usuario u) { return u == null ? "" : u.getNombre(); }
            @Override public Usuario fromString(String s) { return null; }
        });
        comboUsuarioCompra.getSelectionModel().selectedItemProperty().addListener((obs, old, nuevo) -> {
            if (nuevo != null) { usuarioActual = nuevo; cargarComprasUsuario(); }
        });
    }

    private void cargarEventos() {
        tablaEventos.setItems(FXCollections.observableArrayList(gestion.getEventos()));
    }

    private void cargarMetodosPago() {
        comboPago.setItems(FXCollections.observableArrayList("Tarjeta", "Efectivo", "PSE", "Nequi"));
    }

    private void cargarFiltros() {
        List<String> ciudades = gestion.getEventos().stream()
                .map(Evento::getCiudad).distinct().collect(Collectors.toList());
        ciudades.add(0, "Todas");
        txtFiltroCiudad.setItems(FXCollections.observableArrayList(ciudades));
        txtFiltroCiudad.setValue("Todas");

        List<String> categorias = gestion.getEventos().stream()
                .map(Evento::getCategoria).distinct().collect(Collectors.toList());
        categorias.add(0, "Todas");
        txtFiltroCategoria.setItems(FXCollections.observableArrayList(categorias));
        txtFiltroCategoria.setValue("Todas");
    }

    private void configurarTablaEventos() {
        colEventoNombre.setCellValueFactory(d    -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
        colEventoCategoria.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCategoria()));
        colEventoCiudad.setCellValueFactory(d    -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCiudad()));
        colEventoFecha.setCellValueFactory(d     -> new javafx.beans.property.SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(d.getValue().getFecha())));
        colEventoDesde.setCellValueFactory(d     -> new javafx.beans.property.SimpleStringProperty("$" + (long)d.getValue().getPrecio()));
    }

    private void configurarTablaCompras() {
        colCompraId.setCellValueFactory(d     -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdCompra()));
        colCompraEvento.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraEstado.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEstado().getNombreEstado()));
        colCompraTotal.setCellValueFactory(d  -> new javafx.beans.property.SimpleStringProperty("$" + (long)d.getValue().getTotal()));
        colCompraFecha.setCellValueFactory(d  -> new javafx.beans.property.SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(d.getValue().getFechaCreacion())));
    }

    // ── PERFIL ───────────────────────────────────────────────

    private void cargarPerfilUsuario(Usuario u) {
        usuarioActual = u;
        txtNombrePerfil.setText(u.getNombre());
        txtCorreoPerfil.setText(u.getCorreoElectronico());
        txtTelefonoPerfil.setText(u.getTelefono());
        cargarComprasUsuario();
        cargarZonasEvento();
    }

    @FXML
    private void aplicarPerfil() {
        if (usuarioActual == null) { mostrarAlerta("Selecciona un usuario primero."); return; }
        String nombre   = txtNombrePerfil.getText().trim();
        String correo   = txtCorreoPerfil.getText().trim();
        String telefono = txtTelefonoPerfil.getText().trim();
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) { mostrarAlerta("Todos los campos son obligatorios."); return; }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))                 { mostrarAlerta("El nombre solo puede contener letras."); return; }
        if (!correo.matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) { mostrarAlerta("Correo inválido. Ejemplo: usuario@gmail.com"); return; }
        if (!telefono.matches("\\d{7,10}"))                              { mostrarAlerta("Teléfono inválido. Solo números, 7-10 dígitos."); return; }
        usuarioActual.setNombre(nombre);
        usuarioActual.setCorreoElectronico(correo);
        usuarioActual.setTelefono(telefono);
        cargarUsuarios();
        comboUsuario.setValue(usuarioActual);
        mostrarAlerta("✅ Perfil actualizado correctamente.");
    }

    @FXML
    private void registrarUsuario() {
        String nombre   = txtNombreRegistro.getText().trim();
        String correo   = txtCorreoRegistro.getText().trim();
        String telefono = txtTelefonoRegistro.getText().trim();
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) { mostrarAlerta("Todos los campos son obligatorios."); return; }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))                 { mostrarAlerta("El nombre solo puede contener letras."); return; }
        if (gestion.getUsuarios().stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre))) { mostrarAlerta("Ya existe un usuario con ese nombre."); return; }
        if (!correo.matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) { mostrarAlerta("Correo inválido. Ejemplo: usuario@gmail.com"); return; }
        if (!telefono.matches("\\d{7,10}"))                              { mostrarAlerta("Teléfono inválido. Solo números, 7-10 dígitos."); return; }
        String id = "U" + (gestion.getUsuarios().size() + 1);
        gestion.agregarUsuario(new Usuario(id, nombre, correo, telefono));
        cargarUsuarios();
        txtNombreRegistro.clear(); txtCorreoRegistro.clear(); txtTelefonoRegistro.clear();
        mostrarAlerta("✅ Usuario registrado: " + nombre);
    }

    // ── FILTROS ──────────────────────────────────────────────

    @FXML
    private void aplicarFiltros() {
        String ciudad    = txtFiltroCiudad.getValue();
        String categoria = txtFiltroCategoria.getValue();
        String precioStr = txtFiltroPrecio.getText().trim();
        List<Evento> filtrados = gestion.getEventos().stream()
                .filter(e -> ciudad == null    || ciudad.equals("Todas")    || e.getCiudad().equalsIgnoreCase(ciudad))
                .filter(e -> categoria == null || categoria.equals("Todas") || e.getCategoria().equalsIgnoreCase(categoria))
                .filter(e -> {
                    if (precioStr.isEmpty()) return true;
                    try { return e.getPrecio() <= Double.parseDouble(precioStr); }
                    catch (NumberFormatException ex) { return true; }
                })
                .collect(Collectors.toList());
        tablaEventos.setItems(FXCollections.observableArrayList(filtrados));
    }

    @FXML
    private void limpiarFiltros() {
        txtFiltroCiudad.setValue("Todas");
        txtFiltroCategoria.setValue("Todas");
        txtFiltroPrecio.clear();
        filtroFecha.setValue(null);
        cargarEventos();
    }

    // ── EVENTOS ──────────────────────────────────────────────

    private void mostrarDetalleEvento(Evento e) {
        txtDetalleEvento.setText(
                "🎫 " + e.getNombre() + "\n" +
                        "📂 Categoría: " + e.getCategoria() + "\n" +
                        "📍 Ciudad: " + e.getCiudad() + "\n" +
                        "📅 Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(e.getFecha()) + "\n" +
                        "📝 " + e.getDescripcion() + "\n" +
                        "💰 Precio base: $" + (long)e.getPrecio()
        );
        cargarZonasEvento();
    }

    private void cargarZonasEvento() {
        Evento eventoSel = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSel != null && eventoSel.getRecinto() != null) {
            comboZona.setItems(FXCollections.observableArrayList(eventoSel.getRecinto().getZonas()));
            comboZona.setConverter(new javafx.util.StringConverter<Zona>() {
                @Override public String toString(Zona z) { return z == null ? "" : z.getNombre() + " - $" + (long)z.getPrecioBase(); }
                @Override public Zona fromString(String s) { return null; }
            });
        }
    }

    private void cargarAsientos(Zona zona) {
        comboAsiento.setItems(FXCollections.observableArrayList(zona.getAsientos()));
    }

    private void cargarComprasUsuario() {
        if (usuarioActual == null) return;
        tablaCompras.setItems(FXCollections.observableArrayList(gestion.getComprasPorUsuario(usuarioActual)));
    }

    // ── TOTAL EN TIEMPO REAL ─────────────────────────────────

    private void actualizarTotal() {
        Zona zona = comboZona.getValue();
        if (zona == null) { lblTotalCompra.setText("Total: $0"); return; }
        double total = zona.getPrecioBase();
        if (chkVip.isSelected())         total += COSTO_VIP;
        if (chkSeguro.isSelected())      total += COSTO_SEGURO;
        if (chkMerch.isSelected())       total += COSTO_MERCH;
        if (chkParqueadero.isSelected()) total += COSTO_PARQUEADERO;
        lblTotalCompra.setText("Total: $" + (long)total);
    }

    // ── COMPRA ───────────────────────────────────────────────

    @FXML
    private void crearCompra() {
        if (usuarioActual == null)                                   { mostrarAlerta("Selecciona un usuario."); return; }
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        if (evento == null)                                          { mostrarAlerta("Selecciona un evento."); return; }
        Zona zona = comboZona.getValue();
        if (zona == null)                                            { mostrarAlerta("Selecciona una zona."); return; }
        if (comboPago.getValue() == null)                            { mostrarAlerta("Selecciona un método de pago."); return; }

        String idCompra = "C" + (gestion.getCompras().size() + 1);
        Compra.CompraBuilder builder = new Compra.CompraBuilder()
                .idCompra(idCompra).usuario(usuarioActual)
                .evento(evento).fechaCreacion(new Date());

        EntradaBase entradaBase = new EntradaBase("E" + idCompra, zona, zona.getPrecioBase(), EstadoEntrada.ACTIVA);
        Entrada entrada = entradaBase;
        if (chkVip.isSelected())         entrada = new VipDecorator(entrada);
        if (chkSeguro.isSelected())      entrada = new SeguroDecorator(entrada);
        if (chkMerch.isSelected())       entrada = new PreferencialDecorator(entrada);
        if (chkParqueadero.isSelected()) entrada = new ParqueaderoDecorator(entrada);
        builder.agregarEntrada(entrada);

        MetodoPago pago = switch (comboPago.getValue()) {
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
        lblTotalCompra.setText("Total: $" + (long)compra.getTotal());
        mostrarAlerta("✅ Compra creada exitosamente!\n" +
                "Evento: " + evento.getNombre() + "\n" +
                "Zona: " + zona.getNombre() + "\n" +
                "Pago: " + comboPago.getValue() + "\n" +
                "Total: $" + (long)compra.getTotal());
    }

    @FXML
    private void confirmarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) { mostrarAlerta("Selecciona una compra."); return; }
        compra.confirmar();
        tablaCompras.refresh();
        mostrarMensaje("✅ Compra confirmada.");
    }

    @FXML
    private void cancelarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) { mostrarAlerta("Selecciona una compra."); return; }
        compra.cancelar();
        tablaCompras.refresh();
        mostrarMensaje("❌ Compra cancelada.");
    }

    // ── EXPORTAR CSV ─────────────────────────────────────────

    @FXML
    private void exportarCSV() {
        if (usuarioActual == null) { mostrarAlerta("Selecciona un usuario primero."); return; }
        List<Compra> compras = gestion.getComprasPorUsuario(usuarioActual);
        if (compras.isEmpty()) { mostrarAlerta("No hay compras para exportar."); return; }

        String nombreArchivo = "compras_" + usuarioActual.getNombre().replace(" ", "_") + ".csv";
        File archivo = new File(System.getProperty("user.home") + "/Downloads/" + nombreArchivo);

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("ID,Evento,Ciudad,Fecha Evento,Zona,Estado,Total,Fecha Compra");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            for (Compra c : compras) {
                String zona = c.getEntradas().isEmpty() ? "-" : c.getEntradas().get(0).getZona().getNombre();
                pw.printf("%s,%s,%s,%s,%s,%s,$%d,%s%n",
                        c.getIdCompra(),
                        c.getEvento().getNombre(),
                        c.getEvento().getCiudad(),
                        sdf2.format(c.getEvento().getFecha()),
                        zona,
                        c.getEstado().getNombreEstado(),
                        (long)c.getTotal(),
                        sdf.format(c.getFechaCreacion()));
            }
            mostrarAlerta("✅ CSV exportado en:\n" + archivo.getAbsolutePath());
        } catch (IOException e) {
            mostrarAlerta("Error al exportar CSV: " + e.getMessage());
        }
    }

    // ── EXPORTAR PDF (sin librerías, formato texto) ───────────

    @FXML
    private void exportarPDF() {
        if (usuarioActual == null) { mostrarAlerta("Selecciona un usuario primero."); return; }
        List<Compra> compras = gestion.getComprasPorUsuario(usuarioActual);
        if (compras.isEmpty()) { mostrarAlerta("No hay compras para exportar."); return; }

        String nombreArchivo = "boletas_" + usuarioActual.getNombre().replace(" ", "_") + ".txt";
        File archivo = new File(System.getProperty("user.home") + "/Downloads/" + nombreArchivo);
        SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("╔══════════════════════════════════════════════╗");
            pw.println("║           SISTEMA DE BOLETERÍA               ║");
            pw.println("╚══════════════════════════════════════════════╝");
            pw.println();
            pw.println("Usuario : " + usuarioActual.getNombre());
            pw.println("Correo  : " + usuarioActual.getCorreoElectronico());
            pw.println("Teléfono: " + usuarioActual.getTelefono());
            pw.println("Fecha   : " + sdf.format(new Date()));
            pw.println("──────────────────────────────────────────────");
            pw.println();

            double totalGeneral = 0;
            for (Compra c : compras) {
                String zona = c.getEntradas().isEmpty() ? "-" : c.getEntradas().get(0).getZona().getNombre();
                pw.println("  🎫 BOLETA #" + c.getIdCompra());
                pw.println("  Evento  : " + c.getEvento().getNombre());
                pw.println("  Ciudad  : " + c.getEvento().getCiudad());
                pw.println("  Fecha   : " + sdf2.format(c.getEvento().getFecha()));
                pw.println("  Zona    : " + zona);
                pw.println("  Estado  : " + c.getEstado().getNombreEstado());
                pw.println("  Total   : $" + (long)c.getTotal());
                pw.println("  Comprado: " + sdf.format(c.getFechaCreacion()));
                pw.println("  ··············································");
                pw.println();
                totalGeneral += c.getTotal();
            }

            pw.println("══════════════════════════════════════════════");
            pw.println("  TOTAL GENERAL: $" + (long)totalGeneral);
            pw.println("══════════════════════════════════════════════");

            mostrarAlerta("✅ Boletas exportadas en:\n" + archivo.getAbsolutePath());
        } catch (IOException e) {
            mostrarAlerta("Error al exportar: " + e.getMessage());
        }
    }

    // ── UTILIDADES ───────────────────────────────────────────

    private void mostrarMensaje(String msg) { lblMensajeUsuario.setText(msg); }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sistema de Boletería");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}