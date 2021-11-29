package SERVER;

public class MainServer {
    public static void main(String[] args) {
        
        Server server = new Server(4000);

        server.connessione();
        server.comunicazione();
    }
        
}