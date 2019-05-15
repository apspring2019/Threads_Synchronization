import java.util.concurrent.locks.ReentrantLock;

public class SimpleLockTest
{
    public static void main(String[] args) throws InterruptedException
    {
        final Object lock1 = new Object();
        new SynchronizedBlockLockUser(lock1, "Sfirst").start();
        Thread.sleep(100);
        new SynchronizedBlockLockUser(lock1, "Ssecond").start();

        ReentrantLock lock2 = new ReentrantLock();
        new ReentrantLockUser(lock2, "Rfirst").start();
        Thread.sleep(100);
        new ReentrantLockUser(lock2, "Rsecond").start();
    }
}

class SynchronizedBlockLockUser extends Thread
{
    private final Object lock;

    public SynchronizedBlockLockUser(Object lock, String name)
    {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Thread.sleep(100);
                synchronized (lock)
                {
                    System.out.println("Lock acquired by thread: " + getName());
                    System.out.println("Holding lock for 3 sec");
                    Thread.sleep(3000);
                }
            }
        }
        catch (Exception ignored) { }
    }
}

class ReentrantLockUser extends Thread
{
    private final ReentrantLock lock;

    public ReentrantLockUser(ReentrantLock lock, String name)
    {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Thread.sleep(100);
                lock.lock();
                System.out.println("Lock acquired by thread: " + getName());
                System.out.println("Holding lock for 3 sec");
                Thread.sleep(3000);
                lock.unlock();
            }
        }
        catch (Exception ignored) { }
        finally
        {
            if(lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }
}
