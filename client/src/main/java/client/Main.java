package client;

import storage.Worker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Dismefront(Erik Romaikin)
 */

public class Main {

    public static void main(String ... args) {

        Client client = new Client("localhost", 8080);

        try {
            client.setUserProps(args[0], args[1]);
            if (args.length >= 3 && args[2].equals("reg"))
                client.registrationNeeded();
        }
        catch (Exception ex) {
            System.out.println("No login and password given");
            System.exit(2);
        }

        while (true) {
            try {
                client.start();
            }
            catch (Exception ex) {
                System.out.println("Waiting server...");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
