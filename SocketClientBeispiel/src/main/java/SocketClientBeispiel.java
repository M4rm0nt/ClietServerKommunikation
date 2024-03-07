import java.io.*;
import java.net.*;

public class SocketClientBeispiel {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Echo vom Server: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Host unbekannt: " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O Fehler beim Verbinden zum Server " + hostname);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
