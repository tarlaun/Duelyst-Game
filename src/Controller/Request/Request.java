package Controller.Request;

import Model.BattleMode;
import Model.Coordinate;
import Model.GameType;
import Model.Process;
import Server.Controller.SocketPair;
import View.Message;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Scanner;

public class Request {
    private int port;
    private RequestType type;
    private transient DirectRequest directRequest;
    private String[] args;

    public Request(int port, RequestType type, String... args) {
        this.port = port;
        this.type = type;
        this.args = args;
    }

    public static Request fromJson(String json) throws JsonSyntaxException {
        return new Gson().fromJson(json, Request.class);
    }

    public int getPort() {
        return port;
    }

    public RequestType getType() {
        return type;
    }

    public DirectRequest getDirectRequest() {
        switch (type) {
            case LOGIN:
                directRequest = new LoginRequest(args);
                break;
            case CREATE_ACCOUNT:
                directRequest = new CreateAccountRequest(args);
                break;
            case LOGOUT:
                directRequest = new LogoutRequest(args);
                break;
            case SAVE:
                directRequest = new SaveRequest(args);
                break;
            case SHOP:
                directRequest = new ShopRequest();
                break;
            case BUY:
                directRequest = new BuyRequest(args);
                break;
            case SELL:
                directRequest = new SellRequest(args);
                break;
            case SET_AUCTION:
                directRequest = new AuctionRequest(args);
                break;
            case GET_AUCTION:
                directRequest = new FetchAuctionRequest(args);
                break;
            case INCREASE_AUCTION:
                directRequest = new IncreaseAuctionRequest(args);
                break;
            case DISCARD_AUCTION:
                directRequest = new DiscardAuction(args);
                break;
            case CHAT_ROOM:
                directRequest = new ChatRoomRequest(args);
                break;
            case SEND_MESSAGE:
                directRequest = new SendRequest(args);
                break;
            case ENTER_CHAT:
                directRequest = new EnterChatRequest(args);
                break;
        }
        return directRequest;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void sendTo(SocketPair socketPair) {
        synchronized (socketPair) {
            socketPair.getFormatter().format(this.toJson() + "\n");
            socketPair.getFormatter().flush();
        }
    }
}
