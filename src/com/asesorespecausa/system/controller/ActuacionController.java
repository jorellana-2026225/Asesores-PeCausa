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

    private ObservableList<String[]> listaActuaciones
            = FXCollections.observableArrayList();

    private ActuacionRepository actuacionRepository
            = new ActuacionRepository();

    private String idExpediente;
    private String idUsuario;

  @Override
public void initialize(URL url, ResourceBundle rb) {

    tblActuaciones.setItems(listaActuaciones);

    tblActuaciones.getSelectionModel().selectedItemProperty().addListener(
            (observable, anterior, actual) -> {

                if (actual != null) {
                    cargarActuacionSeleccionada();
                }
            }
    );
}
    @FXML
    public void onEliminarActuacion(ActionEvent event) {

        String[] actuacion = obtenerActuacionSeleccionada();

        if (actuacion == null) {
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar actuación");
        alerta.setHeaderText(null);
        alerta.setContentText(
                "¿Está seguro de que desea eliminar esta actuación?");

        if (alerta.showAndWait().get() == ButtonType.OK) {
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

        listaActuaciones.add(new String[]{
            fecha.toString(),
            titulo,
            descripcion
        });

        tblActuaciones.refresh();

        txtTitulo.clear();
        txtDescripcion.clear();
        dpFechaActuacion.setValue(null);
        txtArchivoAdjunto.clear();

        System.out.println("Actuación registrada.");
    }

    public void setIdExpediente(String idExpediente) {
        this.idExpediente = idExpediente;
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

    dpFechaActuacion.setValue(LocalDate.parse(actuacion[0]));
    txtTitulo.setText(actuacion[1]);
    txtDescripcion.setText(actuacion[2]);
}
    @FXML
public void onEditarActuacion(ActionEvent event) {

    String[] actuacion = obtenerActuacionSeleccionada();

    if (actuacion == null) {
        return;
    }

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

    System.out.println("Título: " + titulo);
    System.out.println("Descripción: " + descripcion);
    System.out.println("Fecha: " + fecha);
}
}