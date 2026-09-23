package com.asesorespecausa.system.controller;

import com.asesorespecausa.system.model.Expediente;
import com.asesorespecausa.system.utils.ViewFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList; 

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class ExpedienteController implements Initializable {

    @FXML
    private TextField txtNumeroExpediente;

    @FXML
    private TextField txtNombre;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtBuscador; 

    @FXML
    private TableView<Expediente> tablaExpedientes;

    @FXML
    private TableColumn<Expediente, String> colNumero;

    @FXML
    private TableColumn<Expediente, String> colNombre;

    @FXML
    private TableColumn<Expediente, LocalDate> colFecha;

    @FXML
    private TableColumn<Expediente, String> colEstado;

    private ObservableList<Expediente> expedientes = FXCollections.observableArrayList();
    
    
    private FilteredList<Expediente> expedienteFilteredList;

    private Expediente expedienteEditando = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbEstado.setItems(
                FXCollections.observableArrayList(
                        "Activo",
                        "Pendiente",
                        "Cerrado"
                )
        );

        colNumero.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNumero())
        );

        colNombre.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colFecha.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha())
        );

        colEstado.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getEstado())
        );

        // --- Configuración del Buscador ---
        expedienteFilteredList = new FilteredList<>(expedientes, b -> true);

        txtBuscador.textProperty().addListener((observable, oldValue, newValue) -> {
            expedienteFilteredList.setPredicate(expediente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                
                if (expediente.getNumero().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

       
        tablaExpedientes.setItems(expedienteFilteredList);
    }

    @FXML
    public void onGuardarExpediente(ActionEvent event) {
        String numero = txtNumeroExpediente.getText();
        String nombre = txtNombre.getText();
        LocalDate fecha = dpFecha.getValue();
        String estado = cbEstado.getValue();
        String descripcion = txtDescripcion.getText();

        if (expedienteEditando != null) {
            expedienteEditando.setNumero(numero);
            expedienteEditando.setNombre(nombre);
            expedienteEditando.setFecha(fecha);
            expedienteEditando.setEstado(estado);
            expedienteEditando.setDescripcion(descripcion);

            tablaExpedientes.refresh();
            System.out.println("Expediente actualizado.");
            expedienteEditando = null;
        } else {
            Expediente expediente = new Expediente(numero, nombre, fecha, estado, descripcion);
            expedientes.add(expediente);
            System.out.println("Expediente guardado.");
        }

        limpiarFormulario();
    }

    @FXML
    public void onEditarExpediente(ActionEvent event) {
        Expediente seleccionado = tablaExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Seleccione un expediente para editar.");
            return;
        }

        expedienteEditando = seleccionado;

        txtNumeroExpediente.setText(seleccionado.getNumero());
        txtNombre.setText(seleccionado.getNombre());
        dpFecha.setValue(seleccionado.getFecha());
        cbEstado.setValue(seleccionado.getEstado());
        txtDescripcion.setText(seleccionado.getDescripcion());

        System.out.println("Editando expediente: " + seleccionado.getNumero());
    }

    @FXML
    public void onEliminarExpediente(ActionEvent event) {
        Expediente seleccionado = tablaExpedientes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Seleccione un expediente para eliminar.");
            return;
        }

        expedientes.remove(seleccionado);

        if (expedienteEditando == seleccionado) {
            expedienteEditando = null;
        }

        limpiarFormulario();
        System.out.println("Expediente eliminado: " + seleccionado.getNumero());
    }

    @FXML
    public void onLimpiarFormulario(ActionEvent event) {
        limpiarFormulario();
        expedienteEditando = null; 
        System.out.println("Formulario limpiado.");
    }

    private void limpiarFormulario() {
        txtNumeroExpediente.clear();
        txtNombre.clear();
        dpFecha.setValue(null);
        cbEstado.setValue(null);
        txtDescripcion.clear();
    }

    @FXML
    public void onCloseExpediente(MouseEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewWelcome();
    }
}