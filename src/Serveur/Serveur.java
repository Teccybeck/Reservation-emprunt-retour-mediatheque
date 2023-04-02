package Serveur;

import Serveur.Service.ServiceEmprunt;
import Serveur.Service.ServiceRetour;
import Serveur.Service.ServiceRevervation;

import java.io.IOException;
import java.net.ServerSocket;

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
        while(true){
            try {
                System.err.println("lancement du port "+ this.serveurReservation.getLocalPort());
                System.err.println("lancement du port "+ this.serveurEmprunt.getLocalPort());
                System.err.println("lancement du port "+ this.serveurRetour.getLocalPort());
                while(true){
                    new Thread(new ServiceRevervation(serveurReservation.accept())).start();
                    new Thread(new ServiceEmprunt(serveurEmprunt.accept())).start();
                    new Thread(new ServiceRetour(serveurRetour.accept())).start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
