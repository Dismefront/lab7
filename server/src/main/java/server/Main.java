package server;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        new SocketServer("localhost", 8080).start();
    }
}
