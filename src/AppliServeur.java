import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppliServeur {
    private static final BaseDeDonnee bd = new BaseDeDonnee();

    public static void main(String[] args) throws IOException{
        Serveur serveur = new Serveur();
        new Thread(serveur).start();
    }

    public static BaseDeDonnee accesBD(){
        return bd;
    }
}
