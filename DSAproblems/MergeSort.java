package DSAproblems;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MergeSort {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static void mergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + (r - l) / 2;
        Thread t1 = Thread.ofPlatform().unstarted(() -> {
            mergeSort(arr, l, mid);
        });
        Thread t2 = Thread.ofPlatform().unstarted(() -> {
            mergeSort(arr, mid + 1, r);
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] tmp = new int[r - l + 1];
        int idx = 0;
        int left = l;
        int right = mid + 1;

        while (left <= mid && right <= r) {
            if (arr[left] <= arr[right]) {
                tmp[idx] = arr[left];
                left++;
            } else {
                tmp[idx] = arr[right];
                right++;
            }
            idx++;
        }

        while (left <= mid) {
            tmp[idx] = arr[left];
            left++;
            idx++;
        }
        while (right <= r) {
            tmp[idx] = arr[right];
            right++;
            idx++;
        }

        idx = l;
        for (int val : tmp) {
            arr[idx] = val;
            idx++;
        }

        return;
    }

    private static void mergeSortWithFixedThreads(int[] arr, int l, int r, int numberOfThreads) {
        if (l == r) {
            return;
        }
        int mid = l + (r - l) / 2;
        if (numberOfThreads > 1) {
            Thread t1 = Thread.ofPlatform().unstarted(() -> {
                mergeSortWithFixedThreads(arr, l, mid, numberOfThreads / 2);
            });
            t1.start();
            mergeSortWithFixedThreads(arr, mid + 1, r, numberOfThreads / 2);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            mergeSortWithFixedThreads(arr, l, mid, numberOfThreads / 2);
            mergeSortWithFixedThreads(arr, mid + 1, r, numberOfThreads / 2);
        }

        int[] tmp = new int[r - l + 1];
        int idx = 0;
        int left = l;
        int right = mid + 1;

        while (left <= mid && right <= r) {
            if (arr[left] <= arr[right]) {
                tmp[idx] = arr[left];
                left++;
            } else {
                tmp[idx] = arr[right];
                right++;
            }
            idx++;
        }

        while (left <= mid) {
            tmp[idx] = arr[left];
            left++;
            idx++;
        }
        while (right <= r) {
            tmp[idx] = arr[right];
            right++;
            idx++;
        }

        idx = l;
        for (int val : tmp) {
            arr[idx] = val;
            idx++;
        }

        return;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int SIZE = 10000;
        int[] arr = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = random.nextInt(SIZE);
        }
        long start = System.nanoTime();
        mergeSort(arr, 0, SIZE - 1);
        System.out.println("Naive Threading : " + (System.nanoTime() - start) / 1000000 + " ms");
        System.out.println("Correctness : " + isSorted(arr));

        for (int i = 0; i < SIZE; i++) {
            arr[i] = random.nextInt(SIZE);
        }
        start = System.nanoTime();
        mergeSortWithFixedThreads(arr, 0, SIZE - 1, 8);
        System.out.println("Fixed threads : " + (System.nanoTime() - start) / 1000000 + " ms");
        System.out.println("Correctness : " + isSorted(arr));
    }
}
