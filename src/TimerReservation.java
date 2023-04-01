import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerReservation implements Runnable{
    private Timer timer;
    private int duree;
    private int numDVD;

    public TimerReservation(int duree, int numDVD){
        this.timer = new Timer();
        this.duree = duree;
        this.numDVD = numDVD;
        AppliServeur.accesBD().addTimer(this);
    }

    @Override
    public void run() {
        dureeTimer();
    }

    public void dureeTimer(){
        TimerTask task = new TimerTask() {
            public void run() {
                AppliServeur.accesBD().removeTimer(TimerReservation.this);
                AppliServeur.accesBD().libererDVD(numDVD);
            }
        };
        this.timer.schedule(task, duree);
    }

    public void stop(){
        AppliServeur.accesBD().removeTimer(this);
        this.timer.cancel();
    }

    public int getNumDVD(){
        return numDVD;
    }

}
