package uberRiderProblem;

public class UberRiderProblem {
    private int republicans = 0;
    private int democrates = 0;
    private int releaseR = 0;
    private int releaseD = 0;
    private boolean acceptingRequests = true;
    private int seats = 0;
    private int rideNum = 0;

    public synchronized void rideRepublican() throws InterruptedException {
        while (acceptingRequests == false) {
            wait();
        }
        republicans++;
        if(republicans == 2 && democrates >=2) {
            releaseR = 1;
            releaseD = 2;
            acceptingRequests = false;
            rideNum++;
        }
        else if (republicans == 4) {
            releaseR = 3;
            acceptingRequests = false;
            rideNum++;
        } else {
            while (releaseR == 0) {
                wait();
            }
            releaseR--;
        }
        seats++;
        System.out.println("Thread : " + Thread.currentThread().getName() + " seated at " + seats + "  Ride Number : " + rideNum);
        if(seats == 4) {
            System.out.println(Thread.currentThread().getName() + " starts the drive for ride Number : " + rideNum);
            acceptingRequests = true;
            seats = 0;
        }
        republicans--;
        notifyAll();
    }


    public synchronized void rideDemocrat() throws InterruptedException {
        while (acceptingRequests == false) {
            wait();
        }
        democrates++;
        if(democrates == 2 && republicans >=2) {
            releaseD = 1;
            releaseR = 2;
            acceptingRequests = false;
            rideNum++;
        }
        else if (democrates == 4) {
            releaseD = 3;
            acceptingRequests = false;
            rideNum++;
        } else {
            while (releaseD == 0) {
                wait();
            }
            releaseD--;
        }
        seats++;
        System.out.println("Thread : " + Thread.currentThread().getName() + " seated at " + seats + "  Ride Number : " + rideNum);
        if(seats == 4) {
            System.out.println(Thread.currentThread().getName() + " starts the drive for ride Number : " + rideNum);
            acceptingRequests = true;
            seats = 0;
        }
        democrates--;
        notifyAll();
    }


    @Override
    public String toString() {
        return "UberRiderProblem [republicans=" + republicans + ", democrates=" + democrates + ", releaseR=" + releaseR
                + ", releaseD=" + releaseD + ", acceptingRequests=" + acceptingRequests + ", seats=" + seats + ", rideNum=" + rideNum
                + "]";
    }
}
