package command;

import collection.CollectionData;
import correspondency.CommandType;
import correspondency.InputParser;
import correspondency.ResponseCo;
import storage.Worker;

import java.util.ArrayList;
import java.util.Collections;

@PointCommand(name="add_if_min", description = "добавить новый элемент в коллекцию, " +
        "если его значение меньше, чем у наименьшего элемента этой коллекции")
public class CommandAddIfMin extends Command {

    @Override
    public void execute() {
        ResponseCo response = new ResponseCo(
                "Please, enter the object",
                true, false
        );
        response.setCommandType(CommandType.ADDIFMIN);
        this.setResponse(response);
    }

    public static String addWorker(Worker w) {
        ArrayList<Worker> tmp = (ArrayList<Worker>) ((ArrayList<Worker>) CollectionData.collection.getCollection()).clone();
        Collections.sort(tmp);
        String response;
        if (tmp.size() == 0 || tmp.get(0).getSalary() > w.getSalary()) {
            CollectionData.collection.add(w);
            response = "Added into the collection";
            return response;
        }
        response = "The element is not the smallest in the collection";
        return response;
    }

}
