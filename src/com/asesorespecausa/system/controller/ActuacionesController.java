package com.asesorespecausa.system.controller;

import com.asesorespecausa.system.utils.ViewFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ActuacionesController implements Initializable {

//Atributos
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
    private TableView<String[]> tblActuaciones;
    @FXML
    private TableColumn<String[], String> colFecha;
    @FXML
    private TableColumn<String[], String> colActuacion;
    @FXML
    private TableColumn<String[], String> colDetalle;

    @FXML
    private Button btnCerrar;

    private final ObservableList<String[]> listaActuaciones = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    @FXML
    public void onCloseActuaciones() {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewWelcome();
    }

    private void configurarTabla() {
        colFecha.setCellValueFactory(data -> {
            if (data.getValue() != null && data.getValue().length > 0 && data.getValue()[0] != null) {
                return new SimpleStringProperty(data.getValue()[0]);
            }
            return new SimpleStringProperty("");
        });

        colActuacion.setCellValueFactory(data -> {
            if (data.getValue() != null && data.getValue().length > 1 && data.getValue()[1] != null) {
                return new SimpleStringProperty(data.getValue()[1]);
            }
            return new SimpleStringProperty("");
        });

        colDetalle.setCellValueFactory(data -> {
            if (data.getValue() != null && data.getValue().length > 2 && data.getValue()[2] != null) {
                return new SimpleStringProperty(data.getValue()[2]);
            }
            return new SimpleStringProperty("");
        });

        tblActuaciones.setItems(listaActuaciones);
    }

    public boolean validarCamposCliente(String cliente, String expediente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            return false;
        }
        if (expediente == null || expediente.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public void setInformacionCliente(String cliente, String expediente) {
        if (validarCamposCliente(cliente, expediente)) {
            lblNombreCliente.setText(cliente.trim());
            lblNumeroExpediente.setText(expediente.trim());
        } else {
            lblNombreCliente.setText("Sin cliente asignado");
            lblNumeroExpediente.setText("Sin expediente");
        }
    }

    public void agregarRegistroTabla(String fecha, String actuacion, String detalle) {
        if (fecha != null && actuacion != null && detalle != null) {
            if (!fecha.trim().isEmpty() || !actuacion.trim().isEmpty() || !detalle.trim().isEmpty()) {
                listaActuaciones.add(new String[]{fecha.trim(), actuacion.trim(), detalle.trim()});
            }
        }
    }

    public boolean tieneRegistros() {
        return !listaActuaciones.isEmpty();
    }

    public void limpiarTabla() {
        listaActuaciones.clear();
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        if (event != null && event.getSource() instanceof Node) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (stage != null) {
                stage.close();
            }
        }
    }

}
