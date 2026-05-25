package Controller;
import Model.*;
import Model.Enums.EntidadAfectada;
import Model.Enums.EstadoAsiento;
import Model.Enums.EstadoEvento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

    public class AdminController {

        // Usuarios
        @FXML private TextField txtAdminUsuarioNombre, txtAdminUsuarioCorreo, txtAdminUsuarioTelefono;
        @FXML private TableView<Usuario> tablaAdminUsuarios;
        @FXML private TableColumn<Usuario, String> colAdminUsuarioId, colAdminUsuarioNombre, colAdminUsuarioCorreo;

        // Recintos
        @FXML private TextField txtRecintoNombre, txtRecintoDireccion, txtRecintoCiudad;
        @FXML private TableView<Recinto> tablaRecintos;
        @FXML private TableColumn<Recinto, String> colRecintoId, colRecintoNombre, colRecintoCiudad, colRecintoCapacidad;

        // Zonas
        @FXML private ComboBox<Recinto> comboRecintoZona;
        @FXML private TextField txtZonaNombre, txtZonaCapacidad, txtZonaPrecio;
        @FXML private ComboBox<String> comboTipoZona;
        @FXML private TableView<Zona> tablaZonas;
        @FXML private TableColumn<Zona, String> colZonaId, colZonaNombre, colZonaCapacidad, colZonaPrecio, colZonaOcupacion;

        // Asientos
        @FXML private ComboBox<Zona> comboZonaAsiento;
        @FXML private TextField txtAsientoFila, txtAsientoNumero;
        @FXML private ComboBox<EstadoAsiento> comboEstadoAsiento;
        @FXML private TableView<Asiento> tablaAsientos;
        @FXML private TableColumn<Asiento, String> colAsientoId, colAsientoFila, colAsientoNumero, colAsientoEstado;

        // Eventos
        @FXML private TextField txtEventoNombre, txtEventoCategoria, txtEventoCiudad;
        @FXML private DatePicker dpEventoFecha;
        @FXML private ComboBox<Recinto> comboEventoRecinto;
        @FXML private ComboBox<EstadoEvento> comboEventoEstado;
        @FXML private TextArea txtEventoDescripcion;
        @FXML private TableView<Evento> tablaAdminEventos;
        @FXML private TableColumn<Evento, String> colAdminEventoId, colAdminEventoNombre, colAdminEventoEstado, colAdminEventoRecinto;

        // Compras
        @FXML private TableView<Compra> tablaAdminCompras;
        @FXML private TableColumn<Compra, String> colAdminCompraId, colAdminCompraUsuario, colAdminCompraEvento, colAdminCompraEstado, colAdminCompraTotal;

        // Incidencias
        @FXML private TextField txtIncidenciaTipo, txtIncidenciaEntidad;
        @FXML private TextArea txtIncidenciaDescripcion;
        @FXML private TableView<Incidencia> tablaIncidencias;
        @FXML private TableColumn<Incidencia, String> colIncidenciaId, colIncidenciaTipo, colIncidenciaEntidad, colIncidenciaFecha;

        // Metricas
        @FXML private Label lblMetricasResumen, lblAdminMensaje;
        @FXML private ComboBox<String> comboTipoReporte;
        @FXML private DatePicker dpReporteInicio, dpReporteFin;
        @FXML private BarChart<String, Number> chartComprasEstado;
        @FXML private PieChart chartEventosEstado;
        @FXML private LineChart<String, Number> chartIngresos;

        private GestionEventos gestion = GestionEventos.getInstancia();

        @FXML
        public void initialize() {
            configurarTablas();
            cargarDatos();
            comboTipoZona.setItems(FXCollections.observableArrayList("Oriental", "Central", "Occidental"));
            comboEstadoAsiento.setItems(FXCollections.observableArrayList(EstadoAsiento.values()));
            comboEventoEstado.setItems(FXCollections.observableArrayList(EstadoEvento.values()));
            comboTipoReporte.setItems(FXCollections.observableArrayList("Ventas por período", "Ocupación por zona", "Top eventos", "Tasa cancelación"));

            comboRecintoZona.getSelectionModel().selectedItemProperty().addListener((obs, old, r) -> {
                if (r != null) cargarZonasDeRecinto(r);
            });
        }

        private void configurarTablas() {
            colAdminUsuarioId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdUsuario()));
            colAdminUsuarioNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
            colAdminUsuarioCorreo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCorreoElectronico()));

            colRecintoId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdRecinto()));
            colRecintoNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
            colRecintoCiudad.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCiudad()));
            colRecintoCapacidad.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getCapacidadTotal())));

            colZonaId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdZona()));
            colZonaNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
            colZonaCapacidad.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getCapacidad())));
            colZonaPrecio.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + d.getValue().getPrecioBase()));
            colZonaOcupacion.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getOcupacion() + "%"));

            colAsientoId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdAsiento()));
            colAsientoFila.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFila()));
            colAsientoNumero.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getNumero())));
            colAsientoEstado.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEstado().name()));

            colAdminEventoId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdEvento()));
            colAdminEventoNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));
            colAdminEventoEstado.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEstado().name()));
            colAdminEventoRecinto.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getRecinto() != null ? d.getValue().getRecinto().getNombre() : "Sin recinto"));

            colAdminCompraId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdCompra()));
            colAdminCompraUsuario.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getUsuario().getNombre()));
            colAdminCompraEvento.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEvento().getNombre()));
            colAdminCompraEstado.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEstado().getNombreEstado()));
            colAdminCompraTotal.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("$" + d.getValue().getTotal()));

            colIncidenciaId.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getIdIncidencia()));
            colIncidenciaTipo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getTipo()));
            colIncidenciaEntidad.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEntidadAfectada().name()));
            colIncidenciaFecha.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFecha().toString()));
        }

        private void cargarDatos() {
            tablaAdminUsuarios.setItems(FXCollections.observableArrayList(gestion.getUsuarios()));
            tablaRecintos.setItems(FXCollections.observableArrayList(gestion.getRecintos()));
            tablaAdminEventos.setItems(FXCollections.observableArrayList(gestion.getEventos()));
            tablaAdminCompras.setItems(FXCollections.observableArrayList(gestion.getCompras()));
            tablaIncidencias.setItems(FXCollections.observableArrayList(gestion.getIncidencias()));
            comboRecintoZona.setItems(FXCollections.observableArrayList(gestion.getRecintos()));
            comboEventoRecinto.setItems(FXCollections.observableArrayList(gestion.getRecintos()));
            actualizarMetricas();
        }

        private void cargarZonasDeRecinto(Recinto r) {
            tablaZonas.setItems(FXCollections.observableArrayList(r.getZonas()));
            comboZonaAsiento.setItems(FXCollections.observableArrayList(r.getZonas()));
        }

        @FXML
        private void refrescarTodo() {
            cargarDatos();
            mostrarMensaje("Datos actualizados.");
        }

        @FXML
        private void crearUsuarioAdmin() {
            String nombre = txtAdminUsuarioNombre.getText();
            String correo = txtAdminUsuarioCorreo.getText();
            String telefono = txtAdminUsuarioTelefono.getText();
            if (nombre.isEmpty() || correo.isEmpty()) { mostrarMensaje("Nombre y correo son obligatorios."); return; }
            String id = "U" + (gestion.getUsuarios().size() + 1);
            gestion.agregarUsuario(new Usuario(id, nombre, correo, telefono));
            cargarDatos();
            txtAdminUsuarioNombre.clear(); txtAdminUsuarioCorreo.clear(); txtAdminUsuarioTelefono.clear();
            mostrarMensaje("Usuario creado.");
        }

        @FXML
        private void eliminarUsuarioAdmin() {
            Usuario sel = tablaAdminUsuarios.getSelectionModel().getSelectedItem();
            if (sel == null) { mostrarMensaje("Selecciona un usuario."); return; }
            gestion.eliminarUsuario(sel);
            cargarDatos();
            mostrarMensaje("Usuario eliminado.");
        }

        @FXML
        private void crearRecinto() {
            String nombre = txtRecintoNombre.getText();
            String dir = txtRecintoDireccion.getText();
            String ciudad = txtRecintoCiudad.getText();
            if (nombre.isEmpty()) { mostrarMensaje("Nombre es obligatorio."); return; }
            String id = "R" + (gestion.getRecintos().size() + 1);
            gestion.agregarRecinto(new Recinto(id, "", nombre, dir));
            cargarDatos();
            txtRecintoNombre.clear(); txtRecintoDireccion.clear(); txtRecintoCiudad.clear();
            mostrarMensaje("Recinto creado.");
        }

        @FXML
        private void eliminarRecinto() {
            Recinto sel = tablaRecintos.getSelectionModel().getSelectedItem();
            if (sel == null) { mostrarMensaje("Selecciona un recinto."); return; }
            gestion.eliminarRecinto(sel);
            cargarDatos();
            mostrarMensaje("Recinto eliminado.");
        }

        @FXML
        private void crearZona() {
            Recinto recinto = comboRecintoZona.getValue();
            if (recinto == null || txtZonaNombre.getText().isEmpty()) { mostrarMensaje("Selecciona recinto y nombre."); return; }
            String id = "Z" + (recinto.getZonas().size() + 1);
            double precio = Double.parseDouble(txtZonaPrecio.getText().isEmpty() ? "0" : txtZonaPrecio.getText());
            int capacidad = Integer.parseInt(txtZonaCapacidad.getText().isEmpty() ? "0" : txtZonaCapacidad.getText());
            Zona zona = new Zona(id, txtZonaNombre.getText(), capacidad, precio, comboTipoZona.getValue());
            recinto.agregarZona(zona);
            cargarZonasDeRecinto(recinto);
            txtZonaNombre.clear(); txtZonaCapacidad.clear(); txtZonaPrecio.clear();
            mostrarMensaje("Zona creada.");
        }

        @FXML
        private void eliminarZona() {
            Zona sel = tablaZonas.getSelectionModel().getSelectedItem();
            Recinto recinto = comboRecintoZona.getValue();
            if (sel == null || recinto == null) { mostrarMensaje("Selecciona zona y recinto."); return; }
            recinto.eliminarZona(sel);
            cargarZonasDeRecinto(recinto);
            mostrarMensaje("Zona eliminada.");
        }

        @FXML
        private void crearAsiento() {
            Zona zona = comboZonaAsiento.getValue();
            if (zona == null || txtAsientoFila.getText().isEmpty()) { mostrarMensaje("Selecciona zona y fila."); return; }
            String id = "A" + (zona.getAsientos().size() + 1);
            int numero = Integer.parseInt(txtAsientoNumero.getText().isEmpty() ? "0" : txtAsientoNumero.getText());
            Asiento asiento = new Asiento(id, txtAsientoFila.getText(), numero, EstadoAsiento.DISPONIBLE);
            zona.agregarAsiento(asiento);
            tablaAsientos.setItems(FXCollections.observableArrayList(zona.getAsientos()));
            txtAsientoFila.clear(); txtAsientoNumero.clear();
            mostrarMensaje("Asiento creado.");
        }

        @FXML
        private void cambiarEstadoAsiento() {
            Asiento sel = tablaAsientos.getSelectionModel().getSelectedItem();
            EstadoAsiento estado = comboEstadoAsiento.getValue();
            if (sel == null || estado == null) { mostrarMensaje("Selecciona asiento y estado."); return; }
            sel.setEstado(estado);
            tablaAsientos.refresh();
            mostrarMensaje("Estado cambiado a " + estado.name());
        }

        @FXML
        private void crearEvento() {
            String nombre = txtEventoNombre.getText();
            if (nombre.isEmpty()) { mostrarMensaje("Nombre es obligatorio."); return; }
            String id = "E" + (gestion.getEventos().size() + 1);
            Recinto recinto = comboEventoRecinto.getValue();
            Evento evento = new Evento(id, nombre, txtEventoCategoria.getText(),
                    txtEventoDescripcion.getText(), txtEventoCiudad.getText(),
                    new java.util.Date(), new java.util.Date(),
                    0, 0.0, "");
            gestion.agregarEvento(evento);
            cargarDatos();
            txtEventoNombre.clear(); txtEventoCategoria.clear(); txtEventoCiudad.clear(); txtEventoDescripcion.clear();
            mostrarMensaje("Evento creado.");
        }

        @FXML
        private void actualizarEstadoEvento() {
            Evento sel = tablaAdminEventos.getSelectionModel().getSelectedItem();
            EstadoEvento estado = comboEventoEstado.getValue();
            if (sel == null || estado == null) { mostrarMensaje("Selecciona evento y estado."); return; }
            sel.setEstado(estado);
            tablaAdminEventos.refresh();
            mostrarMensaje("Estado actualizado.");
        }

        @FXML
        private void eliminarEvento() {
            Evento sel = tablaAdminEventos.getSelectionModel().getSelectedItem();
            if (sel == null) { mostrarMensaje("Selecciona un evento."); return; }
            gestion.eliminarEvento(sel);
            cargarDatos();
            mostrarMensaje("Evento eliminado.");
        }

        @FXML
        private void confirmarCompraAdmin() {
            Compra sel = tablaAdminCompras.getSelectionModel().getSelectedItem();
            if (sel == null) { mostrarMensaje("Selecciona una compra."); return; }
            sel.confirmar();
            tablaAdminCompras.refresh();
            mostrarMensaje("Compra confirmada.");
        }

        @FXML
        private void cancelarCompraAdmin() {
            Compra sel = tablaAdminCompras.getSelectionModel().getSelectedItem();
            if (sel == null) { mostrarMensaje("Selecciona una compra."); return; }
            sel.cancelar();
            tablaAdminCompras.refresh();
            mostrarMensaje("Compra cancelada.");
        }

        @FXML
        private void reembolsarCompraAdmin() {
            Compra sel = tablaAdminCompras.getSelectionModel().getSelectedItem();
            if (sel == null) { mostrarMensaje("Selecciona una compra."); return; }
            sel.setEstado(new EstadoReembolsada());
            tablaAdminCompras.refresh();
            mostrarMensaje("Compra reembolsada.");
        }

        @FXML
        private void registrarIncidencia() {
            String tipo = txtIncidenciaTipo.getText();
            String entidad = txtIncidenciaEntidad.getText();
            String desc = txtIncidenciaDescripcion.getText();
            if (tipo.isEmpty()) { mostrarMensaje("Tipo es obligatorio."); return; }
            String id = "I" + (gestion.getIncidencias().size() + 1);
            EntidadAfectada ea = EntidadAfectada.valueOf(entidad.toUpperCase());
            Incidencia inc = new Incidencia(id, tipo, desc, new java.util.Date(), ea);
            gestion.agregarIncidencia(inc);
            tablaIncidencias.setItems(FXCollections.observableArrayList(gestion.getIncidencias()));
            txtIncidenciaTipo.clear(); txtIncidenciaEntidad.clear(); txtIncidenciaDescripcion.clear();
            mostrarMensaje("Incidencia registrada.");
        }

        @FXML
        private void exportarReporteCSV() {
            mostrarMensaje("Exportando CSV...");
        }

        @FXML
        private void exportarReportePDF() {
            mostrarMensaje("Exportando PDF...");
        }

        private void actualizarMetricas() {
            int total = gestion.getCompras().size();
            int eventos = gestion.getEventos().size();
            lblMetricasResumen.setText("Total compras: " + total + "  |  Eventos activos: " + eventos);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Creada", gestion.getCompras().stream().filter(c -> c.getEstado().getNombreEstado().equals("CREADA")).count()));
            series.getData().add(new XYChart.Data<>("Pagada", gestion.getCompras().stream().filter(c -> c.getEstado().getNombreEstado().equals("PAGADA")).count()));
            series.getData().add(new XYChart.Data<>("Confirmada", gestion.getCompras().stream().filter(c -> c.getEstado().getNombreEstado().equals("CONFIRMADA")).count()));
            series.getData().add(new XYChart.Data<>("Cancelada", gestion.getCompras().stream().filter(c -> c.getEstado().getNombreEstado().equals("CANCELADA")).count()));
            chartComprasEstado.getData().clear();
            chartComprasEstado.getData().add(series);

            chartEventosEstado.getData().clear();
            gestion.getEventos().stream()
                    .collect(java.util.stream.Collectors.groupingBy(
                            e -> e.getEstado().name(),
                            java.util.stream.Collectors.counting()))
                    .forEach((estadoNombre, cantidad) ->
                            chartEventosEstado.getData().add(
                                    new PieChart.Data(estadoNombre, cantidad.doubleValue())));
        }

        private void mostrarMensaje(String msg) {
            lblAdminMensaje.setText(msg);
        }
    }
