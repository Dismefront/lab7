package command;

import collection.CollectionData;
import correspondency.ResponseCo;

@PointCommand(name="sort", description = "отсортировать коллекцию в естественном порядке")
public class CommandSort extends Command {

    @Override
    public void execute() {
        CollectionData.collection.sort();
        setResponse(new ResponseCo("Collection has been sorted"));
    }

}
