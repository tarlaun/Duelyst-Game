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
                    case SHOP:
                        out = manager.shop(request);
                        break;
                    case BUY:
                        out = manager.buy(request);
                        break;
                    case SELL:
                        out = manager.sell(request);
                        break;
                    case SET_AUCTION:
                        out = manager.auction(request);
                        break;
                    case GET_AUCTION:
                        out = manager.fetchAuction(request);
                        break;
                }
                synchronized (socketPair) {
                    socketPair.getFormatter().format(out + "\n");
                    socketPair.getFormatter().flush();
                    try {
                        notifyAll();
                        wait();
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
