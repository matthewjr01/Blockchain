import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Random;
import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import javax.crypto.SecretKey;

public class Clients extends Thread implements Serializable{

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public PrivateKey privateKey;
    public PublicKey publicKey;
    public SecretKey secretKey;
    public Cipher cipher;

    public ArrayList<Thread> Active_Client_Threads = new ArrayList<>();
    public static ArrayList<String> ALLOWED_CCM = new ArrayList<>();

    public Clients(Socket socket){
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            this.socket = socket;
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
//            generateKeyPair();
            new Notification("Made Client for: "+ socket.getInetAddress(), 4);

            PublicKey pub= (PublicKey) objectInputStream.readObject();
            PrivateKey privateKey = (PrivateKey) objectInputStream.readObject();

            publicKey = pub;
            if(!Networking.Network_Clients_Identifiers.contains(StringUtil.applySha256(publicKey.toString()))){
                Networking.Network_Clients_Identifiers.add(StringUtil.applySha256(publicKey.toString()));
            }
            new Notification("CLIENT REGISTERED: "+ Networking.Network_Clients_Identifiers.get(Networking.Network_Clients_Identifiers.size() -1), 4);
            objectOutputStream.flush();

            String Req = (String) objectInputStream.readObject();
            if(Req.matches("GET_ADDRESS")){
                objectOutputStream.writeObject(Networking.Network_Clients_Identifiers);
            }
            if(Req.matches("CCM_NEW")){
                String CCM_IENTI = (String) objectInputStream.readObject();
                if(ALLOWED_CCM.contains(CCM_IENTI)){
                    objectOutputStream.writeObject(Settings.GREEN+ "ALLOWED!!" + Settings.RESET);
                }else {
                    objectOutputStream.writeObject(Settings.RED + "DENIED" + Settings.RESET);
                }
            }
            Thread WAIT_INPUT = new Thread(this::WAIT_INPUT);
            WAIT_INPUT.start();

            Active_Client_Threads.add(WAIT_INPUT);


        }catch (Exception ex){
            new Notification("CLIENT THREAD EXEPTION: "+ ex, 3);
            return;
        }
    }

    public void WAIT_INPUT(){
        while (true){
            try{
                String Req = (String) objectInputStream.readObject();

                if(Req.matches("Msg")){
                    String SendToo = (String) objectInputStream.readObject();
                    String Msg = (String) objectInputStream.readObject();

                    if(Networking.Network_Clients_Identifiers.contains(SendToo)){
                        Networking.Network_Clients.get(FIND_CLIENT(StringUtil.applySha256(publicKey.toString()))).Write_MSG(Msg, StringUtil.applySha256(publicKey.toString()));
                    }
                }
            }catch (Exception ex){
                System.out.println(ex);
                return;
            }
        }
    }

    public int FIND_CLIENT(String FID){
        try{
            for(Clients client: Networking.Network_Clients){
                if(StringUtil.applySha256(publicKey.toString()).matches(FID)){
                    return Networking.Network_Clients.indexOf(client);
                }
            }
        }catch (Exception ex){
            new Notification(ex.toString(), 3);
        }
        return -1;
    }

    public void Write_MSG(String Msg, String From){
        try{
            this.objectOutputStream.writeObject("{"+"FROM::"+From+"} "+ Msg);
            return;
        }catch (Exception ex){
            new Notification(ex.toString(), 3);
            return;
        }
    }

    public void Read(){
        try{
            while (true) {
                Object obj = objectInputStream.readObject();
                if(obj.getClass() == String.class){
                    String msg = (String) obj;
                    System.out.println(Settings.BLUE + "{CLIENT}"+ msg + Settings.RESET);
                }
            }

        }catch (Exception ex){
            new Notification(ex.toString(), 3);
            return;
        }

    }

    public void Send_Object(Object obj){
        try{
            objectOutputStream.writeObject(obj);
            new Notification("SENT TO CLIENT", 4);
            return;
        }catch (Exception ex){
            new Notification(ex.toString(), 3);
            return;
        }
    }


    public void generateKeyPair() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair

            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
