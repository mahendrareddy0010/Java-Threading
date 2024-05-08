package unisexBathroom;

public class UnisexBathroomWithoutStarvation {
    private static enum Turn {
        NONE, MEN, WOMEN
    }

    int MAX = 3;
    int waitingMen = 0;
    int waitingWomen = 0;
    int menServed = 0;
    int womenServed = 0;
    int menInside = 0;
    int womenInside = 0;
    Turn turn = Turn.NONE;

    public UnisexBathroomWithoutStarvation(int mAX) {
        MAX = mAX;
    }

    private void useBathroom() throws InterruptedException {
        System.out.println("********** " + Thread.currentThread().getName() + "   using");
        Thread.sleep(1000);
        System.out.println("########### " + Thread.currentThread().getName() + "   leaving");
        if (menInside > 0 && womenInside > 0) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%");
        }
    }

    public void maleUseBathroom() throws InterruptedException {
        synchronized (this) {
            while (turn == Turn.WOMEN || womenInside > 0 || menInside == MAX) {
                waitingMen++;
                wait();
                waitingMen--;
            }
            turn = Turn.MEN;
            menInside++;
            menServed++;
        }
        useBathroom();
        synchronized(this) {
            menInside--;
            if (menServed == MAX || menInside == 0) {
                if (waitingWomen > 0) {
                    turn = Turn.WOMEN;  
                } else {
                    turn = Turn.NONE;   // when you don't have anymore writes
                }
                menServed = 0; 
            }
            notifyAll();
        }
    }

    public void womenUseBathroom() throws InterruptedException {
        synchronized (this) {
            while (turn == Turn.MEN || menInside > 0 || womenInside == MAX) {
                waitingWomen++;
                wait();
                waitingWomen--;
            }
            turn = Turn.WOMEN;
            womenInside++;
            womenServed++;
        }
        useBathroom();
        synchronized(this) {
            womenInside--;
            if (womenServed == MAX || womenInside == 0) {
                if (waitingMen > 0) {
                    turn = Turn.MEN;
                } else {
                    turn = Turn.NONE;
                }
                womenServed = 0;
            }
            notifyAll();
        }
    }
}
