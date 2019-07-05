package View;

import Model.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Arrays;

import Model.Menu;
import javafx.animation.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
    private transient Menu menu = Menu.getInstance();
    private static final View view = new View();
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static int[] cells = {16, 34, 7, 43, 41, 5};
    private static int cCell = 0;
    private transient Image cursor = new Image("ui/mouse_auto@2x.png");
    private transient Image battleCursor = new Image("ui/mouse_attack@2x.png");


    private View() {

    }

    public Scene getScene() {
        //Image icon = new Image("booster_pack_opening/booster_orb.png");

        //scene.setCursor(new ImageCursor(cursor, Constants.CURSOR_LENGTH, Constants.CURSOR_LENGTH));
        return scene;
    }

    public static View getInstance() {
        return view;
    }

    public void passwordInsertion() {
        System.out.println("Password: ");
    }

    public void accountCreation(Boolean valid) {
        if (valid) {
            System.out.println("Account created");
            return;
        }
        System.out.println("Account already exists");
    }

    public void showMatchHistory(ArrayList<Match> matches, int level) {
        System.out.println("number level win/lose time");
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();


        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getTime().getHour() == hour) {
                int mins = minutes - matches.get(i).getTime().getMinute();
                System.out.println(i + " . LEVEL:" + level + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + mins);
            } else {
                int hours = hour - matches.get(i).getTime().getHour();
                System.out.println(i + " . LEVEL:" + level + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + hours);
            }
        }

    }

    //  MENUUUUUU
    public void matchHistoryMenu(ArrayList<Match> matches, AnchorPane back) {

        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();
        Label label = new Label("MATCH HISTORY");
        label.setLayoutX(scene.getWidth() / 3);
        label.setLayoutY(scene.getHeight() / 6 - 50);
        label.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 46));
        TextArea textField = new TextArea();
        textField.setLayoutX(scene.getWidth() / 3);
        textField.setLayoutY(scene.getHeight() / 6);
        textField.setMaxSize(Constants.MATCH_HISTORY_FIELD_WIDTH, Constants.MATCH_HISTORY_FIELD_HEIGHT);
        textField.setMinSize(Constants.MATCH_HISTORY_FIELD_WIDTH, Constants.MATCH_HISTORY_FIELD_HEIGHT);
        textField.setEditable(false);
        if (matches.size() == 0)
            textField.appendText("NO MATCHES TO SHOW");
        for (int i = 0; i < matches.size(); i++) {
            String opp = matches.get(i).getRival();
            String state = "";
            switch (matches.get(i).getResult()) {
                case WON:
                    state = "WON";
                    break;
                case LOST:
                    state = "LOST";
                    break;
                case TIE:
                    state = "TIE";
                    break;
            }
            if (matches.get(i).getTime().getHour() == hour) {
                int mins = minutes - matches.get(i).getTime().getMinute();

                textField.appendText(i + 1 + "_ " + opp + "  " + state + "  " + " TIME: " + mins + " mins ago" + "\r\n");

            } else {
                int hours = hour - matches.get(i).getTime().getHour();
                textField.appendText(i + 1 + "_ " + opp + "  " + state + "  " + " TIME: " + hours + " hours ago" + "\r\n");
            }

        }
        Image background = new Image("scenes/obsidian_woods/obsidian_woods_background.jpg");
        ImageView backgroundView = new ImageView(background);
        Image backArrow = new Image("ui/button_back_corner.png");
        ImageView arrow = new ImageView(backArrow);
        back.getChildren().add(arrow);
        lightning(back);
        root.getChildren().addAll(backgroundView, textField, label, back);


    }

    public void showMatchHistory(ArrayList<Match> matches, String name) {
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int minutes = time.getMinute();
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getTime().getHour() == hour) {
                int mins = minutes - matches.get(i).getTime().getMinute();
                System.out.println(i + " . OPPONENT:" + name + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + mins);
            } else {
                int hours = hour - matches.get(i).getTime().getHour();
                System.out.println(i + " . NAME:" + name + "WIN OR LOST" + matches.get(i).getResult() + "TIME: " + hours);
            }
        }
    }

    public void login(AlertMessage message) {
        message.getResult();
    }

    public void logout() {
        System.out.println("Successful logout!!!:))))");
    }

    public void printLeaderboard(ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + 1 + " - UserName : " + accounts.get(i).getName() +
                    " - Wins : " + accounts.get(i).getWins());
        }

    }
    //Graphic

    public void graveYardMenu(Card[][] cards, AnchorPane next, int turn, AnchorPane prev, AnchorPane back, int page) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/shimzar/bg@2x.jpg"));
        scrollPane(backView, next, prev, back);
        root.getChildren().addAll(backView, next, prev, back);
        showCards((ArrayList<Card>) Arrays.asList(cards[turn % 2]), new ArrayList<>(), new Label(), page);
    }


    public void selectUserMenu(ArrayList<Account> accounts, Label label, TextField textField) {
        root.getChildren().clear();
        Image background = new Image("scenes/shimzar/bg@2x.jpg");
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

    public void backGroundMenu(ImageView redrock, ImageView vanar, ImageView shimzar, ImageView abyssian, ImageView china
            , ImageView lion, ImageView candle, ImageView octa, ImageView metal, ImageView ice, ImageView purple) {
        ImageView imageView = new ImageView(new Image("codex/chapter1_background@2x.jpg"));
        imageView.setFitHeight(Constants.WINDOW_HEIGHT);
        imageView.setFitWidth(Constants.WINDOW_WIDTH);
        redrock.relocate(75, 100);
        abyssian.relocate(375, 100);
        shimzar.relocate(675, 100);
        vanar.relocate(975, 100);
        china.relocate(150, 300);
        metal.relocate(500, 300);
        lion.relocate(850, 300);
        candle.relocate(75, 500);
        ice.relocate(375, 500);
        octa.relocate(675, 500);
        purple.relocate(975, 500);
        setScaleForPic(redrock, shimzar, abyssian, vanar, lion, octa, candle, ice, purple, china, metal);

        lightning(redrock, shimzar, abyssian, vanar, lion, octa, candle, ice, purple, china, metal);

        root.getChildren().addAll(imageView, redrock, vanar, shimzar, abyssian, lion, octa, candle, ice, purple, china, metal);


    }

    public void cardBackGround(BattleCards battleCards) {
        Label power = new Label();
        Label health = new Label();
        Label label = new Label();
        ImageView imageView = new ImageView(new Image("card_backgrounds/craftable_unit@2x.png"));
        imageView.relocate(1000, 200);
        imageView.setFitHeight(230);
        imageView.setFitWidth(170);
        ImageView imageView1 = new ImageView(battleCards.getImageView()[0].getImage());
        imageView1.setFitWidth(170);
        imageView1.setFitHeight(170);
        imageView1.relocate(1000, 170);
        if (battleCards.getCard().getType().equals("Spell")) {
            imageView1.setFitWidth(90);
            imageView1.setFitHeight(90);
            imageView1.relocate(1030, 220);
        }
        imageView1.setScaleX(-1);
        power.setTextFill(Color.rgb(255, 253, 253));
        health.setText(String.valueOf(battleCards.getCard().getHealthPoint()));
        health.setFont(Font.font(20));
        power.setFont(Font.font(20));
        power.relocate(1035, 325);
        health.setTextFill(Color.rgb(255, 253, 253));
        power.setText(String.valueOf(battleCards.getCard().getAssaultPower()));
        health.relocate(1125, 325);
        label.setText(battleCards.getCard().getName());
        label.setTextFill(Color.rgb(255, 255, 255));
        label.relocate(1030, 380);
        label.setFont(Font.font(15));
        root.getChildren().addAll(imageView, imageView1, health, power, label);
    }

    public void setScaleForPic(ImageView... imageViews) {
        for (ImageView imageView : imageViews) {
            imageView.setFitHeight(150);
            imageView.setFitWidth(290);
        }
    }

    public void battleMenu(Account[] accounts, BattleCards[] battleHeros, Polygon[] polygon, ImageView view,
                           Label labels, ImageView[] mana, ImageView[] handcards, BattleCards[] battleCards,
                           ImageView backGround, ImageView foreGround, ImageView back, ImageView flag, BattleMode battleMode, ImageView[] flags, int[] cellEffect) {
        root.getChildren().clear();
        maps(backGround, foreGround);
        battleFieldView(polygon, cellEffect);
        heroGifs(accounts, battleHeros, polygon);
        endTurnButton(view, labels);
        mana(accounts[0], mana);
        handCardRings(handcards);
        handGifs(battleCards);
        backButton(back);
        if (battleMode.equals(BattleMode.FLAG)) {
            flag = new ImageView(new Image("Crystal Wisp_run.gif"));
            flag.relocate((polygon[22].getPoints().get(0) + polygon[22].getPoints().get(2)) / 2 - 50,
                    (polygon[22].getPoints().get(1) + polygon[22].getPoints().get(5)) / 2 - 85);
            flag.setFitWidth(100);
            flag.setFitHeight(100);
            root.getChildren().add(flag);
        }

    }


    public void collectFlags(ImageView[] flags, Polygon[] polygon, int[] randomC, int a) {
        for (int i = 0; i < 6; i++) {
            if (flags[i] != null) {
                int x = randomC[i];
                flags[i].relocate((polygon[x].getPoints().get(0) + polygon[x].getPoints().get(2)) / 2 - 50,
                        (polygon[x].getPoints().get(1) + polygon[x].getPoints().get(5)) / 2 - 85);
                if (a == 1) {
                    flags[i].relocate((polygon[x].getPoints().get(0) + polygon[x].getPoints().get(2)) / 2 - 35,
                            (polygon[x].getPoints().get(1) + polygon[x].getPoints().get(5)) / 2 - 45);
                    System.out.println(x);
                    flags[i].setFitWidth(60);
                    flags[i].setFitHeight(60);
                } else {
                    flags[i].setFitWidth(100);
                    flags[i].setFitHeight(100);
                }
                root.getChildren().add(flags[i]);
            }
        }
    }

    private void backButton(ImageView back) {
        back.setImage(new Image("ui/button_back_corner@2x.png"));
        root.getChildren().add(back);
    }

    private void handGifs(BattleCards[] battleCards) {
        for (int i = 0; i < 5; i++) {
            System.out.println(battleCards[i].getCard().getName());
            battleCards[i].getImageView()[0].relocate(250 + 120 * i, 490);
            battleCards[i].getImageView()[0].setFitHeight(160);
            battleCards[i].getImageView()[0].setFitWidth(160);
            if (battleCards[i].getCard().getType().equals("Spell")) {
                battleCards[i].getImageView()[0].relocate(290 + 120 * i, 545);
                battleCards[i].getImageView()[0].setFitHeight(80);
                battleCards[i].getImageView()[0].setFitWidth(80);
            }
            lightning(battleCards[i].getImageView()[0]);
            root.getChildren().add(battleCards[i].getImageView()[0]);
        }
    }

    private void heroGifs(Account[] accounts, BattleCards[] battleHeros, Polygon[] polygon) {
        Image firstHero, secondHero;
        firstHero = getImage(accounts[0]);
        secondHero = getImage(accounts[1]);
        ImageView firstHeroView = new ImageView(firstHero);
        ImageView secondHeroView = new ImageView(secondHero);
        firstHeroView.setFitHeight(160);
        firstHeroView.setFitWidth(160);
        secondHeroView.setFitWidth(160);
        secondHeroView.setFitHeight(160);
        bossImageSettings(firstHeroView, secondHeroView);
        battleHeros[1].getImageView()[0].relocate((polygon[26].getPoints().get(0) + polygon[26].getPoints().get(2)) / 2 - 55, (polygon[26].getPoints().get(1) + polygon[26].getPoints().get(5)) / 2 - 105);
        battleHeros[1].getImageView()[0].setScaleX(-1);
        battleHeros[0].getImageView()[0].relocate((polygon[18].getPoints().get(0) + polygon[18].getPoints().get(2)) / 2 - 60, (polygon[18].getPoints().get(1) + polygon[18].getPoints().get(5)) / 2 - 105);
        lightning(battleHeros[0].getImageView()[0], battleHeros[1].getImageView()[0]);
        root.getChildren().addAll(firstHeroView, secondHeroView, battleHeros[0].getImageView()[0], battleHeros[1].getImageView()[0]);
    }

    public void handView(Coordinate[] coordinate, BattleCards battleCard) {
        if (!battleCard.getCard().getType().equals("Spell")) {
            if (coordinate[0].getY() < 500) {
                battleCard.getImageView()[0].relocate(coordinate[0].getX(), coordinate[0].getY());
            } else {
                battleCard.getImageView()[0].relocate(coordinate[0].getX() - 40, coordinate[0].getY() - 55);
            }
            battleCard.getImageView()[0].setFitHeight(160);
            battleCard.getImageView()[0].setFitWidth(160);
        }
        if (battleCard.getCard().getType().equals("Spell")) {
            if (coordinate[0].getY() < 500) {
                battleCard.getImageView()[0].relocate(coordinate[0].getX() + 40, coordinate[0].getY() + 55);
            } else {
                battleCard.getImageView()[0].relocate(coordinate[0].getX(), coordinate[0].getY());
            }
            battleCard.getImageView()[0].setFitHeight(80);
            battleCard.getImageView()[0].setFitWidth(80);
        }
        lightning(battleCard.getImageView());
        root.getChildren().addAll(battleCard.getImageView()[0]);
    }

    private void handCardRings(ImageView[] handcards) {
        for (int i = 0; i < 5; i++) {
            handcards[i].setImage(new Image("ui/replace_outer_ring_smoke@2x.png"));
            handcards[i].relocate(270 + 120 * i, 525);
            handcards[i].setFitHeight(120);
            handcards[i].setFitWidth(120);
            lightning(handcards[i]);
            root.getChildren().add(handcards[i]);
        }
    }

    public void cellEffect(String s, int a, Polygon[] polygons, int i, Label label) {
        label.setText(s);
        if (i == 0) {
            label.relocate(polygons[a].getPoints().get(0) + 5, polygons[a].getPoints().get(1) + 20);
            label.setTextFill(Color.rgb(255, 255, 255));
            root.getChildren().addAll(label);
        } else {
            root.getChildren().remove(label);
        }
    }

    private void battleFieldView(Polygon[] polygon, int[] cell) {
        battleField(polygon, cell);
        for (int i = 0; i < 45; i++) {
            root.getChildren().add(polygon[i]);
        }
    }

    private void maps(ImageView background, ImageView foreground) {
        background.setFitWidth(Constants.WINDOW_WIDTH);
        background.setFitHeight(Constants.WINDOW_HEIGHT);
        foreground.setFitWidth(Constants.WINDOW_WIDTH);
        foreground.setFitHeight(Constants.WINDOW_HEIGHT);
        root.getChildren().addAll(background, foreground);
    }


    private void mana(Account account, ImageView[] mana) {
        for (int i = 0; i < 9; i++) {
            if (i < account.getMana()) {
                mana[i].setImage(new Image("ui/icon_mana@2x.png"));
            } else {
                mana[i].setImage(new Image("ui/icon_mana_inactive@2x.png"));
            }
            mana[i].relocate(320 + i * 29, 150 - i * 3);
            mana[i].setFitWidth(35);
            mana[i].setFitHeight(35);
            lightning(mana[i]);
        }
        root.getChildren().addAll(mana);
    }

    private void endTurnButton(ImageView view, Label labels) {
        view.setImage(new Image("ui/button_end_turn_mine@2x.png"));
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

    public void aiHandGifs(BattleCards[] aiCards, Polygon[] polygons, int i) {

        if (aiCards[i].isInside() && cCell < 4) {
            aiCards[i].getImageView()[0].relocate(polygons[cells[cCell]].getPoints().get(0) - 40, polygons[cells[cCell]].getPoints().get(1) - 95);
            aiCards[i].getImageView()[0].setScaleX(-1);
            aiCards[i].getImageView()[0].setFitHeight(160);
            aiCards[i].getImageView()[0].setFitWidth(160);
            root.getChildren().addAll(aiCards[i].getImageView()[0]);
            cCell++;
        }
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
        File file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/sfx/sfx_f1_general_attack_swing.m4a");
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
                firstHero = new Image("boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "ZAHAK":
                firstHero = new Image("boss_battles/boss_calibero_portrait_image_hex@2x.png");
                break;
            case "ARASH":
                firstHero = new Image("boss_battles/boss_boreal_juggernaut_portrait_image_hex@2x.png");
                break;
            case "SIMORGH":
                firstHero = new Image("boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
            case "SEVEN_HEADED_DRAGON":
                firstHero = new Image("boss_battles/boss_crystal_portrait_hex.png");
                break;
            case "RAKHSH":
                firstHero = new Image("boss_battles/boss_wraith_portrait_hex@2x.png");
                break;
            case "KAVEH":
                firstHero = new Image("boss_battles/boss_vampire_portrait_hex@2x.png");
                break;
            case "AFSANEH":
                firstHero = new Image("boss_battles/boss_spelleater_portrait_hex@2x.png");
                break;
            case "ESFANDIAR":
                firstHero = new Image("boss_battles/boss_skurge_portrait_hex@2x.png");
                break;
            case "ROSTAM":
                firstHero = new Image("boss_battles/boss_shinkage_zendo_portrait_image_hex@2x.png");
                break;
        }
        return firstHero;
    }

    private void battleField(Polygon[] polygon, int[] cell) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.7);
        Glow glow = new Glow();
        glow.setLevel(0.9);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                polygon[j + 9 * i] = new Polygon();
                polygon[j + 9 * i].getPoints().addAll(-i * 8 + 380.0 + (60 + i * 2) * j + 2,
                        205.0 + i * 50 + 2, -i * 8 + 380.0 + (60 + i * 2) * (j + 1) - 2,
                        205.0 + i * 50 + 2, -(i + 1) * 8 + 380.0 + (60 + (i + 1) * 2) * (j + 1) - 2,
                        205.0 + ((i + 1) * 50) - 2, -(i + 1) * 8 + 380.0 + (60 + (i + 1) * 2) * j + 2, 205.0 + ((i + 1) * 50) - 2);
                polygon[j + 9 * i].setFill(Color.rgb(119, 104, 180, 0.6));
                glowPolygon(colorAdjust, polygon[j + 9 * i]);
                for (int k = 0; k < 3; k++) {
                    if (cell[k] == j + 9 * i) {
                        if (k == 0)
                            polygon[j + 9 * i].setFill(Color.rgb(7, 69, 62, 0.6));
                        if (k == 1)
                            polygon[j + 9 * i].setFill(Color.rgb(191, 120, 69, 0.6));
                        if (k == 2)
                            polygon[j + 9 * i].setFill(Color.rgb(175, 180, 88, 0.6));
                    }
                }
            }
        }
    }

    private void glowPolygon(ColorAdjust colorAdjust, Polygon polygon1) {
        polygon1.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> polygon1.setEffect(colorAdjust));
        polygon1.addEventFilter(MouseEvent.MOUSE_EXITED, e -> polygon1.setEffect(null));
    }

    public void mainMenu(AnchorPane login, AnchorPane create, AnchorPane exit, TextField username, PasswordField password) {
        Image background = new Image("scenes/obsidian_woods/obsidian_woods_background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("scenes/obsidian_woods/obsidian_woods_cliff.png");
        ImageView foregroundView = getImageView(background, foreground);
        ImageView buttonImage = new ImageView(new Image("ui/button_primary_middle_glow@2x.png"));
        buttonImage.setFitWidth(Constants.PRIMITIVE_WIDTH);
        buttonImage.setFitHeight(Constants.PRIMITIVE_HEIGHT);
        login.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Login", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        create.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Create", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        exit.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Exit", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        verticalList(Alignment.CENTRE, Constants.MAIN_MENU_X, Constants.CENTRE_Y, buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), login, create, exit);
        password.setPrefWidth(Constants.FIELD_WIDTH);
        password.setPrefHeight(Constants.FIELD_HEIGHT);
        password.setLayoutX(Constants.CENTRE_X - password.getPrefWidth() / 2);
        password.setLayoutY(login.getLayoutY() - 2 * password.getPrefHeight());
        username.setPrefWidth(Constants.FIELD_WIDTH);
        username.setPrefHeight(Constants.FIELD_HEIGHT);
        username.setLayoutX(password.getLayoutX());
        username.setLayoutY(password.getLayoutY() - 2 * Constants.FIELD_HEIGHT);
        lightning(login, create, exit);
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
            if (nodes[i] instanceof TextField) {
                TextField field = (TextField) nodes[i];
                field.setPrefWidth(Constants.FIELD_WIDTH);
                field.setPrefHeight(Constants.FIELD_HEIGHT);
                if (alignment == Alignment.CENTRE)
                    field.setLayoutY(y - field.getPrefHeight() / 2);
            }
            if (nodes[i] instanceof ComboBox) {
                ComboBox box = (ComboBox) nodes[i];
                box.setPrefWidth(Constants.SLIDE);
                box.setPrefWidth(Constants.SLIDE);
                if (alignment == Alignment.CENTRE)
                    box.setLayoutY(y - box.getPrefWidth() / 2);
            }
            if (nodes[i] instanceof Label) {
                Label label = (Label) nodes[i];
                label.setFont(Font.font(Constants.INFO_FONT, FontWeight.EXTRA_BOLD, Constants.FONT_SIZE));
                label.setTextFill(Color.NAVY);
                if (alignment == Alignment.CENTRE)
                    label.setLayoutY(y - 2 * Constants.LABEL_HEIGHT);
            }
            if (alignment == Alignment.UP || alignment == Alignment.DOWN) {
                nodes[i].setLayoutY(y);
                if (nodes[i] instanceof Label) {
                    nodes[i].setLayoutY(y - Constants.LABEL_HEIGHT);
                }
            }
        }
        if (nodes[0] instanceof TextField) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((TextField) nodes[nodes.length / 2]).getPrefWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.FIELD_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() + 2 * Constants.FIELD_WIDTH);
            }
        }
        if (nodes[0] instanceof ComboBox) {
            nodes[nodes.length / 2].setLayoutX(x +
                    ((nodes.length + 1) % 2) * ((ComboBox) nodes[nodes.length / 2]).getPrefWidth() / 2);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.COMBO_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.COMBO_WIDTH);
            }
        }
        if (nodes[0] instanceof Label) {
            nodes[nodes.length / 2].setLayoutX(x);
            for (int i = nodes.length / 2 - 1; i >= 0; i--) {
                nodes[i].setLayoutX(nodes[i + 1].getLayoutX() - 2 * Constants.LABEL_WIDTH);
            }
            for (int i = nodes.length / 2 + 1; i < nodes.length; i++) {
                nodes[i].setLayoutX(nodes[i - 1].getLayoutX() + 2 * Constants.LABEL_WIDTH);
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

    public void verticalList(Alignment alignment, double x, double y, double width, double height,
                             AnchorPane... anchorPanes) {
        for (int i = 0; i < anchorPanes.length; i++) {
            anchorPanes[i].setPrefWidth(width);
            anchorPanes[i].setPrefHeight(height);
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

    public void accountMenu(String player, AnchorPane play, AnchorPane collection, AnchorPane shop, AnchorPane history,
                            AnchorPane leaderboard,
                            AnchorPane logout, AnchorPane customCard, AnchorPane customBuff, AnchorPane save) {
        root.getChildren().clear();
        Label playerName = new Label("Welcome " + player + "!");
        Image background = new Image("scenes/frostfire/background.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image foreground = new Image("scenes/frostfire/foreground.png");
        ImageView foregroundView = getImageView(background, foreground);
        ImageView buttonImage = new ImageView(new Image("ui/button_primary_middle_glow@2x.png"));
        buttonImage.setFitWidth(Constants.PRIMITIVE_WIDTH);
        buttonImage.setFitHeight(Constants.PRIMITIVE_HEIGHT);
        play.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Play", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        collection.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Collection", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        shop.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Shop", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        history.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Match History", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        leaderboard.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "LeaderBoard", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        save.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Save", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        logout.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Logout", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        customCard.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Custom Card", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        customBuff.getChildren().addAll(new ImageButton(new ImageView(buttonImage.getImage()), buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), "Custom Buff", Constants.FONT_SIZE, Color.WHEAT).getPane().getChildren());
        verticalList(Alignment.CENTRE, Constants.ACCOUNT_MENU_X, Constants.CENTRE_Y * 0.9, buttonImage.getFitWidth(),
                buttonImage.getFitHeight(), play, collection, shop, customCard, customBuff, history, leaderboard, save, logout);
        verticalList(Alignment.LEFT, 200, Constants.CENTRE_Y * 0.9, play, collection, shop, customCard,
                customBuff, history, leaderboard, save, logout);
        lightning(play, collection, shop, customCard, customBuff, history, leaderboard, save, logout);
        playerName.translateXProperty().bind(playerName.widthProperty().divide(2).negate());
        playerName.setFont(Font.font(Constants.PAGE_TITLE_FONT, FontWeight.EXTRA_BOLD, Constants.PAGE_TITLE_SIZE));
        playerName.relocate(Constants.CENTRE_X, Constants.PAGE_TITLE_Y);
        playerName.setTextFill(Color.LIGHTPINK);
        root.getChildren().addAll(backgroundView, foregroundView, play, collection, shop, customCard, customBuff,
                history, leaderboard, save, logout, playerName);
    }

    public void customCardMenu(AnchorPane back, AnchorPane next, AnchorPane prev, AnchorPane detail,
                               ComboBox<String> type, TextField name, TextField price, TextField mana) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/vetruvian/bg@2x.jpg"));
        ImageView detailView = new ImageView(new Image("ui/button_icon_middle_glow@2x.png"));
        detail.getChildren().addAll(new ImageButton(detailView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "DETAIL",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        type.getItems().clear();
        type.getItems().addAll("Hero", "Minion", "Spell");
        type.setEditable(false);
        Label nameLabel = new Label("Name");
        Label typeLabel = new Label("Type");
        Label priceLabel = new Label("Price");
        Label manaLabel = new Label("Mana");
        type.relocate(Constants.CUSTOM_CARD_X * 1.1, Constants.CUSTOM_CARD_Y);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, name, price, mana);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, typeLabel, nameLabel,
                priceLabel, manaLabel);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y * 0.4,
                detailView.getFitWidth(), detailView.getFitHeight(), detail);
        scrollPane(backView, next, prev, back);
        lightning(detail);
        root.getChildren().addAll(backView, next, prev, back, type, name, price, mana, nameLabel, typeLabel,
                priceLabel, manaLabel, detail);
    }

    public void customUnitMenu(String type, ComboBox<String> attackType, TextField ap, TextField hp, TextField range,
                               ComboBox<String> target, AnchorPane create) {
        root.getChildren().remove(root.getChildren().size() - 1);
        ImageView createView = new ImageView(new Image("ui/button_icon_middle_glow@2x.png"));
        create.getChildren().addAll(new ImageButton(createView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "CREATE",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        attackType.getItems().clear();
        attackType.getItems().addAll("Melee", "Ranged", "Hybrid");
        attackType.setEditable(false);
        attackType.relocate(Constants.CUSTOM_CARD_X * 2, 6 * Constants.CUSTOM_CARD_Y);
        target.getItems().clear();
        target.getItems().addAll("Hero", "Minion", "Spell");
        target.setEditable(false);
        target.relocate(Constants.CUSTOM_CARD_X * 2 + 2 * Constants.LABEL_WIDTH, 6 * Constants.CUSTOM_CARD_Y);
        Label attackTypeLabel = new Label("AttackType");
        Label apLabel = new Label("AP");
        Label hpLabel = new Label("HP");
        Label rangeLabel = new Label("range");
        Label targetLabel = new Label("Target");
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, 3 * Constants.CUSTOM_CARD_Y, ap, hp, range);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, 3 * Constants.CUSTOM_CARD_Y, apLabel, hpLabel,
                rangeLabel);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y,
                createView.getFitWidth(), createView.getFitHeight(), create);
        horizontalList(Alignment.UP, 2.9 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y * 6, attackTypeLabel
                , targetLabel);
        lightning(create);
        root.getChildren().addAll(attackType, ap, hp, range, target,
                attackTypeLabel, apLabel, hpLabel, rangeLabel, targetLabel, create);
    }

    public void customBuffMenu(AnchorPane back, AnchorPane create, TextField name, ComboBox<String> type,
                               TextField power, TextField turn, ComboBox<String> side, ComboBox<String> attribute) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/vetruvian/bg@2x.jpg"));
        ImageView createView = new ImageView(new Image("ui/button_icon_middle_glow@2x.png"));
        create.getChildren().addAll(new ImageButton(createView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "CREATE",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        type.getItems().clear();
        type.getItems().addAll("Holy", "Poison", "Power", "Weakness", "Stun", "Disarm");
        type.setEditable(false);
        Label nameLabel = new Label("Name");
        Label typeLabel = new Label("Type");
        Label powerLabel = new Label("Power");
        Label turnLabel = new Label("Turn");
        Label sideLabel = new Label("Side");
        Label attributeLabel = new Label("Attribute");
        type.relocate(Constants.CUSTOM_CARD_X * 1.1, Constants.CUSTOM_CARD_Y);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, name, type, power, turn);
        horizontalList(Alignment.UP, 3 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y, nameLabel, typeLabel,
                powerLabel, turnLabel);
        side.getItems().clear();
        side.getItems().addAll("Comrade", "Enemy");
        side.setEditable(false);
        attribute.getItems().clear();
        attribute.getItems().addAll("Health", "Hit", "Attack", "Counter");
        attribute.setEditable(false);
        side.relocate(Constants.CUSTOM_CARD_X * 2, Constants.CUSTOM_CARD_Y * 3);
        attribute.relocate(Constants.CUSTOM_CARD_X * 2 + 2 * Constants.LABEL_WIDTH, Constants.CUSTOM_CARD_Y * 3);
        horizontalList(Alignment.UP, 2.9 * Constants.CUSTOM_CARD_X, Constants.CUSTOM_CARD_Y * 3, sideLabel, attributeLabel);
        verticalList(Alignment.CENTRE, Constants.CENTRE_X, Constants.CENTRE_Y,
                createView.getFitWidth(), createView.getFitHeight(), create);
        lightning(create);
        scrollPane(backView, new AnchorPane(), new AnchorPane(), back);
        root.getChildren().addAll(backView, back, type, name, power, turn, nameLabel, typeLabel, powerLabel, turnLabel,
                side, attribute, sideLabel, attributeLabel, create);
    }

    public void customSpellMenu(ComboBox<String> target) {

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
        // Image background = new Image("scenes/magaari_ember_highlands/magaari_ember_highlands_background@2x.jpg");
        Image background = new Image("scenes/load/scene_load_background@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image firstImage = new Image("play/play_mode_arenagauntlet@2x.jpg");
        ImageView firstImageView = new ImageView(firstImage);
        firstImageView.setFitWidth(425);
        firstImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        firstImageView.setLayoutX(0);
        Image secondImage = new Image("codex/chapter17_preview@2x.jpg");
        ImageView secondImageView = new ImageView(secondImage);
        secondImageView.setFitWidth(425);
        secondImageView.setFitHeight(Constants.WINDOW_HEIGHT);
        secondImageView.setLayoutX(425);
        Image thirdImage = new Image("codex/generic_preview@2x.jpg");
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
        Image background = new Image("scenes/vetruvian/bg@2x.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(Constants.WINDOW_WIDTH);
        backgroundView.setFitHeight(Constants.WINDOW_HEIGHT);
        Image singleP = new Image("crests/crest_f1@2x.png");
        ImageView singlePview = new ImageView(singleP);
        singlePview.setFitWidth(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setFitHeight(Constants.SINGLE_PLAYER_HEIGHT);
        singlePview.setLayoutY(200);
        singlePview.setLayoutX(Constants.SINGLE_PLAYER_HEIGHT);
        Image singleM = new Image("crests/crest_f2@2x.png");
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

    public void shopMenu(Account account, boolean mode, TextField object, ArrayList<Card> cards, ArrayList<Item> items,
                         AnchorPane back, AnchorPane next, AnchorPane prev, AnchorPane sell, AnchorPane buy, int page) {
        root.getChildren().clear();
        ImageView backView = new ImageView(new Image("scenes/load/scene_load_background.jpg"));
        ImageView buyView = new ImageView(new Image("ui/button_confirm_glow@2x.png"));
        ImageView sellView = new ImageView(new Image("ui/button_cancel_glow@2x.png"));
        Label modeLabel, budget;
        if (mode)
            modeLabel = new Label("Shop Objects");
        else
            modeLabel = new Label("Collection Objects");
        budget = new Label("Budget: " + account.getBudget());
        budget.setFont(Font.font(Constants.PAGE_TITLE_FONT, FontWeight.EXTRA_BOLD, Constants.PAGE_TITLE_SIZE));
        budget.setTextFill(Color.DARKGREEN);
        budget.relocate(Constants.SELL_X, 2 * Constants.SELL_HEIGHT);
        object.setPrefWidth(Constants.FIELD_WIDTH);
        object.setPrefHeight(Constants.FIELD_HEIGHT);
        object.relocate(Constants.SELL_X + 0.1 * Constants.SELL_WIDTH + Constants.SELL_WIDTH / 2, 200);
        buy.getChildren().addAll(new ImageButton(buyView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "BUY",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        sell.getChildren().addAll(new ImageButton(sellView, Constants.SELL_WIDTH, Constants.SELL_HEIGHT, "SELL",
                Constants.SELL_TEXT_SIZE, Color.NAVY).getPane().getChildren());
        verticalList(Alignment.LEFT, Constants.SELL_PANE_X, Constants.CENTRE_Y,
                buyView.getFitWidth(), buyView.getFitHeight(), buy, sell);
        scrollPane(backView, next, prev, back);
        lightning(buy);
        lightning(sell);
        root.getChildren().addAll(backView, sell, buy, next, prev, back, object, modeLabel, budget);
        showCards(cards, items, modeLabel, page);
    }


    public void collectionMenu(String mode, TextField object, ArrayList<Card> cards, ArrayList<Item> items
            , AnchorPane createDeck, AnchorPane removeDeck, AnchorPane showDeck, AnchorPane back, AnchorPane collection
            , AnchorPane next, AnchorPane prev, AnchorPane mainDeck, AnchorPane setMainDeck, AnchorPane exportDeck
            , AnchorPane importDeck, int page) {
        root.getChildren().clear();
        Label modeLabel = new Label(mode + " Objects");
        ImageView backView = new ImageView(new Image("scenes/load/scene_load_background.jpg"));
        scrollPane(backView, next, prev, back);
        object.setPrefWidth(Constants.FIELD_WIDTH);
        object.setPrefHeight(Constants.FIELD_HEIGHT);
        object.relocate(Constants.COLLECTION_SEARCH_X, Constants.COLLECTION_SEARCH_Y);
        ImageView deckPane = new ImageView(new Image("card_backgrounds/deck_builder_prismatic_card_bg@2x.png"));
        createDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Create Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        removeDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Remove Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        mainDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Main Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        setMainDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Set as MAIN", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        showDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Show Deck", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        collection.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Collection", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        exportDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Export...", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        importDeck.getChildren().addAll(new ImageButton(new ImageView(deckPane.getImage()), Constants.DECK_PANE_WIDTH,
                Constants.DECK_PANE_HEIGHT, "Import...", Constants.FONT_SIZE, Color.LIGHTBLUE)
                .getPane().getChildren());
        verticalList(Alignment.LEFT, Constants.DECK_PANE_X, Constants.DECK_PANE_Y + Constants.DECK_PANE_HEIGHT
                , Constants.DECK_PANE_WIDTH, Constants.DECK_PANE_HEIGHT, showDeck, setMainDeck, mainDeck
                , createDeck, removeDeck, collection, importDeck, exportDeck);
        lightning(createDeck, removeDeck, mainDeck, setMainDeck, showDeck, collection, importDeck, exportDeck);
        root.getChildren().addAll(backView, modeLabel, next, prev, back, object, showDeck, setMainDeck, mainDeck,
                createDeck, removeDeck, collection, exportDeck, importDeck);
        showCards(cards, items, modeLabel, page);
    }

    private void scrollPane(ImageView backView, AnchorPane next, AnchorPane prev, AnchorPane back) {
        Image slide = new Image("ui/sliding_panel/sliding_panel_paging_button@2x.png");
        Image arrow = new Image("ui/sliding_panel/sliding_panel_paging_button_text@2x.png");
        ImageView leftArrow = new ImageView(), rightArrow = new ImageView();
        Image backArrow = new Image("ui/button_back_corner@2x.png");
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

    private void showCards(ArrayList<Card> cards, ArrayList<Item> items, Label modeLabel, int page) {
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
        modeLabel.setFont(Font.font(Constants.PAGE_TITLE_FONT, FontWeight.EXTRA_BOLD, Constants.PAGE_TITLE_SIZE));
        modeLabel.setTextFill(Color.NAVY);
        modeLabel.translateXProperty().bind(modeLabel.widthProperty().divide(2).negate());
        modeLabel.relocate(Constants.SCROLLER_X, Constants.PAGE_TITLE_Y);
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
            singlePview.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> singlePview.setEffect(colorAdjust));
            singlePview.addEventFilter(MouseEvent.MOUSE_EXITED, e -> singlePview.setEffect(null));
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

    public void setCardImage(String name) {
        Image image = new Image("generals/general_f4.jpg");
        switch (name) {
            case "WHITE_DIV":
                image = new Image("generals/general_f6third.jpg");
                break;
            case "SIMORGH":
                image = new Image("generals/general_f5alt.jpg");
                break;
            case "SEVEN_HEADED_DRAGON":
                image = new Image("generals/general_f5third.jpg");
                break;
            case "RAKHSH":
                image = new Image("generals/general_f3third.jpg");
                break;
            case "ZAHAK":
                image = new Image("generals/general_f2.jpg");
                break;
            case "KAVEH":
                image = new Image("generals/general_f3.jpg");
                break;
            case "ARASH":
                image = new Image("generals/general_f3alt.jpg");
                break;
            case "AFSANEH":
                image = new Image("generals/general_f4.jpg");
                break;
            case "ESFANDIAR":
                image = new Image("generals/general_f6.jpg");
                break;
            case "ROSTAM":
                image = new Image("generals/general_f1.jpg");
                break;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(Constants.CARD_WIDTH);
        imageView.setFitHeight(Constants.CARD_HEIGHT);
    }

}



