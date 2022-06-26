package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import server.DatabaseManager;

@PointCommand(name="remove_by_id", description = "удалить элемент из коллекции по его id")
public class CommandRemoveById extends Command {

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
        setResponse(new ResponseCo(CollectionData.collection.removeById(r, getUsername())));
    }

}
