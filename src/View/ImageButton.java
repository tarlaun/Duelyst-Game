package View;

import Model.Constants;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ImageButton {
    private ImageView imageView;
    private Label label;
    private AnchorPane pane = new AnchorPane();

    public ImageButton(ImageView imageView, double width, double height, String text, int size, Color color) {
        this.imageView = imageView;
        label = new Label(text);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        label.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, size));
        label.setTextFill(color);
        label.translateXProperty().bind(label.widthProperty().divide(2));
        label.translateYProperty().bind(label.heightProperty().divide(2));
        label.relocate(width / 2, height / 2);
        pane.getChildren().addAll(imageView, label);
        pane.setPrefWidth(imageView.getFitWidth());
        pane.setPrefHeight(imageView.getFitHeight());
    }

    public AnchorPane getPane() {
        return pane;
    }
}
