package com.asesorespecausa.system.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.asesorespecausa.system.utils.ViewFactory;

public class WelcomeController implements Initializable {

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
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewActuaciones();
    }
    
    
    

}
