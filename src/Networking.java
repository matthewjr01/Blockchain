import org.junit.jupiter.api.parallel.Execution;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
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

                    } catch (IOException ex) {
                        new Notification("NETWORK_CLIENT CLASS (SOCKET): " + ex, 3);
                        return;
                    }
                }
            }catch (BindException bindException){
                END_PROG();
            }catch (IOException ioException){
                new Notification(ioException.toString(), 3);
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

    public static String Version(){
        String V = StringUtil.applySha512(Clients.class.toGenericString()+ Console.class.toGenericString()+main.class.toGenericString()+Networking.class.toString()+Settings.class.toString()+StringUtil.class);
        System.out.println("Version: "+ V);
        return V;
    }

    public static void END_PROG(){
        try{
                Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","pkill -9 java; java -jar Blockchain.jar"});
                new Notification("SOCKET IN USE REBOOTING PROGGRAM", 3);
        }catch (Exception ex){
            return;
        }
    }
}
