import org.junit.jupiter.api.MethodOrderer;

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


    public Clients(Socket socket){
        try {
            this.socket = socket;
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            generateKeyPair();


            Random r = new Random(100002203 * socket.getPort());
            int x = r.nextInt(socket.getPort());

            objectOutputStream.writeObject("111Test");
            objectOutputStream.flush();


        }catch (Exception ex){
            new Notification("CLIENT THREAD EXEPTION: "+ ex, 3);
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
