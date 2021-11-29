package CLIENT;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private int porta;
    private String indirizzoServer;
    private Socket server;
    private BufferedReader receive;
    private DataOutputStream send;

    public Client(int porta, String indirizzoServer) {

        this.porta = porta;
        this.indirizzoServer = indirizzoServer;
    }

    public void connessione() {
        try {
            server = new Socket(indirizzoServer, porta);
            receive = new BufferedReader(new InputStreamReader(server.getInputStream()));
            send = new DataOutputStream(server.getOutputStream());

        } catch (Exception e) {
            System.out.println("ERRORE NELLA CONNESSIONE VERSO IL SERVER \n" + e.getMessage());
        }

    }

    public void comunicazione() {

        System.out.println("INIZIO COMUNICAZIONE");
        Scanner tastiera = new Scanner(System.in);
        String in;

        try {

            for (;;) {
                System.out.println(receive.readLine());
                System.out.println("\n");
                in = tastiera.nextLine();

                send.writeBytes(in + "\n");

                if (in.equals("FINE")) {
                    System.out.println("\n--> chiusura connessione..");
                    break;
                }
            }

            tastiera.close();
            receive.close();
            send.close();
            server.close();

        } catch (Exception e) {
            System.out.println("ERRORE NELLA COMUNICAZIONE VERSO IL SERVER" + e.getMessage());
        }

    }

}