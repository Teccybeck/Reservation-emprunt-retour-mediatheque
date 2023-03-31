import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliClientReservation {
    private final static int portReservation = 3000;
    private final static int portEmprunt = 4000;
    private final static int portRetour = 5000;
    private final static String host = "localhost";

    public static void main(String[] args) {
        try{
            Socket socketReservation = new Socket(host, portReservation);

            BufferedReader sInReservation = new BufferedReader(new InputStreamReader(socketReservation.getInputStream()));
            PrintWriter sOutReservation = new PrintWriter(socketReservation.getOutputStream(), true);

            BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));

            String text = sInReservation.readLine();
            while(!text.isEmpty()){
                System.out.println(text);
                text = sInReservation.readLine();
            }

            System.out.println(sInReservation.readLine());
            String line = entreeClavier.readLine();
            sOutReservation.println(line);
            text = sInReservation.readLine();
            while(!text.equals("¨")){
                System.out.println(text);
                line = entreeClavier.readLine();
                sOutReservation.println(line);
                text = sInReservation.readLine();
            }
            System.out.println(sInReservation.readLine());

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
