import java.util.ArrayList;

public class Block {

    public  ArrayList<Transaction> Transactions = new ArrayList<>();
    public  String Chain_Identifier = "";
    public  String ENCRYPTION_TYPE = "";
    public  String PrevHash = "";
    public  String Hash = "";
    public  String DATETIME = "";

    public Block(String PrevHash, String BlockHash, ArrayList<Transaction> Transactions, String DATETIME, String ENCRP_TYPE, String CHAIN_IDENTIFIER) {
        this.Chain_Identifier = CHAIN_IDENTIFIER;
        this.DATETIME = DATETIME;
        this.PrevHash = PrevHash;
        this.Hash = BlockHash;
        this.Transactions = Transactions;
        this.ENCRYPTION_TYPE = ENCRP_TYPE;
    }
}
