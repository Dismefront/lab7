package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import storage.Worker;

import java.util.ArrayList;
import java.util.stream.Collectors;

@PointCommand(name="clear", description = "очистить коллекцию")
public class CommandClear extends Command {

    @Override
    public void execute() {
        ArrayList<Long> arr = new ArrayList<>();
        for (Worker x : CollectionData.collection) {
            arr.add(x.getId());
        }
        for (Long x : arr) {
            CollectionData.collection.removeById(x, getUsername());
        }
        setResponse(new ResponseCo("Collection has benn cleared"));
    }
}
