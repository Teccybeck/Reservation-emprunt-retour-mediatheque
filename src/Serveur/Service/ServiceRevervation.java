package Serveur.Service;

import Serveur.AppliServeur;
import Serveur.TimerReservation;

import java.io.*;
import java.net.Socket;

public class ServiceRevervation implements Runnable{
    private Socket socket;
    private BufferedReader sIn;
    private PrintWriter sOut;
    private final static int tempsReservation = 7200000;

    public ServiceRevervation(Socket s) throws IOException {
        socket = s;
        sIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sOut = new PrintWriter(socket.getOutputStream(),true);
    }
    @Override
    public void run() {
        try {
            synchronized (AppliServeur.accesBD()){
                sOut.println(AppliServeur.accesBD().selectCatalogue());
                int numeroAbonne = -1;
                do{
                    sOut.println("Quel est votre numéro d'abonné ?");
                    numeroAbonne = Integer.parseInt(sIn.readLine());
                } while(!AppliServeur.accesBD().verifAbonneExist(numeroAbonne));

                sOut.println("Quel DVD souhaitez-vous réserver ?");
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
                    else if(!AppliServeur.accesBD().verifDVDLibre(numeroDVD)) {
                        sOut.println("Le DVD choisis n'est pas disponible.");
                        numeroDVD = Integer.parseInt(sIn.readLine());
                    }
                    else{
                        verifDVD = true;
                        sOut.println("¨");
                    }
                }
                AppliServeur.accesBD().reserverDVD(numeroDVD,numeroAbonne);
                sOut.println("Votre DVD est réservé pendant 2h.");

                new Thread(new TimerReservation(10000,numeroDVD)).start();

                sIn.close();
                sOut.close();
                socket.close();
            }
        } catch (IOException ignored) {
        }
    }
}
