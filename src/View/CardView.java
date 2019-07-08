package View;

import Model.Card;
import Model.Constants;
import Model.Item;
import javafx.animation.AnimationTimer;
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
    private Label name;
    private Label price;
    private Label count;
    private Label timeLabel;
    private Label fetcher;
    private AnchorPane pane = new AnchorPane();

    public CardView(Card card) {
        try {
            switch (card.getType()) {
                case "Hero":
                    character = new ImageView(new Image(card.getIdleSrc()));
                    if (card.getCountInSerie() == 0) {
                        template = new ImageView(new Image("card_backgrounds/unusable_prismatic_unit@2x.png"));
                    } else {
                        template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    }
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    power.translateXProperty().bind(power.widthProperty().divide(2).negate());
                    health.translateXProperty().bind(health.widthProperty().divide(2).negate());
                    break;
                case "Minion":
                    character = new ImageView(new Image(card.getIdleSrc()));
                    if (card.getCountInSerie() == 0) {
                        template = new ImageView(new Image("card_backgrounds/unusable_prismatic_unit@2x.png"));
                    } else {
                        template = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
                    }
                    power = new Label(Integer.toString(card.getAssaultPower()));
                    health = new Label(Integer.toString(card.getHealthPoint()));
                    power.translateXProperty().bind(power.widthProperty().divide(2).negate());
                    health.translateXProperty().bind(health.widthProperty().divide(2).negate());
                    break;
                case "Spell":
                    character = new ImageView(new Image(card.getIdleSrc()));
                    if (card.getCountInSerie() == 0) {
                        template = new ImageView(new Image("card_backgrounds/unusable_spell@2x.png"));
                    } else {
                        template = new ImageView(new Image("card_backgrounds/craftable_spell@2x.png"));
                    }
                    break;
            }
            type = new Label(card.getType());
            name = new Label(card.getName());
            count = new Label("x" + card.getCountInSerie());
            price = new Label(Integer.toString(card.getPrice()));
            assert character != null;
            character.setId(Integer.toString(card.getId()));
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            type.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
            type.setTextFill(Color.LIGHTCYAN);
            type.translateXProperty().bind(type.widthProperty().divide(2).negate());
            type.relocate(Constants.CARD_TYPE_X, Constants.CARD_TYPE_Y);
            price.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
            price.setTextFill(Color.LIGHTCYAN);
            price.translateXProperty().bind(price.widthProperty().divide(2).negate());
            price.relocate(Constants.CARD_PRICE_X, Constants.CARD_PRICE_Y);
            name.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
            name.setTextFill(Color.LIGHTCYAN);
            name.translateXProperty().bind(name.widthProperty().divide(2).negate());
            name.relocate(Constants.CARD_NAME_X, Constants.CARD_NAME_Y);
            count.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
            count.setTextFill(Color.LIGHTCYAN);
            count.translateXProperty().bind(count.widthProperty().divide(2).negate());
            count.relocate(Constants.CARD_NAME_X, Constants.CARD_COUNT_Y);
            if (!card.getType().equals("Spell")) {
                assert power != null;
                power.setTextFill(Color.YELLOW);
                health.setTextFill(Color.RED);
                power.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                health.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                power.relocate(Constants.CARD_POWER_X, Constants.CARD_INFO_Y);
                health.relocate(Constants.CARD_HEALTH_X, Constants.CARD_INFO_Y);
                character.setFitHeight(Constants.GIF_HEIGHT);
                character.setFitWidth(Constants.GIF_WIDTH);
                character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 2);
                pane.getChildren().addAll(template, character, type, name, price, power, health, count);

            } else {
                character.setFitHeight(Constants.GIF_HEIGHT / 2);
                character.setFitWidth(Constants.GIF_WIDTH / 2);
                character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 4);
                character.setLayoutY(Constants.GIF_HEIGHT / 4);
                pane.getChildren().addAll(template, character, type, name, count);
            }
            if (card.getAuctioneer() != null) {
                timeLabel = new Label();
                fetcher = new Label();
                timeLabel.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
                timeLabel.setTextFill(Color.LIGHTCYAN);
                timeLabel.translateXProperty().bind(count.widthProperty().divide(2).negate());
                timeLabel.relocate(Constants.CARD_TYPE_X, Constants.CARD_TIME_Y);
                fetcher.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
                fetcher.setTextFill(Color.LIGHTCYAN);
                fetcher.translateXProperty().bind(count.widthProperty().divide(2).negate());
                fetcher.relocate(Constants.CARD_FETCHER_X, Constants.CARD_FETCHER_Y);
                pane.getChildren().addAll(timeLabel, fetcher);
                if (card.getAuctioneer() != null) {
                    AnimationTimer timer = new AnimationTimer() {
                        private long lastTime = Constants.AUCTION_DURATION_MILIS;
                        private long second = 1000000000;
                        private long synch = (card.getAuctionTime() + Constants.AUCTION_DURATION_MILIS) * (second / 1000);

                        @Override
                        public void handle(long now) {
                            if (lastTime == Constants.AUCTION_DURATION_MILIS * (second / 1000))
                                lastTime = now;
                            if (now > lastTime + second) {
                                lastTime = now;
                                timeLabel.setText(String.format("%02d", ((synch - lastTime) / (second * 60)) % 60)
                                        + ":" + String.format("%02d", ((synch - lastTime) / second) % 60));
                                if (card.getAuctionFetcher() != null)
                                    fetcher.setText(card.getAuctionFetcher());
                                price.setText(Integer.toString(card.getAuctionPrice()));
                            }
                        }
                    };
                    timer.start();
                }
            }
        } catch (
                Exception e) {

        }

    }

    public CardView(Item item) {
        try {
            character = new ImageView(new Image(item.getIdleSrc()));
            character.setId(Integer.toString(item.getId()));
            character.setFitHeight(Constants.GIF_HEIGHT / 2);
            character.setFitWidth(Constants.GIF_WIDTH / 2);
            character.setLayoutX(Constants.CARD_WIDTH / 2 - Constants.GIF_WIDTH / 4);
            character.setLayoutY(Constants.GIF_HEIGHT / 4);
            type = new Label("Item");
            type.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
            type.setTextFill(Color.LIGHTCYAN);
            type.translateXProperty().bind(type.widthProperty().divide(2).negate());
            type.relocate(Constants.CARD_TYPE_X, Constants.CARD_TYPE_Y);
            name = new Label(item.getName());
            name.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
            name.setTextFill(Color.LIGHTCYAN);
            name.translateXProperty().bind(name.widthProperty().divide(2).negate());
            name.relocate(Constants.CARD_NAME_X, Constants.CARD_NAME_Y);
            count = new Label("x" + Integer.toString(item.getCountInShop()));
            count.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
            count.setTextFill(Color.LIGHTCYAN);
            count.translateXProperty().bind(count.widthProperty().divide(2).negate());
            count.relocate(Constants.CARD_NAME_X, Constants.CARD_COUNT_Y);
            if (item.getPrice() != 0 || item.getCountInShop() == 0) {
                price = new Label(Integer.toString(item.getPrice()));
                template = new ImageView(new Image("card_backgrounds/craftable_artifact@2x.png"));
            } else {
                price = new Label("Collectible");
                template = new ImageView(new Image("card_backgrounds/unusable_prismatic_artifact@2x.png"));
            }
            template.setFitHeight(Constants.CARD_HEIGHT);
            template.setFitWidth(Constants.CARD_WIDTH);
            price.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.CARD_INFO_FONT));
            price.setTextFill(Color.LIGHTCYAN);
            price.translateXProperty().bind(price.widthProperty().divide(2).negate());
            price.relocate(Constants.CARD_PRICE_X, Constants.CARD_PRICE_Y);
            pane.getChildren().addAll(template, character, type, name, price, count);
            if (item.getAuctioneer() != null) {
                AnimationTimer timer = new AnimationTimer() {
                    private long lastTime = Constants.AUCTION_DURATION_MILIS;
                    private long synch = item.getAuctionTime() * 1000 + Constants.AUCTION_DURATION_MILIS;
                    private long second = 1000000000;

                    @Override
                    public void handle(long now) {
                        if (lastTime == Constants.AUCTION_DURATION_MILIS)
                            lastTime = now;
                        if (now < lastTime + second) {
                            lastTime = now;
                            timeLabel.setText(String.format("%02d", ((synch - lastTime) / (second * 60)) % 60)
                                    + ":" + String.format("%02d", ((synch - lastTime) / second) % 60));
                        }
                        if (item.getAuctionFetcher() != null)
                            fetcher.setText(item.getAuctionFetcher());
                    }
                };
                timer.start();
            }
            pane.setOnMouseClicked(event -> System.out.println(character.getId()));
        } catch (Exception e) {

        }
    }

    public AnchorPane getPane() {
        return pane;
    }

}

