package com.asesorespecausa.system.controller;

import com.asesorespecausa.system.model.Actuacion;
import com.asesorespecausa.system.repository.ActuacionRepository;
import com.asesorespecausa.system.utils.ViewFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;

public class ActuacionController implements Initializable {

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblCliente;

    @FXML
    private Label lblNombreCliente;

    @FXML
    private Label lblExpediente;

    @FXML
    private Label lblNumeroExpediente;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private DatePicker dpFechaActuacion;

    @FXML
    private TextField txtArchivoAdjunto;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TableView<String[]> tblActuaciones;

    @FXML
    private TableColumn<String[], String> colFecha;

    @FXML
    private TableColumn<String[], String> colActuacion;

    @FXML
    private TableColumn<String[], String> colDetalle;

    @FXML
    private Button btnCerrar;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnBuscar;

    private ObservableList<String[]> listaActuaciones
            = FXCollections.observableArrayList();

    private ActuacionRepository actuacionRepository
            = new ActuacionRepository();

    private String idExpediente;
    private String idUsuario;

  @Override
public void initialize(URL url, ResourceBundle rb) {

    // TEMPORAL: solo para pruebas, mientras se conecta el flujo real
    // desde la vista Expediente. Reemplaza estos valores por los que
    // obtuviste con las consultas SQL, y borra estas 2 líneas cuando
    // ya tengas la navegación real implementada.
    idExpediente = "EXP11111-1111-1111-1111-111111111111";
    idUsuario = "22222222-2222-2222-2222-222222222222";

    // T1.1 - Asignar sus Propiedades a cada Columna
    colFecha.setCellValueFactory(dataColumna
            -> new SimpleStringProperty(dataColumna.getValue()[1]));

    colActuacion.setCellValueFactory(dataColumna
            -> new SimpleStringProperty(dataColumna.getValue()[2]));

    colDetalle.setCellValueFactory(dataColumna
            -> new SimpleStringProperty(dataColumna.getValue()[3]));

    tblActuaciones.setItems(listaActuaciones);

    tblActuaciones.getSelectionModel().selectedItemProperty().addListener(
            (observable, anterior, actual) -> {

                if (actual != null) {
                    cargarActuacionSeleccionada();
                }
            }
    );

    // TEMPORAL: normalmente esto se dispara solo con setIdExpediente,
    // pero como aún no hay navegación real, lo llamamos aquí para probar.
    cargarActuaciones();
}
    @FXML
    public void onEliminarActuacion(ActionEvent event) {

        String[] actuacion = obtenerActuacionSeleccionada();

        if (actuacion == null) {
            return;
        }

        // T1.2 - Lanzar una Alerta de Confirmacion
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar actuación");
        alerta.setHeaderText(null);
        alerta.setContentText(
                "¿Está seguro de que desea eliminar esta actuación?");

        // T1.3 - Si el Usuario Acepta Removerla de la Tabla
        if (alerta.showAndWait().get() == ButtonType.OK) {

            String idActuacion = actuacion[0];

            actuacionRepository.eliminar(idActuacion);

            listaActuaciones.remove(actuacion);
            tblActuaciones.refresh();

            System.out.println("Actuación eliminada.");
        }
    }

    @FXML
    public void onRegistrarActuacion(ActionEvent event) {

        String titulo = txtTitulo.getText();
        String descripcion = txtDescripcion.getText();
        LocalDate fecha = dpFechaActuacion.getValue();
        String archivo = txtArchivoAdjunto.getText();

        if (titulo.isEmpty()) {
            System.out.println("Ingrese el título.");
            return;
        }

        if (descripcion.isEmpty()) {
            System.out.println("Ingrese la descripción.");
            return;
        }

        if (fecha == null) {
            System.out.println("Ingrese la fecha.");
            return;
        }

        if (archivo.isEmpty()) {
            System.out.println("Ingrese el archivo.");
            return;
        }

        if (idExpediente == null) {
            System.out.println("No hay expediente.");
            return;
        }

        if (idUsuario == null) {
            System.out.println("No hay usuario.");
            return;
        }

        Actuacion actuacion = new Actuacion(
                null,
                idExpediente,
                idUsuario,
                titulo,
                descripcion,
                fecha,
                archivo
        );

        actuacionRepository.create(actuacion);

        txtTitulo.clear();
        txtDescripcion.clear();
        dpFechaActuacion.setValue(null);
        txtArchivoAdjunto.clear();

        cargarActuaciones();

        System.out.println("Actuación registrada.");
    }

