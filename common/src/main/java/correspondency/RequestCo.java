package correspondency;

import storage.Worker;

import java.io.Serializable;
import java.security.MessageDigest;
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
    private String username;
    private byte[] hashedPassword;
    private boolean regNeed = false;

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

    public void setUserData(String username, String password, boolean regNeed) {
        this.username = username;
        this.regNeed = regNeed;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hashedPassword = md.digest(password.getBytes());
        }
        catch (Exception ex) {
            System.out.println("No algorithm");
            System.exit(2);
        }
    }

    public String getUsername() {
        return username;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public boolean isRegNeeded() {
        return regNeed;
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

    public RequestCo getRequestObject(Scanner scanner, String user) {
        try {
            this.obj = InputParser.getWorkerFromInput(scanner, user);
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
