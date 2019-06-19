package View;

import Model.Card;
import Model.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardView {
    private ImageView template;
    private ImageView character;
    private AnchorPane pane = new AnchorPane();

    public CardView(Card card) {
        try {
            switch (card.getType()) {
                case "Hero":
                    character = new ImageView(new Image("characters/heroes/idle/" + card.getName() + ".gif"));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    break;
                case "Minion":
                    character = new ImageView(new Image("characters/minions/idle/" + card.getName() + ".gif"));
                    template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    break;
                case "Spell":
                    character = new ImageView(new Image("characters/spells/idle/" + card.getName() + ".gif"));
                    template = new ImageView(new Image("card_backgrounds/craftable_spell@2x.png"));
                    break;
            }
            assert character != null;
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            character.setFitHeight(Constants.GIF_HEIGHT);
            character.setFitWidth(Constants.GIF_WIDTH);
            character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 2);
            character.setLayoutY(Constants.GIF_CARD_REL_Y);
            pane.getChildren().addAll(template, character);
        } catch (Exception e){

        }
    }

    public AnchorPane getPane(){
        return pane;
    }
}
