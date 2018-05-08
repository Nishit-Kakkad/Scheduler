import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.IOException;

public class Scheduler implements Runnable {
    String path;

    public Scheduler() {}

    public Scheduler(String absolutePath) {
        path = absolutePath;
    }

    public void run() {
        if(path != null) {
            try {
                File f = new File(path);
                if(f.exists() && !f.isDirectory()) {
                    System.out.println("doing the scheduled task");
                    String cmd = "java -jar " + path;
                    Runtime.getRuntime().exec(cmd);
                } else {
                    System.out.println("Jar file not found. Please specify the valid path");
                }
            } catch(IOException ex) {
                System.out.println("IOException caught");
            }
        } else {
            System.out.println("Path is empty");
        }
    }

    public void scheduleTaskEveryday(int hour, int min, int sec) {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask(){
            public void run(){
                Calendar cal = Calendar.getInstance();
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                if(currentHour == hour){
                    Thread t = new Thread(new Scheduler(path));
                    t.start();
                } else {
                    System.out.println("not doing the scheduled task");
                }
            }
        };
        Calendar cal = Calendar.getInstance();  
        cal.setTime(new Date());  
        cal.set(Calendar.HOUR_OF_DAY, hour);  
        cal.set(Calendar.MINUTE, min);  
        cal.set(Calendar.SECOND, sec);  
        cal.set(Calendar.MILLISECOND, 0);

        timer.schedule(tt, cal.getTime());
    }
}