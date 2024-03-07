import java.io.*;
import java.net.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketClientBeispiel {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 1234;
    private static final Logger logger = LogManager.getLogger(SocketClientBeispiel.class);

    public static void main(String[] args) {
        logger.info("Verbindung zum Server {} auf Port {}", HOSTNAME, PORT);

        try (Socket socket = new Socket(HOSTNAME, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                logger.info("Echo vom Server: {}", in.readLine());
            }
        } catch (UnknownHostException e) {
            logger.error("Host unbekannt: {}", HOSTNAME, e);
            System.exit(1);
        } catch (IOException e) {
            logger.error("I/O Fehler beim Verbinden zum Server {}: {}", HOSTNAME, e.getMessage(), e);
            System.exit(1);
        }
    }
}
