import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console extends Thread {

    public static void c(){
        while (true){
            try {
                String Input = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                Input = bufferedReader.readLine();
                if(Input.matches("")){

                }else {
                    switch (Input){
                        case "END":
                            System.out.println("ENDING NODE");
                            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","pkill -9 java"});
                            return;
                        case "UPDATE":
                            System.out.println(Settings.RED+ "UPDATING AND REBOOTING!!!" + Settings.RESET);
                            Process p1 = Runtime.getRuntime().exec(new String[]{"bash","-c","git pull; echo "+"REBOOTING NODE"+"; pkill -9 java; java -jar Blockchain.jar"});
                        case "TEST_ENC":
                            for(int x = 0; x <= 10000000; x++){
                                if (x >= x%2){
                                    System.out.println(StringUtil.applySha256(Integer.toString(x)));
                                }else {
                                    System.out.println(Settings.GREEN+ StringUtil.applySha512(Integer.toString(x))+ Settings.RESET + "     FOUND!!");
                                }
                            }
                            new Test_Mgr();

                    }
                }
            }catch (Exception ex){
                new Notification("ERROR READ CONSOLE: "+ ex, 3);
                return;
            }
        }
    }
}
