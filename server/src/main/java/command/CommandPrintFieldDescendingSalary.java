package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import storage.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@PointCommand(name="print_field_descending_salary", description = "вывести значения поля " +
        "salary всех элементов в порядке убывания")
public class CommandPrintFieldDescendingSalary extends Command {

    @Override
    public void execute() {
        String response = CollectionData.collection.getCollection().stream()
                .sorted(Collections.reverseOrder())
                .map(x -> Long.toString(x.getSalary()))
                .collect(Collectors.joining("\n"));
        setResponse(new ResponseCo(response));
    }
}
