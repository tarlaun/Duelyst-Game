package View;

import Model.Card;
import Model.Constants;
import Model.Item;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CardView {
    private ImageView template;
    private ImageView idle;
    private ImageView run;
    private ImageView action;
    private ImageView death;
    private Label power;
    private Label health;
    private Label type;
    private Label name;
    private AnchorPane pane = new AnchorPane();

    public CardView(Card card) {
        try {
            switch (card.getType()) {
                case "Hero":
                    idle = new ImageView(new Image(card.getIdleSrc()));
                    run = new ImageView(new Image(card.getRunSrc()));
                    action = new ImageView(new Image(card.getAttackSrc()));
                    death = new ImageView(new Image(card.getDeathSrc()));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    power.translateXProperty().bind(power.widthProperty().divide(2).negate());
                    health.translateXProperty().bind(health.widthProperty().divide(2).negate());
                    break;
                case "Minion":
                    idle = new ImageView(new Image(card.getIdleSrc()));
                    run = new ImageView(new Image(card.getRunSrc()));
                    action = new ImageView(new Image(card.getAttackSrc()));
                    death = new ImageView(new Image(card.getDeathSrc()));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    power.translateXProperty().bind(power.widthProperty().divide(2).negate());
                    health.translateXProperty().bind(health.widthProperty().divide(2).negate());
                    break;
                case "Spell":
                    idle = new ImageView(new Image(card.getIdleSrc()));
                    template = new ImageView(new Image("card_backgrounds/craftable_spell@2x.png"));
                    break;
            }
            type = new Label(card.getType());
            name = new Label(card.getName());
            assert idle != null;
            idle.setId(Integer.toString(card.getId()));
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            type.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
            type.setTextFill(Color.LIGHTCYAN);
            type.translateXProperty().bind(type.widthProperty().divide(2).negate());
            type.relocate(Constants.CARD_TYPE_X, Constants.CARD_TYPE_Y);
            name.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE / 2));
            name.setTextFill(Color.LIGHTCYAN);
            name.translateXProperty().bind(name.widthProperty().divide(2).negate());
            name.relocate(Constants.CARD_NAME_X, Constants.CARD_NAME_Y);
            if (!card.getType().equals("Spell")) {
                assert power != null;
                power.setTextFill(Color.YELLOW);
                health.setTextFill(Color.RED);
                power.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                health.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                power.relocate(Constants.CARD_POWER_X, Constants.CARD_INFO_Y);
                health.relocate(Constants.CARD_HEALTH_X, Constants.CARD_INFO_Y);
                idle.setFitHeight(Constants.GIF_HEIGHT);
                idle.setFitWidth(Constants.GIF_WIDTH);
                idle.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 2);
                pane.getChildren().addAll(template, idle, type, name, power, health);

            } else {
                idle.setFitHeight(Constants.GIF_HEIGHT / 2);
                idle.setFitWidth(Constants.GIF_WIDTH / 2);
                idle.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 4);
                idle.setLayoutY(Constants.GIF_HEIGHT / 4);
                pane.getChildren().addAll(template, idle, type, name);
            }
            pane.setOnMouseClicked(event -> System.out.println(idle.getId()));
        } catch (Exception e) {

        }
    }

    public CardView(Item item) {
        try {
            idle = new ImageView(new Image(item.getIdleSrc()));
            template = new ImageView(new Image("card_backgrounds/craftable_artifact@2x.png"));
            idle.setId(Integer.toString(item.getId()));
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            idle.setFitHeight(Constants.GIF_HEIGHT / 2);
            idle.setFitWidth(Constants.GIF_WIDTH / 2);
            idle.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 4);
            idle.setLayoutY(Constants.GIF_HEIGHT / 4);
            type = new Label("Item");
            type.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
            type.setTextFill(Color.LIGHTCYAN);
            type.translateXProperty().bind(type.widthProperty().divide(2).negate());
            type.relocate(Constants.CARD_TYPE_X, Constants.CARD_TYPE_Y);
            name = new Label(item.getName());
            name.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE / 2));
            name.setTextFill(Color.LIGHTCYAN);
            name.translateXProperty().bind(name.widthProperty().divide(2).negate());
            name.relocate(Constants.CARD_NAME_X, Constants.CARD_NAME_Y);
            pane.getChildren().addAll(template, idle, type, name);
            pane.setOnMouseClicked(event -> System.out.println(idle.getId()));
        } catch (Exception e) {

        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public ImageView getIdle() {
        return idle;
    }

    public ImageView getRun() {
        return run;
    }

    public ImageView getAction() {
        return action;
    }

    public ImageView getDeath() {
        return death;
    }
}
