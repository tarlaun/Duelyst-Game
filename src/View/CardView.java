package View;

import Model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardView {
    private ImageView template;
    private ImageView character;
    private AnchorPane view;

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
    }
}
