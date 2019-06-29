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
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        label.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, size));
        label.setTextFill(color);
        label.relocate(width / 2, height / 2);
        label.translateXProperty().bind(label.widthProperty().divide(2).negate());
        label.translateYProperty().bind(label.heightProperty().divide(2).negate());
        pane.getChildren().addAll(imageView, label);
    }

    public AnchorPane getPane() {
        return pane;
    }
}
