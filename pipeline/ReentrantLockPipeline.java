package pipeline;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPipeline extends MyPipeline
{
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition receiveCondition = lock.newCondition();
    private Condition sendCondition = lock.newCondition();

    public ReentrantLockPipeline()
    {
    }

    public ReentrantLockPipeline(int maxCapacity)
    {
        super(maxCapacity);
    }

    @Override
    public Message receive() throws InterruptedException
    {
        lock.lock();
        try
        {
            if (messageQueue.isEmpty())
                receiveCondition.await();
            Message retVal = messageQueue.poll();
            sendCondition.signal();
            return retVal;
        }
        finally { lock.unlock(); }
    }

    @Override
    public void send(Message message) throws InterruptedException
    {
        lock.lock();
        try
        {
            if(messageQueue.size() == maxCapacity)
                sendCondition.await();
            messageQueue.add(message);
            receiveCondition.signal();
        }
        finally { lock.unlock(); }
    }
}
