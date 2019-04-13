package Model;

import View.Message;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public int search(String objectName) {
        for (Card card: cards) {
            if(card.getName().equals(objectName)){
                return card.getId();
            }
        }
        for (Item item : items ) {
            if(item.getName().equals(objectName)){
                return item.getId();
            }
        }

        return -1;
    }

    public ArrayList<Integer> searchCollection(String objectName,Collection collection) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Card card: collection.getCards()) {
            if(card.getName().equals(objectName)){
               list.add(card.getId());
            }
        }
        for (Item item : items ) {
            if(item.getName().equals(objectName)){
                list.add(item.getId());
            }
        }
        return list;
    }

    public Message buy(String objectName,Account account) {

        if(search(objectName)==-1){
            return Message.OBJECT_NOT_FOUND;
        }

         if(account.getBudget()<cards.get(search(objectName)).getPrice()){
            return Message.INSUFFICIENCY;
        }
        if(account.getCollection().getItems().size()==3){
            return Message.MAXIMUM_ITEM_COUNT;
        }
         Card card = Card.getCardByID(search(objectName),cards);
        if (!card.equals(null)) {
            account.getCollection().getCards().add(card);
            account.modifyAccountBudget(-card.getPrice());
        }
        Item item =Item.getItemByID(search(objectName),items);
        if (!item.equals(null)) {
            account.getCollection().getItems().add(item);
            account.modifyAccountBudget(-item.getPrice());
        }
        return Message.SUCCESSFUL_PURCHASE;
    }

    public boolean sell(int objectId,Account account) {
        Card card=Card.getCardByID(objectId,account.getCollection().getCards()
                .toArray(new Card[account.getCollection().getCards().size()]));
        if (!card.equals(null)) {
            account.modifyAccountBudget(card.getPrice());
            account.getCollection().getCards().remove(card);
            return true;
        }
        Item item=Item.getItemByID(objectId,account.getCollection().getItems());
        if (!item.equals(null)) {
            account.modifyAccountBudget(item.getPrice());
            account.getCollection().getItems().remove(item);
            return true;
        }
        return false;
    }

    public void show() {

    }

    public void showCollection() {

    }


}
