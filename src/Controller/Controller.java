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
import javafx.scene.text.Text;
import sun.security.util.Password;

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
    private transient ImageView[][] heroes = new ImageView[Constants.HEROES_COUNT][3];
    private ImageView[] currentImageView = new ImageView[3];
    private transient ImageView[] mana = new ImageView[9];
    private transient javafx.scene.image.ImageView[] minions = new ImageView[Constants.MINIONS_COUNT];
    private transient ImageView[] spells = new ImageView[Constants.SPELLS_COUNT];
    private transient ImageView[] handCards = new ImageView[5];
    private int[][] heroId = new int[2][2];
    private int[] lastSelectedCardId = {0,0};
    private int[] currentCardId = {0,0};
    private ImageView[] imageViews = new ImageView[40];
    private transient javafx.scene.image.ImageView[] items = new ImageView[Constants.ITEMS_COUNT];
    private BattleCards[] battleCards = new BattleCards[40];
    private BattleCards[] handCardGifs = new BattleCards[5];
    private BattleCards battleCard = new BattleCards();
    private static final Controller controller = new Controller();
    File file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_mainmenu_lyonar.m4a");
    Media media = new Media(file.toURI().toString());
    MediaPlayer player = new MediaPlayer(media);
    Polygon[] polygon = new Polygon[45];
    private int collectionPage = 0, shopPage = 0;
    private ArrayList<Card> cardsInShop, cardsInCollection;
    private ArrayList<Item> itemsInShop, itemsInCollection;
    private boolean buyMode = true;
    private boolean insideBattle = false;

    private Controller() {
        initializeGame();
        for (int i = 0; i <40 ; i++) {
            //battleCards[i].setImageView(new ImageView[3]);
        }
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
        for (int i = 0; i < 5; i++) {
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
            imageViews[i] = new ImageView();
        }
        for (int i = 0; i < 9; i++) {
            mana[i] = new ImageView();
        }
        for (int i = 0; i < polygon.length; i++) {
            polygon[i] = new Polygon();
        }

        for (int i = 0; i < heroes.length; i++) {
            heroes[i][0] = new ImageView();
            heroes[i][0] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            heroes[i][1] = new ImageView();
            heroes[i][1] = new ImageView(new Image("gifs/Abomination_idle.gif"));
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

    /*public void handleCommands() {
        Request request = new Request();

//        while (true) {
        request.getNewCommand();
        switch (request.getType()) {
            case NULL:
                invalidCommand();
                break;
            case SHOW_MATCH_HISTORY:
                showMatchHistory(request);
            case CREATE_ACCOUNT:
                createAccount(request);
                break;
            case LOGIN:
                login(request);
                break;
            case LEADERBOARD:
                showLeaderBoard(request);
                break;
            case SAVE:
                try {
                    save();
                } catch (OutOfMemoryError h) {
                    System.out.println("Saving error!");
                }
                break;
            case LOGOUT:
                logout();
                break;
            case HELP:
                help();
                break;
            case EXIT:
                exit();
                break;
            case ENTRANCE:
                enter(request);
                break;
            case SHOW_COLLECTION:
                showTheCollection();
                break;
            case SEARCH_COLLECTION:
                searchInCollection(request);
                break;
            case SAVE_IN_COLLECTION:
                saveCollection();
                break;
            case CREATE_DECK:
                createDeck(request);
                break;
            case DELETE_DECK:
                deleteDeck(request);
                break;
            case ADD:
                addToDeck(request);
                break;
            case REMOVE:
                removeFromDeck(request);
                break;
            case VALIDATION:
                validateDeck(request);
                break;
            case SELECT_DECK:
                selectDeck(request);
                break;
            case SHOW_ALL_DECK:
                showAllDecks(request);
                break;
            case SHOW_DECK:
                showDeck(request);
                break;
            case SEARCH:
                search(request);
                break;
            case BUY:
                buy(request);
                break;
            case SELL:
                sell(request);
                break;
            case SHOW:
                showShop();
                break;
            case SINGLE_PLAYER:
                setGameType(request);
                break;
            case MULTI_PLAYER:
                setGameType(request);
                break;
            case STORY:
                setProcess(request);
                break;
            case CUSTOM:
                setProcess(request);
                break;
            case KILL_ENEMY_HERO:
                setBattleMode(request);
                break;
            case COLLECTING:
                setBattleMode(request);
                break;
            case FLAG:
                setBattleMode(request);
                break;
            case SELECT_USER:
                selectUser(request);
                break;
            case GAME_INFO:
                gameInfo();
                break;
            case SHOW_MAP:
                showMap();
                break;
            case SHOW_MY_MININOS:
                showMyMinions();
                break;
            case SHOW_OPP_MINIONS:
                showOppMinions();
                break;
            case SHOW_CARD_INFO:
                showCardInfo(request);
                break;
            case SELECTION:
                select(request);
                break;
            case MOVE:
                moveToInBattle(request);
                break;
            case ATTACK:
                battleAttack(request);
                break;
            case COMBO:
                battleComboAttack(request);
                break;
            case USE_SP:
                specialPowerValidation();
                if (battle.validSpecialPower() == Message.NULL) {
                    useSpecialPower(request);
                }
                break;
            case SHOW_HAND:
                showHand();
                break;
            case INSERTION:
                insertCard(request);
                break;
            case END_TURN:
                endTurn();
                AiFunctions();
                break;
            case SHOW_COLLECTABLES:
                showCollectables();
                break;
            case SHOW_COLLECTABLE_INFO:
                showCollectableInfo();
                break;
            case USE_ITEM:
                useItem(request);
                break;
            case NEXT_CARD:
                showNextCard();
                break;
            case SHOW_CARDS:
                showCards();
                break;
            case END_GAME:
                endGame();
                break;
            case SHOW_MENU:
                showMenu();
                break;
        }
//        }
    }*/

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
            case GAME_TYPE:
                view.gameTypeMenu(buttons[Buttons.SINGLE_PLAYER.ordinal()], buttons[Buttons.MULTI_PLAYER.ordinal()]);
                file = new File("/Users/Nefario/ProjeCHEEEEZ/resources/resources/music/music_battlemap_firesofvictory.m4a");
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
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
                for (int i = 0; i < battle.getPlayerHands()[0].length ; i++) {
                    handCardGifs[i].setCard(battle.getPlayerHands()[0][i]);
                    handCardGifs[i].setImageView(setGifForCards(battle.getPlayerHands()[0][i]));
                }

                view.battleMenu(battle.getAccounts(), getImageViewGif(battle.getAccounts()[0], 0),
                        getImageViewGif(battle.getAccounts()[1], 1), polygon, imageViews[ImageViews.END_TURN.ordinal()],
                        labels[Labels.END_TURN.ordinal()],mana,handCards ,handCardGifs);
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
        }
        player.setAutoPlay(true);
        handlePolygon();
        handleButtons();
        handleTextFields();
        handleHeroGifs();
        handleMinions();
        //handleBattleCards();

    }

    public void handleMinions(){
        for (int i = 0; i <handCardGifs.length ; i++) {
            int finalI = i;
            handCardGifs[i].getImageView()[0].setOnMouseClicked(event -> {
                insideBattle = false;
                currentImageView[0] = handCardGifs[finalI].getImageView()[0];
            currentImageView[1] = handCardGifs[finalI].getImageView()[1];
            });
        }
    }

    public ImageView[] setGifForCards ( Card card){
        ImageView[] imageViews = new ImageView[3];
        switch (card.getName()){
            case "PERSIAN_CHAMPION":
            case "PERSIAN_COMMANDER":
                imageViews[0]= new ImageView(new Image("minionGifs/Alabaster Titan_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Alabaster Titan_idle.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Alabaster Titan_attack.gif"));
                break;
            case "PERSIAN_LANCER":
            case "PERSIAN_HORSEMAN":
                imageViews[0]= new ImageView(new Image("minionGifs/Spriggin_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Spriggin_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Spriggin_attack.gif"));
                break;

            case "PERSIAN_ARCHER":
            case "PERSIAN_SWORDS_WOMAN":
                imageViews[0]= new ImageView(new Image("minionGifs/Sunrise Cleric_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Sunrise Cleric_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Sunrise Cleric_attack.gif"));
                break;
            case "TURANIAN_ARCHER":
            case "TURANIAN_SPY":
                imageViews[0]= new ImageView(new Image("minionGifs/Worldcore Golem_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Worldcore Golem_attack.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Worldcore Golem_attack.gif"));
                break;
            case "TURANIAN_CATAPULT":
            case "TURANIAN_LANCER":
                imageViews[0]= new ImageView(new Image("minionGifs/Blood Taura_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Blood Taura_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Blood Taura_attack.gif"));
                break;
            case "TURANIAN_MACER":
            case "TURANIAN_PRINCE":
                imageViews[0]= new ImageView(new Image("minionGifs/Riftwalker_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Riftwalker_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Riftwalker_attack.gif"));
                break;
            case "BLACK_GIANT":
            case "CATAPULT_GIANT":
            case "HOG_RIDER_GIANT":
                imageViews[0]= new ImageView(new Image("minionGifs/Chaos Elemental_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Chaos Elemental_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Chaos Elemental_attack.gif"));
                break;
            case "TWO_HEADED_GIANT":
            case "GONDE_BACK_GIANT":
                imageViews[0]= new ImageView(new Image("minionGifs/Furiosa_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Furiosa_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Furiosa_attack.gif"));
                break;
            case "EAGLE":
            case "CYCLOPS":
            case "VENOM_SNAKE":
                imageViews[0]= new ImageView(new Image("minionGifs/Elkowl_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Elkowl_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Elkowl_attack.gif"));
                break;
            case "DRAGON":
            case "LION":
            case "GIANT_SNAKE":
                imageViews[0]= new ImageView(new Image("minionGifs/Chaos Elemental_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Chaos Elemental_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Chaos Elemental_attack.gif"));
                break;
            case "WHITE_WOLF":
            case "PALANG":
                imageViews[0]= new ImageView(new Image("minionGifs/Katastrophosaurus_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Katastrophosaurus_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Katastrophosaurus_attack.gif"));
                break;
            case "WOLF":
            case "WILD_HOG":
                imageViews[0]= new ImageView(new Image("minionGifs/Azurite Lion_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Azurite Lion_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Azurite Lion_attack.gif"));
                break;
            case "WITCH":
            case "NANE_WITCH":
                imageViews[0]= new ImageView(new Image("minionGifs/Pandora_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Pandora_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Pandora_attack.gif"));
                break;
            case "JEN":
            case "PIRAN":
                imageViews[0]= new ImageView(new Image("minionGifs/Cryptographer_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Cryptographer_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Cryptographer_attack.gif"));
                break;
            case "GIV":
            case "BAHMAN":
                imageViews[0]= new ImageView(new Image("minionGifs/Silverbeak_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Silverbeak_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Silverbeak_attack.gif"));
                break;
            case "ASHKBOOS":
            case "IRAJ":
                imageViews[0]= new ImageView(new Image("minionGifs/Fog_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Fog_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Fog_attack.gif"));
                break;
            case "NANE_SARMA":
            case "FOOLADZEREH":
            case "SIAVASH":
                imageViews[0]= new ImageView(new Image("minionGifs/Healing Mystic_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Healing Mystic_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Healing Mystic_attack.gif"));
                break;
            case "SHAGHUL":
            case "ARZHANG":
                imageViews[0]= new ImageView(new Image("minionGifs/Kin_idle.gif"));
                imageViews[1]= new ImageView(new Image("minionGifs/Kin_run.gif"));
                imageViews[2]= new ImageView(new Image("minionGifs/Kin_attack.gif"));
                break;
            case "TOTAL_DISARM":
            case "SACRIFICE":
                    imageViews[0]= new ImageView(new Image("spell/Flash Freeze_active.gif"));
                    imageViews[1]= new ImageView(new Image("spell/Flash Freeze_active.gif"));
                    imageViews[2]= new ImageView(new Image("spell/Flash Freeze_active.gif"));
                break;
            case "EMPOWER":
            case "FIREBALL":
            case "GOD_STRENGTH":
                imageViews[0]= new ImageView(new Image("spell/Unleash the Evil_active.gif"));
                imageViews[1]= new ImageView(new Image("spell/Unleash the Evil_active.gif"));
                imageViews[2]= new ImageView(new Image("spell/Unleash the Evil_active.gif"));
                break;
            case "HELL_FIRE":
            case "LIGHTNING_BOLT":
            case "POISON_LAKE":
                imageViews[0]= new ImageView(new Image("spell/Lasting Judgement_active.gif"));
                imageViews[1]= new ImageView(new Image("spell/Lasting Judgement_active.gif"));
                imageViews[2]= new ImageView(new Image("spell/Lasting Judgement_active.gif"));
                break;
            case "MADNESS":
            case "ALL_DISARM":
            case "ALL_POISON":
                imageViews[0]= new ImageView(new Image("spell/Icebreak Ambush_active.gif"));
                imageViews[1]= new ImageView(new Image("spell/Icebreak Ambush_active.giff"));
                imageViews[2]= new ImageView(new Image("spell/Icebreak Ambush_active.gif"));
                break;
            case "DISPEL":
            case "ALL_ATTACK":
            case "POWER_UP":
                imageViews[0]= new ImageView(new Image("spell/Horrific Visage_active.gif"));
                imageViews[1]= new ImageView(new Image("spell/Horrific Visage_active.gif"));
                imageViews[2]= new ImageView(new Image("spell/Horrific Visage_active.gif"));
                break;
            case "ALL_POWER":
            case "HEALTH_WITH_PROFIT":
            case "WEAKENING":
                imageViews[0]= new ImageView(new Image("spell/Homeostatic Rebuke_active.gif"));
                imageViews[1]= new ImageView(new Image("spell/Homeostatic Rebuke_active.gif"));
                imageViews[2]= new ImageView(new Image("spell/Homeostatic Rebuke_active.gif"));
                break;
            case "KINGS_GUARD":
            case "SHOCK":
            case "AREA_DISPEL":
                imageViews[0]= new ImageView(new Image("spell/Aspect of Shim'Zar_active.gif"));
                imageViews[1]= new ImageView(new Image("spell/Aspect of Shim'Zar_active.gif"));
                imageViews[2]= new ImageView(new Image("spell/Aspect of Shim'Zar_active.gif"));
                break;
        }
        return imageViews;
    }

    private void handleBattleCards(){
        for (int i = 0; i < 40 ; i++) {
            if(battleCards[i].getCard()!=null){
                int finalI = i;
                battleCards[i].getImageView()[0].setOnMouseClicked(event -> battleCard=battleCards[finalI]);
            }
        }
    }

    private void handleHeroGifs() {
        for (int i = 0; i < 2; i++) {
            int a = i;
            heroes[heroId[i][0]][0].setOnMouseClicked(event -> {
                insideBattle = true;
                if(lastSelectedCardId[0]!=0){
                    battle.attack(heroId[a][1],Card.getCardByID(lastSelectedCardId[0],battle.getFieldCards()[(a+1)%2]));
                    currentImageView[0] = heroes[heroId[(a+1)%2][0]][0];
                    currentImageView[1] = heroes[heroId[(a+1)%2][0]][1];
                    currentImageView[2] = heroes[heroId[(a+1)%2][0]][2];
                    view.attack(currentImageView);
                    System.out.println("hamle");
                    lastSelectedCardId[0]=0;
                }else {
                    battle.selectCard(heroId[a][1]);
                    currentImageView[0] = heroes[heroId[a][0]][0];
                    currentImageView[1] = heroes[heroId[a][0]][1];
                    currentCardId[0] = heroId[a][1];
                    currentCardId[1] = heroId[a][0];
                    lastSelectedCardId[0] = currentCardId[0];
                    lastSelectedCardId[1] = currentCardId[1];
                }
            });
        }
    }

    private void handlePolygon() {
        for (int i = 0; i < polygon.length; i++) {
            int a = i;
            polygon[i].setOnMouseClicked(event -> {
                view.move(polygon[a].getPoints().get(0), polygon[a].getPoints().get(1), currentImageView[0], currentImageView[1]);
                battle.moveTo(new Coordinate(a-(a/9),a/9));
                lastSelectedCardId[0]=0;
                if(!insideBattle){
                    System.out.println("shitttrfg");
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
        buttons[Buttons.SHOP.ordinal()].setOnMouseClicked(event -> {
            cardsInShop = shop.getCards();
            itemsInShop = shop.getItems();
            menu.setStat(MenuStat.SHOP);
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
                if (i <battle.getAccounts()[0].getMana()) {
                    mana[i].setImage(new Image("resources/ui/icon_mana@2x.png"));
                } else {
                    mana[i].setImage(new Image("resources/ui/icon_mana_inactive@2x.png"));
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
            menu.setStat(MenuStat.BATTLE);
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
            menu.setStat(MenuStat.BATTLE);
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

    private ImageView getImageViewGif(Account account, int a) {
        heroId[a][1] = account.getCollection().getMainDeck().getHero().getId();
        switch (account.getCollection().getMainDeck().getHero().getName()) {
            case "WHITE_DIV":
                heroId[a][0] = 0;
                heroes[0][1] = new ImageView(new Image("gifs/Abomination_run.gif"));
                heroes[0][2]= new ImageView(new Image("gifs/Abomination_attack.gif"));
                return heroes[0][0] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            case "ZAHAK":
                heroId[a][0] = 1;
                heroes[1][2]= new ImageView(new Image("gifs/Abomination_attack.gif"));
                heroes[1][1] = new ImageView(new Image("gifs/Abomination_run.gif"));
                return heroes[1][0] = new ImageView(new Image("gifs/Abomination_idle.gif"));
            case "ARASH":
                heroId[a][0] = 2;
                heroes[2][2]= new ImageView(new Image("gifs/f5_altgeneraltier2_attack.gif"));
                heroes[2][1] = new ImageView(new Image("gifs/f5_altgeneraltier2_run.gif"));
                return heroes[2][0] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
            case "SIMORGH":
                heroId[a][0] = 3;
                heroes[3][2]= new ImageView(new Image("gifs/f4_altgeneraltier2_attack.gif"));
                heroes[3][1] = new ImageView(new Image("gifs/f4_altgeneraltier2_run.gif"));
                return heroes[3][0] = new ImageView(new Image("gifs/f4_altgeneraltier2_idle.gif"));
            case "SEVEN_HEADED_DRAGON":
                heroId[a][0] = 4;
                heroes[4][2]= new ImageView(new Image("gifs/f5_altgeneraltier2_attack.gif"));
                heroes[4][1] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
                return heroes[4][0] = new ImageView(new Image("gifs/f5_altgeneraltier2_idle.gif"));
            case "RAKHSH":
                heroId[a][0] = 5;
                heroes[5][2]= new ImageView(new Image("gifs/f6_altgeneraltier2_attack.gif"));
                heroes[5][1] = new ImageView(new Image("gifs/f6_altgeneraltier2_run.gif"));
                return heroes[5][0] = new ImageView(new Image("gifs/f6_altgeneraltier2_idle.gif"));
            case "KAVEH":
                heroId[a][0] = 6;
                heroes[6][2]= new ImageView(new Image("gifs/boss_cindera_attack.gif"));
                heroes[6][1] = new ImageView(new Image("gifs/boss_cindera_run.gif"));
                return heroes[6][0] = new ImageView(new Image("gifs/boss_cindera_idle.gif"));
            case "AFSANEH":
                heroId[a][0] = 7;
                heroes[7][2]= new ImageView(new Image("gifs/f6_altgeneraltier2_attack.gif"));
                heroes[7][1] = new ImageView(new Image("gifs/f6_altgeneraltier2_run.gif"));
                return heroes[7][0] = new ImageView(new Image("gifs/f6_altgeneraltier2_idle.gif"));
            case "ESFANDIAR":
                heroId[a][0] = 8;
                heroes[8][2]= new ImageView(new Image("gifs/Brome Warcrest_attack.gif"));
                heroes[8][1] = new ImageView(new Image("gifs/Brome Warcrest_run.gif"));
                return heroes[8][0] = new ImageView(new Image("gifs/Brome Warcrest_idle.gif"));
            case "ROSTAM":
                heroId[a][0] = 9;
                heroes[9][2]= new ImageView(new Image("gifs/f1_tier2general_attack.gif"));
                heroes[9][1] = new ImageView(new Image("gifs/f1_tier2general_attack.gif"));
                return heroes[9][0] = new ImageView(new Image("gifs/f1_tier2general_idle.gif"));
        }
        return null;
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
