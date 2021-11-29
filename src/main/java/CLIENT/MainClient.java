package CLIENT;


public class MainClient {
    public static void main(String[] args) {

        Client client = new Client(4000, "localhost");

        client.connessione();
        client.comunicazione();
        
    }
}
