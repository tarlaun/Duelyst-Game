package Model;

import View.Message;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private File file;
    private static final Shop shop = new Shop();

    private Shop() {

    }

    public static Shop getInstance() {
        return shop;
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

/*
    public Message buy(String objectName, Account account) {

        if (search(objectName) == -1) {
            return Message.OBJECT_NOT_FOUND;
        }

        if (account.getBudget() < cards.get(search(objectName)).getPrice()) {
            return Message.INSUFFICIENCY;
        }
        if (account.getCollection().getItems().size() == 3) {
            return Message.MAXIMUM_ITEM_COUNT;
        }
        Card card = Card.getCardByID(search(objectName), cards.toArray(Card[]::new));
        if (!card.equals(null)) {
            account.getCollection().getCards().add(card);
            account.modifyAccountBudget(-card.getPrice());
        }
        Item item = Item.getItemByID(search(objectName), items.toArray(Item[]::new));
        if (!item.equals(null)) {
            account.getCollection().getItems().add(item);
            account.modifyAccountBudget(-item.getPrice());
        }
        return Message.SUCCESSFUL_PURCHASE;
    }
*/

/*
    public boolean sell(int objectId, Account account) {
        Card card = Card.getCardByID(objectId, account.getCollection().getCards()
                .toArray(new Card[account.getCollection().getCards().size()]));
        if (!card.equals(null)) {
            account.modifyAccountBudget(card.getPrice());
            account.getCollection().getCards().remove(card);
            return true;
        }
        Item item = Item.getItemByID(objectId, account.getCollection().getItems().toArray(Item[]::new));
        if (!item.equals(null)) {
            account.modifyAccountBudget(item.getPrice());
            account.getCollection().getItems().remove(item);
            return true;
        }
        return false;
    }
*/


    public void addCard(Card card) {
        cards.add(card);
    }

/*
    public void initialCards() {
        Gson gson = new Gson();
        file = new File("Heroes");
        Scanner scanner;
        String[] info;
        int idCounter = 0;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                info = scanner.nextLine().split(Constants.CARD_INFO_SPLITTER);
                if (Integer.parseInt(info[MainInfoOrder.MAX_MOVE.ordinal()]) == Constants.UNDEFINED_MAX_MOVE)
                    cards.add(new Spell(idCounter, info));
            }
        } catch (FileNotFoundException error) {
            error.printStackTrace();
        }

    }
*/

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
