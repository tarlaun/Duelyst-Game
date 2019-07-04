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

            try {
                switch (request.getType()) {
                    case LOGIN:
                        break;
                    case CREATE_ACCOUNT:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
