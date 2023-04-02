package Serveur.Donnees;

public class Abonne {
    int IdAbonne;
    String Nom;
    String Prenom;
    int Age;
    String DateNaissance;

    public Abonne(int Id, String Nom, String Prenom, String DateNaissance, int Age) {
        this.IdAbonne = Id;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.DateNaissance = DateNaissance;
        this.Age = Age;
    }

    public String toString(){
        return IdAbonne + " " + Nom + " " + Prenom + " " + Age + " " + DateNaissance;
    }

    public int getIdAbonne(){
        return IdAbonne;
    }

    public int getAge(){
        return Age;
    }
}
