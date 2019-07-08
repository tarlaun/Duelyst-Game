package Server.Controller;

import Controller.Request.*;
import Model.*;
import View.AlertMessage;
import View.Message;
import javafx.scene.control.Alert;

import java.net.Socket;
import java.util.HashMap;

public class RequestManger {
    private static final RequestManger REQUEST_MANGER = new RequestManger();
    private Game game = Game.getInstance();
    private Shop shop = Shop.getInstance();
    private final HashMap<Integer, SocketPair> sockets = new HashMap<>();
    private final HashMap<String, SocketPair> accountSockets = new HashMap<>();

    private RequestManger() {

    }

    public static RequestManger getInstance() {
        return REQUEST_MANGER;
    }

    public void addSocket(SocketPair socketPair) {
        sockets.put(socketPair.getSocket().getPort(), socketPair);
    }

    public void addOnline(String name, SocketPair socketPair) {
        accountSockets.put(name, socketPair);
    }

    public void removeUser(String name) {
        accountSockets.remove(name);
    }

    public void removeSocket(Socket socket) {
        sockets.remove(socket.getPort());
    }

    public String login(Request request, SocketPair socketPair) {
        LoginRequest loginRequest = (LoginRequest) request.getDirectRequest();
        Message message = Account.login(loginRequest.getUserName(), loginRequest.getPassword());
        if (message == Message.SUCCESSFUL_LOGIN) {
            Account.getAccountByName(loginRequest.getUserName(), game.getAccounts()).setLoggedIn(true);
            addOnline(loginRequest.getUserName(), socketPair);
            return Account.getAccountByName(loginRequest.getUserName(), game.getAccounts()).toJson();
        } else {
            return message.toJson();
        }
    }


    public String createAccount(Request request) {
        CreateAccountRequest createRequest = (CreateAccountRequest) request.getDirectRequest();
        if (Account.getAccountByName(createRequest.getUserName(), game.getAccounts()) != null) {
            return Message.EXISTING_ACCOUNT.toJson();
        } else if (createRequest.getUserName().equals("") || createRequest.getPassword().equals("")) {
            return Message.INAPPROPRIATE_PASSWORD.toJson();
        } else {
            Account account = new Account(createRequest.getUserName(), createRequest.getPassword());
            game.getAccounts().add(account);
            game.save(account);
            return account.toJson();
        }
    }

    public String logout(Request request) {
        LogoutRequest logoutRequest = (LogoutRequest) request.getDirectRequest();
        game.logout(Account.getAccountByName(logoutRequest.getUserName(), game.getAccounts()));
        removeUser(logoutRequest.getUserName());
        return Message.SUCCESSFUL_LOGOUT.toJson();
    }

    public String save(Request request) {
        SaveRequest saveRequest = (SaveRequest) request.getDirectRequest();
        game.save(saveRequest.getAccount());
        return Message.SUCCESSFUL_SAVE.toJson();
    }

    public String shop(Request request) {
        ShopRequest shopRequest = (ShopRequest) request.getDirectRequest();
        shop.setGame(game);
        return shop.toJson();
    }

    public String buy(Request request) {
        BuyRequest buyRequest = (BuyRequest) request.getDirectRequest();
        Message message = shop.buy(buyRequest.getName(), buyRequest.getAccount());
        if (message == Message.SUCCESSFUL_PURCHASE) {
            return buyRequest.getAccount().toJson();
        } else {
            return message.toJson();
        }
    }

    public String sell(Request request) {
        SellRequest sellRequest = (SellRequest) request.getDirectRequest();
        return sellRequest.getAccount().toJson();
    }

    public String auction(Request request) {
        AuctionRequest auctionRequest = (AuctionRequest) request.getDirectRequest();
        shop.auction(auctionRequest.getId(), auctionRequest.getAccount(), auctionRequest.getInitialPrice());
        return auctionRequest.getAccount().toJson();
    }

    public String fetchAuction(Request request) {
        FetchAuctionRequest fetchAuctionRequest = (FetchAuctionRequest) request.getDirectRequest();
        Message message = shop.fetchAuction(fetchAuctionRequest.getId(), fetchAuctionRequest.getFetcher());
        if (message == Message.SUCCESSFUL_PURCHASE) {
            return fetchAuctionRequest.getFetcher().toJson();
        } else {
            return message.toJson();
        }
    }

    public String increaseAuction(Request request) {
        IncreaseAuctionRequest increaseAuctionRequest = (IncreaseAuctionRequest) request.getDirectRequest();
        Message message = shop.increaseAuction(increaseAuctionRequest.getId(), increaseAuctionRequest.getAccount(),
                increaseAuctionRequest.getPrice());
        if (message == Message.VALID_AUCTION)
            return increaseAuctionRequest.getAccount().toJson();
        return message.toJson();
    }

    public String discardAuction(Request request) {
        DiscardAuction discardAuction = (DiscardAuction) request.getDirectRequest();
        shop.discardAuction(discardAuction.getId(), discardAuction.getAccount());
        return discardAuction.getAccount().toJson();
    }
}
