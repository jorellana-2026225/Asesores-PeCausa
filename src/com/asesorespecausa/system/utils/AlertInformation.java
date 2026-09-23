package com.asesorespecausa.system.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.function.Supplier;

public class AlertInformation {


    public AlertInformation() {
    }

    public void viewAlert(String titulo, String cabecera, String mensaje, String tipoAlerta) {

        Supplier<Alert> alertSupplier = switch (tipoAlerta.toUpperCase()) {
            case "INFO"        -> () -> new Alert(AlertType.INFORMATION);
            case "WARNING"     -> () -> new Alert(AlertType.WARNING);
            case "ERROR"       -> () -> new Alert(AlertType.ERROR);
            case "CONFIRM"     -> () -> new Alert(AlertType.CONFIRMATION);
            case "NONE"        -> () -> new Alert(AlertType.NONE);
            default            -> () -> new Alert(AlertType.INFORMATION);
        };

        Alert alert = alertSupplier.get();

        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
