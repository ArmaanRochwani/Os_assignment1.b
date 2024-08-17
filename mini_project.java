class PrintNumbers implements Runnable {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class PrintLetters implements Runnable {
    public void run() {
        for (char c = 'A'; c <= 'J'; c++) {
            System.out.println(c);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class MultiThreadExample {
    public static void main(String[] args) {
        // Create instances of PrintNumbers and PrintLetters
        Runnable printNumbers = new PrintNumbers();
        Runnable printLetters = new PrintLetters();

        // Create threads
        Thread thread1 = new Thread(printNumbers);
        Thread thread2 = new Thread(printLetters);

        // Start threads
        thread1.start();
        thread2.start();
    }
}
