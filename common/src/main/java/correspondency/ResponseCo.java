package correspondency;

import java.io.Serializable;

public class ResponseCo implements Serializable {

    private boolean objectNeeded;
    private String responseMsg;
    private boolean executeScript;
    private CommandType type;
    private Long id;
    private String execute_file;
    private boolean isExit = false;

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public String getExecute_file() {
        return execute_file;
    }

    public void setExecute_file(String file) {
        this.execute_file = file;
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

    public ResponseCo(String response,
                      boolean objectNeeded,
                      boolean executeScript) {
        this.responseMsg = response;
        this.objectNeeded = objectNeeded;
        this.executeScript = executeScript;
    }

    public ResponseCo(String response) {
        this.responseMsg = response;
        this.objectNeeded = false;
        this.executeScript = false;
    }

    public String getResponseMessage() {
        return responseMsg;
    }

    public boolean isObjectNeeded() {
        return this.objectNeeded;
    }

    public boolean isExecuteScript() {
        return this.executeScript;
    }

}
