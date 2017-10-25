/**
 * Created by jh on 10/21/17.
 */
public class Fibonacci
{
  public FThread t1, t2;

  Fibonacci()
  {
    t1 = new FThread("Thread 1");
    t2 = new FThread("Thread 2");

    t1.start();
    t2.start();
  }

  public class FThread extends Thread
  {
    private final String NAME; //Name of thread
    private long step = 0; //Steps since start.
    private long x = 1; // fib(step-2)
    private long y = 1; // fib(step-1)
    private long z; // fib(step)

    FThread(String name)
    {
      this.NAME = name;
      z = x + y;
      step++;
    }

    public void run()
    {
      while (!this.isInterrupted())
      {
        synchronized (this)
        {
          if (z == 7540113804746346429L)
          {
            x = 1;
            y = 1;
            z = 2;
          }
          else
          {
            x = y;
            y = z;
            z = x + y;
          }

          step++;
//          System.out.println(this.NAME + ":: " + this.x + "," + this.y +"," + this.z);
        }
      }

      System.out.println(this.NAME + " is signing out...");
    }
  }

  public static void main(String[] args)
  {
    Fibonacci fib = new Fibonacci();


    for(int i = 0; i < 10; i++)
    {
      synchronized (fib.t1)
      {
        System.out.println(fib.t1.NAME + " ("+ fib.t1.step + ") " +  fib.t1.x + "," + fib.t1.y + "," + fib.t1.z);
      }
      synchronized (fib.t2)
      {
        System.out.println(fib.t2.NAME + " ("+ fib.t2.step + ") " + fib.t2.x + "," + fib.t2.y + "," + fib.t2.z);
      }

      try
      {
        Thread.sleep(2000);
      }
      catch (InterruptedException e)
      {
      }
    }

    fib.t1.interrupt();
    fib.t2.interrupt();

    while (true)
    {
      if (!fib.t1.isAlive() && !fib.t2.isAlive())
      {
        System.out.println("Program Exit");
        System.exit(0);
      }
    }
  }
}
