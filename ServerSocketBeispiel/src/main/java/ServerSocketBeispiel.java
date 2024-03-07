import java.io.*;
import java.net.*;

public class ServerSocketBeispiel {
    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server gestartet und hört auf Port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    String text;
                    while ((text = in.readLine()) != null) {
                        System.out.println("Nachricht vom Client: " + text);
                        out.println("Echo: " + text);
                    }
                } catch (IOException e) {
                    System.out.println("Exception im Server: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Exception beim Erstellen des ServerSockets: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
