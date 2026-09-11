/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asesorespecausa.system.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import com.asesorespecausa.system.Main;
import com.asesorespecausa.system.utils.SceneManager;

public class ViewFactory {

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
                case "Welcome" ->
                    scene = loadFileFXML("ViewWelcome.fxml", 400, 400);
                case "Login" -> {
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setTitle("Inicio de sesión");
                    SceneManager.getInstanciaSceneManger().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("ViewLogin.fxml", 500, 400);
                }

                default ->
                    scene = loadFileFXML("ViewWelcome.fxml", 400, 400);

            }
            SceneManager.getInstanciaSceneManger().changeScene(scene);
        } catch (NullPointerException e) {
            System.out.println("Error load Scene");
            //Alert
        }
    }
    public void viewLogin() {
        loadScene("Login");
    }
    public void viewWelcome() {
        loadScene("Welcome");
    }

}
