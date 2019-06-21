package View;

import Model.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

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

    public void battleMenu(Account[] accounts, ImageView imageView1, ImageView imageView2, Polygon[] polygon, ImageView view,
                           Label labels, ImageView[] mana, ImageView[] handcards, BattleCards[] battleCards) {
        root.getChildren().clear();
        maps();
        battleFieldView(polygon);
        heroGifs(accounts, imageView1, imageView2, polygon);
        endTurnButton(view, labels);
        mana(accounts[0], mana);
        handCardRings(handcards);


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

    private void heroGifs(Account[] accounts, ImageView imageView1, ImageView imageView2, Polygon[] polygon) {
        Image firstHero, secondHero;
        firstHero = getImage(accounts[0]);
        secondHero = getImage(accounts[1]);
        ImageView firstHeroView = new ImageView(firstHero);
        ImageView secondHeroView = new ImageView(secondHero);
        bossImageSettings(firstHeroView, secondHeroView);
        imageView2.relocate((polygon[26].getPoints().get(0) + polygon[26].getPoints().get(2)) / 2 - 55, (polygon[26].getPoints().get(1) + polygon[26].getPoints().get(5)) / 2 - 105);
        imageView2.setScaleX(-1);
        imageView1.relocate((polygon[18].getPoints().get(0) + polygon[18].getPoints().get(2)) / 2 - 60, (polygon[18].getPoints().get(1) + polygon[18].getPoints().get(5)) / 2 - 105);
        lightning(imageView1, imageView2);
        root.getChildren().addAll(firstHeroView, secondHeroView, imageView1, imageView2);
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
        polygon1.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> polygon1.setEffect(colorAdjust));
        polygon1.addEventFilter(MouseEvent.MOUSE_EXITED, e -> polygon1.setEffect(null));
    }

    public void mainMenu(Button login, Button create, Button exit, TextField username, TextField password) {
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

    public void shopMenu(ImageView[] heroes, ImageView[] mininos, ImageView[] spells, ImageView[] items,
                         ImageView back, ImageView next, ImageView prev) {
        root.getChildren().clear();
        Image slide = new Image("resources/ui/sliding_panel/sliding_panel_paging_button.png");
        Image arrow = new Image("resources/ui/sliding_panel/sliding_panel_paging_button_text.png");
        Image backArrow = new Image("resources/ui/button_back_corner.png");
        Image background = new Image("resources/scenes/load/scene_load_background@2x.jpg");
        ImageView backView = new ImageView(background);
        ImageView leftArrow = new ImageView(arrow);
        ImageView rightArrow = new ImageView(arrow);
        scrollPaneMethod(backView, rightArrow, next, prev, back, slide, backArrow);
        horizontalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.WINDOW_HEIGHT - 100, prev, next);
        horizontalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.WINDOW_HEIGHT - 100, leftArrow, rightArrow);
        setImageSize(leftArrow, rightArrow);
        root.getChildren().addAll(backView, next, prev, back, leftArrow, rightArrow);
        lightning(back);
        lightning(prev, leftArrow);
        lightning(next, rightArrow);
    }

    private void verticalList(Alignment alignment, double x, double y, Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof Button) {
                Button button = (Button) node;
                setButtonSize(button);
                if (alignment == Alignment.CENTRE)
                    button.setLayoutX(x - button.getPrefWidth() / 2);
            }
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                if (alignment == Alignment.CENTRE)
                    imageView.setLayoutX(x - imageView.getFitWidth() / 2);
            }
            if (alignment == Alignment.LEFT)
                node.setLayoutX(x);
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

    private void setButtonSize(Button... buttons) {
        for (Button button : buttons) {
            button.setPrefHeight(Constants.BUTTON_HEIGHT);
            if (button.getText().length() >= 8)
                button.setPrefWidth(2 * Constants.BUTTON_WIDTH);
            else
                button.setPrefWidth(Constants.BUTTON_WIDTH);
        }
    }

    private void setImageSize(ImageView... imageViews) {
        double currentSize;
        for (ImageView imageView : imageViews) {
            currentSize = imageView.getFitHeight();
            imageView.setFitHeight(Constants.ARROW);
            imageView.setFitWidth(Constants.ARROW);
            imageView.setLayoutX(imageView.getLayoutX() + (currentSize - Constants.ARROW) / 2);
            imageView.setLayoutY(imageView.getLayoutY() + (currentSize - Constants.ARROW) / 2);
        }

    }

    private void horizontalList(Alignment alignment, double x, double y, Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof Button) {
                Button button = (Button) node;
                setButtonSize(button);
                if (alignment == Alignment.CENTRE)
                    button.setLayoutY(y - button.getPrefHeight() / 2);
            }
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setFitWidth(Constants.SLIDE);
                imageView.setFitHeight(Constants.SLIDE);
                if (alignment == Alignment.CENTRE)
                    imageView.setLayoutY(y - imageView.getFitHeight() / 2);
            }
            if (alignment == Alignment.UP || alignment == Alignment.DOWN)
                node.setLayoutY(y);
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


    public void collectionMenu(Account account, ImageView createDeck, TextField name, ImageView back, ImageView next, ImageView prev) {
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
        ImageView leftArrow = new ImageView(), rightArrow = new ImageView();
        scrollPane(backView, rightArrow, leftArrow, next, prev, back);
//        lightning(createDeck, create);
        root.getChildren().addAll(backView, next, prev, back, rightArrow, leftArrow);
        showCards(account.getCollection().getCards(), 4);
    }

    private void scrollPane(ImageView backView, ImageView rightArrow, ImageView leftArrow,
                            ImageView next, ImageView prev, ImageView back) {
        Image slide = new Image("resources/ui/sliding_panel/sliding_panel_paging_button.png");
        Image arrow = new Image("resources/ui/sliding_panel/sliding_panel_paging_button_text.png");
        Image backArrow = new Image("resources/ui/button_back_corner.png");
        leftArrow.setImage(arrow);
        rightArrow.setImage(arrow);
        scrollPaneMethod(backView, rightArrow, next, prev, back, slide, backArrow);
        horizontalList(Alignment.CENTRE, Constants.SCROLLER_X, Constants.SCROLLER_Y, prev, next);
        horizontalList(Alignment.CENTRE, Constants.SCROLLER_X, Constants.SCROLLER_Y, leftArrow, rightArrow);
        setImageSize(leftArrow, rightArrow);
        lightning(back);
        lightning(prev, leftArrow);
        lightning(next, rightArrow);
    }

    private void scrollPaneMethod(ImageView backView, ImageView rightArrow, ImageView next, ImageView prev, ImageView back, Image slide, Image backArrow) {
        rightArrow.setRotate(180);
        backView.setFitHeight(Constants.WINDOW_HEIGHT);
        backView.setFitWidth(Constants.WINDOW_WIDTH);
        backView.setOpacity(0.5);
        next.setImage(slide);
        prev.setImage(slide);
        back.setImage(backArrow);
        horizontalList(Alignment.UP, 0, 0, back);
    }

    private void showCards(ArrayList<Card> cards, int page) {
        if (page <= (cards.size() - 1) / Constants.CARD_PER_PAGE) {
            for (int i = 0; i < Constants.CARD_PER_COLUMN; i++) {
                for (int j = 0; j < Constants.CARD_PER_ROW; j++) {
                    try {
                        int index = page * Constants.CARD_PER_PAGE + i * Constants.CARD_PER_ROW + j;
                        AnchorPane anchorPane = cards.get(index).getCardView().getPane();
                        anchorPane.setLayoutX(Constants.CARD_X + j * (Constants.CARD_WIDTH + Constants.CARD_X_GAP));
                        anchorPane.setLayoutY(Constants.CARD_Y + i * (Constants.CARD_HEIGHT + Constants.CARD_Y_GAP));
                        //lightning(anchorPane);
                        root.getChildren().add(anchorPane);
                    } catch (Exception ignored) {

                    }
                }
            }
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



