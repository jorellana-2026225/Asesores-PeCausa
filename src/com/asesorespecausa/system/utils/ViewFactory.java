package com.asesorespecausa.system.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import com.asesorespecausa.system.Main;
import com.asesorespecausa.system.controller.ActuacionController;

public class ViewFactory {

//Atributos
    private final String PATH_VIEWS = "/com/asesorespecausa/system/view/";

//Metodos
    public Scene loadFileFXML(String nameFile, int width, int height) {
        String pathOfFile = PATH_VIEWS + nameFile;

        try {
            //Llamar al FXMLLoader
            FXMLLoader loadFXML = new FXMLLoader();
            //Obtener la URL del Archivo, viene de la Clase Main
            URL urlFile = Main.class.getResource(pathOfFile);
            loadFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loadFXML.setLocation(urlFile);

            return new Scene(loadFXML.load(), width, height);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void loadScene(String nameFile) {
        Scene scene = null;
        try {
            switch (nameFile) {
                case "Welcome" -> {
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Bienvenida");
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("ViewWelcome.fxml", 400, 400);
                }
                case "Login" -> {
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Inicio de Sesion");
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("ViewLogin.fxml", 500, 400);
                }
                case "Registro" -> {
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Registro");
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("ViewRegistro.fxml", 400, 535);
                }
                case "Expediente" -> {
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Expediente");
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("viewExpediente.fxml", 900, 600);
                }
                default -> {
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Inicio de Sesion");
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("ViewLogin.fxml", 400, 400);
                }
            }
            SceneManager.getInstanciaSceneManger().changeScene(scene);
        } catch (NullPointerException e) {
            System.out.println("Error load Scene");
            //Alert
        }
    }

    public void viewWelcome() {
        loadScene("Welcome");
    }

    public void viewLogin() {
        loadScene("Login");
    }

    public void viewRegistro() {
        loadScene("Registro");
    }
    
    public void viewExpediente() {
        loadScene("Expediente");
    }
    
    /**
     * Abre la pantalla de Actuaciones ya cargada con el expediente,
     * el cliente y el usuario que corresponden. Se necesita el
     * controlador (no solo la Scene) para poder pasarle esos datos
     * antes de mostrar la ventana, por eso no usa loadFileFXML().
     */
    public void viewActuaciones(String idExpediente, String idUsuario,
            String nombreCliente, String numeroExpediente) {

        try {
            FXMLLoader loadFXML = new FXMLLoader();
            URL urlFile = Main.class.getResource(PATH_VIEWS + "ViewActuaciones.fxml");
            loadFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loadFXML.setLocation(urlFile);

            // Ancho y alto igual al diseño del FXML (900 x 760) para que
            // no se encimen los botones ni se corte la tabla.
            Scene scene = new Scene(loadFXML.load(), 900, 760);

            ActuacionController controlador = loadFXML.getController();
            controlador.setIdUsuario(idUsuario);
            controlador.setInformacionCliente(nombreCliente, numeroExpediente);
            controlador.setIdExpediente(idExpediente);

            SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Actuaciones");
            SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(true);
            SceneManager.getInstanciaSceneManger().changeScene(scene);

        } catch (IOException e) {
            System.out.println("Error al abrir la pantalla de Actuaciones");
            e.printStackTrace();
        }
    }

}