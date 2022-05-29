package server;

import command.CommandSave;

import java.util.Scanner;

public class ServerConsole {

    public static void read() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                String line = sc.nextLine();
                if (line.equals("save")) {
                    new CommandSave().save();
                }
            }
            catch (Exception ex) {
                new CommandSave().save();
                System.exit(0);
            }
        }
    }

}
