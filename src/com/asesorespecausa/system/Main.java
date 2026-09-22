package com.asesorespecausa.system;

import com.asesorespecausa.system.utils.SceneManager;
import com.asesorespecausa.system.utils.ViewFactory;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stageRoot) {
        SceneManager.getInstanciaSceneManger().setStagePrincipal(stageRoot);
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewLogin();
    }
    
}
