package Server.Controller;

import Controller.Request.Request;
import Model.Game;
import View.Message;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Controller {
    private transient ServerSocket serverSocket;
    private transient RequestManger manager = RequestManger.getInstance();
    private static final int PORT = 8080;
    private transient Game game = Game.getInstance();
    private static final Controller CONTROLLER = new Controller();

    private Controller() {

    }

    public static Controller getInstance() {
        return CONTROLLER;
    }

    public void main() {
        initializeGame();
        try {
            serverSocket = new ServerSocket(PORT);
            handleClients();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void initializeGame() {
        try {
            game.initializeAccounts();
        } catch (IOException f) {
            System.out.println("Account initializing error!");
        }
        try {
            game.initializeHero();
        } catch (IOException f) {
            System.out.println("Hero initializing error!");
        }
        try {
            game.initializeMinion();
        } catch (IOException f) {
            System.out.println("Minion initializing error!");
        }
        try {
            game.initializeSpell();
        } catch (IOException f) {
            System.out.println("Spell initializing error!");
        }
        try {
            game.initializeItem();
        } catch (IOException f) {
            System.out.println("Item initializing error!");
        }
        game.setSrcs();
    }


    private void handleClients() throws IOException {
        while (true) {
            SocketPair socketPair = new SocketPair(serverSocket.accept());
            new Thread(() -> {
                try {
                    manager.addSocket(socketPair);
                    handleClient(socketPair);
                    manager.removeSocket(socketPair.getSocket());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    private void handleClient(SocketPair socketPair) throws IOException {
        Socket socket = socketPair.getSocket();
        BufferedReader socketScanner = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (socket.isConnected()) {
            Request request;
            try {
                String json = socketScanner.readLine();
                if (json == null) {
                    break;
                }
                request = Request.fromJson(json);
            } catch (JsonSyntaxException e) {
                continue;
            } catch (SocketException e) {
                break;
            }
            String out = null;
            try {
                switch (request.getType()) {
                    case LOGIN:
                        out = manager.login(request);
                        break;
                    case CREATE_ACCOUNT:
                        out = manager.createAccount(request);
                        break;
                    case LOGOUT:
                        out = manager.logout(request);
                        break;
                    case SAVE:
                        out = manager.save(request);
                        break;
                    case SELECT_USER:
                        out = manager.selectUser(request);
                        break;
                    case BATTLE_MODE:
                        out = manager.chooseBattleMode(request);
                        break;
                    case GAME_TYPE:
                        out = manager.chooseBattleType(request);
                        break;
                    case MOVE:
                        out = manager.move(request);
                        break;
                    case SHOW:
                        break;
                    case COMBO:
                        break;
                    case ATTACK:
                        out = manager.attack(request);
                        break;
                    case USE_SP:
                        out = manager.useSp(request);
                        break;
                    case BATTLE:
                        out = manager.battle(request);
                        break;
                    case END_GAME:
                        break;
                    case ENTRANCE:
                        break;
                    case END_TURN:
                        break;
                    case USE_ITEM:
                        break;
                    case GAME_INFO:
                        break;
                    case NEXT_CARD:
                        break;
                    case INSERTION:
                        out = manager.insert(request);
                        break;
                    case SELECTION:
                        out = manager.select(request);
                        break;
                    case SHOW_HAND:
                        break;
                    case RIVAL:
                        out = manager.getRival(request);
                        break;
                    case NULL:
                        break;
                }
                synchronized (socketPair) {
                    socketPair.getFormatter().format(out + "\n");
                    socketPair.getFormatter().flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
