import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;

public class Networking implements Serializable {

    public static ArrayList<Thread> Network_Threads = new ArrayList<>();
    public static ArrayList<Thread> Network_Clients = new ArrayList<>();
    public static ArrayList<String> Network_Clients_Identifiers = new ArrayList<>();


    public static void Network_Client(){
        while (true) {
            try {
                new Notification("STARTING NETWORK CLIENT CONNECTION", 1);
                while (true) {
                    ServerSocket serverSocket = new ServerSocket(Settings.API_NET_PORT);
                    try {
                        while (true) {
                            Socket socket = serverSocket.accept();
                            new Notification("NETWORK CLIENT CONNECTION OPEN!", 1);
                            Clients clients = new Clients(socket);
                            clients.start();
                            new Notification("GOT CONNECTION FROM: "+ socket.getInetAddress(), 4);
                            Network_Clients.add(clients);


                        }

                    } catch (Exception ex) {
                        new Notification("NETWORK_CLIENT CLASS (SOCKET): " + ex, 3);
                        return;
                    }
                }
            } catch (Exception ex) {
                new Notification("NETWORK_CLIENT CLASS: " + ex, 3);
                return;
            }
        }
    }

    public static void Network_ClientMSG(){
        try{


        }catch (Exception ex){
            new Notification("NETWORK CLIENTMSG: "+ ex, 3);
            return;
        }
    }
}
