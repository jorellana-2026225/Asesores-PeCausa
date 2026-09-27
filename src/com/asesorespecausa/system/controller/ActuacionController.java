
package com.asesorespecausa.system.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.asesorespecausa.system.model.Actuacion;
import com.asesorespecausa.system.repository.ActuacionRepository;
import com.asesorespecausa.system.utils.ViewFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ActuacionController implements Initializable {

    @FXML
    private Label lblNombreCliente;

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
    private TextField txtBuscar;

    @FXML
    private TableView<Actuacion> tblActuaciones;

    @FXML
    private TableColumn<Actuacion, String> colFecha;

    @FXML
    private TableColumn<Actuacion, String> colActuacion;

    @FXML
    private TableColumn<Actuacion, String> colDetalle;

    @FXML
    private Button btnCerrar;

    // Lista que se muestra en la tabla (puede estar filtrada por la busqueda)
    private ObservableList<Actuacion> listaActuaciones =
            FXCollections.observableArrayList();

    // Lista completa sin filtrar, se usa como base para el buscador
    private List<Actuacion> listaCompleta;

    private ActuacionRepository actuacionRepository =
            new ActuacionRepository();

    private String idExpediente;
    private String idUsuario;

    // Si no es null, significa que se esta editando esta actuacion
    // en vez de estar creando una nueva.
    private Actuacion actuacionEnEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tblActuaciones.setItems(listaActuaciones);

        colFecha.setCellValueFactory(datos ->
                new SimpleStringProperty(datos.getValue().getFechaActuacion().toString()));

        colActuacion.setCellValueFactory(datos ->
                new SimpleStringProperty(datos.getValue().getTitulo()));

        colDetalle.setCellValueFactory(datos ->
                new SimpleStringProperty(datos.getValue().getDescripcion()));
    }

    /**
     * Trae de la base de datos todas las actuaciones del expediente
     * actual y las muestra en la tabla.
     */
    private void cargarTabla() {

        if (idExpediente == null) {
            return;
        }

        listaCompleta = actuacionRepository.obtenerPorExpediente(idExpediente);
        listaActuaciones.setAll(listaCompleta);
    }

    @FXML
    public void onBuscarActuacion(ActionEvent event) {

        String textoBuscado = txtBuscar.getText();

        if (listaCompleta == null) {
            return;
        }

        if (textoBuscado == null || textoBuscado.isBlank()) {
            listaActuaciones.setAll(listaCompleta);
            return;
        }

        String texto = textoBuscado.toLowerCase();

        listaActuaciones.clear();

        for (Actuacion actuacion : listaCompleta) {
            boolean coincideTitulo = actuacion.getTitulo() != null
                    && actuacion.getTitulo().toLowerCase().contains(texto);
            boolean coincideDescripcion = actuacion.getDescripcion() != null
                    && actuacion.getDescripcion().toLowerCase().contains(texto);

            if (coincideTitulo || coincideDescripcion) {
                listaActuaciones.add(actuacion);
            }
        }
    }

    @FXML
    public void onEditarActuacion(ActionEvent event) {

        Actuacion actuacion = obtenerActuacionSeleccionada();

        if (actuacion == null) {
            return;
        }

        actuacionEnEdicion = actuacion;

        txtTitulo.setText(actuacion.getTitulo());
        txtDescripcion.setText(actuacion.getDescripcion());
        dpFechaActuacion.setValue(actuacion.getFechaActuacion());
        txtArchivoAdjunto.setText(actuacion.getArchivoAdjunto());

        btnRegistrar.setText("Guardar cambios");
    }

    @FXML
    public void onEliminarActuacion(ActionEvent event) {

        Actuacion actuacion = obtenerActuacionSeleccionada();

        if (actuacion == null) {
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar actuación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro de que desea eliminar esta actuación?");

        if (alerta.showAndWait().get() == ButtonType.OK) {

            actuacionRepository.eliminar(actuacion.getIdActuacion());
            cargarTabla();

            System.out.println("Actuación eliminada.");
        }
    }

    @FXML
    public void onRegistrarActuacion(ActionEvent event) {

        String titulo = txtTitulo.getText();
        String descripcion = txtDescripcion.getText();
        LocalDate fecha = dpFechaActuacion.getValue();
        String archivo = txtArchivoAdjunto.getText();

        if (titulo == null || titulo.isEmpty()) {
            mostrarAdvertencia("Ingrese el título.");
            return;
        }

        if (descripcion == null || descripcion.isEmpty()) {
            mostrarAdvertencia("Ingrese la descripción.");
            return;
        }

        if (fecha == null) {
            mostrarAdvertencia("Ingrese la fecha.");
            return;
        }

        if (archivo == null || archivo.isEmpty()) {
            mostrarAdvertencia("Ingrese el archivo.");
            return;
        }

        if (idExpediente == null) {
            mostrarAdvertencia("No hay un expediente asignado a esta ventana.");
            return;
        }

        if (idUsuario == null) {
            mostrarAdvertencia("No hay un usuario con sesión iniciada.");
            return;
        }

        if (actuacionEnEdicion == null) {

            // Se esta creando una actuacion nueva
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
            System.out.println("Actuación registrada.");

        } else {

            // Se esta guardando la edicion de una actuacion existente
            actuacionEnEdicion.setTitulo(titulo);
            actuacionEnEdicion.setDescripcion(descripcion);
            actuacionEnEdicion.setFechaActuacion(fecha);
            actuacionEnEdicion.setArchivoAdjunto(archivo);

            actuacionRepository.editar(actuacionEnEdicion);
            actuacionEnEdicion = null;
            btnRegistrar.setText("Registrar actuación");

            System.out.println("Actuación actualizada.");
        }

        cargarTabla();
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtTitulo.clear();
        txtDescripcion.clear();
        dpFechaActuacion.setValue(null);
        txtArchivoAdjunto.clear();
    }

    private void mostrarAdvertencia(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Actuaciones");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setIdExpediente(String idExpediente) {
        this.idExpediente = idExpediente;
        cargarTabla();
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

    private Actuacion obtenerActuacionSeleccionada() {

        Actuacion actuacion = tblActuaciones.getSelectionModel().getSelectedItem();

        if (actuacion == null) {
            mostrarAdvertencia("Seleccione una actuación de la tabla.");
            return null;
        }

        return actuacion;
    }
}