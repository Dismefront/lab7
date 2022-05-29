package command;

import correspondency.ResponseCo;

@PointCommand(name = "help", description = "вывести справку по доступным командам")
public class CommandHelp extends Command {

    @Override
    public void execute() {
        String response = "";
        response += "The list of allowed commands: \n";
        for (String s : Invoker.commands) {
            Class<?> clazz;
            try {
                clazz = Class.forName(Invoker.currentCommandFolderPath + "." + s);
            } catch (Exception e) {
                continue;
            }
            PointCommand annotation = clazz.getAnnotation(PointCommand.class);
            if (annotation != null) {
                try {
                    response += "\t" +
                            annotation.name() +
                            ": " + annotation.description()
                            + "\n";
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setResponse(new ResponseCo(response));
    }

}
