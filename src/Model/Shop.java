package Model;

import View.Message;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private static final Shop shop = new Shop();
    private ArrayList<Card> auctionCards = new ArrayList<>();
    private ArrayList<Item> auctionItems = new ArrayList<>();
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
        Card card = Card.getCardByID(search(objectName), cards.toArray(new Card[0]));
        if (card != null) {
            if (card.getCountInSerie() == 0)
                return Message.NOT_AVAILABLE;
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
            card.decrementCount();
        }
        Item item = Item.getItemByID(search(objectName), items.toArray(new Item[0]));
        if (item != null) {
            if (item.getCountInShop() == 0)
                return Message.NOT_AVAILABLE;
            Item instance = new Item(item);
            if (account.getCollection().getItems().size() == 3) {
                return Message.MAXIMUM_ITEM_COUNT;
            }
            account.getCollection().getItems().add(item);
            account.modifyAccountBudget(-item.getPrice());
            game.incrementItemId();
            instance.setId(game.getLastItemId());
            this.items.add(instance);
            item.decrementCount();
        }
        return Message.SUCCESSFUL_PURCHASE;
    }

    public boolean sell(int objectId, Account account) {
        Card card = Card.getCardByID(objectId, account.getCollection().getCards().toArray(new Card[0]));
        if (card != null) {
            ((Card) searchByName(card.getName())).incrementCount();
            account.modifyAccountBudget(card.getPrice());
            account.getCollection().getCards().remove(card);
            account.getCollection().deleteFromAllDecks(card.getId());
            return true;
        }
        Item item = Item.getItemByID(objectId, account.getCollection().getItems().toArray(new Item[0]));
        if (item != null) {
            ((Item) searchByName(item.getName())).incrementCount();
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

    public ArrayList<Card> getAuctionCards() {
        return auctionCards;
    }

    public ArrayList<Item> getAuctionItems() {
        return auctionItems;
    }

    public Message fetchAuction(int id, Account account) {
        Card card = Card.getCardByID(id, auctionCards.toArray(new Card[0]));
        if (card != null) {
            if (account.getBudget() < card.getAuctionPrice())
                return Message.INSUFFICIENCY;
            account.getCollection().getCards().add(card);
            auctionCards.remove(card);
            account.modifyAccountBudget(-card.getAuctionPrice());
            Account auctioneer = Account.getAccountByName(card.getAuctioneer(), game.getAccounts());
            assert auctioneer != null;
            auctioneer.getCollection().getCards().remove(card);
            card.setAuction((long) 0, null, 0);
        } else {
            Item item = Item.getItemByID(id, auctionItems.toArray(new Item[0]));
            if (account.getCollection().getItems().size() == 3)
                return Message.MAXIMUM_ITEM_COUNT;
            if (account.getBudget() < item.getAuctionPrice())
                return Message.INSUFFICIENCY;
            account.getCollection().getItems().add(item);
            auctionItems.remove(item);
            account.modifyAccountBudget(-item.getAuctionPrice());
            Account auctioneer = Account.getAccountByName(item.getAuctioneer(), game.getAccounts());
            assert auctioneer != null;
            auctioneer.getCollection().getItems().remove(item);
            item.setAuction((long) 0, null, 0);
        }
        return Message.SUCCESSFUL_PURCHASE;
    }

    public void auction(int id, Account account, int initialPrice) {
        Card card = Card.getCardByID(id, account.getCollection().getCards().toArray(new Card[0]));
        Item item = Item.getItemByID(id, account.getCollection().getItems().toArray(new Item[0]));
        if (card != null) {
            card.setAuction(System.currentTimeMillis(), account.getName(), initialPrice);
            shop.getAuctionCards().add(card);
        } else {
            item.setAuction(System.currentTimeMillis(), account.getName(), initialPrice);
            shop.getAuctionItems().add(item);
        }
        new Thread(() -> {
            try {
                Thread.sleep(Constants.AUCTION_DURATION_MILLIS);
                if (card != null) {
                    if (card.getAuctionFetcher() == null)
                        discardAuction(id, account);
                    else
                        fetchAuction(id, Account.getAccountByName(card.getAuctionFetcher(), game.getAccounts()));
                } else {
                    if (item.getAuctionFetcher() == null)
                        discardAuction(id, account);
                    else
                        fetchAuction(id, Account.getAccountByName(item.getAuctionFetcher(), game.getAccounts()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void discardAuction(int id, Account account) {
        Card card = Card.getCardByID(id, shop.getAuctionCards().toArray(new Card[0]));
        if (card != null) {
            shop.getAuctionCards().remove(card);
            card.setAuction((long) 0, account.getName(), 0);
        } else {
            Item item = Item.getItemByID(id, shop.getAuctionItems().toArray(new Item[0]));
            shop.getAuctionItems().remove(item);
            item.setAuction((long) 0, account.getName(), 0);
        }
    }

    public Message increaseAuction(int id, Account account, int price) {
        Card card = Card.getCardByID(id, shop.getAuctionCards().toArray(new Card[0]));
        Item item = Item.getItemByID(id, shop.getAuctionItems().toArray(new Item[0]));
        if (card != null) {
            if (card.getAuctionPrice() >= price)
                return Message.INVALID_AUCTION;
            card.setAuctionPrice(price);
            card.setAuctionFetcher(account.getName());
        } else {
            if (item.getAuctionPrice() >= price)
                return Message.INVALID_AUCTION;
            item.setAuctionPrice(price);
            item.setAuctionFetcher(account.getName());
        }
        return Message.VALID_AUCTION;
    }
}
