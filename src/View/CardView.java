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
    private ImageView character;
    private Label power;
    private Label health;
    private Label type;
    private AnchorPane pane = new AnchorPane();

    public CardView(Card card) {
        try {
            switch (card.getType()) {
                case "Hero":
                    character = new ImageView(new Image(card.getIdleSrc()));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    break;
                case "Minion":
                    character = new ImageView(new Image(card.getIdleSrc()));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    break;
                case "Spell":
                    character = new ImageView(new Image(card.getIdleSrc()));
                    template = new ImageView(new Image("card_backgrounds/craftable_spell@2x.png"));
                    break;
            }
            type = new Label(card.getType());
            assert character != null;
            character.setId(Integer.toString(card.getId()));
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            type.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
            type.setTextFill(Color.LIGHTCYAN);
            type.translateXProperty().bind(type.widthProperty().divide(2).negate());
            type.relocate(Constants.CARD_TYPE_X, Constants.CARD_TYPE_Y);
            if (!card.getType().equals("Spell")) {
                assert power != null;
                power.setTextFill(Color.YELLOW);
                health.setTextFill(Color.RED);
                power.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                health.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                power.setLayoutY(Constants.CARD_INFO_Y);
                health.setLayoutY(Constants.CARD_INFO_Y);
                power.setLayoutX(Constants.CARD_POWER_X);
                health.setLayoutX(Constants.CARD_HEALTH_X);
                character.setFitHeight(Constants.GIF_HEIGHT);
                character.setFitWidth(Constants.GIF_WIDTH);
                character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 2);
                pane.getChildren().addAll(template, character, type, power, health);

            } else {
                character.setFitHeight(Constants.GIF_HEIGHT / 2);
                character.setFitWidth(Constants.GIF_WIDTH / 2);
                character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 4);
                character.setLayoutY(Constants.GIF_HEIGHT / 4);
                pane.getChildren().addAll(template, character, type);
            }
            pane.setOnMouseClicked(event -> System.out.println(character.getId()));
        } catch (Exception e) {

        }
    }

    public CardView(Item item) {
        try {
            character = new ImageView(new Image(item.getIdleSrc()));
            template = new ImageView(new Image("card_backgrounds/craftable_artifact@2x.png"));
            character.setId(Integer.toString(item.getId()));
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            character.setFitHeight(Constants.GIF_HEIGHT / 2);
            character.setFitWidth(Constants.GIF_WIDTH / 2);
            character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 4);
            character.setLayoutY(Constants.GIF_HEIGHT / 4);
            type = new Label("Item");
            type.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
            type.setTextFill(Color.LIGHTCYAN);
            type.translateXProperty().bind(type.widthProperty().divide(2).negate());
            type.relocate(Constants.CARD_TYPE_X, Constants.CARD_TYPE_Y);
            pane.getChildren().addAll(template, character, type);
            pane.setOnMouseClicked(event -> System.out.println(character.getId()));
        } catch (Exception e) {

        }
    }

    public AnchorPane getPane() {
        return pane;
    }
}
