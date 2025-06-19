public class DBLogger implements Runnable {
    @Override
    public void run() {
        System.out.println("Logging DB activity in background... [Thread: " + Thread.currentThread().getName() + "]");
        try {
            Thread.sleep(500); // Simulate delay
        } catch (InterruptedException e) {
            System.out.println("Logger interrupted: " + e.getMessage());
        }
    }
}
