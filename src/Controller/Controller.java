package Controller;

import Model.*;
import Model.Menu;
import View.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private View view = View.getInstance();
    private Game game = Game.getInstance();
    private Menu menu = Menu.getInstance();
    private Shop shop = Shop.getInstance();
    private Account account;
    private Battle battle = Battle.getInstance();
    private transient Button[] buttons = new Button[Buttons.values().length];
    private transient PasswordField passwordField = new PasswordField();
    private transient Label[] labels = new Label[Labels.values().length];
    private transient AnchorPane[] anchorPanes = new AnchorPane[Anchorpanes.values().length];
    private transient TextField[] fields = new TextField[Texts.values().length];
    private transient BattleCards[] heroes = new BattleCards[2];
    private ImageView[] currentImageView = new ImageView[3];
    private transient ImageView[] mana = new ImageView[9];
    private transient ImageView[] handCards = new ImageView[20];
    private ImageView[] imageViews = new ImageView[40];
    private BattleCards[] handCardGifs = new BattleCards[20];
    private BattleCards battleCard = null;
    private Coordinate[] currentCoordinate = new Coordinate[2];
    private static final Controller controller = new Controller();
    private File file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_mainmenu_lyonar.m4a");
    private Media media = new Media(file.toURI().toString());
    private MediaPlayer player = new MediaPlayer(media);
    private Polygon[] polygon = new Polygon[45];
    private int collectionPage = 0, shopPage = 0;
    private ArrayList<Card> cardsInShop, cardsInCollection;
    private ArrayList<Item> itemsInShop, itemsInCollection;
    private boolean buyMode = true;
    private int currentHandCardPointer = 0;
    private int currentI;

    private Controller() {
        initializeGame();
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
            imageView[0] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            imageView[1] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            imageView[2] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            handCardGifs[i].setImageView(imageView);
        }
        for (int i = 0; i < 5; i++) {
            handCards[i] = new ImageView();
        }
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(new Image("gifs/Abomination_idle.gif"));
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
            imageView[0] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            imageView[1] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            imageView[2] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            heroes[i].setImageView(imageView);
        }
        menu.setStat(MenuStat.MAIN);
    }

    public static Controller getInstance() {
        return controller;
    }

    public void initializeGame() {
        try {
            game.initializeAccounts();
        } catch (IOException f) {
            System.out.println("Account initializing error!");
        }
        try {
            game.initializeHero();
        } catch (IOException f) {
            System.out.println("Hero initializing error!");
        }
        try {
            game.initializeMinion();
        } catch (IOException f) {
            System.out.println("Minion initializing error!");
        }
        try {
            game.initializeSpell();
        } catch (IOException f) {
            System.out.println("Spell initializing error!");
        }
        try {
            game.initializeItem();
        } catch (IOException f) {
            System.out.println("Item initializing error!");
        }
        game.setSrcs();
    }

    public void main() {

        player.stop();
        switch (menu.getStat()) {
            case MAIN:
                view.mainMenu(buttons[Buttons.LOGIN.ordinal()], buttons[Buttons.CREATE_ACCOUNT.ordinal()],
                        buttons[Buttons.EXIT.ordinal()], fields[Texts.USERNAME.ordinal()], passwordField);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_vetruv.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case ACCOUNT:
                view.accountMenu(buttons[Buttons.PLAY.ordinal()], buttons[Buttons.COLLECTION.ordinal()],
                        buttons[Buttons.SHOP.ordinal()], buttons[Buttons.LEADER_BOARD.ordinal()],
                        buttons[Buttons.LOGOUT.ordinal()]);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_playmode.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SHOP:
                view.shopMenu(buyMode, fields[Texts.OBJECT.ordinal()], cardsInShop, itemsInShop,
                        anchorPanes[Anchorpanes.BACK.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], anchorPanes[Anchorpanes.SELL.ordinal()],
                        anchorPanes[Anchorpanes.BUY.ordinal()], shopPage);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case COLLECTION:
                view.collectionMenu(fields[Texts.OBJECT.ordinal()], cardsInCollection, itemsInCollection,
                        anchorPanes[Anchorpanes.CREATE.ordinal()], fields[Texts.DECKNAME.ordinal()],
                        anchorPanes[Anchorpanes.BACK.ordinal()], anchorPanes[Anchorpanes.NEXT.ordinal()],
                        anchorPanes[Anchorpanes.PREV.ordinal()], collectionPage);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_morinkhur.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;

            case BACK_GROUND:
                imageViews[ImageViews.REDROCK.ordinal()].setImage(new Image("resources/maps/redrock/midground@2x.png"));
                imageViews[ImageViews.VANAR.ordinal()].setImage(new Image("resources/maps/vanar/midground@2x.png"));
                imageViews[ImageViews.SHIMZAR.ordinal()].setImage(new Image("resources/maps/shimzar/midground@2x.png"));
                imageViews[ImageViews.ABYSSIAN.ordinal()].setImage(new Image("resources/maps/abyssian/midground@2x.png"));
                imageViews[ImageViews.PURPLE.ordinal()].setImage(new Image("resources/maps/battlemap4_middleground@2x.png"));
                imageViews[ImageViews.ICE.ordinal()].setImage(new Image("resources/maps/battlemap3_middleground@2x.png"));
                imageViews[ImageViews.METAL.ordinal()].setImage(new Image("resources/maps/battlemap7_middleground@2x.png"));
                imageViews[ImageViews.CANDLE.ordinal()].setImage(new Image("resources/maps/battlemap6_middleground@2x.png"));
                imageViews[ImageViews.CHINA.ordinal()].setImage(new Image("resources/maps/battlemap1_middleground@2x.png"));
                imageViews[ImageViews.OCTA.ordinal()].setImage(new Image("resources/maps/battlemap2_middleground@2x.png"));
                imageViews[ImageViews.LION.ordinal()].setImage(new Image("resources/maps/battlemap0_middleground@2x.png"));
                imageViews[ImageViews.ABYSSIAN.ordinal()].setImage(new Image("resources/maps/abyssian/midground@2x.png"));
                view.backGroundMenu(imageViews[ImageViews.REDROCK.ordinal()], imageViews[ImageViews.VANAR.ordinal()],
                        imageViews[ImageViews.SHIMZAR.ordinal()], imageViews[ImageViews.ABYSSIAN.ordinal()],
                        imageViews[ImageViews.PURPLE.ordinal()], imageViews[ImageViews.OCTA.ordinal()]
                        , imageViews[ImageViews.METAL.ordinal()], imageViews[ImageViews.CHINA.ordinal()],
                        imageViews[ImageViews.ICE.ordinal()], imageViews[ImageViews.CANDLE.ordinal()],
                        imageViews[ImageViews.LION.ordinal()]);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_abyssian.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case GAME_TYPE:
                view.gameTypeMenu(buttons[Buttons.SINGLE_PLAYER.ordinal()], buttons[Buttons.MULTI_PLAYER.ordinal()]);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_firesofvictory.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case PROCESS:
                break;
            case BATTLE_MODE:
                view.battleMode(buttons[Buttons.KILL_ENEMY_HERO.ordinal()], buttons[Buttons.FLAG_COLLECTING.ordinal()],
                        buttons[Buttons.HOLD_FLAG.ordinal()]);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_songhai.m4a");
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
                view.battleMenu(battle.getAccounts(), heroes, polygon, imageViews[ImageViews.END_TURN.ordinal()],
                        labels[Labels.END_TURN.ordinal()], mana, handCards, handCardGifs, imageViews[ImageViews.BACKGROUND.ordinal()],
                        imageViews[ImageViews.FOREGROUND.ordinal()], imageViews[ImageViews.back.ordinal()], imageViews[ImageViews.FLAG.ordinal()], battle.getMode());
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap01.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case SELECT_USER:
                view.selectUserMenu(game.getAccounts(), labels[Labels.STATUS.ordinal()], fields[Texts.USER_NAME.ordinal()]);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_abyssian.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                break;
            case GRAVEYARD:
                view.graveYardMenu();
                break;
            case ITEM_SELECTION:
                break;
        }
        player.setAutoPlay(true);
        handlePolygon();
        handleButtons();
        handleTextFields();
        handleHeroGifs();
        handleMinions();

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
        buttons[Buttons.CREATE_ACCOUNT.ordinal()].setOnMouseClicked(event -> createAccount());
        buttons[Buttons.LOGIN.ordinal()].setOnMouseClicked(event -> login());
        buttons[Buttons.EXIT.ordinal()].setOnMouseClicked(event -> exit());
        buttons[Buttons.PLAY.ordinal()].setOnMouseClicked(event -> chooseBattleType());
        buttons[Buttons.LOGOUT.ordinal()].setOnMouseClicked(event -> logout());
        buttons[Buttons.LEADER_BOARD.ordinal()].setOnMouseClicked(event -> showLeaderBoard());
        imageViews[ImageViews.REDROCK.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/redrock/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/redrock/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.ABYSSIAN.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/abyssian/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/abyssian/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.SHIMZAR.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/shimzar/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/shimzar/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.VANAR.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/vanar/background@2x.jpg"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/vanar/midground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.LION.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap0_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap0_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.CHINA.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap1_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap1_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.OCTA.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap2_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap2_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.ICE.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap3_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap3_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.PURPLE.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap4_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap4_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.CANDLE.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap6_middleground@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap6_middleground@2x.png"));
            menu.setStat(MenuStat.BATTLE);
            main();
        });
        imageViews[ImageViews.METAL.ordinal()].setOnMouseClicked(event -> {
            imageViews[ImageViews.BACKGROUND.ordinal()].setImage(new Image("resources/maps/battlemap7_background@2x.png"));
            imageViews[ImageViews.FOREGROUND.ordinal()].setImage(new Image("resources/maps/battlemap7_middleground@2x.png"));
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
        buttons[Buttons.COLLECTION.ordinal()].setOnMouseClicked(event -> {
            cardsInCollection = account.getCollection().getCards();
            itemsInCollection = account.getCollection().getItems();
            menu.setStat(MenuStat.COLLECTION);
            main();
        });
        imageViews[ImageViews.END_TURN.ordinal()].setOnMouseClicked(event -> {
            battle.endTurn();
            AiFunctions();
        });
        labels[Labels.END_TURN.ordinal()].setOnMouseClicked(event -> {
            battle.endTurn();
            AiFunctions();
            for (int i = 0; i < 9; i++) {
                if (i < battle.getAccounts()[0].getMana()) {
                    mana[i].setImage(new Image("resources/ui/icon_mana@2x.png"));
                } else {
                    mana[i].setImage(new Image("resources/ui/icon_mana_inactive@2x.png"));
                }
            }
            if (Constants.MAXIMUM_FLAGS > battle.getTurn() / 2) {
                for (int i = 0; i < battle.getTurn() / 2; i++) {

                }
            }
        });
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
                    collectionPage < (account.getCollection().getCards().size() + account.getCollection().getItems().size())
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

        buttons[Buttons.BUY.ordinal()].setOnMouseClicked(event -> buy());
        buttons[Buttons.SINGLE_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeSingle());
        buttons[Buttons.MULTI_PLAYER.ordinal()].setOnMouseClicked(event -> setBattleModeMulti());
        buttons[Buttons.KILL_ENEMY_HERO.ordinal()].setOnMouseClicked(event -> setBattleMode(1));
        buttons[Buttons.FLAG_COLLECTING.ordinal()].setOnMouseClicked(event -> setBattleMode(2));
        buttons[Buttons.HOLD_FLAG.ordinal()].setOnMouseClicked(event -> setBattleMode(3));
        anchorPanes[Anchorpanes.CREATE.ordinal()].setOnMouseClicked(event -> createDeck(fields[Texts.DECKNAME.ordinal()].toString()));
    }

    private void handleTextFields() {
        fields[Texts.USER_NAME.ordinal()].setOnAction(event -> selectUser(fields[Texts.USER_NAME.ordinal()].getText()));
        fields[Texts.OBJECT.ordinal()].setOnKeyReleased(event -> {
            if (menu.getStat() == MenuStat.SHOP) {
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
                cardsInCollection = Card.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                        account.getCollection().getCards());
                itemsInCollection = Item.matchSearch(fields[Texts.OBJECT.ordinal()].getCharacters().toString(),
                        account.getCollection().getItems());
            }
            main();
        });
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
            showMap();
        }
    }

    private void showMap() {
        if (menu.getStat() == MenuStat.BATTLE) {
            view.drawMap(battle);
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
        System.out.println(card.getName());
        switch (card.getName()) {
            case "WHITE_DIV":
                imageViews[1] = new ImageView(new Image("gifs/Abomination_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/Abomination_attack.gif"));
                imageViews[0] = new ImageView(new Image("gifs/Abomination_idle.gif"));
                break;
            case "KAVEH":
                imageViews[0] = new ImageView(new Image("gifs/Abomination_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/Abomination_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/Abomination_idle.gif"));
                break;
            case "ARASH":
                imageViews[1] = new ImageView(new Image("gifs/f5_altgeneraltier2_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[0] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "SIMORGH":
                imageViews[0] = new ImageView(new Image("gifs/f4_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/f4_altgeneraltier2_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/f4_altgeneraltier2_idle.gif"));
                break;
            case "SEVEN_HEADED_DRAGON":
                imageViews[0] = new ImageView(new Image("gifs/Abomination_run.gif"));
                imageViews[1] = new ImageView(new Image("gifs/Abomination_attack.gif"));
                imageViews[2] = new ImageView(new Image("gifs/Abomination_idle.gif"));
                break;
            case "RAKHSH":
                imageViews[0] = new ImageView(new Image("gifs/Abomination_run.gif"));
                imageViews[1] = new ImageView(new Image("gifs/Abomination_attack.gif"));
                imageViews[2] = new ImageView(new Image("gifs/Abomination_idle.gif"));
                break;
            case "ZAHAK":
                imageViews[0] = new ImageView(new Image("gifs/Brome Warcrest_idle.gif"));
                imageViews[1] = new ImageView(new Image("gifs/Brome Warcrest_run.gif"));
                imageViews[2] = new ImageView(new Image("gifs/Brome Warcrest_attack.gif"));
                break;
            case "AFSANEH":
                imageViews[0] = new ImageView(new Image("gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "ESFANDIAR":
                imageViews[0] = new ImageView(new Image("gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                break;
            case "ROSTAM":
                imageViews[0] = new ImageView(new Image("gifs/f5_altgeneraltier2_attack.gif"));
                imageViews[1] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                imageViews[2] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
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


    private void createAccount() {
        String username = fields[Texts.USERNAME.ordinal()].getText();
        String password = passwordField.getText();
        this.account = new Account(username, password);
        menu.setStat(MenuStat.ACCOUNT);
        main();
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
        if (Account.login(username, password) == Message.SUCCESSFUL_LOGIN) {
            this.account = game.getAccounts().get(Account.accountIndex(username));
            menu.setStat(MenuStat.ACCOUNT);
            main();
        }
    }

    private void showLeaderBoard() {
        game.sortAccounts();
        view.printLeaderboard(game.getAccounts());
    }

    private void save() {
        if (menu.getStat() == MenuStat.ACCOUNT) {
            game.save(account);
        }
    }

    private void logout() {
        game.logout(account);
        this.account = null;
        menu.setStat(MenuStat.MAIN);
        main();
    }


    private void exit() {
        menu.exitMenu();
        main();
    }


    private void createDeck(String deckName) {
        /*if (request.checkDeckSyntax() && menu.getStat() == MenuStat.COLLECTION) {
            view.createDeck(this.account.getCollection().createDeck(request.getDeckName(request.getCommand())));
        }*/
        this.account.getCollection().createDeck(deckName);
    }

    private void buy() {
        if (shop.getGame() == null)
            shop.setGame(this.game);
        shop.buy(fields[Texts.OBJECT.ordinal()].getText(), this.account);
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

}
