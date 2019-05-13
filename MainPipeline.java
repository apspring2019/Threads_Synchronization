import java.util.Iterator;
import java.util.Random;

public class MainPipeline
{
    public static void main(String[] args) throws InterruptedException
    {
        final MyPipeline pipeline = new MyPipeline(10);

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
