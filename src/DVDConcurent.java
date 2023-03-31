public class DVDConcurent implements Document{
    private DVD dvd;
    public DVDConcurent(DVD d){
        dvd = d;
    }
    @Override
    public int numero() {
        synchronized(dvd) {
            return dvd.numero();
        }
    }

    @Override
    public Abonne emprunteur() {
        synchronized(dvd) {
            return dvd.emprunteur();
        }
    }

    @Override
    public Abonne reserveur() {
        synchronized(dvd) {
            return dvd.reserveur();
        }
    }

    @Override
    public void reservationPour(Abonne ab) {
        synchronized(dvd){
            dvd.reservationPour(ab);
        }
    }

    @Override
    public void empruntPar(Abonne ab) {
        synchronized(dvd){
            dvd.empruntPar(ab);
        }
    }

    @Override
    public void retour() {
        synchronized(dvd){
            dvd.retour();
        }
    }

    public String toString(){
        synchronized (dvd){
            return dvd.toString();
        }
    }

    public boolean estLibre(){
        synchronized (dvd){
            return dvd.estLibre();
        }
    }

    public boolean isAdulte(){
        synchronized (dvd){
            return dvd.isAdulte();
        }
    }

    public boolean estReserve(){
        synchronized (dvd){
            return dvd.estReserve();
        }
    }

    public boolean estEmprunte(){
        synchronized (dvd){
            return dvd.estEmprunte();
        }
    }
}
