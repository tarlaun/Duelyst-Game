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
            case MOVE:
                directRequest = new MoveRequest(args);
                break;
            case SELECTION:
                directRequest = new SelectRequest(args);
                break;
            case INSERTION:
                directRequest = new InsertionRequest(args);
                break;
            case ATTACK:
                directRequest = new AttackRequest(args);
                break;
            case SELECT_USER:
                directRequest = new SelectUserRequest(args);
                break;
            case USE_SP:
                directRequest = new UseSpRequest(args);
                break;
            case RIVAL:
                directRequest = new RivalRequest(args);
                break;
            case GAME_TYPE:
                directRequest = new GameTypeRequest(args);
                break;
            case BATTLE_MODE:
                directRequest = new BattleModeRequest(args);
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