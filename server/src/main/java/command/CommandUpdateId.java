package command;

import collection.CollectionData;
import correspondency.CommandType;
import correspondency.ResponseCo;
import server.DatabaseManager;
import storage.Worker;

@PointCommand(name="update", description = "обновить значение элемента " +
        "коллекции, id которого равен заданному")
public class CommandUpdateId extends Command {

    @Override
    public void execute() {
        Long r;
        try {
            String q = this.args.get(0);
            r = Long.parseLong(q);
        }
        catch (Exception e) {
            setResponse(new ResponseCo("Wrong command argument"));
            return;
        }
        ResponseCo response = new ResponseCo(
                "Please, enter the object:",
                true, false);
        response.setId(r);
        response.setCommandType(CommandType.UPDATEID);
        setResponse(response);
    }

    public static String updateId(Worker w, Long id, String requester) {
        if (DatabaseManager.checkValidity(id, requester)) {
            w.setId(id);
            return CollectionData.collection.updateId(w);
        }
        else
            return "You're not the owner of this slot";
    }

}
