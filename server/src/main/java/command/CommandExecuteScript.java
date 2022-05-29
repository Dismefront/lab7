package command;

import correspondency.ResponseCo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

@PointCommand(name="execute_script", description = "считать и исполнить скрипт из указанного файла. " +
        "В скрипте содержатся команды в таком же виде, " +
        "в котором их вводит пользователь в интерактивном режиме")
public class CommandExecuteScript extends Command {

    //private static HashMap<File, Boolean> files = new HashMap<>();

    @Override
    public void execute() {
        try {
            ResponseCo res = new ResponseCo(
                    "Executing script...", false, true
            );
            res.setExecute_file(args.get(0));
            setResponse(res);
        }
        catch(Exception ex) {
            ResponseCo res = new ResponseCo(
                    "Could not work with file..."
            );
            setResponse(res);
        }
        /*try {
            File file = new File(args.get(0));
            if (files.containsKey(file)) {
                System.out.println("Recursion found.");
                return;
            }
            System.out.println("executing " + file + "...");
            files.put(file, Boolean.TRUE);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                CommandReflectionProcessor.launchInput(sc);
            }
        }
        catch (FileNotFoundException e) {
            files.clear();
            System.out.println("Couldn't find file");
            return;
        }
        files.clear();
        System.out.println("Script finished executing");
         */
    }
}
