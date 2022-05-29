package command;

import correspondency.ResponseCo;

@PointCommand(name = "exit", description = "завершить программу (без сохранения в файл)")
public class CommandExit extends Command {

    @Override
    public void execute() {
        ResponseCo res = new ResponseCo("Exit");
        res.setExit(true);
        setResponse(res);
    }

}
