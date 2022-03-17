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
                            for(int x = 0; x <= 1000; x++){
                                System.out.println(StringUtil.applySha256(Integer.toString(x)));
                            }
                    }
                }
            }catch (Exception ex){
                new Notification("ERROR READ CONSOLE: "+ ex, 3);
                return;
            }
        }
    }
}
