package pipeline;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pipeline.Message;
import pipeline.MyPipeline;
import pipeline.ReentrantLockPipeline;
import pipeline.WaitNotifyPipeline;

public class MainPipeline
{
    public static void main(String[] args) throws InterruptedException
    {
        final MyPipeline pipeline = new WaitNotifyPipeline(10);
//        final MyPipeline pipeline = new ReentrantLockPipeline(10);
        
        Thread reader = new Thread(() ->
        {
            Iterator<Integer> randoms = new Random().ints(500, 2000).iterator();
            try
            {
                while (true)
                {
                    Thread.sleep(randoms.next());
                    System.out.println(pipeline.receive());
                    System.out.println("--------");
                }
            }
            catch (InterruptedException e) { System.err.println("Reader Interrupted"); }
        });
        reader.start();

        Thread writer = new Thread(() ->
        {
            Iterator<Integer> randoms = new Random().ints(500, 2000).iterator();
            int counter = 0;
            try
            {
                while (true)
                {
                    Thread.sleep(randoms.next());
                    Thread.sleep(2000);
                    pipeline.send(new Message("Hi" + counter++, String.valueOf(System.currentTimeMillis())));
                }
            }
            catch (InterruptedException e) { System.err.println("Writer Interrupted"); }
        });
        writer.start();
    }
}
