package polak.dawid.server.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import polak.dawid.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerControl {

    private final Server server;
    private final PrintWriter out;
    private final BufferedReader in;

    public ServerControl(Server server) throws IOException {
        this.server = server;
        out = new PrintWriter(this.server.getClientSocket().getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(
                this.server.getClientSocket().getInputStream()));
    }

    public void run() throws IOException {
        boolean run = true;
        String body = "\nEnter command:\n" +
                "1 - uptime\n" +
                "2 - info\n" +
                "3 - help\n" +
                "4 - stop\n";

        while (run) {
            sendCommand(true, body);

            switch (in.readLine()) {
                case "1" -> uptime();
                case "2" -> info();
                case "3" -> help();
                case "4" -> {
                    stop();
                    run = false;
                }
                default -> sendCommand(false, "There is no such command. Please try again");
            }
        }
    }

    private void sendCommand(boolean sendResponse, String body) {
        Command command = new Command(sendResponse, body);
        out.println(new Gson().toJson(command));
    }

    private void sendCommand(boolean sendResponse, String body, boolean stopConnection) {
        Command command = new Command(sendResponse, body, stopConnection);
        out.println(new Gson().toJson(command));
    }

    private void uptime() {
        String json = new Gson().toJson(server.getServerTime().getUptime());
        sendCommand(false, json);
    }

    private void info() {
        JsonObject response = new JsonObject();
        response.addProperty("serverVersion", server.getServerVersion());
        response.addProperty("creationDate", server.getStartDateOfServer().toString());
        sendCommand(false, response.toString());
    }

    private void help() {
        JsonObject response = new JsonObject();
        response.addProperty("uptime", Option.getUPTIME());
        response.addProperty("info", Option.getINFO());
        response.addProperty("help", Option.getHELP());
        response.addProperty("stop", Option.getSTOP());
        sendCommand(false, response.toString());
    }

    private void stop() {
        try {
            sendCommand(false,"Exit. Bye bye!", true);
            in.close();
            out.close();
            server.getClientSocket().close();
            server.getServerSocket().close();
        } catch (IOException exception) {
            System.err.println("Error while stopping connection");
        }
    }

    private static class Option {

        private static final String UPTIME = "1  - life time of the server";
        private static final String INFO = "2  - server version and creation date";
        private static final String HELP = "3 - list of available commands with description";
        private static final String STOP = "4 - stop both the server and the client";



        public static String getUPTIME() {
            return UPTIME;
        }

        public static String getINFO() {
            return INFO;
        }

        public static String getHELP() {
            return HELP;
        }

        public static String getSTOP() {
            return STOP;
        }
    }
}