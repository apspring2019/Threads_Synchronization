public class WaitLockTest
{
    public static void main(String[] args) throws InterruptedException
    {
        final Object obj = new Object();
        final long startTime = System.currentTimeMillis();
        new Waiter(obj, "first", startTime).start();
        Thread.sleep(100);
        new Waiter(obj, "second", startTime).start();

        Thread.sleep(15000);
        synchronized (obj)
        {
            obj.notifyAll();
        }
    }
}

class Waiter extends Thread
{
    private final Object lock;

    private final long startTime;

    public Waiter(Object lock, String name, long startTime)
    {
        super(name);
        this.lock = lock;
        this.startTime = startTime;
    }

    @Override
    public void run()
    {
        System.out.println("Starting running at: " + getTimeAndName());
        synchronized (lock)
        {
            System.out.println("Going to sleep for 3 sec at: " + getTimeAndName());
            try
            {
                Thread.sleep(3000);
                System.out.println("After sleep at: " + getTimeAndName());
                System.out.println("Now going to wait. " + getTimeAndName());
                lock.wait();
                System.out.println("Back from wait at: " + getTimeAndName());
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {}
            System.out.println("Now done! " + getTimeAndName());
        }
    }

    private String getTimeAndName()
    {
        return String.format("%d, Thread: %s", System.currentTimeMillis() - startTime, getName());
    }
}
