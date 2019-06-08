package com.company;

import Controller.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        Controller controller = Controller.getInstance();
        controller.main();
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setScene(new Scene(root,100,100));
        primaryStage.show();

    }
}
