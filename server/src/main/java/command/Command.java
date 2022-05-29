package command;

import correspondency.ResponseCo;
import datastructures.Pair;
import exceptions.CommandDoesNotExistException;

import java.util.ArrayList;

public abstract class Command {

    protected String action;
    protected ArrayList<String> args;
    private String command;
    private ResponseCo response;

    public void setResponse(ResponseCo response) {
        this.response = response;
    }

    public ResponseCo getResponse() {
        return this.response;
    }

    public void setCommand(String commandLine) {
        this.command = commandLine;
        try {
            this.distributeCommand();
        } catch (CommandDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void distributeCommand() throws CommandDoesNotExistException {
        checkCommandCorrectness(this.command);
        Pair<String, ArrayList<String>> p = getSplit(this.command);
        this.action = p.first();
        this.args = p.second();
    }

    private static boolean isBlank(String name) {
        return name == null || name.isEmpty();
    }

    private static void checkCommandCorrectness(String msg) throws CommandDoesNotExistException {
        if (isBlank(msg))
            throw new CommandDoesNotExistException();
    }

    public static Pair<String, ArrayList<String>> getSplit(String command) {
        String[] parsed = command.trim().split(" ");
        ArrayList<String> na = new ArrayList<>();
        for (int i = 1; i < parsed.length; i++)
            na.add(parsed[i]);
        return new Pair<>(parsed[0], na);
    }

    public abstract void execute();

}
