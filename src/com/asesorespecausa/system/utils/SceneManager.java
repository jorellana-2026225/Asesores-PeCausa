/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asesorespecausa.system.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author informatica
 */
public class  SceneManager {
  
    //Atributos 
    private static SceneManager instanciaSceneManager;

 
    private Stage stagePrincipal;
    
    
    //Metodos
    private SceneManager(){}
    
    public static SceneManager getInstanciaSceneManger() {
        if (instanciaSceneManager == null) {
            instanciaSceneManager = new SceneManager();
        }
        return instanciaSceneManager;
    }


 public void changeScene(Scene scene) {
        try {
            stagePrincipal.setScene(scene);
            stagePrincipal.sizeToScene();
            stagePrincipal.show();
        } catch (NullPointerException objetoNulo) {
            //Alert
        }
    }


    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

 
    public Stage getStagePrincipal() {
        return stagePrincipal;
    }
    
    
    
}
