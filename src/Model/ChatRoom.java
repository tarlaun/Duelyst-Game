package Model;

import java.util.ArrayList;

public class ChatRoom {
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<PM> pms = new ArrayList<>();

    private static final ChatRoom CHAT_ROOM = new ChatRoom();

    private ChatRoom() {

    }

    public static ChatRoom getInstance() {
        return CHAT_ROOM;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<PM> getPMs() {
        return pms;
    }
}
