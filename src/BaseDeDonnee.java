import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BaseDeDonnee {
    private Connection connection;
    private ArrayList<DVDConcurent> DVDs;
    private ArrayList<Abonne> Abonnes;
    public BaseDeDonnee() {
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
            connection= DriverManager.getConnection("jdbc:ucanaccess://E:\\Projet visual studio/Projet Archi-Log/Database.accdb");//Establishing Connection
            System.out.println("Connected Successfully");
            DVDs = this.getDVD();
            Abonnes = this.getAbonnes();
        }catch(Exception e){
            System.out.println("Error in connection");

        }
    }

    private ArrayList<DVDConcurent> getDVD() throws SQLException {
        ArrayList<DVDConcurent> list = new ArrayList<>();
        String request = "Select * from DVD";
        PreparedStatement preparedStatement=connection.prepareStatement(request);
        ResultSet resultSet=preparedStatement.executeQuery();
        int numero;
        String titre;
        int etat;
        boolean adulte;
        while(resultSet.next()){
            numero = Integer.parseInt(resultSet.getString(1));
            titre = resultSet.getString(2);
            adulte = Boolean.parseBoolean(resultSet.getString(3));
            etat = Integer.parseInt(resultSet.getString(4));
            list.add(new DVDConcurent(new DVD(numero,titre,etat,adulte)));
        }
        return list;
    }

    private ArrayList<Abonne> getAbonnes() throws SQLException {
        ArrayList<Abonne> list = new ArrayList<>();
        String request = "Select * from Abonne";
        PreparedStatement preparedStatement=connection.prepareStatement(request);
        ResultSet resultSet=preparedStatement.executeQuery();
        int numero = 0;
        String nom = "";
        String prenom = "";
        String dateNaiss = "";
        int age = 0;
        while(resultSet.next()){
            numero = Integer.parseInt(resultSet.getString(1));
            nom = resultSet.getString(2);
            prenom = resultSet.getString(3);
            dateNaiss = resultSet.getString(4);
            age = (Integer.parseInt(String.valueOf(java.time.LocalDate.now()).split("-")[1]) > Integer.parseInt(resultSet.getString(4).split("-")[1])) || (Integer.valueOf(String.valueOf(LocalDate.now()).split("-")[1]).equals(Integer.valueOf(resultSet.getString(4).split("-")[1])) && Integer.parseInt(String.valueOf(java.time.LocalDate.now()).split("-")[2]) >= Integer.parseInt(resultSet.getString(4).split("-")[1].split(" ")[0]))?0:-1;
            age += (Integer.parseInt(String.valueOf(java.time.LocalDate.now()).split("-")[0]) - Integer.parseInt(resultSet.getString(4).split("-")[0]));
            list.add(new Abonne(numero,nom,prenom,dateNaiss,age));
        }
        return list;
    }

    public String selectCatalogue(){
        String res = "";
        for(int i = 0; i < DVDs.size(); i++){
            if(DVDs.get(i).estLibre())
                res += DVDs.get(i).toString() + "\n";
        }
        return res;
    }

    public boolean verifAbonneExist(int IdAbonne){
        for(Abonne a : Abonnes){
            if(a.getIdAbonne() == IdAbonne)
                return true;
        }
        return false;
    }

    public boolean verifDVDExist(int id){
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == id)
                return true;
        }
        return false;
    }

    public boolean verifAdulte(int idDVD, int Age){
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == idDVD){
                if(dvd.isAdulte() && Age < 16)
                    return false;
            }
        }
        return true;
    }

    public boolean verifDVDLibre(int id){
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == id)
                return dvd.estLibre();
        }
        return false;
    }

    public void reserverDVD(int idDVD, int idAbonne){
        Abonne abonne = null;
        for(Abonne a : Abonnes){
            if(a.getIdAbonne()==idAbonne)
                abonne = a;
        }
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == idDVD)
                dvd.reservationPour(abonne);
        }
    }

    public boolean verifDVDReserve(int id){
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == id){
                return dvd.estReserve();
            }
        }
        return false;
    }

    public void libererDVD(int idDVD){
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == idDVD){
                dvd.retour();
            }
        }
    }

    public int getAgeById(int id){
        for(Abonne a : Abonnes){
            if(a.getIdAbonne() == id)
                return a.getAge();
        }
        return 0;
    }

    public boolean verifEmprunt(int IdDVD, int IdAbonne) {
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == IdDVD){
                if(dvd.estLibre())
                    return true;
                else if(!dvd.estEmprunte()){
                    if(dvd.reserveur() != null){
                        if(dvd.reserveur().getIdAbonne() == IdAbonne)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public void EmprunterDVD(int IdDVD, int IdAbonne){
        Abonne abonne = null;
        for(Abonne a : Abonnes){
            if(a.getIdAbonne()==IdAbonne)
                abonne = a;
        }
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == IdDVD)
                dvd.empruntPar(abonne);
        }
    }

    public void RetournerDVD(int IdDVD){
        for(DVDConcurent dvd : DVDs){
            if(dvd.numero() == IdDVD)
                dvd.retour();
        }
    }
}
