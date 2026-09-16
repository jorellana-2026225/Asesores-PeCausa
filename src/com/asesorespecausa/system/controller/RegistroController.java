package com.asesorespecausa.system.controller;

import java.net.URL;
import java.util.regex.Pattern;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.asesorespecausa.system.utils.ViewFactory;

public class RegistroController implements Initializable {

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^[0-9]{8}$");

    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtNombreCompleto;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDireccion;

    @FXML
    private Label lblError;

    @FXML
    private Button btnGuardarExpediente;
    @FXML
    private Button btnCancelar;

    @FXML
    private RadioButton rbPersonaIndividual;
    @FXML
    private RadioButton rbEmpresaJuridica;
    @FXML

    private final ViewFactory viewFactory = new ViewFactory();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarError();
        actualizarPromptsSegunTipoCliente();
    }

    @FXML
    public void onCambioTipoCliente(javafx.event.ActionEvent event) {
        ocultarError();
        actualizarPromptsSegunTipoCliente();
    }

    @FXML
    public void onRegistrar(MouseEvent event) {
        String documento = txtDocumento.getText() == null ? "" : txtDocumento.getText().trim();
        String nombreCompleto = txtNombreCompleto.getText() == null ? "" : txtNombreCompleto.getText().trim();
        String telefono = txtTelefono.getText() == null ? "" : txtTelefono.getText().trim();
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String direccion = txtDireccion.getText() == null ? "" : txtDireccion.getText().trim();

        String errorValidacion = validarCampos(documento, nombreCompleto, telefono, email, direccion);
        if (errorValidacion != null) {
            mostrarError(errorValidacion);
            return;
        }


        System.out.println("Expediente registrado: " );

        ocultarError();
        limpiarFormulario();
        viewFactory.viewWelcome();
    }

    @FXML
    public void onCancelarRegistro(MouseEvent event) {
        limpiarFormulario();
        viewFactory.viewWelcome();
    }

    private String validarCampos(String documento, String nombreCompleto,
            String telefono, String email, String direccion) {
        if (documento.isEmpty() || nombreCompleto.isEmpty() || telefono.isEmpty()
                || email.isEmpty() || direccion.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }
        if (!TELEFONO_PATTERN.matcher(telefono).matches()) {
            return "El telefono debe tener 8 digitos numericos.";
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "El correo electronico no es valido.";
        }
        return null;
    }

    private void actualizarPromptsSegunTipoCliente() {
        if (rbEmpresaJuridica.isSelected()) {
            txtDocumento.setPromptText("NIT");
            txtNombreCompleto.setPromptText("Razon social");
        } else {
            txtDocumento.setPromptText("DPI");
            txtNombreCompleto.setPromptText("Nombre completo");
        }
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }

    private void ocultarError() {
        lblError.setText("");
        lblError.setVisible(false);
        lblError.setManaged(false);
    }

    private void limpiarFormulario() {
        txtDocumento.clear();
        txtNombreCompleto.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
        rbPersonaIndividual.setSelected(true);
        actualizarPromptsSegunTipoCliente();
    }
}
