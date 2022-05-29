package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import storage.Worker;

import java.util.stream.Collectors;

@PointCommand(name="show", description = "вывести в стандартный поток вывода все " +
        "элементы коллекции в строковом представлении")
public class CommandShow extends Command {

    @Override
    public void execute() {
        String response = "";
        if (CollectionData.collection.isEmpty())
            response = "The collection is empty";
        else {
            response = CollectionData.collection
                    .getCollection().stream().map(Worker::toString)
                    .collect(Collectors.joining("\n"));
        }
        setResponse(new ResponseCo(response));
    }
}
