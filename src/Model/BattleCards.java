package Model;

import javafx.scene.image.ImageView;

public class BattleCards {
    private ImageView[] imageView = new ImageView[3];
    private Card card;

    public ImageView[] getImageView() {
        return imageView;
    }

    public void setImageView(ImageView[] imageView) {
        this.imageView = imageView;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
