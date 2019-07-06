package Server.Controller;

import Controller.Request.Request;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Controller {
    private ServerSocket serverSocket;
    private RequestManger manager = RequestManger.getInstance();
    private static final int PORT = 8080;

    public void main() {
        try {
            serverSocket = new ServerSocket(PORT);
            handleClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        break;
                    case CREATE_ACCOUNT:
                        break;
                    case SELECT_USER:
                        break;
                    case SINGLE_PLAYER:
                        break;
                    case BUY:
                        break;
                    case LOGOUT:
                        break;
                    case LEADERBOARD:
                        break;
                    case ADD:
                        break;
                    case EXIT:
                        break;
                    case FLAG:
                        break;
                    case HELP:
                        break;
                    case MOVE:
                        out = manager.move(request);
                        break;
                    case SAVE:
                        break;
                    case SELL:
                        break;
                    case SHOW:
                        break;
                    case COMBO:
                        break;
                    case STORY:
                        break;
                    case ATTACK:
                        break;
                    case CUSTOM:
                        break;
                    case REMOVE:
                        break;
                    case SEARCH:
                        break;
                    case USE_SP:
                        break;
                    case END_GAME:
                        break;
                    case ENTRANCE:
                        break;
                    case END_TURN:
                        break;
                    case SHOW_MAP:
                        break;
                    case USE_ITEM:
                        break;
                    case GAME_INFO:
                        break;
                    case NEXT_CARD:
                        break;
                    case INSERTION:
                        break;
                    case SELECTION:
                        out = manager.select(request);
                        break;
                    case SHOW_DECK:
                        break;
                    case SHOW_HAND:
                        break;
                    case SHOW_MENU:
                        break;
                    case COLLECTING:
                        break;
                    case SHOW_CARDS:
                        break;
                    case VALIDATION:
                        break;
                    case CREATE_DECK:
                        break;
                    case DELETE_DECK:
                        break;
                    case SELECT_DECK:
                        break;
                    case MULTI_PLAYER:
                        break;
                    case SHOW_ALL_DECK:
                        break;
                    case SHOW_CARD_INFO:
                        break;
                    case KILL_ENEMY_HERO:
                        break;
                    case SHOW_COLLECTION:
                        break;
                    case SHOW_MY_MININOS:
                        break;
                    case SHOW_OPP_MINIONS:
                        break;
                    case SEARCH_COLLECTION:
                        break;
                    case SHOW_COLLECTABLES:
                        break;
                    case SAVE_IN_COLLECTION:
                        break;
                    case SHOW_MATCH_HISTORY:
                        break;
                    case SHOW_COLLECTABLE_INFO:
                        break;
                    case NULL:
                        break;
                }
                socketPair.getFormatter().format(out+"\n");
                socketPair.getFormatter().flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
