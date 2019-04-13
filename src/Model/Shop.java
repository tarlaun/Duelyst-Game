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
        if (!Card.getCardByID(search(objectName),cards).equals(null)) {
            account.getCollection().getCards().add(Card.getCardByID(search(objectName), cards));
            account.modifyAccountBudget();
        }
        if (!Item.getItemByID(search(objectName),items).equals(null)) {
            account.getCollection().getItems().add(Item.getItemByID(search(objectName), items));
            account.modifyAccountBudget();
        }
        return Message.SUCCESSFUL_PURCHASE;
    }

    public boolean sell(int objectId,Account account) {
        if (!Card.getCardByID(objectId,account.getCollection().getCards()).equals(null)) {
            account.modifyAccountBudget(Card.getCardByID(objectId,account.getCollection().getCards()).getPrice());
            account.getCollection().getCards().remove(Card.getCardByID(objectId,account.getCollection().getCards() ));
            return true;
        }
        if (!Item.getItemByID(objectId,account.getCollection().getItems()).equals(null)) {
//            account.modifyAccountBudget(Item.getItemByID(objectId,account.getCollection().getItems()));
            account.getCollection().getItems().remove(Item.getItemByID(objectId,account.getCollection().getItems()));
            return true;
        }
        return false;
    }

    public void show() {

    }

    public void showCollection() {

    }


}
