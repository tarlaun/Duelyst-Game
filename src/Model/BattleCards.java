package Model;

import javafx.scene.image.ImageView;

public class BattleCards {
    private ImageView[] imageView = new ImageView[3];
    private int cardId=0;

    public ImageView[] getImageView() {
        return imageView;
    }

    public void setImageView(ImageView[] imageView) {
        this.imageView = imageView;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
