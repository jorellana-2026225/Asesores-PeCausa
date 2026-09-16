package com.asesorespecausa.system.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.asesorespecausa.system.utils.ViewFactory;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void onIniciarSesion(MouseEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewWelcome();
    }
 
}
