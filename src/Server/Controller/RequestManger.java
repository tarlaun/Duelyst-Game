package Server.Controller;

import Controller.Request.*;
import Model.*;
import View.Message;
import Controller.Request.Request;

import java.net.Socket;
import java.util.HashMap;

public class RequestManger {
    private static final RequestManger REQUEST_MANGER = new RequestManger();
    private Game game = Game.getInstance();
    private Battle battle;
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

    public String login(Request request) {
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
    }

    public String logout(Request request) {
        LogoutRequest logoutRequest = (LogoutRequest) request.getDirectRequest();
        game.logout(Account.getAccountByName(logoutRequest.getUserName(), game.getAccounts()));
        return Message.SUCCESSFUL_LOGOUT.toJson();
    }

    public String save(Request request) {
        SaveRequest saveRequest = (SaveRequest) request.getDirectRequest();
        game.save(saveRequest.getAccount());
        return Message.SUCCESSFUL_SAVE.toJson();
    }


    public String move(Request request) {
        MoveRequest moveRequest = (MoveRequest) request.getDirectRequest();
        System.out.println("manam " + game.getBattles().size());
        System.out.println("manam " + moveRequest.getAccName());
        battle = Battle.findBattleByName(moveRequest.getAccName(), game.getBattles());
        System.out.println(battle.getAccounts() == null);
        System.out.println(battle.getPlayerHands() == null);
        System.out.println(battle.getFieldCards() == null);
        System.out.println(battle.getField(2, 2) == null);
        Message message = battle.moveTo(moveRequest.getCoordinate(), moveRequest.getCardId());
        return message.toJson();
    }

    public String select(Request request) {
        SelectRequest selectRequest = (SelectRequest) request.getDirectRequest();
        battle = Battle.findBattleByName(selectRequest.getAccName(), game.getBattles());
        Message message = battle.selectCard(selectRequest.getCardId());
        return message.toJson();
    }

    public String insert(Request request) {
        InsertionRequest insertionRequest = (InsertionRequest) request.getDirectRequest();
        battle = Battle.findBattleByName(insertionRequest.getAccName(), game.getBattles());
        System.out.println(battle.getField(2, 2) == null);
        Message message = battle.insertCard(insertionRequest.getCoordinate(), insertionRequest.getCardName());
        return message.toJson();
    }

    public String attack(Request request) {
        AttackRequest attackRequest = (AttackRequest) request.getDirectRequest();
        battle = Battle.findBattleByName(attackRequest.getAccName(), game.getBattles());
        Message message = battle.attack(attackRequest.getOpponentCardId(), Card.getCardByID(attackRequest.getCardId(), battle.getFieldCards()[attackRequest.getTurn() % 2]), 0);
        return message.toJson();
    }

    public String selectUser(Request request) {
        SelectUserRequest selectUserRequest = (SelectUserRequest) request.getDirectRequest();
        if (Account.getAccountByName(selectUserRequest.getFirstAccountName(), game.getAccounts()) == null ||
                Account.getAccountByName(selectUserRequest.getSecondAccountName(), game.getAccounts()) == null) {
            return Message.UNSUCCESSFUL_SELECT_USER.toJson();
        }
        battle.setAccounts(Account.getAccountByName(selectUserRequest.getFirstAccountName(), game.getAccounts()),
                Account.getAccountByName(selectUserRequest.getSecondAccountName(), game.getAccounts()));
        return Message.SUCCESSFUL_SELECT_USER.toJson();
    }

    public String useSp(Request request) {
        UseSpRequest useSpRequest = (UseSpRequest) request.getDirectRequest();
        Message message = battle.useSp(useSpRequest.getCoordinate());
        return message.toJson();
    }

    public String getRival(Request request) {
        RivalRequest rivalRequest = (RivalRequest) request.getDirectRequest();
        return Account.getAccountByName(rivalRequest.getName(), game.getAccounts()).toJson();
    }

    public String chooseBattleType(Request request) {
        GameTypeRequest gameTypeRequest = (GameTypeRequest) request.getDirectRequest();
        if (gameTypeRequest.getBattleType().equals(GameType.SINGLEPLAYER.toString())) {
            return Message.SINGLEPLAYER_CHOSEN.toJson();

        } else {
            return Message.MULTIPLAYER_CHOSEN.toJson();
        }
    }

    public String chooseBattleMode(Request request) {
        BattleModeRequest battleModeRequest = (BattleModeRequest) request.getDirectRequest();
        if (battleModeRequest.getBattleMode().equals(BattleMode.KILLENEMYHERO.toString())) {
            return Message.KILL_CHOSEN.toJson();

        } else if (battleModeRequest.getBattleMode().equals(BattleMode.COLLECTING.toString())) {
            return Message.COLLECT_CHOSEN.toJson();
        } else {
            return Message.FLAG_CHOSEN.toJson();
        }
    }

    public String battle(Request request) {
        BattleRequest battleRequest = (BattleRequest) request.getDirectRequest();
        Account[] accounts = new Account[2];
        accounts[0] = Account.getAccountByName(battleRequest.getAccount1Name(), game.getAccounts());
        accounts[1] = Account.getAccountByName(battleRequest.getAccount2Name(), game.getAccounts());
        battle = new Battle(accounts, GameType.findGameType(battleRequest.getGameType()), BattleMode.findBattleMode(battleRequest.getBattleMode()));
        battle.startBattle();
        game.getBattles().add(battle);
        return battle.toJson();
    }

    public String endTurn(Request request) {
        EndTurnRequest endTurnRequest = (EndTurnRequest) request.getDirectRequest();
        Message message = battle.endTurn();
        return message.toJson();
    }

    public String killEnemy(Request request) {
        KillRequest killRequest = (KillRequest) request.getDirectRequest();
        Message message = battle.killEnemy(killRequest.getCardId());
        return message.toString();
    }
}
