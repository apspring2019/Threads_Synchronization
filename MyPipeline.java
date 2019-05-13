import java.util.LinkedList;
import java.util.Queue;

public class MyPipeline
{
    private Queue<Message> messageQueue = new LinkedList<>();
    private int maxCapacity = Integer.MAX_VALUE;

    public MyPipeline() {}

    public MyPipeline(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    public Message receive() throws InterruptedException
    {
        synchronized (messageQueue)
        {
            if (messageQueue.isEmpty())
                messageQueue.wait();
            Message retVal = messageQueue.poll();
            if (messageQueue.size() == maxCapacity - 1)
                messageQueue.notify();
            return retVal;
        }
    }

    public void send(Message message) throws InterruptedException
    {
        synchronized (messageQueue)
        {
            if (messageQueue.size() == maxCapacity)
                messageQueue.wait();
            messageQueue.add(message);
            if (messageQueue.size() == 1)
                messageQueue.notify();
        }
    }
}
