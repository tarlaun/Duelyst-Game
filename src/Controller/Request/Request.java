package Controller.Request;

import Model.BattleMode;
import Model.Coordinate;
import Model.GameType;
import Model.Process;
import View.Message;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Scanner;

public class Request {
    private int port;
    private RequestType type;
    private String requestId;

    private Request(int port, RequestType type, String id) {
        this.port = port;
        this.type = type;
        this.requestId = id;
    }

    public static Request newLoginRequest(int port, String username) {
        return null;
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

    public String getRequestId() {
        return requestId;
    }
}