    public void setIdExpediente(String idExpediente) {
        this.idExpediente = idExpediente;
        cargarActuaciones();
    }

    // T1.2 - Llamar al Metodo nesesario desde ActuacionRepository
    private void cargarActuaciones() {

        if (idExpediente == null) {
            return;
        }

        listaActuaciones.setAll(
                actuacionRepository.listarPorExpediente(idExpediente));

        tblActuaciones.refresh();
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    public void onCloseActuaciones(ActionEvent event) {

        ViewFactory viewFactory = new ViewFactory();
        viewFactory.viewWelcome();
    }

    public void setInformacionCliente(String cliente, String expediente) {

        lblNombreCliente.setText(cliente);
        lblNumeroExpediente.setText(expediente);
    }

    public void agregarRegistroTabla(
            String fecha,
            String actuacion,
            String detalle) {

        listaActuaciones.add(new String[]{
            null,
            fecha,
            actuacion,
            detalle
        });
    }

    public boolean tieneRegistros() {

        return !listaActuaciones.isEmpty();
    }

    public void limpiarTabla() {

        listaActuaciones.clear();
    }

    private String[] obtenerActuacionSeleccionada() {

        String[] actuacion
                = tblActuaciones.getSelectionModel().getSelectedItem();

        if (actuacion == null) {
            System.out.println("Seleccione una actuación.");
            return null;
        }

        return actuacion;
    }
    private void cargarActuacionSeleccionada() {

    String[] actuacion = obtenerActuacionSeleccionada();

    if (actuacion == null) {
        return;
    }

    // T1.1 - Seleccionar la Actuacion de la Tabla
    dpFechaActuacion.setValue(LocalDate.parse(actuacion[1]));
    txtTitulo.setText(actuacion[2]);
    txtDescripcion.setText(actuacion[3]);
}
    @FXML
public void onEditarActuacion(ActionEvent event) {

    // T1.1 - Seleccionar la Actuacion de la Tabla
    String[] actuacion = obtenerActuacionSeleccionada();

    if (actuacion == null) {
        return;
    }

    // T1.2 - Capturar los Valores Modificados en los TextFields
    String titulo = txtTitulo.getText();
    String descripcion = txtDescripcion.getText();
    LocalDate fecha = dpFechaActuacion.getValue();

    if (titulo.isEmpty()) {
        System.out.println("Ingrese el título.");
        return;
    }

    if (descripcion.isEmpty()) {
        System.out.println("Ingrese la descripción.");
        return;
    }

    if (fecha == null) {
        System.out.println("Ingrese la fecha.");
        return;
    }

    String idActuacion = actuacion[0];

    Actuacion actuacionEditada = new Actuacion(
            idActuacion,
            idExpediente,
            idUsuario,
            titulo,
            descripcion,
            fecha,
            txtArchivoAdjunto.getText()
    );

    // T1.3 - Llamar a ActuacionRepository
    actuacionRepository.editar(actuacionEditada);

    // T1.4 - Refrescar la tabla con los nuevos cambios
    cargarActuaciones();

    System.out.println("Actuación editada.");
}

    // T1.1 - Capturar el Texto Ingresado para Buscar
    @FXML
    public void onBuscarActuacion(ActionEvent event) {

        if (idExpediente == null) {
            return;
        }

        String texto = txtBuscar.getText();

        if (texto == null || texto.isEmpty()) {
            // T1.2 - Llamar una Consulta de ActuacionRepository
            cargarActuaciones();
            return;
        }

        // T1.2 - Llamar una Consulta de ActuacionRepository
        ObservableList<String[]> resultados
                = actuacionRepository.buscarPorTitulo(idExpediente, texto);

        // T1.3 - Remplazar los datos de la Columna
        listaActuaciones.setAll(resultados);

        tblActuaciones.refresh();

        System.out.println("Búsqueda realizada: " + texto);
    }
}