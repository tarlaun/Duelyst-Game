package Server.Controller;

import Controller.Request.*;
import Model.*;
import View.AlertMessage;
import View.Message;
import Controller.Request.Request;

import java.net.Socket;
import java.util.HashMap;

public class RequestManger {
    private static final RequestManger REQUEST_MANGER = new RequestManger();
    private Game game = Game.getInstance();
    private Battle battle = Battle.getInstance();
    private final HashMap<Integer, SocketPair> sockets = new HashMap<>();

    private RequestManger() {

    }

    public static RequestManger getInstance() {
        return REQUEST_MANGER;
    }

    public void addSocket(SocketPair socketPair) {
        sockets.put(socketPair.getSocket().getPort(), socketPair);
    }

    public void removeSocket(Socket socket) {
        sockets.remove(socket.getPort());
    }

   /* public String login(Request request) {
        LoginRequest loginRequest = (LoginRequest) request.getDirectRequest();
        Message message = Account.login(loginRequest.getUserName(), loginRequest.getPassword());
        if (message == Message.SUCCESSFUL_LOGIN) {
            Account.getAccountByName(loginRequest.getUserName(), game.getAccounts()).setLoggedIn(true);
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
    }*/

   /* public String logout(Request request) {
        LogoutRequest logoutRequest = (LogoutRequest) request.getDirectRequest();
        game.logout(Account.getAccountByName(logoutRequest.getUserName(), game.getAccounts()));
        return Message.SUCCESSFUL_LOGOUT.toJson();
    }*/


    public String move(Request request) {
        MoveRequest moveRequest = (MoveRequest) request.getDirectRequest();
        Message message = battle.moveTo(moveRequest.getCoordinate());
        return message.toJson();
    }

    public String select(Request request) {
        SelectRequest selectRequest = (SelectRequest) request.getDirectRequest();
        Message message = battle.selectCard(selectRequest.getCardId());
        return message.toJson();
    }

    public String insert(Request request){
        InsertionRequest insertionRequest = (InsertionRequest) request.getDirectRequest();
        Message message = battle.insertCard(insertionRequest.getCoordinate(),insertionRequest.getCardName());
        return message.toJson();
    }

    public String attack(Request request){
        AttackRequest attackRequest = (AttackRequest) request.getDirectRequest();
        Message message = battle.attack(attackRequest.getOpponentCardId(),Card.getCardByID(attackRequest.getCardId(),battle.getFieldCards()[attackRequest.getTurn()%2]));
        return message.toJson();
    }

    public String selectUser(Request request){
        SelectUserRequest selectUserRequest = (SelectUserRequest) request.getDirectRequest();
        if (Account.getAccountByName(selectUserRequest.getFirstAccountName(), game.getAccounts()) == null ||
                Account.getAccountByName(selectUserRequest.getSecondAccountName(), game.getAccounts()) == null ) {
            return Message.UNSUCCESSFUL_SELECT_USER.toJson();
        }
        battle.setAccounts(Account.getAccountByName(selectUserRequest.getFirstAccountName(),game.getAccounts()),
                Account.getAccountByName(selectUserRequest.getSecondAccountName(),game.getAccounts()));
        return Message.SUCCESSFUL_SELECT_USER.toJson();
    }

}
