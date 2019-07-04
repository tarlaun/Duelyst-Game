package Server.Controller;

import java.net.Socket;
import java.util.HashMap;

public class RequestManger {
    private static final RequestManger REQUEST_MANGER = new RequestManger();
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
}
