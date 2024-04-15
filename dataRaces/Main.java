package dataRaces;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final DataRaceDemo obj = new DataRaceDemo();

        Thread incrementThread = Thread.ofPlatform().unstarted(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i = i + 1) {
                obj.increment();
            }
        });
        Thread conditionCheckingThread = Thread.ofPlatform().unstarted(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i = i + 1) {
                obj.checkDataRace();
            }
        });
        incrementThread.start();
        conditionCheckingThread.start();
    }

}
