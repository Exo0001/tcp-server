import java.net.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Server Port: ");
        int port = scanner.nextInt();
        ServerSocket server = new ServerSocket(port);
        int clientCount = 0;
        System.out.println(String.format("Server started on port %d", port));

        while(true) {
            Socket conn = server.accept();
            String clientAddr = conn.getLocalSocketAddress().toString();
            clientCount += 1;
            System.out.println(String.format("%d | New client connection | %s", clientCount, clientAddr));
            cmd(conn, scanner);
        }
    }

    public static void cmd(Socket conn, Scanner scanner) throws IOException {
        InputStreamReader input = new InputStreamReader(conn.getInputStream());
        PrintWriter writer = new PrintWriter(conn.getOutputStream());
        BufferedReader reader = new BufferedReader(input);

        writer.println("Welcome, type 'MSG' to contact the server.");
        writer.flush();

        while(true) {
            writer.print("[Client -]$ ");
            writer.flush();
            String data = reader.readLine();

            if (data == null) {
                System.out.println("Client disconnected");
                return;
            }

            switch(data) {
                case "MSG":
                    writer.print("Message to server:  ");
                    writer.flush();
                    String serverMsg = reader.readLine();
                    System.out.println(String.format("Msg from client < %s >", serverMsg));
            }
        }
    }
}