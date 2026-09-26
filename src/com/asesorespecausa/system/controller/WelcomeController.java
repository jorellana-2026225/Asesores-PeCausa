package com.asesorespecausa.system.controller;

import com.asesorespecausa.system.model.Expediente;
import com.asesorespecausa.system.repository.ExpedienteRepository;
import com.asesorespecausa.system.utils.Sesion;
import com.asesorespecausa.system.utils.ViewFactory;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class WelcomeController implements Initializable {

    private final ExpedienteRepository expedienteRepository = new ExpedienteRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

 //Se utilizaba para redirigir a la Vista Login :D    
//    @FXML
//    public void onLogin(MouseEvent event) {  
//        ViewFactory viewFacto = new ViewFactory();
//        viewFacto.viewLogin();
//    }

    @FXML
    public void onRegistro(MouseEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewRegistro();
    }
    
    @FXML
    public void onExpediente(MouseEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewExpediente();
    }
    
    @FXML
    public void onActuaciones(MouseEvent event) {

        // Todavia no existe una pantalla para elegir el expediente de
        // una lista, asi que mientras tanto se pide su numero aqui.
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Seleccionar expediente");
        dialogo.setHeaderText("Ingrese el numero de expediente (ej. EXP-001)");
        dialogo.setContentText("Numero de expediente:");

        Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isEmpty() || resultado.get().trim().isEmpty()) {
            return;
        }

        String numeroExpediente = resultado.get().trim();
        Expediente expediente = expedienteRepository.buscarPorNumero(numeroExpediente);

        if (expediente == null) {
            mostrarError("No se encontro el expediente " + numeroExpediente + ".");
            return;
        }

        if (!Sesion.haySesionActiva()) {
            mostrarError("Debe iniciar sesion antes de registrar actuaciones.");
            return;
        }

        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewActuaciones(
                expediente.getIdExpediente(),
                Sesion.getIdUsuarioActual(),
                expediente.getNombreCliente(),
                expediente.getNumeroExpediente()
        );
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Actuaciones");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}