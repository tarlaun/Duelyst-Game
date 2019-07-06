package Model;

import View.Message;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private static final Shop shop = new Shop();
    private Game game;

    private Shop() {

    }

    public Game getGame() {
        return game;
    }

    public static Shop getInstance() {
        return shop;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int search(String objectName) {
        for (Card card : cards) {
            if (card.getName().equals(objectName)) {
                return card.getId();
            }
        }
        for (Item item : items) {
            if (item.getName().equals(objectName)) {
                return item.getId();
            }
        }

        return -1;
    }

    public Object searchByName(String objectName) {
        for (Card card : cards) {
            if (card.getName().equals(objectName)) {
                return card;
            }
        }
        for (Item item : items) {
            if (item.getName().equals(objectName)) {
                return item;
            }
        }

        return null;
    }

    public ArrayList<Integer> searchCollection(String objectName, Collection collection) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Card card : collection.getCards()) {
            if (card.getName().equals(objectName)) {
                list.add(card.getId());
            }
        }
        for (Item item : items) {
            if (item.getName().equals(objectName)) {
                list.add(item.getId());
            }
        }
        return list;
    }

    public Message buy(String objectName, Account account) throws OutOfMemoryError {

        if (search(objectName) == -1) {
            return Message.OBJECT_NOT_FOUND;
        }

        try {
            if (account.getBudget() < cards.get(cards.indexOf(Card.getCardByID(search(objectName),
                    cards.toArray(new Card[cards.size()])))).getPrice()) {
                return Message.INSUFFICIENCY;
            }

            if (account.getBudget() < items.get(items.indexOf(Item.getItemByID(search(objectName),
                    items.toArray(new Item[items.size()])))).getPrice()) {
                return Message.INSUFFICIENCY;
            }
        } catch (IndexOutOfBoundsException e) {

        }
        Card card = Card.getCardByID(search(objectName), cards.toArray(new Card[cards.size()]));
        if (card != null) {
            Card instance = new Card(card);
            if (card.getType().equals("Hero")) {
                game.incrementHeroId();
                instance.setId(game.getLastHeroId());
            }
            if (card.getType().equals("Minon")) {
                game.incrementMinionId();
                instance.setId(game.getLastMinionId());
            }
            if (card.getType().equals("Spell")) {
                game.incrementSpellId();
                instance.setId(game.getLastSpellId());
            }
            this.cards.add(instance);
            account.getCollection().getCards().add(card);
            account.modifyAccountBudget(-card.getPrice());
        }
        Item item = Item.getItemByID(search(objectName), items.toArray(new Item[items.size()]));
        if (item != null) {
            Item instance = new Item(item);
            if (account.getCollection().getItems().size() == 3) {
                return Message.MAXIMUM_ITEM_COUNT;
            }
            account.getCollection().getItems().add(item);
            account.modifyAccountBudget(-item.getPrice());
            game.incrementItemId();
            instance.setId(game.getLastItemId());
            shop.items.add(instance);
        }
        return Message.SUCCESSFUL_PURCHASE;
    }

    public boolean sell(int objectId, Account account) {
        Card card = Card.getCardByID(objectId, account.getCollection().getCards()
                .toArray(new Card[account.getCollection().getCards().size()]));
        if (card != null) {
            account.modifyAccountBudget(card.getPrice());
            account.getCollection().getCards().remove(card);
            account.getCollection().deleteFromAllDecks(card.getId());
            return true;
        }
        Item item = Item.getItemByID(objectId, account.getCollection().getItems().toArray(new Item[
                account.getCollection().getItems().size()]));
        if (item != null) {
            account.modifyAccountBudget(item.getPrice());
            account.getCollection().getItems().remove(item);
            account.getCollection().deleteFromAllDecks(item.getId());
            return true;
        }
        return false;
    }


    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Shop fromJson(String json) {
        return new Gson().fromJson(json, Shop.class);
    }
}
