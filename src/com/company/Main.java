package com.company;

import Controller.Controller;
import View.View;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Controller controller = Controller.getInstance();
    private View view = View.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(view.getScene());
        primaryStage.show();
        controller.main();
    }
}
