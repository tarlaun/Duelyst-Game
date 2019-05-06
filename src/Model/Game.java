package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Game {
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Account> loggedInAccounts = new ArrayList<>();
    private static final Game SINGLETON_CLASS = new Game();
    private GameType gameType;
    private BattleMode mode;
    private Menu menu = Menu.getInstance();
    private Shop shop = Shop.getInstance();
    private int lastSpellId = Constants.spellId;
    private int lastMinionId = Constants.minionId;
    private int lastHeroId = Constants.heroId;
    private int lastItemId = Constants.itemId;
    private int lastAccountId = Constants.accoutnId;

    private Game() {

    }

    public int getLastSpellId() {
        return lastSpellId;
    }

    public void incrementSpellId() {
        this.lastSpellId++;
    }

    public int getLastMinionId() {
        return lastMinionId;
    }

    public void incrementMinionId() {
        this.lastMinionId++;
    }

    public int getLastHeroId() {
        return lastHeroId;
    }

    public void incrementHeroId() {
        this.lastHeroId++;
    }

    public int getLastItemId() {
        return lastItemId;
    }

    public void incrementItemId() {
        this.lastItemId++;
    }

    public int getLastAccountId() {
        return lastAccountId;
    }

    public void incrementAccountId() {
        this.lastAccountId++;
    }

    public void incrementSpellId(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setLoggedInAccounts(ArrayList<Account> loggedInAccounts) {
        this.loggedInAccounts = loggedInAccounts;
    }

    public static Game getSingletonClass() {
        return SINGLETON_CLASS;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public BattleMode getMode() {
        return mode;
    }

    public void setMode(BattleMode mode) {
        this.mode = mode;
    }

    private ArrayList<Account> getLoggedInAccounts() {
        for (Account account :
                accounts) {
            if (account.isLoggedIn()) {
                loggedInAccounts.add(account);
            }
        }
        return loggedInAccounts;
    }

    public void createBattle() {
        Battle battle = new Battle(loggedInAccounts.toArray(new Account[loggedInAccounts.size()]), gameType, mode);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static Game getInstance() {
        return SINGLETON_CLASS;
    }

    public boolean logout(Account account) {
        account.setLoggedIn(false);
        save(account);
        menu.setStat(MenuStat.MAIN);
        return true;
    }

    public void save(Account account) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(account);
        try {
            FileWriter writer = new FileWriter(account.getName() + ".json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortAccounts() {
        Comparator<Account> compareById = Comparator.comparingInt(Account::getWins);
        accounts.sort(compareById.reversed());
    }

    public void initializeAccounts() throws Exception {
        File dir = new File("./");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        if (!file.getName().matches("\\w+[.]json"))
                            continue;
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        Account account = new Gson().fromJson(reader, Account.class);
                        accountObjectInitializer(account);
                        accounts.add(account);
                    }
                }
            }
        }
    }

    private void accountObjectInitializer(Account account) {
        Collection temp = new Collection();
        for (Card card : account.getCollection().getCards()) {
            for (Deck deck : account.getCollection().getDecks()) {
                if (Card.getCardByID(card.getId(), deck.getCards().toArray(new Card[deck.getCards().size()])) != null)
                    continue;
                temp.getCards().add(card);
            }
        }
        for (Item item : account.getCollection().getItems()) {
            for (Deck deck : account.getCollection().getDecks()) {
                if (Item.getItemByID(item.getId(), deck.getItem()) != null)
                    continue;
                temp.getItems().add(item);
            }
        }
        account.getCollection().getCards().clear();
        account.getCollection().getItems().clear();
        accountCardRefactor(account.getCollection(), temp.getCards().toArray(new Card[temp.getCards().size()]));
        accountItemRefactor(account.getCollection(), temp.getItems().toArray(new Item[temp.getItems().size()]));
        for (Deck deck : account.getCollection().getDecks()) {
            accountCardRefactor(account.getCollection(), deck.getCards().toArray(new Card[deck.getCards().size()]));
            accountItemRefactor(account.getCollection(), deck.getItem());
        }
    }

    private void accountCardRefactor(Collection collection, Card... cards) {
        for (int i = 0; i < cards.length; i++) {
            switch (cards[i].getClass().getName()) {
                case "Hero":
                    cards[i].setId(++lastHeroId);
                    break;
                case "Minion":
                    cards[i].setId(++lastMinionId);
                    break;
                case "Spell":
                    cards[i].setId(++lastSpellId);
                    break;
            }
            collection.getCards().add(cards[i]);
        }
    }

    private void accountItemRefactor(Collection collection, Item... items) {
        for (int i = 0; i < items.length; i++) {
            items[i].setId(++lastItemId);
            collection.getItems().add(items[i]);
        }
    }

    private void sortCards(ArrayList<Card> cards) {
        Comparator<Card> compareById = Comparator.comparingInt(Card::getId);
        cards.sort(compareById.reversed());
    }

    private void sortItems(ArrayList<Item> items) {
        Comparator<Item> compareById = Comparator.comparingInt(Item::getId);
        items.sort(compareById.reversed());
    }

    public void initializeHero() throws Exception {
        File dir = new File("./src/Objects/Cards/Heroes");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        Hero hero = new Gson().fromJson(reader, Hero.class);
                        hero.setId(++lastHeroId);
                        shop.getCards().add(hero);
                    }
                }
            }
        }
    }

    public void initializeMinion() throws Exception {
        File dir = new File("./src/Objects/Cards/Minions");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        Minion minion = new Gson().fromJson(reader, Minion.class);
                        minion.setId(++lastMinionId);
                        shop.getCards().add(minion);
                    }
                }
            }
        }
    }

    public void initializeSpell() throws Exception {
        File dir = new File("./src/Objects/Cards/Spells");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        Spell spell = new Gson().fromJson(reader, Spell.class);
                        spell.setId(++lastSpellId);
                        shop.getCards().add(spell);
                    }
                }
            }
        }
    }

}
