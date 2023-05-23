package assignment4;

public class BoundedBuffer
{
    private String[] items;
    private Status[] status;

    private int writePos = 0;
    private int readPos = 0;
    private int findPos = 0;

    private String findString;
    private String replaceString;


    public BoundedBuffer(int elements, String find, String replace)
    {
        this.findString = find;
        this.replaceString = replace;

        items = new String[elements];
        status = new Status[elements];
        for (int i = 0; i < status.length; i++)
        {
            status[i] = Status.EMPTY;
        }
    }

    /**
     * Writes a string to the buffer, waits until there is an empty slot.
     * @param s the string to write
     */
    public synchronized void write (String s)
    {
        while (status[writePos] != Status.EMPTY) //Checks if the buffer is empty or not, if not empty. Waits for thread.
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        status[writePos] = Status.NEW; //Makes writePos to New so modify can handle it.
        items[writePos] = s;
        writePos = (writePos+1) % items.length;
        notifyAll();
    }//Låset släpps här


    public synchronized String read()
    {
        while (status[readPos] != Status.CHECKED) //waits until status is .checked
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String item = items[readPos];
        status[readPos++] = Status.EMPTY;
        readPos %= items.length;
        notifyAll();
        return item;

    }

    public synchronized void modify()
    {
        while (status[findPos] != Status.NEW) //Sleeps until status is marked with bufferstatus.new.
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        items[findPos] = items[findPos].replace(findString, replaceString);
        status[findPos] = Status.CHECKED;
        findPos = (findPos + 1) % items.length; // Makes sure that all lines gets marked and modified.
        notifyAll();
    }
}
