package SERVER;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class Server {

    private int porta;
    private ServerSocket server;
    private Socket client;
    private BufferedReader receive;
    private DataOutputStream send;
    ArrayList<String> dati = new ArrayList<>();
    private int conta = 1;

    public Server(int porta) {

        this.porta = porta;

        try {
            server = new ServerSocket(porta);
        } catch (Exception e) {
            System.out.println("ERRORE SULLA PORTA \n" + e.getMessage());
        }
    }

    public void connessione() {

        try {

            System.out.println("---> SERVER IN ATTESA..");

            client = server.accept();

            System.out.println("---> NUOVO CLIENT IN CONNESSIONE");
            receive = new BufferedReader(new InputStreamReader(client.getInputStream()));
            send = new DataOutputStream(client.getOutputStream());

        } catch (Exception e) {
            System.out.println("ERRORE NELLA CONNESSIONE VERSO IL CLIENT \n" + e.getMessage());
        }

    }

    public void comunicazione() {

        boolean aperto = true;

        try {

            

            send.writeBytes("---> BENVENUTO! Connessione effettuata" + "Iniziamo.. premi invio!" + "\n");
            
            

            while (aperto) {

                String messaggio = "";

                messaggio = receive.readLine();
                dati.add(messaggio);

                //ogni volta che ricevo un messaggio dal client inserisco il messaggio dentro un Array, 
                //e sapiamo sempre che in posizione 1 abbiamo il primo numero, 
                //in posizione 2 il secondo numero, 
                //in posizione 3 la funzione matematica 
                //ed infine in posizione 4 la risposta alla domanda : Vuoi effettuare un nuovo calcolo (Y/N)
                // avendo e sapendo queste cose si può gestire i calcoli con l'array e una variabile che ci tiene il conto

                if(conta <= 2){
                    send.writeBytes("---> Dammi il " + conta  + " numero" + "\n");
                }
                else if(conta == 3){
                    send.writeBytes("---> Scegli l'operazione da eseguire (+, -, *, /)" + "\n");
                }
                else if(conta == 4)
                {

                    if(dati.get(3).equals("+"))
                    {
                        int n1 = Integer.parseInt(dati.get(1));
                        int n2 = Integer.parseInt(dati.get(2));

                        int risultato = n1 + n2;
                        send.writeBytes("---> Il risultato dell'operazione è: " + risultato + "\n");

                    }else if(dati.get(3).equals("-")) {
                        int n1 = Integer.parseInt(dati.get(1));
                        int n2 = Integer.parseInt(dati.get(2));

                        int risultato = n1 - n2;
                        send.writeBytes("---> Il risultato dell'operazione è: " + risultato + "\n");

                    }else if(dati.get(3).equals("*"))
                    {
                        int n1 = Integer.parseInt(dati.get(1));
                        int n2 = Integer.parseInt(dati.get(2));

                        int risultato = n1 * n2;
                        send.writeBytes("---> Il risultato dell'operazione è: " + risultato + "\n");
                    }else if(dati.get(3).equals("/"))
                    {
                        int n1 = Integer.parseInt(dati.get(1));
                        int n2 = Integer.parseInt(dati.get(2));

                        int risultato = n1 / n2;
                        send.writeBytes("---> Il risultato dell'operazione e: " + risultato + "digita invio" + "\n");
                    }

                }else if (conta == 5){
                    send.writeBytes("---> Vuoi effettuare un nuovo calcolo (Y/N)" + "\n");
                    String mess = receive.readLine();

                    System.out.println(mess);
                    if(mess.equals("N"))
                    {
                            System.out.println("---> CHIUSURA IN CORSO");
                            close();
                    }else if (mess.equals("Y")){
                        conta = 0;
                        //dati = new ArrayList<>();
                        dati.clear();
                        send.writeBytes("---> GRAZIE" + "\n");


                    }
                }

                //send.writeBytes("---> Dammi il secondo numero: " + "\n");

                System.out.println("---> MESSAGGIO DEL CLIENT: " + messaggio);
                conta ++;

                //manda un messaggio al client

            }

        } catch (Exception e) {

            System.out.println("ERRORE NELLA COMUNICAZIONE VERSO IL CLIENT \n" + e.getMessage());
        }

    }

    public void close()
    {
        try {

            client.close();
            send.close();
            receive.close();
            server.close();
            
        } catch (Exception e) {
            System.out.println("ERRORE NELLA CHIUSURA\n" + e.getMessage());
        }

    }

}