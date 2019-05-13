public class ImageProcessor extends Thread
{
    @Override
    public void run()
    {
        try
        {
            for (int x = 0; x < 100000; x++)
            {
                if(interrupted())
                    throw new InterruptedException();
                for (int y = 0; y < 100000; y++)
                    doSomethingHard(x, y);
            }
        }
        catch (InterruptedException ex) { System.err.println("Interrupted!"); }
        finally { System.out.println("Finished"); }

    }

    private void doSomethingHard(int x, int y)
    {
        if (x == 0 || y == 0)
            return;
        doSomethingHard(x - 1, y - 1);
    }
}
