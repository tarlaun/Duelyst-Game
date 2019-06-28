package com.company;

import Controller.Controller;
import View.View;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private Controller controller = Controller.getInstance();
    private View view = View.getInstance();
    private Image icon = new Image("resources/crests/crest_f3.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(view.getScene());
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.getIcons().add(icon);
        controller.main();
    }
}
