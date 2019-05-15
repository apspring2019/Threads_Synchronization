package pipeline;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyPipeline extends MyPipeline
{

    public WaitNotifyPipeline()
    {
    }

    public WaitNotifyPipeline(int maxCapacity)
    {
        super(maxCapacity);
    }

    public synchronized Message receive() throws InterruptedException
    {
        while (messageQueue.isEmpty())
            this.wait();
        Message retVal = messageQueue.poll();
        this.notify();
        return retVal;
    }

    public synchronized void send(Message message) throws InterruptedException
    {
        while (messageQueue.size() == maxCapacity)
            this.wait();
        messageQueue.add(message);
        this.notify();
    }
}
