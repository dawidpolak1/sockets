package polak.dawid.server;

import polak.dawid.server.control.ServerControl;
import polak.dawid.server.control.support.ServerTime;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class Server {

    private static final String SERVER_VERSION = "0.1";
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ServerTime serverTime;

    private void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running...");
            serverTime = new ServerTime();

            clientSocket = serverSocket.accept();
            System.out.println("Server has started and listening to port: " + port);
            ServerControl serverControl = new ServerControl(this);
            serverControl.run();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public String getServerVersion() { return SERVER_VERSION;}

    public ServerTime getServerTime() {
        return serverTime;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public LocalDate getStartDateOfServer() { return serverTime.getStartDateOfServer();}

    public static void main(String[] args) {

        Server server = new Server();
        server.startServer(3333);
    }
}