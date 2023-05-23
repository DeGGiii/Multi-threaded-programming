package assignment4;

import java.util.List;

public class Writer implements Runnable
{
    private BoundedBuffer buffer;
    public String[] stringToWrite;

    /**
     * Constructor
     * @param buffer BoundedBuffer class
     * @param stringToWrite string that is getting written
     */
    public Writer(BoundedBuffer buffer, String[] stringToWrite)
    {
        this.buffer = buffer;
        this.stringToWrite = stringToWrite;

    }

    @Override
    public void run()
    {
        for (String s : stringToWrite)
        {
            buffer.write(s);
        }

    }
}
