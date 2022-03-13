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
                            ProcessBuilder pb = new ProcessBuilder("pkill -9 java");
                            pb.redirectErrorStream(true);
                            Process p = pb.start();
                    }
                }
            }catch (Exception ex){
                new Notification("ERROR READ CONSOLE: "+ ex, 3);
                return;
            }
        }
    }
}
