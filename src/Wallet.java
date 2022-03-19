import org.bouncycastle.asn1.ocsp.TBSRequest;

import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import javax.crypto.SecretKey;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public SecretKey secretKey;
    public Cipher cipher;


    public Wallet() {
        generateKeyPair();
    }

    public float Balance() {
        try {
            float input_total = 0;
            float OUTPUTS_TOTAL = 0;
            ArrayList<Transaction> INPUTS = new ArrayList<>();
            ArrayList<Transaction> OUTPUTS = new ArrayList<>();
            for (Block block : Blockchain.Blockchain) {
                for (Transaction transaction : block.Transactions) {
                    if (transaction.TOO.matches(StringUtil.applySha256(this.publicKey.toString()))) {
                        input_total += transaction.Total_Value;
                        INPUTS.add(transaction);
                    }
                    if (transaction.From.matches(StringUtil.applySha256(this.publicKey.toString()))) {
                        OUTPUTS_TOTAL += transaction.Total_Value;
                        OUTPUTS.add(transaction);
                    }
                }
            }
            float Total = input_total -= OUTPUTS_TOTAL;
            return Total;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }


    public ArrayList<Transaction> UTXO_TRANSACTIONS() {
        try {
            ArrayList<Transaction> INPUTS = new ArrayList<>();
            ArrayList<Transaction> OUTPUTS = new ArrayList<>();
            for (Block block : Blockchain.Blockchain) {
                for (Transaction transaction : block.Transactions) {
                    if (transaction.TOO.matches(StringUtil.applySha256(this.publicKey.toString()))) {
                        INPUTS.add(transaction);
                    }
                    if (transaction.From.matches(StringUtil.applySha256(this.publicKey.toString()))) {
                        OUTPUTS.add(transaction);
                    }
                }
            }
            ArrayList<Transaction> UTXO = new ArrayList<>();
            UTXO.addAll(INPUTS);
            UTXO.addAll(OUTPUTS);
            return UTXO;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
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
