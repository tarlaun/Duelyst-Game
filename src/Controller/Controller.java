package Controller;

import Controller.Request.RequestType;
import Model.Menu;
import Model.*;
import View.AlertMessage;
import View.Message;
import Controller.Request.Request;
import View.View;
import com.google.gson.Gson;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Controller {
    private transient Socket socket;
    private transient Formatter formatter;
    private transient BufferedReader reader;
    private transient int port;
    private transient View view = View.getInstance();
    private transient Game game = Game.getInstance();
    private transient Menu menu = Menu.getInstance();
    private transient Shop shop;
    private Account account;
    private transient Battle battle = Battle.getInstance();
    private transient Button[] buttons = new Button[Buttons.values().length];
    private transient PasswordField passwordField = new PasswordField();
    private transient Label[] labels = new Label[Labels.values().length];
    private transient AnchorPane[] anchorPanes = new AnchorPane[Anchorpanes.values().length];
    private transient TextField[] fields = new TextField[Texts.values().length];
    private transient javafx.scene.image.ImageView[] minions = new ImageView[Constants.MINIONS_COUNT];
    private transient ComboBox<String>[] boxes = new ComboBox[Boxes.values().length];
    private transient BattleCards[] heroes = new BattleCards[2];
    private ImageView[] currentImageView = new ImageView[3];
    private transient ImageView[] mana = new ImageView[9];
    private transient ImageView[] handCards = new ImageView[20];
    private ImageView[] imageViews = new ImageView[40];
    private BattleCards[] handCardGifs = new BattleCards[20];
    private BattleCards battleCard = null;
    private Coordinate[] currentCoordinate = new Coordinate[2];
    private static final Controller controller = new Controller();
    private File file = new File("resources/music/music_mainmenu_lyonar.m4a");
    private Media media = new Media(file.toURI().toString());
    private MediaPlayer player = new MediaPlayer(media);
    private Polygon[] polygon = new Polygon[45];
    private int collectionPage = 0, shopPage = 0, graveyardPage = 0;
    private ArrayList<Card> cardsInShop, cardsInCollection;
    private ArrayList<Item> itemsInShop, itemsInCollection;
    private boolean buyMode = true;
    private int currentHandCardPointer = 0;
    private int currentI;
    private String deckName = "Collection";

    private Controller() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
        }
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label();
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new TextField();
        }
        for (int i = 0; i < anchorPanes.length; i++) {
            anchorPanes[i] = new AnchorPane();
        }
        for (int i = 0; i < 20; i++) {
            handCards[i] = new ImageView();
            handCardGifs[i] = new BattleCards();
            ImageView[] imageView = new ImageView[3];
            imageView[0] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            imageView[1] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            imageView[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            handCardGifs[i].setImageView(imageView);
        }
        for (int i = 0; i < 5; i++) {
            handCards[i] = new ImageView();
        }
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
        }
        for (int i = 0; i < 9; i++) {
            mana[i] = new ImageView();
        }
        for (int i = 0; i < polygon.length; i++) {
            polygon[i] = new Polygon();
        }

        for (int i = 0; i < heroes.length; i++) {
            heroes[i] = new BattleCards();
            ImageView[] imageView = new ImageView[3];
            imageView[0] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            imageView[1] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            imageView[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            heroes[i].setImageView(imageView);
        }
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new ComboBox<>();
        }
        menu.setStat(MenuStat.MAIN);
        try {
            socket = new Socket(Constants.IP, Constants.SOCKET_PORT);
            this.formatter = new Formatter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Controller getInstance() {
        return controller;
    }

    public void main() {

        player.stop();
        switch (menu.getStat()) {
            case MAIN:
                view.mainMenu(anchorPanes[Anchorpanes.LOGIN.ordinal()], anchorPanes[Anchorpanes.CREATE_ACCOUNT.ordinal()],
                        anchorPanes[Anchorpanes.EXIT.ordinal()], fields[Texts.USERNAME.ordinal()], passwordField);
                file = new File("resources/music/music_battlemap_vetruv.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case ACCOUNT:
                view.accountMenu(account.getName(), anchorPanes[Anchorpanes.PLAY.ordinal()],
                        anchorPanes[Anchorpanes.COLLECTION.ordinal()], anchorPanes[Anchorpanes.SHOP.ordinal()],
                        anchorPanes[Anchorpanes.MATCH_HISTORY.ordinal()], anchorPanes[Anchorpanes.LEADER_BOARD.ordinal()],
                        anchorPanes[Anchorpanes.LOGOUT.ordinal()], anchorPanes[Anchorpanes.CUSTOM_CARD.ordinal()],
                        anchorPanes[Anchorpanes.CUSTOM_BUFF.ordinal()], anchorPanes[Anchorpanes.SAVE.ordinal()]);
                file = new File("resources/music/music_playmode.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SHOP:
                view.shopMenu(account, buyMode, fields[Texts.OBJECT.ordinal()], cardsInShop, itemsInShop,
                        anchorPanes[Anchorpanes.BACK.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.SELL.ordinal()],
                        anchorPanes[Anchorpanes.BUY.ordinal()], shopPage);
                file = new File("resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                handleInstances(cardsInShop, itemsInShop);
                break;
            case COLLECTION:
                view.collectionMenu(deckName, fields[Texts.OBJECT.ordinal()], cardsInCollection, itemsInCollection,
                        anchorPanes[Anchorpanes.CREATE_DECK.ordinal()], anchorPanes[Anchorpanes.REMOVE_DECK.ordinal()],
                        anchorPanes[Anchorpanes.SHOW_DECk.ordinal()], anchorPanes[Anchorpanes.BACK.ordinal()],
                        anchorPanes[Anchorpanes.WHOLE_COLLECTION.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.MAIN_DECK.ordinal()],
                        anchorPanes[Anchorpanes.SET_MAIN_DECK.ordinal()], anchorPanes[Anchorpanes.EXPORT_DECK.ordinal()],
                        anchorPanes[Anchorpanes.IMPORT_DECK.ordinal()], collectionPage);
                file = new File("resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                try {
                    handleCollection(cardsInCollection, itemsInCollection);
                } catch (Exception ignored) {

                }
                break;
            case CUSTOM_CARD:
                view.customCardMenu(anchorPanes[Anchorpanes.BACK.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.DETAIL.ordinal()],
                        boxes[Boxes.CARD_TYPE.ordinal()], fields[Texts.CARD_NAME.ordinal()],
                        fields[Texts.CARD_PRICE.ordinal()], fields[Texts.MANA.ordinal()]);
                customCardButtons();
                break;
            case CUSTOM_BUFF:
                view.customBuffMenu(anchorPanes[Anchorpanes.BACK.ordinal()], anchorPanes[Anchorpanes.CREATE.ordinal()],
                        fields[Texts.BUFF_NAME.ordinal()], boxes[Boxes.BUFF_TYPE.ordinal()],
                        fields[Texts.BUFF_POWER.ordinal()], fields[Texts.TURN.ordinal()], boxes[Boxes.SIDE.ordinal()],
                        boxes[Boxes.ATTRIBUTE.ordinal()]);
                break;
            case BACK_GROUND:
                imageViews[ImageViews.REDROCK.ordinal()].setImage(new Image("maps/redrock/midground@2x.png"));
                imageViews[ImageViews.VANAR.ordinal()].setImage(new Image("maps/vanar/midground@2x.png"));
                imageViews[ImageViews.SHIMZAR.ordinal()].setImage(new Image("maps/shimzar/midground@2x.png"));
                imageViews[ImageViews.ABYSSIAN.ordinal()].setImage(new Image("maps/abyssian/midground@2x.png"));
                imageViews[ImageViews.PURPLE.ordinal()].setImage(new Image("maps/battlemap4_middleground@2x.png"));
                imageViews[ImageViews.ICE.ordinal()].setImage(new Image("maps/battlemap3_middleground@2x.png"));
                imageViews[ImageViews.METAL.ordinal()].setImage(new Image("maps/battlemap7_middleground@2x.png"));
                imageViews[ImageViews.CANDLE.ordinal()].setImage(new Image("maps/battlemap6_middleground@2x.png"));
                imageViews[ImageViews.CHINA.ordinal()].setImage(new Image("maps/battlemap1_middleground@2x.png"));
                imageViews[ImageViews.OCTA.ordinal()].setImage(new Image("maps/battlemap2_middleground@2x.png"));
                imageViews[ImageViews.LION.ordinal()].setImage(new Image("maps/battlemap0_middleground@2x.png"));
                imageViews[ImageViews.ABYSSIAN.ordinal()].setImage(new Image("maps/abyssian/midground@2x.png"));
                view.backGroundMenu(imageViews[ImageViews.REDROCK.ordinal()], imageViews[ImageViews.VANAR.ordinal()],
                        imageViews[ImageViews.SHIMZAR.ordinal()], imageViews[ImageViews.ABYSSIAN.ordinal()],
                        imageViews[ImageViews.PURPLE.ordinal()], imageViews[ImageViews.OCTA.ordinal()]
                        , imageViews[ImageViews.METAL.ordinal()], imageViews[ImageViews.CHINA.ordinal()],
                        imageViews[ImageViews.ICE.ordinal()], imageViews[ImageViews.CANDLE.ordinal()],
                        imageViews[ImageViews.LION.ordinal()]);
                file = new File("resources/music/music_battlemap_abyssian.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case GAME_TYPE:
                view.gameTypeMenu(buttons[Buttons.SINGLE_PLAYER.ordinal()], buttons[Buttons.MULTI_PLAYER.ordinal()]);
                file = new File("resources/music/music_battlemap_firesofvictory.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case PROCESS:
                break;
            case BATTLE_MODE:
                view.battleMode(buttons[Buttons.KILL_ENEMY_HERO.ordinal()], buttons[Buttons.FLAG_COLLECTING.ordinal()],
                        buttons[Buttons.HOLD_FLAG.ordinal()]);
                file = new File("resources/music/music_battlemap_songhai.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case BATTLE:
                handleMinions();
                for (int i = 0; i < 2; i++) {
                    heroes[i].setCard(battle.getAccounts()[i].getCollection().getMainDeck().getHero());
                    heroes[i].setInside(true);
                    heroes[i].setImageView(getImageViewGif(battle.getAccounts()[i].getCollection().getMainDeck().getHero()));
                }
                for (int i = 0; i < 15; i++) {
                    System.out.println(battle.getAccounts()[0].getCollection().getMainDeck().getCards().size());
                    handCardGifs[i].setInside(false);
                    handCardGifs[i].setCard(battle.getAccounts()[0].getCollection().getMainDeck().getCards().get(i));
                    handCardGifs[i].setImageView(setGifForCards(battle.getAccounts()[0].getCollection().getMainDeck().getCards().get(i)));
                }
/*
                view.battleMenu(battle.getAccounts(), heroes, polygon, imageViews[ImageViews.END_TURN.ordinal()],
                        labels[Labels.END_TURN.ordinal()], mana, handCards, handCardGifs, imageViews[ImageViews.BACKGROUND.ordinal()],
                        imageViews[ImageViews.FOREGROUND.ordinal()], imageViews[ImageViews.back.ordinal()]);
*/
                file = new File("resources/music/music_battlemap01.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SELECT_USER:
                view.selectUserMenu(game.getAccounts(), labels[Labels.STATUS.ordinal()], fields[Texts.USER_NAME.ordinal()]);
                file = new File("resources/music/music_battlemap_abyssian.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case GRAVEYARD:
                view.graveYardMenu(battle.getGraveyard(), anchorPanes[Anchorpanes.NEXT.ordinal()], battle.getTurn(),
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.BACK.ordinal()], graveyardPage);
                break;
            case MATCH_HISTORY:
                Match match = new Match();
                match.setRival("FAKEMATCH");
                match.setResult(MatchResult.WON);
                match.setTime(LocalDateTime.now());
                Match match2 = new Match();
                match2.setRival("NO ONE");
                match2.setResult(MatchResult.TIE);
                match2.setTime(LocalDateTime.now());
                account.getMatchHistory().add(match);
                account.getMatchHistory().add(match2);
                view.matchHistoryMenu(account.getMatchHistory(), anchorPanes[Anchorpanes.BACK.ordinal()]);
                break;
            case ITEM_SELECTION:
                break;
        }
        player.setAutoPlay(true);
        try {
            handlePolygon();
            handleButtons();
            handleTextFields();
            handleHeroGifs();
            handleMinions();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleMinions() {
        for (int i = 0; i < handCardGifs.length; i++) {
            int finalI = i;
            handCardGifs[i].getImageView()[0].setOnMouseClicked(event -> {
                if (battleCard != null && handCardGifs[finalI].isInside() && battleCard.getCard().getId() != handCardGifs[finalI].getCard().getId()) {
                    readyForAttack(finalI, handCardGifs);
                } else {
                    battle.selectCard(handCardGifs[finalI].getCard().getId());
                    currentImageView[0] = handCardGifs[finalI].getImageView()[0];
                    currentImageView[1] = handCardGifs[finalI].getImageView()[1];
                    battleCard = handCardGifs[finalI];
                    currentI = finalI;
                }

                currentCoordinate[0] = new Coordinate((int) handCardGifs[finalI].getImageView()[0].getLayoutX(), (int) handCardGifs[finalI].getImageView()[0].getLayoutY());
            });
        }
    }

    public ImageView[] setGifForCards(Card card) {
        ImageView[] imageViews = new ImageView[3];
        switch (card.getName()) {
            case "PERSIAN_CHAMPION":
            case "PERSIAN_SWORDS_WOMAN":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Alabaster Titan_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Alabaster Titan_idle.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Alabaster Titan_attack.gif"));
                break;
            case "PERSIAN_COMMANDER":
            case "PERSIAN_HORSEMAN":
                //bad
                imageViews[0] = new ImageView(new Image("minionGifs/Spriggin_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Spriggin_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Spriggin_attack.gif"));
                break;
            case "TURANIAN_ARCHER":
            case "CATAPULT_GIANT":
                //motevaset
                imageViews[0] = new ImageView(new Image("minionGifs/Sunrise Cleric_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Sunrise Cleric_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Sunrise Cleric_attack.gif"));
                break;
            case "VENOM_SNAKE":
            case "TURANIAN_SPY":
                //motevaset
                imageViews[0] = new ImageView(new Image("minionGifs/Worldcore Golem_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Worldcore Golem_attack.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Worldcore Golem_attack.gif"));
                break;
            case "TURANIAN_CATAPULT":
            case "TURANIAN_LANCER":
                //tek
                imageViews[0] = new ImageView(new Image("minionGifs/Blood Taura_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Blood Taura_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Blood Taura_attack.gif"));
                break;
            case "PERSIAN_LANCER":
            case "TURANIAN_PRINCE":
                //motevaset
                imageViews[0] = new ImageView(new Image("minionGifs/Riftwalker_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Riftwalker_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Riftwalker_attack.gif"));
                break;

            case "HOG_RIDER_GIANT":
            case "CYCLOPS":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Blood Taura_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Blood Taura_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Blood Taura_attack.gif"));
                break;
            case "TWO_HEADED_GIANT":
            case "GONDE_BACK_GIANT":
                //bad
                imageViews[0] = new ImageView(new Image("minionGifs/Furiosa_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Furiosa_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Furiosa_attack.gif"));
                break;
            case "EAGLE":
            case "FOOLADZEREH":
            case "WOLF":
                //bad
                imageViews[0] = new ImageView(new Image("minionGifs/Elkowl_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Elkowl_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Elkowl_attack.gif"));
                break;
            case "WHITE_WOLF":
            case "DRAGON":
                ///khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Katastrophosaurus_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Katastrophosaurus_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Katastrophosaurus_attack.gif"));
                break;
            case "NANE_WITCH":
            case "GIANT_SNAKE":
                //tek
                imageViews[0] = new ImageView(new Image("minionGifs/Katastrophosaurus_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Katastrophosaurus_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Katastrophosaurus_attack.gif"));
                break;
            case "LION":
            case "PALANG":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Azurite Lion_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Azurite Lion_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Azurite Lion_attack.gif"));
                break;
            case "WITCH":
            case "WILD_HOG":
                //motevaset
            case "TURANIAN_MACER":
                imageViews[0] = new ImageView(new Image("minionGifs/Pandora_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Pandora_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Pandora_attack.gif"));
                break;
            case "JEN":
            case "PIRAN":
            case "GIV":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Cryptographer_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Cryptographer_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Cryptographer_attack.gif"));
                break;

            case "BAHMAN":
            case "PERSIAN_ARCHER":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Silverbeak_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Silverbeak_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Silverbeak_attack.gif"));
                break;
            case "ASHKBOOS":
            case "IRAJ":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Fog_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Fog_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Fog_attack.gif"));
                break;
            case "NANE_SARMA":
            case "SIAVASH":
            case "SHAGHUL":
                //khoob
                imageViews[0] = new ImageView(new Image("minionGifs/Healing Mystic_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Healing Mystic_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Healing Mystic_attack.gif"));
                break;

            case "ARZHANG":
            case "BLACK_GIANT":
                //bad
                imageViews[0] = new ImageView(new Image("minionGifs/Kin_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Kin_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Kin_attack.gif"));
                break;
            case "TOTAL_DISARM":
            case "SACRIFICE":
                imageViews[0] = new ImageView(new Image("spell/Flash Freeze_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Flash Freeze_active.gif"));
                imageViews[2] = new ImageView(new Image("spell/Flash Freeze_active.gif"));
                break;
            case "EMPOWER":
            case "FIREBALL":
            case "GOD_STRENGTH":
                imageViews[0] = new ImageView(new Image("spell/Unleash the Evil_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Unleash the Evil_active.gif"));
                imageViews[2] = new ImageView(new Image("spell/Unleash the Evil_active.gif"));
                break;
            case "HELL_FIRE":
            case "LIGHTNING_BOLT":
            case "POISON_LAKE":
                imageViews[0] = new ImageView(new Image("spell/Lasting Judgement_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Lasting Judgement_active.gif"));
                imageViews[2] = new ImageView(new Image("spell/Lasting Judgement_active.gif"));
                break;
            case "MADNESS":
            case "ALL_DISARM":
            case "ALL_POISON":
                imageViews[0] = new ImageView(new Image("spell/Icebreak Ambush_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Icebreak Ambush_active.giff"));
                imageViews[2] = new ImageView(new Image("spell/Icebreak Ambush_active.gif"));
                break;
            case "DISPEL":
            case "ALL_ATTACK":
            case "POWER_UP":
                imageViews[0] = new ImageView(new Image("spell/Horrific Visage_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Horrific Visage_active.gif"));
                imageViews[2] = new ImageView(new Image("spell/Horrific Visage_active.gif"));
                break;
            case "ALL_POWER":
            case "HEALTH_WITH_PROFIT":
            case "WEAKENING":
                imageViews[0] = new ImageView(new Image("spell/Homeostatic Rebuke_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Homeostatic Rebuke_active.gif"));
                imageViews[2] = new ImageView(new Image("spell/Homeostatic Rebuke_active.gif"));
                break;
            case "KINGS_GUARD":
            case "SHOCK":
            case "AREA_DISPEL":
                imageViews[0] = new ImageView(new Image("spell/Aspect of Shim'Zar_active.gif"));
                imageViews[1] = new ImageView(new Image("spell/Aspect of Shim'Zar_active.gif"));
                imageViews[2] = new ImageView(new Image("spell/Aspect of Shim'Zar_active.gif"));
                break;
        }
        return imageViews;
    }

    private void mainButtons() {
        anchorPanes[Anchorpanes.CREATE_ACCOUNT.ordinal()].setOnMouseClicked(event -> createAccount());
        anchorPanes[Anchorpanes.LOGIN.ordinal()].setOnMouseClicked(event -> login());
        anchorPanes[Anchorpanes.EXIT.ordinal()].setOnMouseClicked(event -> exit());
    }

    private void accountButtons() {
        anchorPanes[Anchorpanes.PLAY.ordinal()].setOnMouseClicked(event -> chooseBattleType());
        anchorPanes[Anchorpanes.LOGOUT.ordinal()].setOnMouseClicked(event -> logout());
        anchorPanes[Anchorpanes.LEADER_BOARD.ordinal()].setOnMouseClicked(event -> showLeaderBoard());
        anchorPanes[Anchorpanes.SHOP.ordinal()].setOnMouseClicked(event -> {
            fetchShop();
            menu.setStat(MenuStat.SHOP);
            main();
        });
        anchorPanes[Anchorpanes.COLLECTION.ordinal()].setOnMouseClicked(event -> {
            cardsInCollection = account.getCollection().getCards();
            itemsInCollection = account.getCollection().getItems();
            menu.setStat(MenuStat.COLLECTION);
            main();
        });
        anchorPanes[Anchorpanes.CUSTOM_CARD.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.CUSTOM_CARD);
            main();
        });
        anchorPanes[Anchorpanes.CUSTOM_BUFF.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.CUSTOM_BUFF);
            main();
        });
        anchorPanes[Anchorpanes.MATCH_HISTORY.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.MATCH_HISTORY);
            main();
        });
        anchorPanes[Anchorpanes.SAVE.ordinal()].setOnMouseClicked(event -> {
            save();
            main();
        });
    }

    private void scrollerButtons() {
        anchorPanes[Anchorpanes.BACK.ordinal()].setOnMouseClicked(event -> exit());
        anchorPanes[Anchorpanes.PREV.ordinal()].setOnMouseClicked(event -> {
            if (menu.getStat() == MenuStat.COLLECTION && collectionPage > 0) {
                collectionPage--;
                main();
            }
            if (menu.getStat() == MenuStat.SHOP && shopPage > 0) {
                shopPage--;
                main();
            }
        });
        anchorPanes[Anchorpanes.NEXT.ordinal()].setOnMouseClicked(event -> {
            if (menu.getStat() == MenuStat.COLLECTION &&
                    collectionPage < (cardsInCollection.size() + itemsInCollection.size())
                            / Constants.CARD_PER_PAGE) {
                collectionPage++;
                main();
            }
            if (menu.getStat() == MenuStat.SHOP && shopPage < (cardsInShop.size() + itemsInShop.size())
                    / Constants.CARD_PER_PAGE) {
                shopPage++;
                main();
            }
        });
    }

    private void customCardButtons() {
        anchorPanes[Anchorpanes.DETAIL.ordinal()].setOnMouseClicked(event -> {
            try {
                if (boxes[Boxes.CARD_TYPE.ordinal()].getValue().equals("Spell")) {
                    view.customSpellMenu(boxes[Boxes.TARGET.ordinal()]);
                } else {
                    view.customUnitMenu(boxes[Boxes.CARD_TYPE.ordinal()].getValue(), boxes[Boxes.ATTACK_TYPE.ordinal()],
                            fields[Texts.AP.ordinal()], fields[Texts.HP.ordinal()], fields[Texts.RANGE.ordinal()],
                            boxes[Boxes.TARGET.ordinal()], anchorPanes[Anchorpanes.CREATE.ordinal()]);
                }
            } catch (Exception e) {
                AlertMessage alert = new AlertMessage("Invalid arguments", Alert.AlertType.ERROR, "OK");
                alert.getResult();
            }
        });
    }

    private void customButtons() {
        anchorPanes[Anchorpanes.CREATE.ordinal()].setOnMouseClicked(event -> {
            try {
                switch (menu.getStat()) {
                    case CUSTOM_CARD:
                        createCard();
                        break;
                    case CUSTOM_BUFF:
                        createBuff();
                        break;
                }
                AlertMessage alert = new AlertMessage("Successful creation", Alert.AlertType.INFORMATION,
                        "OK");
                alert.getResult();
            } catch (Exception e) {
                AlertMessage alert = new AlertMessage("Invalid arguments", Alert.AlertType.ERROR, "OK");
                alert.getResult();
            }
        });
    }

    private void shopButtons() {
        anchorPanes[Anchorpanes.BUY.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = shop.getCards();
            itemsInShop = shop.getItems();
            shopPage = 0;
            buyMode = true;
            main();
        });
        anchorPanes[Anchorpanes.SELL.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = account.getCollection().getCards();
            itemsInShop = account.getCollection().getItems();
            shopPage = 0;
            buyMode = false;
            main();
        });
    }

    private void collectionButtons() {
        anchorPanes[Anchorpanes.SET_MAIN_DECK.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.SELECT_MAIN_CONST);
            main();
        });
        anchorPanes[Anchorpanes.MAIN_DECK.ordinal()].setOnMouseClicked(event -> {
            deckName = account.getCollection().getMainDeck().getName();
            cardsInCollection = account.getCollection().getMainDeck().getCards();
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.add(account.getCollection().findDeck(deckName).getItem());
            itemsInCollection = itemList;
            collectionPage = 0;
            main();
        });
        anchorPanes[Anchorpanes.WHOLE_COLLECTION.ordinal()].setOnMouseClicked(event -> {
            deckName = "Collection";
            cardsInCollection = account.getCollection().getCards();
            itemsInCollection = account.getCollection().getItems();
            main();
        });
        anchorPanes[Anchorpanes.SHOW_DECk.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.SHOW_DECK_CONST);
            main();
        });
        anchorPanes[Anchorpanes.CREATE_DECK.ordinal()].setOnMouseClicked(event -> {
            createDeck();
            main();
        });
        anchorPanes[Anchorpanes.EXPORT_DECK.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.EXPORT_DECK);
            main();
        });
        anchorPanes[Anchorpanes.IMPORT_DECK.ordinal()].setOnMouseClicked(event -> {
            importDeck();
            main();
        });
        anchorPanes[Anchorpanes.REMOVE_DECK.ordinal()].setOnMouseClicked(event -> {
            deckLing(Constants.REMOVE_DECK);
            main();
        });
    }

    private void handleHeroGifs() {
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            heroes[i].getImageView()[0].setOnMouseClicked(event -> {
                if (battleCard != null && battleCard.getCard().getId() != heroes[finalI].getCard().getId()) {
                    readyForAttack(finalI, heroes);
                } else {
                    battle.selectCard(heroes[finalI].getCard().getId());
                    currentImageView[0] = heroes[finalI].getImageView()[0];
                    currentImageView[1] = heroes[finalI].getImageView()[1];
                    System.out.println("hero entekhab shod");
                    battleCard = heroes[finalI];
                    currentI = finalI;
                }
                currentCoordinate[0] = null;
            });
        }
    }


    private void readyForAttack(int finalI, BattleCards[] heroes) {
        battle.attack(heroes[finalI].getCard().getId(), battleCard.getCard());
        currentImageView[0] = battleCard.getImageView()[0];
        currentImageView[1] = battleCard.getImageView()[1];
        currentImageView[2] = battleCard.getImageView()[2];
        view.attack(currentImageView);
        System.out.println("hamle");
        battleCard = null;
    }

    private void handlePolygon() {
        for (int i = 0; i < polygon.length; i++) {
            int a = i;
            polygon[i].setOnMouseClicked(event -> {
                view.move(polygon[a].getPoints().get(0), polygon[a].getPoints().get(1), currentImageView[0], currentImageView[1]);
                battleCard = null;
                battle.moveTo(new Coordinate(a - (a / 9), a / 9));
                if (currentCoordinate[0] != null) {
                    handCardGifs[currentI].setInside(true);
                    currentHandCardPointer++;
                    if (currentHandCardPointer + 4 < 15) {
                        view.handView(currentCoordinate, handCardGifs[currentHandCardPointer + 4]);
                    }
                }
            });
        }
    }

    private void handleButtons() {
        mainButtons();
        accountButtons();
        scrollerButtons();
        shopButtons();
        collectionButtons();
        anchorPanes[Anchorpanes.GRAVEYARD.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.GRAVEYARD);
            main();
        });

        imageViews[ImageViews.REDROCK.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/redrock/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/redrock/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.ABYSSIAN.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/abyssian/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/abyssian/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.SHIMZAR.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/shimzar/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/shimzar/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.VANAR.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/vanar/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/vanar/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.LION.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap0_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap0_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.CHINA.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap1_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap1_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.OCTA.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap2_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap2_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.ICE.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap3_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap3_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.PURPLE.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap4_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap4_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.CANDLE.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap6_middleground@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap6_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.METAL.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("maps/battlemap7_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("maps/battlemap7_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        buttons[Buttons.SHOP.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = shop.getCards();
            itemsInShop = shop.getItems();
            menu.setStat(MenuStat.SHOP);
            main();
        });
        imageViews[ImageViews.back.ordinal()].setOnMouseClicked(event -> {
            menu.setStat(MenuStat.BACK_GROUND);
            main();

        });
        buttons[Buttons.SINGLE_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeSingle());
        buttons[Buttons.MULTI_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeMulti());
        buttons[Buttons.KILL_ENEMY_HERO.ordinal()].setOnMouseClicked(event -> setBattleMode(1));
        buttons[Buttons.FLAG_COLLECTING.ordinal()].setOnMouseClicked(event -> setBattleMode(2));
        buttons[Buttons.HOLD_FLAG.ordinal()].setOnMouseClicked(event -> setBattleMode(3));
    }

    private void handleTextFields() {
        fields[Texts.USER_NAME.ordinal()].setOnAction(event -> selectUser(fields[Texts.USER_NAME.ordinal()].getText()));
        fields[Texts.OBJECT.ordinal()].setOnKeyReleased(event -> {
            if (menu.getStat() == MenuStat.SHOP) {
                shopPage = 0;
                if (buyMode) {
                    cardsInShop = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(), shop.getCards());
                    itemsInShop = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(), shop.getItems());
                } else {
                    cardsInShop = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                            account.getCollection().getCards());
                    itemsInShop = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                            account.getCollection().getItems());
                }
            }
            if (menu.getStat() == MenuStat.COLLECTION) {
                try {
                    collectionPage = 0;
                    if (deckName.equals("Collection")) {
                        cardsInCollection = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                                account.getCollection().getCards());
                        itemsInCollection = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                                account.getCollection().getItems());
                    } else {
                        cardsInCollection = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                                account.getCollection().findDeck(deckName).getCards());
                        if (fields[Texts.OBJECT.ordinal()].getText().matches(account.getCollection().
                                findDeck(deckName).getItem().getName())) {
                            ArrayList<Item> itemList = new ArrayList<>();
                            itemList.add(account.getCollection().findDeck(deckName).getItem());
                            itemsInCollection = itemList;
                        } else {
                            itemsInCollection = new ArrayList<>();
                        }
                    }
                } catch (NullPointerException ignored) {

                }
            }
            main();
        });
    }

    public void handleCollection(ArrayList<Card> cards, ArrayList<Item> items) {
        for (int i = 0; i < cards.size(); i++) {
            int finalI = i;
            cards.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                deckLing(cards.get(finalI).getId());
                main();
            });
        }
        for (int i = 0; i < items.size(); i++) {
            int finalI = i;
            items.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                deckLing(items.get(finalI).getId());
                main();
            });
        }
    }

    private void deckLing(int id) {
        List<String> deckNames = new ArrayList<>();
        for (Deck deck : account.getCollection().getDecks()) {
            deckNames.add(deck.getName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(account.getCollection().getMainDeck().getName(), deckNames);
        if (id == Constants.SELECT_MAIN_CONST) {
            dialog.setHeaderText("Set the Main");
            dialog.setContentText("Deck... ");
        } else if (id == Constants.SHOW_DECK_CONST) {
            dialog.setHeaderText("Show objects of");
            dialog.setContentText("Deck... ");
        } else if (id == Constants.EXPORT_DECK) {
            dialog.setHeaderText("Export");
            dialog.setContentText("Deck... ");
        } else if (id == Constants.REMOVE_DECK) {
            dialog.setHeaderText("Delete");
            dialog.setContentText("Deck... ");
        } else {
            if (deckName.equals("Collection")) {
                dialog.setHeaderText("Adding to");
                dialog.setContentText("Deck... ");
            } else {
                dialog.setHeaderText("Removeing from");
                dialog.setContentText("Deck... ");
            }
        }
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (id == Constants.SELECT_MAIN_CONST) {
                if (account.getCollection().selectDeck(name)) {
                    AlertMessage alert = new AlertMessage("Main Deck is " + name,
                            Alert.AlertType.INFORMATION, "OK");
                    alert.getResult();
                } else {
                    AlertMessage alert = new AlertMessage("The " + name + " is already the Main Deck",
                            Alert.AlertType.ERROR, "OK");
                    alert.getResult();
                }
            } else if (id == Constants.SHOW_DECK_CONST) {
                setObjectsOfDeck(name);
            } else if (id == Constants.EXPORT_DECK) {
                account.getCollection().exportDeck(name);
                AlertMessage alert = new AlertMessage("Deck exported!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
            } else if (id == Constants.REMOVE_DECK) {
                deleteDeck(name);
                AlertMessage alert = new AlertMessage("Deck deleted!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
            } else {
                if (deckName.equals("Collection")) {
                    Message message = addToDeck(name, id);
                    if (message == Message.OBJECT_ADDED) {
                        AlertMessage alert = new AlertMessage("Card added!", Alert.AlertType.INFORMATION, "OK");
                        alert.getResult();
                    } else {
                        AlertMessage alert = new AlertMessage(message.toString(), Alert.AlertType.ERROR, "OK");
                        alert.getResult();
                    }
                } else {
                    Message message = removeFromDeck(name, id);
                    if (message == Message.SUCCESSFUL_REMOVE) {
                        AlertMessage alert = new AlertMessage("Card removed!", Alert.AlertType.INFORMATION, "OK");
                        alert.getResult();
                    } else {
                        AlertMessage alert = new AlertMessage(message.toString(), Alert.AlertType.ERROR, "OK");
                        alert.getResult();
                    }
                }
            }
        });
    }

    private void deleteDeck(String name) {
        this.account.getCollection().deleteDeck(name);
    }

    private void setObjectsOfDeck(String name) {
        Deck deck = account.getCollection().findDeck(name);
        if (deck.getCards() != null)
            cardsInCollection = account.getCollection().findDeck(name).getCards();
        if (deck.getItem() != null) {
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.add(account.getCollection().findDeck(name).getItem());
            itemsInCollection = itemList;
        }
        collectionPage = 0;
        deckName = name;
    }

    private void importDeck() {
        Stage stage = new Stage();
        stage.setTitle("Import Deck..");
        FileChooser chooser = new FileChooser();
        try {
            File file = chooser.showOpenDialog(stage);
            FileReader reader = new FileReader(file);
            Deck deck = new Gson().fromJson(reader, Deck.class);
            reader.close();
            for (Card card : deck.getCards()) {
                card.setCardView();
            }
            if (deck.getItem() != null) {
                deck.getItem().setCardView();
            }
            account.getCollection().getDecks().add(deck);
            setObjectsOfDeck(deck.getName());
            collectionPage = 0;
        } catch (Exception ignored) {

        }
    }

    private void createDeck() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Create Deck");
        dialog.setContentText("Name... ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (account.getCollection().createDeck(name)) {
                AlertMessage alert = new AlertMessage("Deck created!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
                cardsInCollection = new ArrayList<>();
                itemsInCollection = new ArrayList<>();
                collectionPage = 0;
                deckName = name;
            } else {
                AlertMessage alert = new AlertMessage(Message.EXISTING_DECK.toString(), Alert.AlertType.ERROR, "OK");
                alert.getResult();
            }
        });
    }

    private void handleInstances(ArrayList<Card> cards, ArrayList<Item> items) {
        for (int i = 0; i < cards.size(); i++) {
            int finalI = i;
            cards.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                if (buyMode) {
                    handleBuy(account.getBudget(), cards.get(finalI).getName(), cards.get(finalI).getPrice());
                } else {
                    handleSell(cards.get(finalI).getId());
                }
                main();
            });
        }
        for (int i = 0; i < items.size(); i++) {
            int finalI = i;
            items.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                if (buyMode) {
                    if (items.get(finalI).getPrice() == 0) {
                        AlertMessage alert;
                        alert = new AlertMessage("You cannot buy collectible item!", Alert.AlertType.ERROR, "OK");
                        alert.getResult();
                    } else {
                        handleBuy(account.getBudget(), items.get(finalI).getName(), items.get(finalI).getPrice());
                    }
                } else {
                    handleSell(items.get(finalI).getId());
                }
                main();
            });
        }
    }

    private void handleBuy(int budget, String name, int price) {
        AlertMessage alert;
        if (budget < price) {
            alert = new AlertMessage("Insufficient budget!", Alert.AlertType.ERROR, "OK");
            alert.getResult();
        } else {
            alert = new AlertMessage("It will cost " + price + " Drigs",
                    Alert.AlertType.CONFIRMATION, "OK", "Cancel");
            Optional<ButtonType> result = alert.getResult();
            if (result.isPresent()) {
                switch (result.get().getText()) {
                    case "OK":
                        buy(name);
                        break;
                }
            }
        }
    }

    private void handleSell(int id) {
        AlertMessage alert;
        alert = new AlertMessage("Are you sure to sell it?", Alert.AlertType.CONFIRMATION,
                "Yes", "No");
        Optional<ButtonType> result = alert.getResult();
        if (result.isPresent()) {
            switch (result.get().getText()) {
                case "Yes":
                    sell(id);
                    break;
            }
        }
    }

    private void setBattleMode(int a) {
        switch (a) {
            case 1:
                battle.setMode(BattleMode.KILLENEMYHERO);
                break;
            case 2:
                battle.setMode(BattleMode.COLLECTING);
                break;
            case 3:
                battle.setMode(BattleMode.FLAG);
                break;
        }
        if (battle.getGameType().equals(GameType.SINGLEPLAYER)) {
            menu.setStat(MenuStat.BACK_GROUND);
            Account[] accounts = new Account[2];
            accounts[0] = account;
            for (int i = 0; i < game.getAccounts().size(); i++) {
                if (game.getAccounts().get(i).getName().equals("powerfulAI")) {
                    accounts[1] = game.getAccounts().get(i);
                }
            }
            battle.setAccounts(accounts);
            setMainDeckForAI();
            battle.startBattle();
        } else {
            menu.setStat(MenuStat.SELECT_USER);
        }
        main();
    }

    private void chooseBattleType() {
        menu.setStat(MenuStat.GAME_TYPE);
        main();
    }

    private void AiFunctions() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            System.out.println(battle.getAccounts()[1].getBudget());
            moveAI();
            if (battle.getMode().equals(BattleMode.COLLECTING)) {
                if (battle.checkForWin()) {
//                    menu.setStat(MenuStat.GAME);
                    //view.Success();
                }
            }
            // insertAI();
            System.out.println(battle.getFieldCards()[0][0].getHealthPoint());
            //attackAI();
            if (battle.getAccounts()[0].getCollection().getMainDeck().getHero().getHealthPoint() < 0) {
                System.exit(2);
            }
            endTurn();
        }
    }

    private void selectUser(String name) {
        Account accountt = Account.getAccountByName(name, game.getAccounts());
        if (accountt != null) {
            battle.setAccounts(account, accountt);
            menu.setStat(MenuStat.BACK_GROUND);
            battle.startBattle();
        }
        main();
    }


    private void setBattleModeSingle() {
        battle.setGameType(GameType.SINGLEPLAYER);
        menu.setStat(MenuStat.BATTLE_MODE);
        main();
    }

    private void setBattleModeMulti() {
        battle.setGameType(GameType.MULTIPLAYER);
        menu.setStat(MenuStat.BATTLE_MODE);
        main();
    }


    private void setMainDeckForAI() {
        if (battle.getMode().equals(BattleMode.KILLENEMYHERO)) {
            battle.getAccounts()[1].getCollection().selectDeck("level1");
        }
        if (battle.getMode().equals(BattleMode.FLAG)) {
            battle.getAccounts()[1].getCollection().selectDeck("level2");
        }
        if (battle.getMode().equals(BattleMode.COLLECTING)) {
            battle.getAccounts()[1].getCollection().selectDeck("level3");
        }
    }

    private void moveAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                if (battle.getFieldCards()[1][i] != null) {
                    battle.setCurrentCard(battle.getFieldCards()[1][i]);
                    battle.moveTo(battle.setDestinationCoordinate(battle.getFieldCards()[1][i]));
                }
            }
        }
    }

    private ImageView[] getImageViewGif(Card card) {
        ImageView[] imageViews = new ImageView[3];
        switch (card.getName()) {
            case "WHITE_DIV":
                imageViews[1] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
                imageViews[0] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
                break;
            case "ZAHAK":
                imageViews[0] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
                break;
            case "ARASH":
                imageViews[1] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[0] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "SIMORGH":
                imageViews[0] = new ImageView(new Image("gifs/gifs/f4_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/f4_altgeneraltier2_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/f4_altgeneraltier2_idle.gif"));
                break;
            case "SEVEN_HEADED_DRAGON":
                imageViews[0] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "RAKHSH":
                imageViews[0] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
                break;
            case "KAVEH":
                imageViews[0] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            case "AFSANEH":
                imageViews[0] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "ESFANDIAR":
                imageViews[0] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "ROSTAM":
                imageViews[0] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/f5_altgeneraltier2_idle.gif"));
                break;
        }
        return imageViews;
    }


    private void attackAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                for (int j = 0; j < battle.getFieldCards()[0].length; j++) {
                    if (battle.getFieldCards()[0][j] != null && battle.getFieldCards()[1][i] != null) {
                        battle.attack(battle.getFieldCards()[0][j].getId(), battle.getFieldCards()[1][i]);
                        if (battle.getFieldCards()[1][i].getType().equals("Hero")) {
                            if (battle.AIAssaultTypeBasedInsertion(i, j)) break;
                        }
                    }
                }
            }
            int counter = 0;
            for (int i = 0; i < battle.getFieldCards()[0].length; i++) {
                if (battle.getFieldCards()[0][i] != null) {
                    counter++;
                }
            }
            if (counter == 0) {
                //view.showAttack(Message.BATTLE_FINISHED);
            }
        }
    }

    private void insertAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            ArrayList<Card> cards = convertArrayToList(battle.getPlayerHands()[1]);
            //estefade az se tabe baraye piadesazi insertion ai
            battle.insertCard(battle.setCardCoordinates(), battle.chooseCard(cards).getName());
        }
    }

    private ArrayList<Card> convertArrayToList(Card[] cards) {
        return new ArrayList<>(Arrays.asList(cards));
    }

    private void send(Request request) {
        formatter.format(request.toJson() + "\n");
        formatter.flush();
    }


    private void createAccount() {
        String username = fields[Texts.USERNAME.ordinal()].getText();
        String password = passwordField.getText();
        Request request = new Request(Constants.SOCKET_PORT, RequestType.CREATE_ACCOUNT, username, password);
        send(request);
        Message message = null;
        String line = null;
        try {
            line = reader.readLine();
            message = Message.fromJson(line);
            switch (message) {
                case EXISTING_ACCOUNT:
                    AlertMessage alert1 = new AlertMessage("An account with this name already exists!", Alert.AlertType.ERROR, "OK");
                    alert1.getResult();
                    break;
                case INAPPROPRIATE_PASSWORD:
                    AlertMessage alert2 = new AlertMessage("You should choose a valid username and passowrd!", Alert.AlertType.ERROR, "OK");
                    alert2.getResult();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            this.account = Account.fromJson(line);
            menu.setStat(MenuStat.ACCOUNT);
            main();
        }
    }

    private String getOpponentName(Account account) {
        for (int i = 0; i < battle.getAccounts().length; i++) {
            if (!battle.getAccounts()[i].getName().equals(account.getName())) {
                return battle.getAccounts()[i].getName();
            }
        }
        return null;
    }

    private void login() {
        String username = fields[Texts.USERNAME.ordinal()].getText();
        String password = passwordField.getText();
        Request request = new Request(Constants.SOCKET_PORT, RequestType.LOGIN, username, password);
        send(request);
        Message message = null;
        String line = null;
        try {
            line = reader.readLine();
            message = Message.fromJson(line);
            switch (message) {
                case INVALID_PASSWORD:
                    AlertMessage alertMessage = new AlertMessage("Incorrect Password", Alert.AlertType.ERROR, "OK");
                    alertMessage.getResult();
                    break;
                case INVALID_ACCOUNT:
                    alertMessage = new AlertMessage("An account with this username doesn't exist !", Alert.AlertType.ERROR, "OK");
                    alertMessage.getResult();
                    break;
                case ALREADY_LOGGED_IN:
                    alertMessage = new AlertMessage("This account is already logged-in !", Alert.AlertType.ERROR, "OK");
                    alertMessage.getResult();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            this.account = Account.fromJson(line);
            setCardViews(account);
            menu.setStat(MenuStat.ACCOUNT);
            main();
        }
    }

    private void setCardViews(Account account) {
        for (Card card : account.getCollection().getCards()) {
            card.setCardView();
            for (Deck deck : account.getCollection().getDecks()) {
                try {
                    deck.findCardInDeck(card.getId()).setCardView();
                } catch (NullPointerException ignored) {

                }
            }
        }
        for (Item item : account.getCollection().getItems()) {
            item.setCardView();
            for (Deck deck : account.getCollection().getDecks()) {
                try {
                    deck.findItemInDeck(item.getId()).setCardView();
                } catch (NullPointerException ignored) {

                }
            }
        }
    }

    private void setCardViews(Shop shop) {
        for (Card card : shop.getCards()) {
            card.setCardView();
        }
        for (Item item : shop.getItems()) {
            item.setCardView();
        }
    }

    private void showLeaderBoard() {
        game.sortAccounts();
        view.printLeaderboard(game.getAccounts());
    }

    private void save() {
        Request request = new Request(Constants.SOCKET_PORT, RequestType.SAVE, account.toJson());
        send(request);
        try {
            if (Message.fromJson(reader.readLine()) == Message.SUCCESSFUL_SAVE) {
                AlertMessage alert = new AlertMessage("Saved successfully!", Alert.AlertType.INFORMATION, "OK");
                alert.getResult();
            } else {
                AlertMessage damn = new AlertMessage("Saving failed", Alert.AlertType.INFORMATION, "OK");
                damn.getResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        AlertMessage alert = new AlertMessage("Do you want to save your info?", Alert.AlertType.CONFIRMATION,
                "Yes", "No");
        Optional<ButtonType> result = alert.getResult();
        if (result.isPresent()) {
            switch (result.get().getText()) {
                case "Yes":
                    save();
                default:
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.LOGOUT, account.getName());
                    send(request);
                    try {
                        if (Message.fromJson(reader.readLine()) == Message.SUCCESSFUL_LOGOUT) {
                            account = null;
                            menu.setStat(MenuStat.MAIN);
                            main();
                        } else {
                            AlertMessage damn = new AlertMessage("Logout failed", Alert.AlertType.INFORMATION, "OK");
                            damn.getResult();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }


    private void exit() {
        menu.exitMenu();
        main();
    }


    private Message addToDeck(String deckName, int id) {
        return this.account.getCollection().add(deckName, id);
    }

    private Message removeFromDeck(String name, int id) {
        return this.account.getCollection().remove(name, id);
    }

    private void validateDeck(Request request) {
    }

    private void fetchShop() {
        Request request = new Request(Constants.SOCKET_PORT, RequestType.SHOP);
        send(request);
        try {
            shop = Shop.fromJson(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCardViews(shop);
        cardsInShop = shop.getCards();
        itemsInShop = shop.getItems();
    }

    private void buy(String name) {
        Request request = new Request(Constants.SOCKET_PORT, RequestType.BUY, name, account.toJson());
        send(request);
        Message message;
        String line = null;
        try {
            line = reader.readLine();
            message = Message.fromJson(line);
            AlertMessage alert = new AlertMessage(message.toString(), Alert.AlertType.ERROR, "OK");
            alert.getResult();
        } catch (IOException ignored) {

        } catch (Exception isCard) {
            account = Account.fromJson(line);
            setCardViews(account);
            AlertMessage alert = new AlertMessage(Message.SUCCESSFUL_PURCHASE.toString(), Alert.AlertType.INFORMATION
                    , "OK");
            alert.getResult();
            fetchShop();
        }
    }

    private void sell(int id) {
        Request request;
        try {
            Card card = Card.getCardByID(id, account.getCollection().getCards().toArray(new Card[0]));
            request = new Request(Constants.SOCKET_PORT, RequestType.BUY, card.toJson(), account.toJson());
        } catch (Exception e) {
            Item item = Item.getItemByID(id, account.getCollection().getItems().toArray(new Item[0]));
            request = new Request(Constants.SOCKET_PORT, RequestType.BUY, item.toJson(), account.toJson());
        }
        send(request);
        try {
            account = Account.fromJson(reader.readLine());
            AlertMessage alert = new AlertMessage(Message.SUCCESSFUL_SELL.toString(), Alert.AlertType.INFORMATION
                    , "OK");
            alert.getResult();
            fetchShop();
        } catch (Exception ignored) {
        }
    }

    private void gameInfo() {
    }

    private void showCardInfo(Request request) {
    }

    private void select(Request request) {
    }

    private void moveToInBattle(Request request) {
    }

    private void battleAttack(Request request) {
    }

    private void battleComboAttack(Request request) {
    }

    private void useSpecialPower(Request request) {
    }

    private void insertCard(Request request) {
    }

    private void endTurn() {
        if (menu.getStat() == MenuStat.BATTLE) {
            battle.endTurn();
            this.account = battle.getCurrentPlayer();
            //view.endTurn(account);
        }
    }

    private void endGame() {
        if (menu.getStat() == MenuStat.BATTLE) {
            battle.resign();
            //view.endGame(battle);
        }
    }

    private void createCard() throws Exception {
        Path path = Paths.get("./src/Objects/Cards/" + boxes[Boxes.CARD_TYPE.ordinal()].getValue() + "s/" +
                fields[Texts.CARD_NAME.ordinal()].getText() + ".json");
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
        }
        Card card = new Card(fields[Texts.CARD_NAME.ordinal()].getText(),
                boxes[Boxes.CARD_TYPE.ordinal()].getValue(), fields[Texts.CARD_PRICE.ordinal()].getText(),
                fields[Texts.HP.ordinal()].getText(), fields[Texts.AP.ordinal()].getText(),
                boxes[Boxes.ACTIVATION.ordinal()].getValue(), boxes[Boxes.ATTACK_TYPE.ordinal()].getValue(),
                fields[Texts.RANGE.ordinal()].getText()
        );
        int id = 0;
        String idle, run, attack, death;

        switch (boxes[Boxes.CARD_TYPE.ordinal()].getValue()) {
            case "Hero":
                id = game.getLastHeroId();
                idle = "gifs/gifs/Heroes/boss_cindera_idle.gif";
                run = "gifs/gifs/Heroes/boss_cindera_run.gif";
                attack = "gifs/gifs/Heroes/boss_cindera_attack.gif";
                death = "gifs/gifs/Heroes/boss_cindera_death.gif";
                card.setIdleSrc(idle);
                card.setRunSrc(run);
                card.setAttackSrc(attack);
                card.setDeathSrc(death);
                game.incrementHeroId();
                break;
            case "Minion":
                id = game.getLastHeroId();
                idle = "gifs/gifs/Minions/Alabaster Titan_idle.gif";
                run = "gifs/gifs/Minions/Alabaster Titan_run.gif";
                attack = "gifs/gifs/Minions/Alabaster Titan_attack.gif";
                death = "gifs/gifs/Minions/Alabaster Titan_death.gif";
                card.setIdleSrc(idle);
                card.setRunSrc(run);
                card.setAttackSrc(attack);
                card.setDeathSrc(death);
                game.incrementHeroId();
                break;
            case "Spell":
                id = game.getLastHeroId();
                idle = "gifs/gifs/Spells/Abyssal Scar_actionbar.gif";
                attack = "gifs/gifs/Spells/Abyssal Scar_active.gif";
                card.setIdleSrc(idle);
                card.setAttackSrc(attack);
                game.incrementHeroId();
                break;
        }
        card.setId(id);
        card.setCardView();
        shop.getCards().add(card);
        String json = new Gson().toJson(card);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBuff() throws Exception {
        Path path = Paths.get("./src/Buffs/" + fields[Texts.BUFF_NAME.ordinal()].getText() + ".json");
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
        }
        File file = new File("./src/Buffs/" + fields[Texts.BUFF_NAME.ordinal()].getText() + ".json");
        Buff buff = new Buff(boxes[Boxes.BUFF_TYPE.ordinal()].getValue(), fields[Texts.BUFF_POWER.ordinal()].getText(),
                boxes[Boxes.SIDE.ordinal()].getValue(), boxes[Boxes.ATTRIBUTE.ordinal()].getValue(),
                fields[Texts.TURN.ordinal()].getText());
        String json = new Gson().toJson(buff);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}