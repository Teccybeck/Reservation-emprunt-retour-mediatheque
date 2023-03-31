public class DVD implements Document{
    private int numero;
    private String titre;
    private Abonne abonne;
    private String etat;
    private boolean adulte;

    public DVD(int num, String Titre, int Etat, boolean adulte){
        numero = num;
        titre = Titre;
        abonne = null;
        if(Etat == 1)
            etat = "Libre";
        else if (Etat == 2)
            etat = "Réservé";
        else
            etat = "Emprunté";
        this.adulte = adulte;
    }

    @Override
    public int numero() {
        return numero;
    }

    @Override
    public Abonne emprunteur() {
        if(etat == "Emprunté")
            return abonne;
        return null;
    }

    @Override
    public Abonne reserveur() {
        if(etat == "Réservé")
            return abonne;
        return null;
    }

    @Override
    public void reservationPour(Abonne ab){
        if(etat == "Libre"){
            etat = "Réservé";
            abonne = ab;
        }
        else{
            try {
                throw new Exception("Ce DVD n'est pas libre.");
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void empruntPar(Abonne ab) {
        if(etat == "Libre"){
            etat = "Emprunté";
            abonne = ab;
        }
        else if(etat == "Réservé"){
            if(!(abonne == ab)){
                try {
                    throw new Exception("Ce DVD est déjà emprunté.");
                } catch (Exception ignored) {}
            }
        }
        else{
            try {
                throw new Exception("Ce DVD n'est pas libre.");
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void retour() {
        etat = "Libre";
        abonne = null;
    }

    public String toString(){
        if (adulte) {
            return numero + " | " + titre + " | " + "Adulte";
        }
        return numero + " | " + titre + " | " + "Tout public";
    }

    public boolean estLibre(){
        if(etat == "Libre")
            return true;
        return false;
    }

    public boolean estReserve(){
        if (etat == "Réservé")
            return true;
        return false;
    }

    public boolean estEmprunte(){
        if(etat == "Emprunté")
            return true;
        return false;
    }

    public boolean isAdulte(){
        return adulte;
    }
}
