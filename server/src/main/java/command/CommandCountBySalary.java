package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import storage.Worker;

@PointCommand(name="count_by_salary", description = "вывести количество элементов, " +
        "значение поля salary которых равно заданному")
public class CommandCountBySalary extends Command {

    @Override
    public void execute() {
        String q = this.args.get(0);
        String response;
        Long r;
        try {
            r = Long.parseLong(q);
        }
        catch (Exception e) {
            response = "Wrong command argument";
            setResponse(new ResponseCo(response));
            return;
        }
        long cnt = CollectionData.collection.getCollection().stream()
                .filter(x -> (x.getSalary() == r)).count();
        response = "The number of elements with " + r + " salary: " + cnt;
        setResponse(new ResponseCo(response));
    }
}
