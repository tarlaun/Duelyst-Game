package Controller;

import Controller.Request.Request;
import Controller.Request.RequestType;
import Model.Menu;
import Model.*;
import Model.Menu;
import View.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.TranslateTransition;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import javax.xml.soap.Text;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Optional;

public class Controller {
    private transient Socket socket;
    private transient Formatter formatter;
    private transient BufferedReader reader;
    private transient View view = View.getInstance();
    private transient Game game = Game.getInstance();
    private transient Menu menu = Menu.getInstance();
    private transient Shop shop = Shop.getInstance();
    private Account account;
    private transient Battle battle = Battle.getInstance();
    private transient Button[] buttons = new Button[Buttons.values().length];
    private transient PasswordField passwordField = new PasswordField();
    private transient Label[] labels = new Label[Labels.values().length];
    private transient AnchorPane[] anchorPanes = new AnchorPane[Anchorpanes.values().length];
    private transient TextField[] fields = new TextField[Texts.values().length];
    private transient ComboBox<String>[] boxes = new ComboBox[Boxes.values().length];
    private transient BattleCards[] heroes = new BattleCards[2];
    private ImageView[] currentImageView = new ImageView[3];
    private transient ImageView[] mana = new ImageView[9];
    private transient ImageView[] flags = new ImageView[6];
    private transient ImageView[] collectibleItems = new ImageView[6];
    private transient ImageView[] handCards = new ImageView[20];
    private ImageView[] imageViews = new ImageView[40];
    private BattleCards[] handCardGifs = new BattleCards[20];
    private BattleCards[] aiCards = new BattleCards[20];
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
    private int[][] randomCoordinates = new int[2][6];
    private int[] cellEffect = new int[3];
    private int currentAIHeroCell = 26;
    private int aiCardsInGround = 0;
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
        for (int i = 0; i < flags.length; i++) {
            flags[i] = new ImageView();
            collectibleItems[i] = new ImageView();
        }
        for (int i = 0; i < 20; i++) {
            handCards[i] = new ImageView();
            handCardGifs[i] = new BattleCards();
            aiCards[i] = new BattleCards();
            ImageView[] imageView = new ImageView[3];
            imageView[0] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
            imageView[1] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
            imageView[2] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
            handCardGifs[i].setImageView(imageView);
            aiCards[i].setImageView(imageView);
            aiCards[i].setInside(false);
        }
        for (int i = 0; i < 5; i++) {
            handCards[i] = new ImageView();
        }
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            randomCoordinates[0][i] = random.nextInt(45);
            randomCoordinates[1][i] = random.nextInt(45);
        }
        for (int i = 0; i < 3; i++) {
            cellEffect[i] = random.nextInt(45);
            for (int j = 0; j < i; j++) {
                if (cellEffect[j] == cellEffect[i]) {
                    cellEffect[i] = random.nextInt(45);
                }
            }
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
                        anchorPanes[Anchorpanes.CUSTOM_BUFF.ordinal()], anchorPanes[Anchorpanes.SAVE.ordinal()],
                        anchorPanes[Anchorpanes.REQUESTS.ordinal()]);
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

            case WIN:
                view.winPage();
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
            case REQUESTS:
                view.requestMenu(anchorPanes[Anchorpanes.BACK.ordinal()]);
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
                // handleMinions();
                for (int i = 0; i < 2; i++) {
                    heroes[i].setCard(battle.getAccounts()[i].getCollection().getMainDeck().getHero());
                    heroes[i].setInside(true);
                    heroes[i].setImageView(getImageViewGif(battle.getAccounts()[i].getCollection().getMainDeck().getHero()));
                }
                for (int i = 0; i < 15; i++) {
                    handCardGifs[i].setInside(false);
                    handCardGifs[i].setCard(battle.getAccounts()[0].getCollection().getMainDeck().getCards().get(i));
                    handCardGifs[i].setImageView(setGifForCards(battle.getAccounts()[0].getCollection().getMainDeck().getCards().get(i)));
                }
                for (int i = 0; i < 10; i++) {
                    if (!battle.getAccounts()[1].getCollection().getMainDeck().getCards().get(i).getType().equals("Spell")) {
                        aiCards[i].setCard(battle.getAccounts()[1].getCollection().getMainDeck().getCards().get(i));
                        aiCards[i].setImageView(setGifForCards(battle.getAccounts()[1].getCollection().getMainDeck().getCards().get(i)));
                    }
                }
                view.battleMenu(battle.getAccounts(), heroes, polygon, imageViews[ImageViews.END_TURN.ordinal()],
                        labels[Labels.END_TURN.ordinal()], mana, handCards, handCardGifs, imageViews[ImageViews.BACKGROUND.ordinal()],
                        imageViews[ImageViews.FOREGROUND.ordinal()], imageViews[ImageViews.back.ordinal()],
                        imageViews[ImageViews.FLAG.ordinal()], battle.getMode(), flags, cellEffect);
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
            handAiMinions();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void handleMinions() {
        for (int i = 0; i < handCardGifs.length; i++) {
            int finalI = i;
            handCardGifs[i].getImageView()[0].setOnMouseClicked(event -> {
                view.cardBackGround(handCardGifs[finalI]);
                if (battleCard != null && handCardGifs[finalI].isInside() && battleCard.getCard().getId() != handCardGifs[finalI].getCard().getId()) {
                    readyForAttack(finalI, handCardGifs);
                    String opponentCardId = String.valueOf(handCardGifs[finalI].getCard().getId());
                    String cardId = String.valueOf(battleCard.getCard().getId());
                    String turn = String.valueOf(battle.getTurn());
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.ATTACK, opponentCardId, cardId, turn, account.getName());
                    send(request);
                    battleCard = null;
                } else {
                    battle.selectCard(handCardGifs[finalI].getCard().getId());
                    int cardId = handCardGifs[finalI].getCard().getId();
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.SELECTION, String.valueOf(cardId), account.getName());
                    send(request);
                    try {
                        reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentImageView[0] = handCardGifs[finalI].getImageView()[0];
                    currentImageView[1] = handCardGifs[finalI].getImageView()[1];
                    battleCard = handCardGifs[finalI];
                    currentI = finalI;
                }

                currentCoordinate[0] = new Coordinate((int) handCardGifs[finalI].getImageView()[0].getLayoutX(), (int) handCardGifs[finalI].getImageView()[0].getLayoutY());
            });
        }
    }

    private void handAiMinions() {
        for (int i = 0; i < aiCards.length; i++) {
            int finalI = i;
            aiCards[i].getImageView()[0].setOnMouseClicked(event -> {
                view.cardBackGround(aiCards[finalI]);
                if (battleCard != null && aiCards[finalI].isInside() && battleCard.getCard().getId() != aiCards[finalI].getCard().getId()) {
                    readyForAttack(finalI, aiCards);
                    String opponentCardId = String.valueOf(aiCards[finalI].getCard().getId());
                    String cardId = String.valueOf(battleCard.getCard().getId());
                    String turn = String.valueOf(battle.getTurn());
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.ATTACK, opponentCardId, cardId, turn, account.getName());
                    send(request);
                    battleCard = null;
                } else {
                    battle.selectCard(aiCards[finalI].getCard().getId());
                    int cardId = aiCards[finalI].getCard().getId();
                    ;
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.SELECTION, String.valueOf(cardId), account.getName());
                    ;
                    send(request);
                    try {
                        reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ;
                    currentImageView[0] = aiCards[finalI].getImageView()[0];
                    currentImageView[1] = aiCards[finalI].getImageView()[1];
                    ;
                    battleCard = aiCards[finalI];
                    currentI = finalI;
                }

                currentCoordinate[0] = new Coordinate((int) aiCards[finalI].getImageView()[0].getLayoutX(), (int) aiCards[finalI].getImageView()[0].getLayoutY());
            });
        }

    }

    private ImageView[] setGifForCards(Card card) {
        ImageView[] imageViews = new ImageView[3];
        switch (card.getName()) {
            case "PERSIAN_CHAMPION":
            case "PERSIAN_SWORDS_WOMAN":
                imageViews[0] = new ImageView(new Image("minionGifs/Alabaster Titan_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Alabaster Titan_idle.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Alabaster Titan_attack.gif"));
                break;
            case "PERSIAN_COMMANDER":
            case "PERSIAN_HORSEMAN":
                imageViews[0] = new ImageView(new Image("minionGifs/Spriggin_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Spriggin_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Spriggin_attack.gif"));
                break;
            case "TURANIAN_ARCHER":
            case "CATAPULT_GIANT":
                imageViews[0] = new ImageView(new Image("minionGifs/Sunrise Cleric_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Sunrise Cleric_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Sunrise Cleric_attack.gif"));
                break;
            case "VENOM_SNAKE":
            case "TURANIAN_SPY":
                imageViews[0] = new ImageView(new Image("minionGifs/Worldcore Golem_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Worldcore Golem_attack.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Worldcore Golem_attack.gif"));
                break;
            case "TURANIAN_CATAPULT":
            case "TURANIAN_LANCER":
                imageViews[0] = new ImageView(new Image("minionGifs/Blood Taura_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Blood Taura_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Blood Taura_attack.gif"));
                break;
            case "PERSIAN_LANCER":
            case "TURANIAN_PRINCE":
                imageViews[0] = new ImageView(new Image("minionGifs/Riftwalker_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Riftwalker_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Riftwalker_attack.gif"));
                break;

            case "HOG_RIDER_GIANT":
            case "CYCLOPS":
                imageViews[0] = new ImageView(new Image("minionGifs/Blood Taura_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Blood Taura_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Blood Taura_attack.gif"));
                break;
            case "TWO_HEADED_GIANT":
            case "GONDE_BACK_GIANT":
                imageViews[0] = new ImageView(new Image("minionGifs/Furiosa_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Furiosa_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Furiosa_attack.gif"));
                break;
            case "EAGLE":
            case "FOOLADZEREH":
            case "WOLF":
                imageViews[0] = new ImageView(new Image("minionGifs/Elkowl_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Elkowl_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Elkowl_attack.gif"));
                break;
            case "WHITE_WOLF":
            case "DRAGON":
                imageViews[0] = new ImageView(new Image("minionGifs/Katastrophosaurus_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Katastrophosaurus_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Katastrophosaurus_attack.gif"));
                break;
            case "NANE_WITCH":
            case "GIANT_SNAKE":
                imageViews[0] = new ImageView(new Image("minionGifs/Katastrophosaurus_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Katastrophosaurus_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Katastrophosaurus_attack.gif"));
                break;
            case "LION":
            case "PALANG":
                imageViews[0] = new ImageView(new Image("minionGifs/Azurite Lion_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Azurite Lion_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Azurite Lion_attack.gif"));
                break;
            case "WITCH":
            case "WILD_HOG":
            case "TURANIAN_MACER":
                imageViews[0] = new ImageView(new Image("minionGifs/Pandora_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Pandora_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Pandora_attack.gif"));
                break;
            case "JEN":
            case "PIRAN":
            case "GIV":
                imageViews[0] = new ImageView(new Image("minionGifs/Cryptographer_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Cryptographer_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Cryptographer_attack.gif"));
                break;

            case "BAHMAN":
            case "PERSIAN_ARCHER":
                imageViews[0] = new ImageView(new Image("minionGifs/Silverbeak_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Silverbeak_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Silverbeak_attack.gif"));
                break;
            case "ASHKBOOS":
            case "IRAJ":
                imageViews[0] = new ImageView(new Image("minionGifs/Fog_idle.gif"));
                imageViews[1] = new ImageView(new Image("minionGifs/Fog_run.gif"));
                imageViews[2] = new ImageView(new Image("minionGifs/Fog_attack.gif"));
                break;
            case "NANE_SARMA":
            case "SIAVASH":
            case "SHAGHUL":
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
                imageViews[1] = new ImageView(new Image("spell/Icebreak Ambush_active.gif"));
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
        anchorPanes[Anchorpanes.REQUESTS.ordinal()].setOnMouseClicked(event -> requests());
        anchorPanes[Anchorpanes.SHOP.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = shop.getCards();
            itemsInShop = shop.getItems();
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
                view.cardBackGround(heroes[finalI]);
                if (battleCard != null && battleCard.getCard().getId() != heroes[finalI].getCard().getId()) {

                    readyForAttack(finalI, heroes);
                    String opponentCardId = String.valueOf(heroes[finalI].getCard().getId());
                    String cardId = String.valueOf(battleCard.getCard().getId());
                    String turn = String.valueOf(battle.getTurn());
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.ATTACK, opponentCardId, cardId, turn, account.getName());
                    send(request);
                    battleCard = null;
                } else {
                    battle.selectCard(heroes[finalI].getCard().getId());
                    ;
                    int cardId = heroes[finalI].getCard().getId();
                    ;
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.SELECTION, String.valueOf(cardId), account.getName());
                    ;
                    ;
                    send(request);
                    try {
                        reader.readLine();
                        ;
                    } catch (IOException e) {
                        e.printStackTrace();
                        ;
                    }
                    ;
                    ;
                    currentImageView[0] = heroes[finalI].getImageView()[0];
                    ;
                    ;
                    currentImageView[1] = heroes[finalI].getImageView()[1];
                    battleCard = heroes[finalI];
                    currentI = finalI;
                }
                currentCoordinate[0] = null;
            });
        }
    }


    private void readyForAttack(int finalI, BattleCards[] heroes) {
        System.out.println("attack mmm");
        Message message =battle.attack(heroes[finalI].getCard().getId(), battleCard.getCard(), 0);
        currentImageView[0] = battleCard.getImageView()[0];
        currentImageView[1] = battleCard.getImageView()[1];
        currentImageView[2] = battleCard.getImageView()[2];
        view.attack(currentImageView);
        if (message.equals(Message.SUCCESSFUL_KILL)) {
            Request request = new Request(Constants.SOCKET_PORT, RequestType.KILL, account.getName(), String.valueOf(heroes[0].getCard().getId()));
            send(request);
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.kill(heroes[finalI]);
            if(battle.getMode().equals(BattleMode.KILLENEMYHERO)){
                menu.setStat(MenuStat.WIN);
                TranslateTransition transition = new TranslateTransition(Duration.millis(3000),heroes[finalI].getImageView()[0]);
                transition.playFromStart();
                transition.setOnFinished(event -> {
                    main();
                });
            }
        }

    }

    private void handlePolygon() {
        for (int i = 0; i < polygon.length; i++) {
            int a = i;
            polygon[i].setOnMouseClicked(event -> {
                String polygonNumberX = String.valueOf((a / 9));
                String polygonNumberY = String.valueOf(a - (a / 9) * 9);
                if (currentCoordinate[0] == null) {
                    view.move(polygon[a].getPoints().get(0), polygon[a].getPoints().get(1), currentImageView[0], currentImageView[1]);
                    System.out.println("move kard");
                    battle.moveTo(new Coordinate((a / 9), a - (a / 9) * 9), battleCard.getCard().getId());
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.MOVE, polygonNumberX, polygonNumberY, account.getName(), String.valueOf(battleCard.getCard().getId()));
                    send(request);
                    battleCard = null;
                    try {
                        reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (currentCoordinate[0] != null) {
                    view.move(polygon[a].getPoints().get(0), polygon[a].getPoints().get(1), currentImageView[0], currentImageView[1]);
                    battle.insertCard(new Coordinate((a / 9), a - (a / 9) * 9), handCardGifs[currentI].getCard().getName());
                    Request request = new Request(Constants.SOCKET_PORT, RequestType.INSERTION, handCardGifs[currentI].getCard().getName(), polygonNumberX, polygonNumberY, account.getName());
                    send(request);
                    try {
                        reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handCardGifs[currentI].setInside(true);
                    currentHandCardPointer++;
                    if (currentHandCardPointer + 4 < 15) {
                        view.handView(currentCoordinate, handCardGifs[currentHandCardPointer + 4]);
                    }
                    battleCard = null;
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
        imageViews[ImageViews.END_TURN.ordinal()].setOnMouseClicked(event -> {
            endTurnRequest();
            collectFlags();

        });
        labels[Labels.END_TURN.ordinal()].setOnMouseClicked(event -> {
            endTurnRequest();
            for (int i = 0; i < 9; i++) {
                if (i < battle.getAccounts()[0].getMana()) {
                    mana[i].setImage(new Image("ui/icon_mana@2x.png"));
                } else {
                    mana[i].setImage(new Image("ui/icon_mana_inactive@2x.png"));
                }
            }
            collectFlags();

        });
        buttons[Buttons.SINGLE_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeSingle());
        buttons[Buttons.MULTI_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeMulti());
        buttons[Buttons.KILL_ENEMY_HERO.ordinal()].setOnMouseClicked(event -> setBattleMode(1));
        buttons[Buttons.FLAG_COLLECTING.ordinal()].setOnMouseClicked(event -> setBattleMode(2));
        buttons[Buttons.HOLD_FLAG.ordinal()].setOnMouseClicked(event -> setBattleMode(3));
    }

    private void endTurnRequest() {
        Request request = new Request(Constants.SOCKET_PORT, RequestType.END_TURN, "endTurn");
        send(request);
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        battle.endTurn();
        AiFunctions();
    }

    private void collectFlags() {
        if (Constants.MAXIMUM_FLAGS > battle.getTurn() / 2 && battle.getTurn() % 2 == 1) {
            for (int i = 0; i < battle.getTurn() / 2; i++) {
                if (i == 0)
                    collectibleItems[i] = new ImageView(new Image("Animus Plate_active.gif"));
                if (i == 1)
                    collectibleItems[i] = new ImageView(new Image("Dawn's Eye_active.gif"));
                if (i == 2 || i == 5)
                    collectibleItems[i] = new ImageView(new Image("Obdurator_active.gif"));
                if (i == 3)
                    collectibleItems[i] = new ImageView(new Image("Animus Plate_active.gif"));
                if (i == 4)
                    collectibleItems[i] = new ImageView(new Image("Wraithcrown_active.gif"));
            }
            for (int i = battle.getTurn() / 2; i < 6; i++) {
                collectibleItems[i] = null;
            }
            view.collectFlags(collectibleItems, polygon, randomCoordinates[0], 1);
        }
        if (battle.getMode().equals(BattleMode.COLLECTING) && Constants.MAXIMUM_FLAGS > battle.getTurn() / 2 && battle.getTurn() % 2 == 1) {
            for (int i = 0; i < battle.getTurn() / 2; i++) {
                flags[i] = new ImageView(new Image("Crystal Wisp_run.gif"));
            }
            for (int i = battle.getTurn() / 2; i < 6; i++) {
                flags[i] = null;
            }
            view.collectFlags(flags, polygon, randomCoordinates[1], 2);
        }
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

    private void handleCollection(ArrayList<Card> cards, ArrayList<Item> items) {
        for (int i = 0; i < cards.size(); i++) {
            int finalI = i;
            cards.get(i).getCardView().getPane().setOnMouseClicked(event -> {
                deckLing(cards.get(finalI).getId());
                ;
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
                if ("OK".equals(result.get().getText())) {
                    buy(name);
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
            if ("Yes".equals(result.get().getText())) {
                sell(id);
            }
        }
    }

    private void setBattleMode(int a) {
        System.out.println(battle.getGameType() + "kvmkdmckmkmv");
        String battleModes = null;
        switch (a) {
            case 1:
                battle.setMode(BattleMode.KILLENEMYHERO);
                battleModes = BattleMode.KILLENEMYHERO.toString();
                break;
            case 2:
                battle.setMode(BattleMode.COLLECTING);
                battleModes = BattleMode.COLLECTING.toString();
                break;
            case 3:
                battle.setMode(BattleMode.FLAG);
                battleModes = BattleMode.FLAG.toString();
                break;
        }
        Request requestt = new Request(Constants.SOCKET_PORT, RequestType.BATTLE_MODE, battleModes);
        send(requestt);
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (battle.getGameType().equals(GameType.SINGLEPLAYER)) {
            menu.setStat(MenuStat.BACK_GROUND);
            Account[] accounts = new Account[2];
            accounts[0] = account;
            Request request = new Request(Constants.SOCKET_PORT, RequestType.RIVAL, "powerfulAI");
            send(request);
            Account accountu = null;
            try {
                accountu = Account.fromJson(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (accountu.getName().equals("powerfulAI")) {
                accounts[1] = accountu;
            }
            battle.setAccounts(accounts);
            System.out.println("before");
            System.out.println(battle.getGameType() + "qweqewfe");
            Request request1 = new Request(Constants.SOCKET_PORT, RequestType.BATTLE, accounts[0].getName(),
                    accounts[1].getName(), battle.getGameType().toString(), battle.getMode().toString());
            send(request1);
            try {
                battle = Battle.fromJson(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            setMainDeckForAI();
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
            moveAI();
            insertAI();
        }
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 0) {
            attackAI();
        }
    }


    private void selectUser(String name) {
        Request request = new Request(Constants.SOCKET_PORT, RequestType.SELECT_USER, account.getName(), name);
        send(request);
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Should be written

/*
        if (accountt != null) {
            battle.setAccounts(account, accountt);
            menu.setStat(MenuStat.BACK_GROUND);
            battle.startBattle();
        }
*/
        main();
    }

    private void setBattleModeSingle() {
        System.out.println("single");
        Request request = new Request(Constants.SOCKET_PORT, RequestType.GAME_TYPE, GameType.SINGLEPLAYER.toString());
        send(request);
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        battle.setGameType(GameType.SINGLEPLAYER);
        System.out.println(battle.getGameType() + "ekmvk");
        menu.setStat(MenuStat.BATTLE_MODE);
        main();
    }

    private void setBattleModeMulti() {
        System.out.println("multi");
        Request request = new Request(Constants.SOCKET_PORT, RequestType.GAME_TYPE, GameType.MULTIPLAYER.toString());
        send(request);
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        battle.setGameType(GameType.MULTIPLAYER);
        System.out.println(battle.getGameType() + "kmdcdc");
        menu.setStat(MenuStat.BATTLE_MODE);
        main();
    }


    private void setMainDeckForAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getMode().equals(BattleMode.KILLENEMYHERO)) {
            battle.getAccounts()[1].getCollection().selectDeck("level1");
            System.out.println("one");
        }
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getMode().equals(BattleMode.FLAG)) {
            System.out.println("tw");
            battle.getAccounts()[1].getCollection().selectDeck("level2");
        }
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getMode().equals(BattleMode.COLLECTING)) {
            System.out.println("th");
            battle.getAccounts()[1].getCollection().selectDeck("level3");
        }
    }

    private void moveAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER)) {
            for (int i = 0; i < battle.getFieldCards()[1].length; i++) {
                if (battle.getFieldCards()[1][i] != null) {
                    battle.setCurrentCard(battle.getFieldCards()[1][i]);
                    //battle.moveTo(battle.setDestinationCoordinate(battle.getFieldCards()[1][i]));
                    if (currentAIHeroCell > 19) {
                        currentAIHeroCell--;
                        view.move(polygon[currentAIHeroCell].getPoints().get(0), polygon[currentAIHeroCell].getPoints().get(1), heroes[1].getImageView()[0], heroes[1].getImageView()[1]);
                    }
                }
            }
        }
    }

    private ImageView[] getImageViewGif(Card card) {
        ImageView[] imageViews = new ImageView[3];
        switch (card.getName()) {
            case "WHITE_DIV":
                imageViews[1] = new ImageView(new Image("gifs/Minions/Horror_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/Minions/Horror_attack.gif"));
                imageViews[0] = new ImageView(new Image("gifs/Minions/Horror_idle.gif"));
                break;
            case "KAVEH":
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
                imageViews[0] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
                break;
            case "RAKHSH":
                imageViews[0] = new ImageView(new Image("gifs/gifs/Abomination_run.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/Abomination_attack.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Abomination_idle.gif"));
                break;
            case "ZAHAK":
                imageViews[0] = new ImageView(new Image("gifs/gifs/Brome Warcrest_idle.gif"));
                imageViews[1] = new ImageView(new Image("gifs/gifs/Brome Warcrest_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/gifs/Brome Warcrest_attack.gif"));
                break;
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
        if (battle.getGameType().equals(GameType.SINGLEPLAYER)) {
            view.attack(heroes[1].getImageView());
            Message message = battle.attack(heroes[0].getCard().getId(), heroes[1].getCard(), 0);
            if (message.equals(Message.SUCCESSFUL_KILL)) {
                Request request = new Request(Constants.SOCKET_PORT, RequestType.KILL, account.getName(), String.valueOf(heroes[0].getCard().getId()));
                send(request);
                try {
                    reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                view.kill(heroes[0]);
                if(battle.getMode().equals(BattleMode.KILLENEMYHERO)){
                    menu.setStat(MenuStat.WIN);
                    TranslateTransition transition = new TranslateTransition(Duration.millis(3000),heroes[0].getImageView()[0]);
                    transition.playFromStart();
                    transition.setOnFinished(event -> {
                        main();
                    });
                }
            }

        }
    }

    private void insertAI() {
        if (battle.getGameType().equals(GameType.SINGLEPLAYER) && battle.getTurn() % 2 == 1) {
            aiCards[aiCardsInGround].setInside(true);
            //battle.insertCard(battle.setCardCoordinates(), battle.chooseCard(cards).getName());
            if (aiCards[aiCardsInGround].getCard() != null)
                view.aiHandGifs(aiCards, polygon, aiCardsInGround);
            aiCardsInGround++;
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
        Message message;
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
            if ("Yes".equals(result.get().getText())) {
                save();
            }
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

    private void buy(String name) {
        if (shop.getGame() == null)
            shop.setGame(this.game);
        shop.buy(name, this.account);
    }

    private void sell(int id) {
        shop.sell(id, account);
    }


    class Task extends TimerTask {
        //Timer timer = new Timer();
        //            timer.schedule(Task, 60000);
        public void run() {
            // endTurn();
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
        } catch (FileAlreadyExistsException ignored) {
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
        changeToJason(file, json);
    }

    private void createBuff() throws Exception {
        Path path = Paths.get("./src/Buffs/" + fields[Texts.BUFF_NAME.ordinal()].getText() + ".json");
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException ignored) {
        }
        File file = new File("./src/Buffs/" + fields[Texts.BUFF_NAME.ordinal()].getText() + ".json");
        Buff buff = new Buff(boxes[Boxes.BUFF_TYPE.ordinal()].getValue(), fields[Texts.BUFF_POWER.ordinal()].getText(),
                boxes[Boxes.SIDE.ordinal()].getValue(), boxes[Boxes.ATTRIBUTE.ordinal()].getValue(),
                fields[Texts.TURN.ordinal()].getText());
        String json = new Gson().toJson(buff);
        changeToJason(file, json);
    }

    private void changeToJason(File file, String json) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requests() {
        menu.setStat(MenuStat.REQUESTS);
        main();
    }
}