import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable{
    private static final int portReservation = 3000;
    private static final int portEmprunt = 4000;
    private static final int portRetour = 5000;
    private ServerSocket serveurReservation;
    private ServerSocket serveurEmprunt;
    private ServerSocket serveurRetour;

    public Serveur() throws IOException{
        serveurReservation = new ServerSocket(portReservation);
        serveurEmprunt = new ServerSocket(portEmprunt);
        serveurRetour = new ServerSocket(portRetour);
    }

    @Override
    public void run() {
        try {
            System.err.println("lancement du port "+ this.serveurReservation.getLocalPort());
            System.err.println("lancement du port "+ this.serveurEmprunt.getLocalPort());
            System.err.println("lancement du port "+ this.serveurRetour.getLocalPort());
            while(true){
                Socket socketReservation = serveurReservation.accept();
                Socket socketEmprunt = serveurEmprunt.accept();
                Socket socketRetour = serveurRetour.accept();
                new Thread(new ServiceRevervation(socketReservation)).start();
                new Thread(new ServiceEmprunt(socketEmprunt)).start();
                new Thread(new ServiceRetour(socketRetour)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
