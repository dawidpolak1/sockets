package polak.dawid.client;

public class Command {

    private final boolean sendResponse;
    private final String body;
    private final boolean stopConnection;

    public Command(boolean sendResponse, String body, boolean stopConnection) {
        this.sendResponse = sendResponse;
        this.body = body;
        this.stopConnection = stopConnection;
    }

    public boolean isSendResponse() { return sendResponse; }

    public String getBody() {
        return body;
    }

    public boolean isStopConnection() { return stopConnection; }
}