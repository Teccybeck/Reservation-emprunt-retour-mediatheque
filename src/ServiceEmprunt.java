import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt implements Runnable{
    private Socket socket;
    private BufferedReader sIn;
    private PrintWriter sOut;

    public ServiceEmprunt(Socket s) throws IOException {
        socket = s;
        sIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sOut = new PrintWriter(socket.getOutputStream(),true);
    }
    @Override
    public void run() {
        try {
            sOut.println(AppliServeur.accesBD().selectCatalogue());
            int numeroAbonne = -1;
            do{
                sOut.println("Quel est votre numéro d'abonné ?");
                numeroAbonne = Integer.parseInt(sIn.readLine());
            } while(!AppliServeur.accesBD().verifAbonneExist(numeroAbonne));

            sOut.println("Quel DVD souhaitez-vous emprunter ?");
            int numeroDVD = Integer.parseInt(sIn.readLine());

            boolean verifDVD = false;
            while(!verifDVD){
                if(!AppliServeur.accesBD().verifDVDExist(numeroDVD)){
                    sOut.println("Numéro de DVD invalide, veuillez en choisir un existant.");
                    numeroDVD = Integer.parseInt(sIn.readLine());
                }
                else if(!AppliServeur.accesBD().verifAdulte(numeroDVD, AppliServeur.accesBD().getAgeById(numeroAbonne))){
                    sOut.println("Vous n'avez pas l'age requis pour ce DVD, veuillez en choisir un nouveau.");
                    numeroDVD = Integer.parseInt(sIn.readLine());
                }
                else if(!AppliServeur.accesBD().verifEmprunt(numeroDVD, numeroAbonne)) {
                    sOut.println("Le DVD choisis n'est pas disponible.");
                    numeroDVD = Integer.parseInt(sIn.readLine());
                }
                else{
                    verifDVD = true;
                    sOut.println("¨");
                }
            }
            AppliServeur.accesBD().EmprunterDVD(numeroDVD,numeroAbonne);
            sOut.println("Vous avez emprunté le DVD n°" + numeroDVD + ".");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
