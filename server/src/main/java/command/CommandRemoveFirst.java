package command;

import collection.CollectionData;
import correspondency.ResponseCo;

@PointCommand(name="remove_first", description = "удалить первый элемент из коллекции")
public class CommandRemoveFirst extends Command {

    @Override
    public void execute() {
        CollectionData.collection.removeFirst();
        setResponse(new ResponseCo("First element has been removed"));
    }

}
