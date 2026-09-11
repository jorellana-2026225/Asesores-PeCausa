/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.asesorespecausa.system;
import com.asesorespecausa.system.utils.SceneManager;

import com.asesorespecausa.system.utils.ViewFactory;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author informatica
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stageRoot) {
        SceneManager.getInstanciaSceneManger().setStagePrincipal(stageRoot);
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewWelcome();
    }

}
