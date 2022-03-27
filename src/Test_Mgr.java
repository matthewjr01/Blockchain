import java.util.ArrayList;
import java.util.Random;

public class Test_Mgr {

    public Test_Mgr(){
        Test_ME();
    }

    public static void Test_ME(){
        try{
            ArrayList<Blockchain> Test_Chains = new ArrayList<>();
            ArrayList<Wallet> Test_Wallets = new ArrayList<>();

            Wallet TEST_wallet = new Wallet();
            Blockchain TEST_blockchain = new Blockchain();
            Random r = new Random();

            for(int i = 0; i <= r.nextInt(3900); i++){
                TEST_wallet = new Wallet();
                Test_Wallets.add(TEST_wallet);
                System.out.println("MADE NEW WALLET: "+ TEST_wallet.publicKey.toString());
            }

            for(int x = 0; x<= r.nextInt(3900); x++){
                TEST_blockchain = new Blockchain();
                Test_Chains.add(TEST_blockchain);
                System.out.println("ADDED NEW BLOCKCHAIN AT: "+ Test_Chains.indexOf(TEST_blockchain));
            }
            return;
        }catch (Exception ex){
            new Notification("ERROR IN TEST_ME: "+ ex, 3);
        }
    }
}
