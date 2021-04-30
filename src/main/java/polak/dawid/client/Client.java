package polak.dawid.client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Command response = new Gson().fromJson(in.readLine(), Command.class);
            System.out.println(response.getBody());

            if (response.isStopConnection()) {
                stopConnection();
                break;
            }
            if (response.isSendResponse()) {
                String input = scanner.nextLine();
                out.println(input);
            }
        }
    }


    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException exception) {
            System.err.println("Error while initializing connection");
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException exception) {
            System.err.println("error when closing");
        }
    }

    public static void main(String[] args) throws IOException {

        Client client = new Client();
        client.startConnection("127.0.0.1", 3333);
        client.run();
    }
}