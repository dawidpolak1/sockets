package polak.dawid.server.control;

public class Command {

    private final boolean sendResponse;
    private final String body;
    private final boolean stopConnection;

    public Command(boolean sendResponse, String body, boolean stopConnection) {
        this.sendResponse = sendResponse;
        this.body = body;
        this.stopConnection = stopConnection;
    }

    public Command(boolean sendResponse, String body) {
        this.sendResponse = sendResponse;
        this.body = body;
        this.stopConnection = false;
    }
}