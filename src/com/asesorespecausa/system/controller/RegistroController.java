package com.asesorespecausa.system.controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.asesorespecausa.system.model.Cliente;
import com.asesorespecausa.system.repository.ClienteRepository;
import com.asesorespecausa.system.utils.ViewFactory;

public class RegistroController implements Initializable {

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
    private TextField txtBuscar;

    @FXML
    private Label lblError;

    @FXML
    private Button btnGuardarExpediente;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;

    @FXML
    private RadioButton rbPersonaIndividual;
    @FXML
    private RadioButton rbEmpresaJuridica;

    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, String> colTipoPersona;
    @FXML
    private TableColumn<Cliente, String> colNombreCompleto;
    @FXML
    private TableColumn<Cliente, String> colDocumentoIdentidad;
    @FXML
    private TableColumn<Cliente, String> colTelefonoPrincipal;
    @FXML
    private TableColumn<Cliente, String> colCorreoElectronico;
    @FXML
    private TableColumn<Cliente, String> colDireccionFisica;
    @FXML
    private TableColumn<Cliente, Timestamp> colFechaRegistro;

    private final ViewFactory viewFactory = new ViewFactory();
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private Cliente clienteSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarError();
        actualizarPromptsSegunTipoCliente();
        configurarColumnasTabla();
        cargarClientes();
        configurarSeleccionTabla();
    }

    private void configurarSeleccionTabla() {
        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado == null) {
                return;
            }
            clienteSeleccionado = seleccionado;
            ocultarError();
            txtDocumento.setText(seleccionado.getDocumento_identidad());
            txtNombreCompleto.setText(seleccionado.getNombre_completo());
            txtTelefono.setText(seleccionado.getTelefono_principal());
            txtEmail.setText(seleccionado.getCorreo_electronico());
            txtDireccion.setText(seleccionado.getDireccion_fisica());
            if ("EMPRESA".equals(seleccionado.getTipo_persona())) {
                rbEmpresaJuridica.setSelected(true);
            } else {
                rbPersonaIndividual.setSelected(true);
            }
            actualizarPromptsSegunTipoCliente();
        });
    }

    private void configurarColumnasTabla() {
        colTipoPersona.setCellValueFactory(new PropertyValueFactory<>("tipo_persona"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombre_completo"));
        colDocumentoIdentidad.setCellValueFactory(new PropertyValueFactory<>("documento_identidad"));
        colTelefonoPrincipal.setCellValueFactory(new PropertyValueFactory<>("telefono_principal"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<>("correo_electronico"));
        colDireccionFisica.setCellValueFactory(new PropertyValueFactory<>("direccion_fisica"));
        colFechaRegistro.setCellValueFactory(new PropertyValueFactory<>("fecha_registro"));
    }

    private void cargarClientes() {
        List<Cliente> clientes = clienteRepository.listarClientes();
        ObservableList<Cliente> datos = FXCollections.observableArrayList(clientes);
        tblClientes.setItems(datos);
    }

    
    @FXML
    public void onBuscarCliente(KeyEvent event) {
        refrescarTabla();
    }

    private void refrescarTabla() {
        String texto = txtBuscar.getText() == null ? "" : txtBuscar.getText().trim();

        try {
            List<Cliente> resultado = texto.isEmpty()
                    ? clienteRepository.listarClientes()
                    : clienteRepository.buscarClientes(texto);
            tblClientes.setItems(FXCollections.observableArrayList(resultado));
        } catch (Exception e) {
            System.out.println("Error al buscar clientes");
            e.printStackTrace();
        }
    }

   
    @FXML
    public void onActualizarCliente(MouseEvent event) {
        if (clienteSeleccionado == null) {
            mostrarError("Seleccione un cliente de la tabla.");
            return;
        }

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

        try {
            Cliente modificado = new Cliente(
                    clienteSeleccionado.getId_cliente(),
                    obtenerTipoPersona(),
                    nombreCompleto,
                    documento,
                    clienteSeleccionado.getNit_empresa(),
                    telefono,
                    email,
                    direccion,
                    clienteSeleccionado.getFecha_registro()
            );

            if (clienteRepository.actualizarCliente(modificado)) {
                ocultarError();
                limpiarFormulario();
                clienteSeleccionado = null;
                refrescarTabla();
            } else {
                mostrarError("No se pudo actualizar el cliente.");
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar el cliente.");
            e.printStackTrace();
        }
    }

  
    @FXML
    public void onEliminarCliente(MouseEvent event) {
        Cliente seleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un cliente de la tabla.");
            return;
        }

        try {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminacion");
            alerta.setHeaderText("Eliminar cliente");
            alerta.setContentText("¿Desea eliminar a " + seleccionado.getNombre_completo() + "?");

            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                if (clienteRepository.eliminarCliente(seleccionado.getId_cliente())) {
                    tblClientes.getItems().remove(seleccionado);
                    if (seleccionado == clienteSeleccionado) {
                        clienteSeleccionado = null;
                        limpiarFormulario();
                    }
                    ocultarError();
                } else {
                    mostrarError("No se pudo eliminar el cliente.");
                }
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar el cliente.");
            e.printStackTrace();
        }
    }

    @FXML
    public void onCambioTipoCliente(javafx.event.ActionEvent event) {
        ocultarError();
        actualizarPromptsSegunTipoCliente();
    }


    @FXML
    public void onRegistrarExpediente(MouseEvent event) {
        String documento = obtenerTexto(txtDocumento);
        String nombreCompleto = obtenerTexto(txtNombreCompleto);
        String telefono = obtenerTexto(txtTelefono);
        String email = obtenerTexto(txtEmail);
        String direccion = obtenerTexto(txtDireccion);

        String errorValidacion = validarCampos(documento, nombreCompleto, telefono, email, direccion);
        if (errorValidacion != null) {
            mostrarError(errorValidacion);
            return;
        }

        try {
            Cliente nuevo = new Cliente();
            nuevo.setTipo_persona(obtenerTipoPersona());
            nuevo.setNombre_completo(nombreCompleto);
            nuevo.setDocumento_identidad(documento);
            nuevo.setTelefono_principal(telefono);
            nuevo.setCorreo_electronico(email);
            nuevo.setDireccion_fisica(direccion);

            if (clienteRepository.registrarCliente(nuevo)) {
                ocultarError();
                limpiarFormulario();
                clienteSeleccionado = null;
                refrescarTabla();
            } else {
                mostrarError("No se pudo registrar el cliente.");
            }
        } catch (Exception e) {
            mostrarError("Error al registrar el cliente.");
            e.printStackTrace();
        }
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

        System.out.println("Expediente registrado: ");

        ocultarError();
        limpiarFormulario();
        viewFactory.viewWelcome();
    }

    @FXML
    public void onCancelarRegistro(MouseEvent event) {
        limpiarFormulario();
        viewFactory.viewWelcome();
    }

    private String obtenerTexto(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private String obtenerTipoPersona() {
        return rbEmpresaJuridica.isSelected() ? "EMPRESA" : "INDIVIDUAL";
    }

    private String validarCampos(String documento, String nombreCompleto,
            String telefono, String email, String direccion) {
        if (documento.isEmpty() || nombreCompleto.isEmpty() || telefono.isEmpty()
                || email.isEmpty() || direccion.isEmpty()) {
            return "Todos los campos son obligatorios.";
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
