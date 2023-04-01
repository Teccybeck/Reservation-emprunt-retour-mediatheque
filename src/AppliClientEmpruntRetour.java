import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliClientEmpruntRetour {
    private final static int portReservation = 3000;
    private final static int portEmprunt = 4000;
    private final static int portRetour = 5000;
    private final static String host = "localhost";

    public static void main(String[] args) throws IOException {
        System.out.println("Pour emprunter un document tapez 1, pour en retourner un, tapez 2.");
        BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));
        int mode = Integer.parseInt(entreeClavier.readLine());
        try {
            if(mode == 1){
                Emprunter(entreeClavier);
            }
            else {
                Retourner(entreeClavier);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Emprunter(BufferedReader clavier) throws IOException {
        Socket socketReservation = new Socket(host, portReservation);
        socketReservation.close();
        Socket socketEmprunt = new Socket(host, portEmprunt);

        BufferedReader sInEmprunt = new BufferedReader(new InputStreamReader(socketEmprunt.getInputStream()));
        PrintWriter sOutEmprunt = new PrintWriter(socketEmprunt.getOutputStream(), true);

        String text = sInEmprunt.readLine();
        while(!text.isEmpty()){
            System.out.println(text);
            text = sInEmprunt.readLine();
        }

        System.out.println(sInEmprunt.readLine());
        String line = clavier.readLine();
        sOutEmprunt.println(line);
        text = sInEmprunt.readLine();
        while(!text.equals("¨")){
            System.out.println(text);
            line = clavier.readLine();
            sOutEmprunt.println(line);
            text = sInEmprunt.readLine();
        }
        System.out.println(sInEmprunt.readLine());

        socketEmprunt.close();

        Socket socketRetour = new Socket(host, portRetour);
        socketRetour.close();

    }

    private static void Retourner(BufferedReader clavier) throws IOException {
        Socket socketReservation = new Socket(host, portReservation);
        socketReservation.close();
        Socket socketEmprunt = new Socket(host, portEmprunt);
        socketEmprunt.close();
        Socket socketRetour = new Socket(host, portRetour);

        BufferedReader sInRetour = new BufferedReader(new InputStreamReader(socketRetour.getInputStream()));
        PrintWriter sOutRetour = new PrintWriter(socketRetour.getOutputStream(), true);

        System.out.println(sInRetour.readLine());
        String line = clavier.readLine();
        sOutRetour.println(line);
        String text = sInRetour.readLine();
        while(!text.equals("¨")){
            System.out.println(text);
            line = clavier.readLine();
            sOutRetour.println(line);
            text = sInRetour.readLine();
        }
        System.out.println(sInRetour.readLine());
        socketRetour.close();
    }
}
