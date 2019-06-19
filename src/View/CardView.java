package View;

import Model.Card;
import Model.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardView {
    private ImageView template;
    private ImageView character;
    private AnchorPane view = new AnchorPane();

    public CardView(Card card) {
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
        character.setFitHeight(Constants.CARD_HEIGHT);
        character.setFitWidth(Constants.CARD_WIDTH);
        template.setFitHeight(Constants.GIF_HIGHT);
        template.setFitWidth(Constants.GIF_WIDTH);
        view.getChildren().addAll(template, character);
    }
}
