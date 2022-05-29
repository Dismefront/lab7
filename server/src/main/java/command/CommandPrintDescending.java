package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import storage.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@PointCommand(name="print_descending", description = "вывести элементы коллекции в порядке убывания")
public class CommandPrintDescending extends Command {

    @Override
    public void execute() {
        String response = CollectionData.collection.getCollection().stream()
                .sorted(Collections.reverseOrder())
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
        setResponse(new ResponseCo(response));
    }
}
