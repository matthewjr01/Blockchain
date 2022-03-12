import java.util.ArrayList;

public class Notification {

    private static ArrayList<String> logs = new ArrayList<>();
    public Notification(String Message, int Level){
        Notify(Message, Level);
        Level = 0;
        return;
    }

    private static void Notify(String Msg, int Level){
        logs.add(Msg);
        switch (Level){
            case 1:
                System.out.println(Settings.PURPLE + "ID#"+logs.size()+  Settings.RESET + " " +Settings.BLUE + "{NOTIFICATION}" + " "+ Msg + Settings.RESET + "\n");
            case 2:
                System.out.println(Settings.PURPLE + "ID#"+logs.size()+  Settings.RESET + " " +Settings.YELLOW + "{WARNING}" + " "+ Msg + Settings.RESET + "\n");
            case 3:
                System.out.println(Settings.PURPLE + "ID#"+logs.size()+  Settings.RESET + " " +Settings.RED +"{ERROR}" + " "+ Msg + Settings.RESET + "\n");
            case 4:
                System.out.println(Settings.PURPLE + "ID#"+logs.size()+  Settings.RESET + " " +Settings.GREEN + "{SUCCESS}" + " "+ Msg + Settings.RESET + "\n");
        }
        return;
    }

}
