package View;

import Model.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Model.Menu;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class View {
    private transient AnchorPane root = new AnchorPane();
    private transient Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    private static final View view = new View();
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private Image cursor = new Image("resources/ui/mouse_attack@2x.png");


    private View() {

    }

    public Scene getScene() {
        // ImageCursor imageCursor = new ImageCursor(cursor, Constants.CURSOR_LENGTH, Constants.CURSOR_LENGTH);
        //scene.setCursor(imageCursor);
        return scene;
    }

    public static View getInstance() {
        return view;
    }


    public void printLeaderboard(ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + 1 + " - UserName : " + accounts.get(i).getName() +
                    " - Wins : " + accounts.get(i).getWins());
        }

    }


    public void drawMap(Battle battle) {
        int id;
        boolean isPrinted = false;
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                id = battle.getField(i, j).getCardID();
                for (Flag flag :
                        battle.getFlagsOnTheGround()) {
                    if (flag.getCoordinate().getX() == i && flag.getCoordinate().getY() == j) {
                        System.out.print("*");
                        isPrinted = true;
                    }
                }
                if (battle.getMode().equals(BattleMode.FLAG) &&
                        battle.getMainFlag().getCoordinate().getX() == i && battle.getMainFlag().getCoordinate().getY() == j) {
                    System.out.print("#");
                    isPrinted = true;
                }
                if (id == 0 && !isPrinted) {
                    if (battle.getField(i, j).isHoly())
                        System.out.print("H");
                    else if (battle.getField(i, j).isFire())
                        System.out.print("F");
                    else if (battle.getField(i, j).isPoison())
                        System.out.print("P");

                } else {
                    Card card = Card.getCardByID(id, battle.getFieldCards()[0]);
                    if (card != null) {
                        System.out.print(ANSI_GREEN + card.getType().charAt(0) + ANSI_RESET);
                    } else {
                        card = Card.getCardByID(id, battle.getFieldCards()[1]);
                        System.out.print(ANSI_RED + card.getType().charAt(0) + ANSI_RESET);
                    }
                }

                System.out.print(" ");
                isPrinted = false;
            }
            System.out.println();
        }

    }
    //Graphic

    public void graveYardMenu() {

    }


    public void selectUserMenu(ArrayList<Account> accounts, Label label, TextField textField) {
        root.getChildren().clear();
        Image background = new Image("resources/scenes/shimzar/bg@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        ArrayList<Users> users = new ArrayList<>();

        for (Account account : accounts) {
            String s = "OFFLINE";
            if (account.isLoggedIn()) s = "ONLINE";
            Users users1 = new Users(account.getName(), s);
            users.add(users1);
        }

        ListView<Users> list = new ListView<>();
        ObservableList<Users> items = FXCollections.observableArrayList(users);
        list.setItems(items);
        list.setOrientation(Orientation.VERTICAL);
        if (accounts.size() == 0) {
            list.getItems().add(new Users("NOBODY", "ONLINE"));
        }
        list.setStyle("-fx-control-inner-background: #74e9cf;-fx-font-size:20;");
        list.setMinWidth(200);
        list.setMaxHeight(300);
        list.relocate(175, 125);
        label.setText("FRIENDS");
        label.relocate(225, 80);
        label.setFont(Font.font(35));
        textField.relocate(215, 450);
        textField.setStyle("-fx-control-inner-background: #2b7b71;");
        root.getChildren().addAll(backgroundView, list, label, textField);
    }

    public void battleMenu(Account[] accounts,BattleCards[] battleHeros, Polygon[] polygon, ImageView view,
                           Label labels, ImageView[] mana, ImageView[] handcards, BattleCards[] battleCards) {
        root.getChildren().clear();
        maps();
        battleFieldView(polygon);


        Image firstHero, secondHero;
        firstHero = getImage(accounts[0]);
        secondHero = getImage(accounts[1]);
        ImageView firstHeroView = new ImageView(firstHero);
        ImageView secondHeroView = new ImageView(secondHero);
        bossImageSettings(firstHeroView, secondHeroView);
        battleHeros[1].getImageView()[0].relocate((polygon[26].getPoints().get(0) + polygon[26].getPoints().get(2)) / 2 - 55, (polygon[26].getPoints().get(1) + polygon[26].getPoints().get(5)) / 2 - 105);
        battleHeros[1].getImageView()[0].setScaleX(-1);
        battleHeros[0].getImageView()[0].relocate((polygon[18].getPoints().get(0) + polygon[18].getPoints().get(2)) / 2 - 60, (polygon[18].getPoints().get(1) + polygon[18].getPoints().get(5)) / 2 - 105);
        lightning(battleHeros[0].getImageView()[0],battleHeros[1].getImageView()[0]);
        root.getChildren().addAll(firstHeroView, secondHeroView,battleHeros[0].getImageView()[0],battleHeros[1].getImageView()[0]);



        endTurnButton(view, labels);
        mana(accounts[0], mana);
        handCardRings(handcards);
        for (int i = 0; i <5 ; i++) {
            System.out.println(battleCards[i].getCard().getName());
            battleCards[i].getImageView()[0].relocate(250 + 120 * i, 490);
            battleCards[i].getImageView()[0].setFitHeight(160);
            battleCards[i].getImageView()[0].setFitWidth(160);
            if(battleCards[i].getCard().getType().equals("Spell")){
                battleCards[i].getImageView()[0].relocate(290 + 120 * i, 545);
                battleCards[i].getImageView()[0].setFitHeight(80);
                battleCards[i].getImageView()[0].setFitWidth(80);
            }
            lightning( battleCards[i].getImageView()[0]);
            root.getChildren().add(battleCards[i].getImageView()[0]);
        }

    }

    public void handView (Coordinate[] coordinate , BattleCards battleCard){
        if(!battleCard.getCard().getType().equals("Spell")) {
            if(coordinate[0].getY()<500) {
                battleCard.getImageView()[0].relocate(coordinate[0].getX(), coordinate[0].getY());
            }else {
                battleCard.getImageView()[0].relocate(coordinate[0].getX()-40, coordinate[0].getY()-55);
            }
            battleCard.getImageView()[0].setFitHeight(160);
            battleCard.getImageView()[0].setFitWidth(160);
        }
        if(battleCard.getCard().getType().equals("Spell")){
            if(coordinate[0].getY()<500) {
                battleCard.getImageView()[0].relocate(coordinate[0].getX()+40, coordinate[0].getY()+55);
            }else {
                battleCard.getImageView()[0].relocate(coordinate[0].getX(), coordinate[0].getY());
            }
            battleCard.getImageView()[0].setFitHeight(80);
            battleCard.getImageView()[0].setFitWidth(80);
        }
        root.getChildren().addAll(battleCard.getImageView()[0]);
    }

    private void handCardRings(ImageView[] handcards) {
        for (int i = 0; i < 5; i++) {
            handcards[i].setImage(new Image("resources/ui/replace_outer_ring_smoke@2x.png"));
            handcards[i].relocate(270 + 120 * i, 525);
            handcards[i].setFitHeight(120);
            handcards[i].setFitWidth(120);
            lightning(handcards[i]);
            root.getChildren().add(handcards[i]);
        }
    }

    private void battleFieldView(Polygon[] polygon) {
        battleField(polygon);
        for (int i = 0; i < 45; i++) {
            root.getChildren().add(polygon[i]);
        }
    }

    private void maps() {
        Image background = new Image("resources/maps/abyssian/background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("resources/maps/abyssian/midground@2x.png");
        ImageView foregroundView = getImageView(background, foreground);
        root.getChildren().addAll(backgroundView, foregroundView);
    }


    private void mana(Account account, ImageView[] mana) {
        for (int i = 0; i < 9; i++) {
            if (i < account.getMana()) {
                mana[i].setImage(new Image("resources/ui/icon_mana@2x.png"));
            } else {
                mana[i].setImage(new Image("resources/ui/icon_mana_inactive@2x.png"));
            }
            mana[i].relocate(320 + i * 29, 150 - i * 3);
            mana[i].setFitWidth(35);
            mana[i].setFitHeight(35);
            lightning(mana[i]);
        }
        root.getChildren().addAll(mana);
    }

    private void endTurnButton(ImageView view, Label labels) {
        view.setImage(new Image("resources/ui/button_end_turn_mine@2x.png"));
        view.relocate(900, 550);
        view.setFitWidth(200);
        view.setFitHeight(80);
        labels.relocate(945, 575);
        labels.setText("END TURN");
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.5);
        view.setOnMouseEntered(event -> view.setEffect(bloom));
        view.setOnMouseExited(event -> view.setEffect(null));
        labels.setStyle("-fx-control-inner-background: #000000;-fx-font-size:20;");
        root.getChildren().addAll(view, labels);
    }

    private void bossImageSettings(ImageView firstHeroView, ImageView secondHeroView) {
        firstHeroView.relocate(150, -50);
        firstHeroView.setFitHeight(250);
        firstHeroView.setFitWidth(250);
        secondHeroView.relocate(900, -50);
        secondHeroView.setFitHeight(250);
        secondHeroView.setFitWidth(250);
        lightning(firstHeroView);
        lightning(secondHeroView);
    }

    public void move(double x, double y, ImageView imageView, ImageView imageView2) {
        System.out.println("move");
        Image image = imageView.getImage();
        imageView.setImage(imageView2.getImage());
        TranslateTransition transition = new TranslateTransition(Duration.millis(2000), imageView);
        transition.setToX(x - imageView.getLayoutX() - 45);
        transition.setToY(y - imageView.getLayoutY() - 90);
        transition.playFromStart();
        transition.setOnFinished(event -> imageView.setImage(image));
    }

    public void attack(ImageView[] imageViews) {
        File file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/sfx/sfx_f1_general_attack_swing.m4a");
        Media media = new Media(file.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
        System.out.println("view attack");
        Image image = imageViews[0].getImage();
        imageViews[0].setImage(imageViews[2].getImage());
        TranslateTransition transition = new TranslateTransition(Duration.millis(1500), imageViews[0]);
        transition.playFromStart();
        transition.setOnFinished(event -> imageViews[0].setImage(image));
    }

    private Image getImage(Account account) {
        Image firstHero = null;
        switch (account.getCollection().getMainDeck().getHero().getName()) {
            case "WHITE_DIV":
                firstHero = new Image("resources/boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "ZAHAK":
                firstHero = new Image("resources/boss_battles/boss_calibero_portrait_image_hex@2x.png");
                break;
            case "ARASH":
                firstHero = new Image("resources/boss_battles/boss_boreal_juggernaut_portrait_image_hex@2x.png");
                break;
            case "SIMORGH":
                firstHero = new Image("resources/boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "SEVEN_HEADED_DRAGON":
                firstHero = new Image("resources/boss_battles/boss_crystal_portrait_hex.png");
                break;
            case "RAKHSH":
                firstHero = new Image("resources/boss_battles/boss_wraith_portrait_hex@2x.png");
                break;
            case "KAVEH":
                firstHero = new Image("resources/boss_battles/boss_vampire_portrait_hex@2x.png");
                break;
            case "AFSANEH":
                firstHero = new Image("resources/boss_battles/boss_spelleater_portrait_hex@2x.png");
                break;
            case "ESFANDIAR":
                firstHero = new Image("resources/boss_battles/boss_skurge_portrait_hex@2x.png");
                break;
            case "ROSTAM":
                firstHero = new Image("resources/boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
        }
        return firstHero;
    }

    private void battleField(Polygon[] polygon) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.7);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                polygon[j + 9 * i] = new Polygon();
                polygon[j + 9 * i].getPoints().addAll(-i * 8 + 380.0 + (60 + i * 2) * j + 2, 205.0 + i * 50 + 2, -i * 8 + 380.0 + (60 + i * 2) * (j + 1) - 2, 205.0 + i * 50 + 2, -(i + 1) * 8 + 380.0 + (60 + (i + 1) * 2) * (j + 1) - 2, 205.0 + ((i + 1) * 50) - 2, -(i + 1) * 8 + 380.0 + (60 + (i + 1) * 2) * j + 2, 205.0 + ((i + 1) * 50) - 2);
                polygon[j + 9 * i].setFill(Color.rgb(119, 104, 180, 0.6));
                glowPolygon(colorAdjust, polygon[j + 9 * i]);
            }
        }
    }

    private void glowPolygon(ColorAdjust colorAdjust, Polygon polygon1) {
        polygon1.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {

            polygon1.setEffect(colorAdjust);
            ;

        });
        polygon1.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            polygon1.setEffect(null);
        });
    }

    public void mainMenu(Button login, Button create, Button exit, TextField username, PasswordField password) {
        Image background = new Image("resources/scenes/obsidian_woods/obsidian_woods_background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("resources/scenes/obsidian_woods/obsidian_woods_cliff.png");
        ImageView foregroundView = getImageView(background, foreground);
        login.setText("Login");
        create.setText("Create Account");
        exit.setText("Exit");
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y, login, create, exit);
        password.setPrefWidth(Constants.FIELD_WIDTH);
        password.setPrefHeight(Constants.FIELD_HEIGHT);
        password.setLayoutX(Constants.CENTRE_X - password.getPrefWidth() / 2);
        password.setLayoutY(login.getLayoutY() - 2 * password.getPrefHeight());
        username.setPrefWidth(Constants.FIELD_WIDTH);
        username.setPrefHeight(Constants.FIELD_HEIGHT);
        username.setLayoutX(password.getLayoutX());
        username.setLayoutY(password.getLayoutY() - 2 * Constants.FIELD_HEIGHT);
        root.getChildren().addAll(backgroundView, foregroundView, login, create, exit, username, password);
    }

    public void verticalList(Alignment alignment, double x, double y, Node... nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof Button) {
                Button button = (Button) nodes[i];
                setButtonSize(button);
                if (alignment == Alignment.CENTRE)
                    button.setLayoutX(x - button.getPrefWidth() / 2);
            }
            if (nodes[i] instanceof ImageView) {
                ImageView imageView = (ImageView) nodes[i];
                if (alignment == Alignment.CENTRE)
                    imageView.setLayoutX(x - imageView.getFitWidth() / 2);
            }
            if (alignment == Alignment.LEFT)
                nodes[i].setLayoutX(x);
        }
        if (nodes[0] instanceof Button) {
            nodes[nodes.length / 2].setLayoutY(y +
                    ((nodes.length + 1) % 2) * ((Button) nodes[nodes.length / 2]).getPrefHeight() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutY(nodes[i + 1].getLayoutY() - 2 * Constants.BUTTON_HEIGHT);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutY(nodes[i - 1].getLayoutY() + 2 * Constants.BUTTON_HEIGHT);
            }
        }
        if (nodes[0] instanceof ImageView) {
            nodes[nodes.length / 2].setLayoutY(y +
                    ((nodes.length + 1) % 2) * ((ImageView) nodes[nodes.length / 2]).getFitHeight() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutY(nodes[i + 1].getLayoutY()
                        - ((ImageView) nodes[i]).getFitHeight() - ((ImageView) nodes[i + 1]).getFitHeight());
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutY(nodes[i - 1].getLayoutY() +
                        ((ImageView) nodes[i]).getFitHeight() + ((ImageView) nodes[i - 1]).getFitHeight());
            }
        }

    }

    public void setButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setPrefHeight(Constants.BUTTON_HEIGHT);
            if (button.getText().length() >= 8)
                button.setPrefWidth(2 * Constants.BUTTON_WIDTH);
            else
                button.setPrefWidth(Constants.BUTTON_WIDTH);
        }
    }

    public void setImageSize(double size, ImageView... imageViews) {
        double currentSize;
        for (ImageView imageView : imageViews) {
            currentSize = imageView.getFitHeight();
            imageView.setFitHeight(size);
            imageView.setFitWidth(size);
            imageView.setLayoutX(imageView.getLayoutX() + (currentSize - size) / 2);
            imageView.setLayoutY(imageView.getLayoutY() + (currentSize - size) / 2);
        }

    }

    public void horizontalList(Alignment alignment, double x, double y, Node... nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] instanceof Button) {
                Button button = (Button) nodes[i];
                setButtonSize(button);
                if (alignment == Alignment.CENTRE)
                    button.setLayoutY(y - button.getPrefHeight() / 2);
            }
            if (nodes[i] instanceof ImageView) {
                ImageView imageView = (ImageView) nodes[i];
                imageView.setFitWidth(Constants.SLIDE);
                imageView.setFitHeight(Constants.SLIDE);
                if (alignment == Alignment.CENTRE)
                    imageView.setLayoutY(y - imageView.getFitHeight() / 2);
            }
            if (alignment == Alignment.UP || alignment == Alignment.DOWN)
                nodes[i].setLayoutY(y);
        }
        if (nodes[0] instanceof Button) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((Button) nodes[nodes.length / 2]).getPrefWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.BUTTON_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() + 2 * Constants.BUTTON_WIDTH);
            }
        }
        if (nodes[0] instanceof ImageView) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((ImageView) nodes[nodes.length / 2]).getFitWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX()
                        - ((ImageView) nodes[i]).getFitWidth() - ((ImageView) nodes[i + 1]).getFitWidth());
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() +
                        ((ImageView) nodes[i]).getFitWidth() + ((ImageView) nodes[i - 1]).getFitWidth());
            }
        }
    }

    public void horizantalList(Alignment alignment, double x, double y, AnchorPane... anchorPanes) {
        for (int i = 0; i < anchorPanes.length; i++) {
            if (alignment == Alignment.CENTRE)
                anchorPanes[i].setLayoutY(y);
            if (alignment == Alignment.UP || alignment == Alignment.DOWN) {
                anchorPanes[i].setLayoutY(y + anchorPanes[i].getPrefHeight() / 2);
            }
        }
        anchorPanes[anchorPanes.length / 2].setLayoutX(x + anchorPanes[anchorPanes.length / 2].getPrefWidth() / 2 +
                ((anchorPanes.length + 1) % 2) * (anchorPanes[anchorPanes.length / 2]).getPrefWidth() / 2);
        for (int i = anchorPanes.length / 2 - 1; i >= 0; i--) {
            anchorPanes[i].setLayoutX(anchorPanes[i + 1].getLayoutX() - 2 * anchorPanes[i].getPrefWidth());
        }
        for (int i = anchorPanes.length / 2 + 1; i < anchorPanes.length; i++) {
            anchorPanes[i].setLayoutX(anchorPanes[i - 1].getLayoutX() + 2 * anchorPanes[i].getPrefWidth());
        }
    }

    public void verticalList(Alignment alignment, double x, double y, AnchorPane... anchorPanes) {
        for (int i = 0; i < anchorPanes.length; i++) {
            if (alignment == Alignment.CENTRE)
                anchorPanes[i].setLayoutX(x);
            if (alignment == Alignment.RIGHT || alignment == Alignment.LEFT) {
                anchorPanes[i].setLayoutX(x + anchorPanes[i].getPrefWidth() / 2);
            }
        }
        anchorPanes[anchorPanes.length / 2].setLayoutY(y + anchorPanes[anchorPanes.length / 2].getPrefHeight() / 2 +
                ((anchorPanes.length + 1) % 2) * (anchorPanes[anchorPanes.length / 2]).getPrefHeight() / 2);
        for (int i = anchorPanes.length / 2 - 1; i >= 0; i--) {
            anchorPanes[i].setLayoutY(anchorPanes[i + 1].getLayoutY() - 2 * anchorPanes[i].getPrefHeight());
        }
        for (int i = anchorPanes.length / 2 + 1; i < anchorPanes.length; i++) {
            anchorPanes[i].setLayoutY(anchorPanes[i - 1].getLayoutY() + 2 * anchorPanes[i].getPrefHeight());
        }
    }

    private void setPaneSize(double width, double height, AnchorPane... panes) {
        for (AnchorPane pane : panes) {
            pane.setPrefWidth(width);
            pane.setPrefHeight(height);
        }
    }

    public void accountMenu(Button play, Button collection, Button shop, Button leaderboard, Button logout) {
        root.getChildren().clear();
        Image background = new Image("resources/scenes/frostfire/background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("resources/scenes/frostfire/foreground.png");
        ImageView foregroundView = getImageView(background, foreground);
        play.setText("Play");
        collection.setText("Collection");
        shop.setText("Shop");
        leaderboard.setText("Leaderboard");
        logout.setText("Logout");
        verticalList(Alignment.LEFT, 200, Constants.CENTRE_Y, play, collection, shop, leaderboard, logout);
        root.getChildren().addAll(backgroundView, foregroundView, play, collection, shop, leaderboard, logout);
    }

    private ImageView getImageView(Image background, Image foreground) {
        ImageView foregroundView = new ImageView(foreground);
        foregroundView.setFitWidth(foreground.getWidth() / background.getWidth() * Constants.WINDOW_WIDTH);
        foregroundView.setFitHeight(foreground.getWidth() / background.getWidth() * Constants.WINDOW_HEIGHT);
        foregroundView.setLayoutY(Constants.WINDOW_HEIGHT - foregroundView.getFitHeight());
        return foregroundView;
    }

    public void battleMode(Button first, Button second, Button third) {
        root.getChildren().clear();
        // Image background = new Image("resources/scenes/magaari_ember_highlands/magaari_ember_highlands_background@2x.jpg");
        Image background = new Image("resources/scenes/load/scene_load_background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image firstImage = new Image("resources/play/play_mode_arenagauntlet@2x.jpg");
        ImageView firstImageView = new ImageView(firstImage);
        firstImageView.setFitWidth(425);
        firstImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        firstImageView.setLayoutX(0);
        Image secondImage = new Image("resources/codex/chapter17_preview@2x.jpg");
        ImageView secondImageView = new ImageView(secondImage);
        secondImageView.setFitWidth(425);
        secondImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        secondImageView.setLayoutX(425);
        Image thirdImage = new Image("resources/codex/generic_preview@2x.jpg");
        ImageView thirdImageView = new ImageView(thirdImage);
        thirdImageView.setFitWidth(425);
        thirdImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        thirdImageView.setLayoutX(850);
        lightning(firstImageView);
        lightning(secondImageView);
        lightning(thirdImageView);
        buttonSettings(first, 40, "KILL ENEMY HERO", 10, 50);
        buttonSettings(second, 37, "COLLECTING FLAGS", 430, 50);
        buttonSettings(third, 37, "HOLD SPECIAL FLAG", 855, 50);
        root.getChildren().addAll(backgroundView, firstImageView, secondImageView, thirdImageView, first, second, third);
    }

    private void buttonSettings(Button first, int font, String t, int x, int y) {
        first.relocate(x, y);
        first.setText(t);
        first.setFont(Font.font(font));
        first.setStyle("-fx-background-color: #091841; ");
        first.setTextFill(Color.rgb(209, 188, 208));
    }

    public void gameTypeMenu(Button single, Button multi) {
        root.getChildren().clear();
        Image background = new Image("resources/resources/scenes/vetruvian/bg@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image singleP = new Image("resources/resources/crests/crest_f1@2x.png");
        ImageView singlePview = new ImageView(singleP);
        singlePview.setFitWidth(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setFitHeight(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setLayoutY(200);
        singlePview.setLayoutX(Constants.SINGLE_PLAYER_HEIGHT);
        Image singleM = new Image("resources/resources/crests/crest_f2@2x.png");
        ImageView singleMview = new ImageView(singleM);
        singleMview.setFitWidth(Constants.SINGLE_PLAYER_HEIGHT);
        singleMview.setFitHeight(Constants.SINGLE_PLAYER_HEIGHT);
        singleMview.setLayoutY(200);
        singleMview.setLayoutX(700);
        single.setText("SINGLE PLAYER");
        multi.setText("MULTI PLAYER");
        single.setTextFill(Color.rgb(255, 255, 255));
        multi.setTextFill(Color.rgb(255, 255, 255));
        single.relocate(400, 500);
        multi.relocate(800, 500);
        single.setStyle("-fx-background-color: #111143; ");
        multi.setStyle("-fx-background-color: #091841; ");
        lightning(singlePview);
        lightning(singleMview);
        root.getChildren().addAll(backgroundView, singlePview, singleMview, single, multi);

    }

    public void shopMenu(boolean mode, TextField object, ArrayList<Card> cards, ArrayList<Item> items, AnchorPane back, AnchorPane next,
                         AnchorPane prev, AnchorPane sell, AnchorPane buy, int page) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("resources/scenes/load/scene_load_background.jpg"));
        ImageView buyView = new ImageView(new Image("resources/ui/button_confirm_glow@2x.png"));
        ImageView sellView = new ImageView(new Image("resources/ui/button_cancel_glow@2x.png"));
        Label sellText = new Label("Sell");
        Label buyText = new Label("Buy");
        Label modeLabel;
        if (mode)
            modeLabel = new Label("Shop Objects");
        else
            modeLabel = new Label("Collection Objects");
        modeLabel.setFont(Font.font(Constants.PAGE_TITLE_FONT, FontWeight.EXTRA_BOLD, Constants.PAGE_TITLE_SIZE));
        modeLabel.setTextFill(Color.NAVY);
        modeLabel.translateXProperty().bind(modeLabel.widthProperty().divide(2).negate());
        modeLabel.relocate(Constants.SCROLLER_X, Constants.PAGE_TITLE_Y);
        sellText.translateXProperty().bind(sellText.widthProperty().divide(2).negate());
        sellText.translateYProperty().bind(sellText.heightProperty().divide(2).negate());
        buyText.translateXProperty().bind(buyText.widthProperty().divide(2).negate());
        buyText.translateYProperty().bind(buyText.heightProperty().divide(2).negate());
        sellText.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.SELL_TEXT_SIZE));
        sellText.setTextFill(Color.NAVY);
        buyText.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.SELL_TEXT_SIZE));
        buyText.setTextFill(Color.NAVY);
        sellView.setFitWidth(Constants.SELL_WIDTH);
        sellView.setFitHeight(Constants.SELL_HEIGHT);
        buyView.setFitWidth(Constants.SELL_WIDTH);
        buyView.setFitHeight(Constants.SELL_HEIGHT);
        buyText.relocate(buyView.getFitWidth() / 2, buyView.getFitHeight() / 2);
        sellText.relocate(sellView.getFitWidth() / 2, sellView.getFitHeight() / 2);
        sell.getChildren().addAll(sellView, sellText);
        buy.getChildren().addAll(buyView, buyText);
        sell.setPrefWidth(sellView.getFitWidth());
        sell.setPrefHeight(sellView.getFitHeight());
        buy.setPrefWidth(buyView.getFitWidth());
        buy.setPrefHeight(buyView.getFitHeight());
        verticalList(Alignment.LEFT, Constants.SELL_PANE_X, Constants.CENTRE_Y, buy, sell);
        scrollPane(backView, next, prev, back);
        lightning(buy);
        lightning(sell);
        object.setPrefWidth(Constants.FIELD_WIDTH);
        object.setPrefHeight(Constants.FIELD_HEIGHT);
        object.relocate(Constants.SELL_X + Constants.SELL_WIDTH / 2, 200);
        root.getChildren().addAll(backView, sell, buy, next, prev, back, object, modeLabel);
        showCards(cards, items, page);
    }


    public void collectionMenu(TextField object, ArrayList<Card> cards, ArrayList<Item> items
            , AnchorPane createDeck, TextField name, AnchorPane back, AnchorPane next, AnchorPane prev, int page) {
        root.getChildren().clear();

        Label create = new Label(); //= new Label("Create Deck");
/*        create.setFont(Font.font(Constants.TEXT_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
        create.setTextFill(Color.LIGHTCYAN);
        Image createImage = new Image("ui/button_primary.png");
        createDeck.setImage(createImage);
        createDeck.setFitWidth(2 * Constants.BUTTON_WIDTH);
        createDeck.setFitHeight(2 * Constants.BUTTON_HEIGHT);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y, createDeck);
        create.setLayoutX(createDeck.getLayoutX() + Constants.IMAGE_BUTTON_REL_X);
        create.setLayoutY(createDeck.getLayoutY() + Constants.IMAGE_BUTTON_REL_Y);
        name.setLayoutX(create.getLayoutX());
        name.setLayoutY(createDeck.getLayoutY() - Constants.FIELD_HEIGHT - createDeck.getFitHeight());
        name.setPrefWidth(Constants.FIELD_WIDTH);
        name.setPrefHeight(Constants.FIELD_HEIGHT);
*/
        ImageView backView = new ImageView(new Image("resources/scenes/load/scene_load_background.jpg"));
        scrollPane(backView, next, prev, back);
//        lightning(createDeck, create);
        object.setPrefWidth(Constants.FIELD_WIDTH);
        object.setPrefHeight(Constants.FIELD_HEIGHT);
        object.relocate(Constants.SELL_X + Constants.SELL_WIDTH / 2, 200);
        root.getChildren().addAll(backView, next, prev, back, object);
        showCards(cards, items, page);
    }

    private void scrollPane(ImageView backView, AnchorPane next, AnchorPane prev, AnchorPane back) {
        Image slide = new Image("resources/ui/sliding_panel/sliding_panel_paging_button@2x.png");
        Image arrow = new Image("resources/ui/sliding_panel/sliding_panel_paging_button_text@2x.png");
        ImageView leftArrow = new ImageView(), rightArrow = new ImageView();
        Image backArrow = new Image("resources/ui/button_back_corner@2x.png");
        leftArrow.setImage(arrow);
        rightArrow.setImage(arrow);
        rightArrow.setRotate(180);
        backView.setFitHeight(Constants.WINDOW_HEIGHT);
        backView.setFitWidth(Constants.WINDOW_WIDTH);
        backView.setOpacity(0.5);
        ImageView rightSlider = new ImageView(slide);
        ImageView leftSlider = new ImageView(slide);
        ImageView cornerImage = new ImageView(backArrow);
        setImageSize(Constants.SLIDE, cornerImage);
        setImageSize(Constants.SLIDE, rightSlider, leftSlider);
        setImageSize(Constants.ARROW, leftArrow, rightArrow);
        next.getChildren().addAll(rightSlider, rightArrow);
        prev.getChildren().addAll(leftSlider, leftArrow);
        back.getChildren().addAll(cornerImage);
        setPaneSize(rightSlider.getFitWidth(), rightSlider.getFitHeight(), next, prev);
        setPaneSize(cornerImage.getFitWidth(), cornerImage.getFitHeight(), back);
        horizantalList(Alignment.UP, 0, 0, back);
        horizantalList(Alignment.DOWN, Constants.SCROLLER_X, Constants.SCROLLER_Y, prev, next);
        lightning(back);
        lightning(prev);
        lightning(next);
    }

    private void showCards(ArrayList<Card> cards, ArrayList<Item> items, int page) {
        if (page <= (cards.size() + items.size() - 1) / Constants.CARD_PER_PAGE) {
            for (int i = 0; i < Constants.CARD_PER_COLUMN; i++) {
                for (int j = 0; j < Constants.CARD_PER_ROW; j++) {
                    try {
                        int index = page * Constants.CARD_PER_PAGE + i * Constants.CARD_PER_ROW + j;
                        AnchorPane anchorPane;
                        if (index < cards.size())
                            anchorPane = cards.get(index).getCardView().getPane();
                        else {
                            anchorPane = items.get(index - cards.size()).getCardView().getPane();
                        }
                        anchorPane.setLayoutX(Constants.CARD_X + j * (Constants.CARD_WIDTH + Constants.CARD_X_GAP));
                        anchorPane.setLayoutY(Constants.CARD_Y + i * (Constants.CARD_HEIGHT + Constants.CARD_Y_GAP));
                        lightning(anchorPane);
                        root.getChildren().add(anchorPane);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    private void stableLighning(Node... nodes) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.6);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (int i = 0; i < nodes.length; i++) {
            int finalI = i;
//            nodes[i].addEventFilter(MouseEvent.MOUSE_ENTERED, e -> nodes[finalI].setEffect(colorAdjust));
            nodes[i].addEventFilter(MouseEvent.MOUSE_CLICKED, e -> nodes[finalI].setEffect(colorAdjust));
            nodes[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                for (int j = 0; j < nodes.length; j++) {
                    if (finalI != j) {
                        int finalJ = j;
                        nodes[finalJ].addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, e -> nodes[finalJ].setEffect(null));
                    }
                }
            });
        }
    }

    private void lightning(Node... nodes) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (Node singlePview : nodes) {
            singlePview.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> nodes[0].setEffect(colorAdjust));
            singlePview.removeEventFilter(MouseEvent.MOUSE_ENTERED, e -> nodes[0].setEffect(colorAdjust));
        }
    }

    private void lightning(ImageView... imageViews) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (ImageView singlePview :
                imageViews) {
            singlePview.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> singlePview.setEffect(colorAdjust));
            singlePview.addEventFilter(MouseEvent.MOUSE_EXITED, e -> singlePview.setEffect(null));
        }
    }

}



