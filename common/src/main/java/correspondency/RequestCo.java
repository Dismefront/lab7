package correspondency;

import storage.Worker;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RequestCo implements Serializable {

    String requestLine = null;
    private Worker obj = null;
    private CommandType type;
    private Long id;
    private ArrayList<Worker> objArray = null;
    private String workingFile = null;

    public String getWorkingFile() {
        return workingFile;
    }

    public boolean hasWorkingFile() {
        return workingFile != null;
    }

    public void setWorkingFile(String workingFile) {
        this.workingFile = workingFile;
    }

    public void setObjArray(ArrayList<Worker> objArray) {
        this.objArray = objArray;
    }

    public boolean hasArray() {
        return this.objArray != null;
    }

    public ArrayList<Worker> getObjArray() {
        return objArray;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCommandType(CommandType type) {
        this.type = type;
    }

    public CommandType getCommandType() {
        return this.type;
    }

    public RequestCo getRequestLine(Scanner scanner) {
        this.requestLine = getValidatedLine(scanner);
        return this;
    }

    public boolean hasObject() {
        return obj != null;
    }

    public Worker getObj() {
        return obj;
    }

    public RequestCo getRequestObject(Scanner scanner) {
        try {
            this.obj = InputParser.getWorkerFromInput(scanner);
            return this;
        }
        catch (NoSuchElementException ex) {
            try {
                new Scanner(System.in);
            }
            catch (NoSuchElementException e) {
                System.exit(0);
            }
            System.out.println("Iterrupted input");
            scanner = new Scanner(System.in);
            return null;
        }
    }

    public String getLine() {
        return this.requestLine;
    }

    private static String getValidatedLine(Scanner scanner) {
        try {
            String str = scanner.nextLine().trim();
            if (str.isEmpty())
                return getValidatedLine(scanner);
            return str;
        }
        catch (NoSuchElementException ex) {
            System.exit(0);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine='" + requestLine + '\'' +
                '}';
    }

}
