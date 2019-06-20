package View;

import Model.Card;
import Model.Constants;
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
    private AnchorPane pane = new AnchorPane();

    public CardView(Card card) {
        try {
            switch (card.getType()) {
                case "Hero":
                    character = new ImageView(new Image("characters/heroes/idle/" + card.getName() + ".gif"));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    break;
                case "Minion":
                    character = new ImageView(new Image("characters/minions/idle/" + card.getName() + ".gif"));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    break;
                case "Spell":
                    character = new ImageView(new Image("characters/spells/idle/" + card.getName() + ".gif"));
                    template = new ImageView(new Image("card_backgrounds/craftable_spell@2x.png"));
                    break;
            }
            assert character != null;
            character.setId(Integer.toString(card.getId()));
            if (!card.getType().equals("Spell")) {
                assert power != null;
                power.setTextFill(Color.LIGHTCYAN);
                health.setTextFill(Color.LIGHTCYAN);
                power.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                health.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                power.setLayoutY(Constants.CARD_INFO_Y);
                health.setLayoutY(Constants.CARD_INFO_Y);
                power.setLayoutX(Constants.CARD_POWER_X);
                health.setLayoutX(Constants.CARD_HEALTH_X);
            }
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            character.setFitHeight(Constants.GIF_HEIGHT);
            character.setFitWidth(Constants.GIF_WIDTH);
            character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 2);
            pane.getChildren().addAll(template, character, power, health);
            pane.setOnMouseClicked(event -> System.out.println(character.getId()));
        } catch (Exception e) {

        }
    }

    public AnchorPane getPane() {
        return pane;
    }
}
