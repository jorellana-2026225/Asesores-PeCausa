package com.asesorespecausa.system.controller;

import com.asesorespecausa.system.service.AuthenticationService;
import com.asesorespecausa.system.service.AuthenticationStatus;
import com.asesorespecausa.system.utils.ViewFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private AuthenticationService authService = new AuthenticationService();
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmationField;
    
    @FXML
    public void onInicioSesion(MouseEvent event) {
       String name = nameField.getText();
       String password = passwordField.getText();
       
       AuthenticationStatus status = authService.tryLogin(name,password);

       if (status == AuthenticationStatus.LOGIN_SUCCESS){    
             System.out.println("Logueado");
              ViewFactory viewFacto = new ViewFactory();
              viewFacto.viewWelcome();
              
        } else if (status == AuthenticationStatus.NOT_EXIST_USER){
            System.out.println("El Correo ingresado no esta registrado");
        } else {
            System.out.println("Error en las credenciales");
        }
    }

}