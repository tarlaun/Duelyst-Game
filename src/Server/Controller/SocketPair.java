package Server.Controller;

import Server.Model.Pair;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;

public class SocketPair {
    private Pair<Socket, Formatter> pair;

    SocketPair(Socket socket) throws IOException {
        pair = new Pair<>(socket, new Formatter(socket.getOutputStream()));
    }

    public Formatter getFormatter() {
        return pair.getValue();
    }

    Socket getSocket() {
        return pair.getKey();
    }
}