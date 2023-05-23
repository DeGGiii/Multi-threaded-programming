package assignment4;

public class Modifier implements Runnable
{
    private BoundedBuffer buffer;
    private int length;

    /**
     * Constructs a Modifier
     * @param buffer buffer the buffer to work with
     * @param length length the number of times to call modify
     */
    public Modifier(BoundedBuffer buffer, int length)
    {
        this.buffer = buffer;
        this.length = length;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < length; i++)
        {
            buffer.modify();
            //System.out.println("modified " + i);
        }
    }
}
