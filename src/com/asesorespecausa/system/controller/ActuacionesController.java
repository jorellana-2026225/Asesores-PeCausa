package com.asesorespecausa.system.controller;

import com.asesorespecausa.system.utils.ViewFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ActuacionesController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void onCloseActuaciones() {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewWelcome();
    }

}
