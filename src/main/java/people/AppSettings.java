package people;

import java.util.Random;

public class AppSettings {

    public final Fixed Fixed;
    public class Fixed {

        public int L = 1000;  // the size of the environment
        public int dis = 100; // the distance between two parallel streets
        public int dist = 100; // the maximum distance between two persons for considering they are close (corresponding vertices are neighbors)

        public int getEnvSize(){
            return L;
        }
        public int getStreetDistance() {
            return dis;
        }
        public int getClosePeopleDistance() {
            return dist;
        }
    }

    public final Params Params;
    public class Params {
        public int n = 100;             // ∈ {100, 200, 300, 500} (number of persons)
        public int dstart = 10;          // ∈ {0,5,10,20}
        public int dinfectious = 10;    // ∈ {10,20,30,50,100}
        public int drecovered = 20;     // ∈ {20,50,100,200,500}

        public int getPeopleCount() {
            return n;
        }
        public int getInfectionStartDuration() {
            return dstart;
        }
        public int getInfectionDuration() {
            return dinfectious;
        }
        public int getRecoveryDuration() {
            return drecovered;
        }
    }

    public int CurrentTime;

    private static AppSettings _instance;
    private AppSettings() {
        this.CurrentTime = 0;
        this.Fixed = new Fixed();
        this.Params = new Params();
        this.random = new Random();
    }
    public static AppSettings get_instance(){
        if(_instance == null)
            _instance = new AppSettings();

        return _instance;
    }

    public Random random;
}
