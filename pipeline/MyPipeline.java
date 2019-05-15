package pipeline;

import java.util.LinkedList;
import java.util.Queue;

public abstract class MyPipeline
{
    protected Queue<Message> messageQueue = new LinkedList<>();
    protected int maxCapacity;

    public MyPipeline()
    {
        maxCapacity = Integer.MAX_VALUE;
    }

    public MyPipeline(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    public abstract void send(Message message) throws InterruptedException;

    public abstract Message receive() throws InterruptedException;
}
