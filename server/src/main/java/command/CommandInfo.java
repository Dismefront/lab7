package command;

import collection.CollectionData;
import correspondency.ResponseCo;

@PointCommand(name="info", description="вывести в стандартный поток вывода информацию о коллекции " +
        "(тип, дата инициализации, количество элементов и т.д.)")
public class CommandInfo extends Command {

    @Override
    public void execute() {
        String response = CollectionData.collection.getInfo();
        setResponse(new ResponseCo(response));
    }

}
