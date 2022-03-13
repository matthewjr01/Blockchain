public class main {

    public static void main(String[] args) {
        Thread Network_Client = new Thread(Networking::Network_Client);
        Thread Network_MGR = new Thread(Networking::Network_ClientMSG);

        Thread con = new Thread(Console::c);

        con.start();
        Networking.Network_Threads.add(Network_Client);



        Network_Client.start();
    }
}
