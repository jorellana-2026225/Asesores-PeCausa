package com.asesorespecausa.system.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    //Atributos 
    private static SceneManager instanciaSceneManager;

    private Stage stagePrincipal;

    private SceneManager() {
    }
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
