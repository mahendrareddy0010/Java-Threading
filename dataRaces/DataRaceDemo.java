package dataRaces;

public class DataRaceDemo {

    // to see data races remove volatile keyword
    // private int x = 0;
    // private int y = 0;
    
    // to avoid data races add volatile keyword
    private volatile int x = 0;
    private volatile int y = 0;

    public void increment() {
        x++;
        y++;
    }

    public void checkDataRace() {
        if (y > x) {
            System.out.println("Data race detected : x < y ");
        }
    }

}
