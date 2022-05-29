package command;

import collection.CollectionData;
import correspondency.ResponseCo;

@PointCommand(name="clear", description = "очистить коллекцию")
public class CommandClear extends Command {

    @Override
    public void execute() {
        CollectionData.collection.clear();
        setResponse(new ResponseCo("Collection has benn cleared"));
    }
}
