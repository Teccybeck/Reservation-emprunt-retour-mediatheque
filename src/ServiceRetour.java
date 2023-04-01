import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceRetour implements Runnable{
    private Socket socket;
    private BufferedReader sIn;
    private PrintWriter sOut;

    public ServiceRetour(Socket s) throws IOException {
        socket = s;
        sIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sOut = new PrintWriter(socket.getOutputStream(),true);
    }
    @Override
    public void run() {
        try {
            sOut.println("Quel DVD souhaitez-vous retourner ?");
            int numeroDVD = Integer.parseInt(sIn.readLine());

            boolean verifDVD = false;
            while(!verifDVD){
                if(!AppliServeur.accesBD().verifDVDExist(numeroDVD)){
                    sOut.println("Numéro de DVD invalide, veuillez en choisir un existant.");
                    numeroDVD = Integer.parseInt(sIn.readLine());
                }
                else if(AppliServeur.accesBD().verifDVDLibre(numeroDVD)) {
                    sOut.println("Le DVD choisis est déjà libre.");
                    numeroDVD = Integer.parseInt(sIn.readLine());
                }
                else if(AppliServeur.accesBD().verifDVDReserve(numeroDVD)){
                    sOut.println("Le DVD choisis est réservé, il ne peut pas être retourné.");
                    numeroDVD = Integer.parseInt(sIn.readLine());
                }
                else{
                    verifDVD = true;
                    sOut.println("¨");
                }
            }
            AppliServeur.accesBD().RetournerDVD(numeroDVD);
            sOut.println("Vous avez retourner le DVD n°" + numeroDVD + ".");

        } catch (IOException ignored) {
        }
    }
}
