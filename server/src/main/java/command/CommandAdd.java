package command;

import collection.CollectionData;
import correspondency.CommandType;
import correspondency.ResponseCo;
import storage.Worker;

@PointCommand(name = "add", description = "добавить новый элемент в коллекцию")
public class CommandAdd extends Command {

    @Override
    public void execute() {
        ResponseCo response = new ResponseCo(
                "Please, enter the object",
                true, false
                );
        response.setCommandType(CommandType.ADD);
        this.setResponse(response);
    }

    public static void addWorker(Worker w) {
        CollectionData.collection.add(w);
    }

}
