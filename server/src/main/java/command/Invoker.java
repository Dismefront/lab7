package command;

import correspondency.RequestCo;
import datastructures.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Invoker {

    public static String[] commands = {
            "CommandAdd",
            "CommandAddIfMin",
            "CommandClear",
            "CommandCountBySalary",
            "CommandExecuteScript",
            "CommandExit",
            "CommandHelp",
            "CommandInfo",
            "CommandPrintDescending",
            "CommandPrintFieldDescendingSalary",
            "CommandRemoveById",
            "CommandRemoveFirst",
            "CommandSave",
            "CommandShow",
            "CommandSort",
            "CommandUpdateId"
    };

    public static String currentCommandFolderPath = "command";

    public static Command launch(RequestCo req) {
        String inp = req.getLine();
        Pair<String, ArrayList<String>> command = Command.getSplit(inp);

        for (String s : commands) {
            Class<?> clazz;
            try {
                clazz = Class.forName(currentCommandFolderPath + "." + s);
            } catch (Exception e) {
                continue;
            }
            PointCommand annotation = clazz.getAnnotation(PointCommand.class);
            if (annotation != null && annotation.name().equals(command.first())) {

                try {
                    Command c = (Command)clazz.getConstructor().newInstance();
                    c.setCommand(inp);
                    c.execute();
                    return c;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

}
