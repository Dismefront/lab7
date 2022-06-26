package server;

import storage.Worker;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    String q;

    public static void main(String[] args) throws IOException, SQLException {
        DatabaseManager.extractDatabase();
        DatabaseManager.loadDataToCollection();
        new SocketServer("localhost", 8080).start();
        /*
        ExecutorService ctp = Executors.newCachedThreadPool();
        ctp.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {

                }
            }
        });*/
    }
}
