package assignment4;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Reader implements Runnable
{
    private BoundedBuffer buffer;
    private String[] stringToRead;
    private int length;
    private JTextArea target;

    /**
     * Constructor
     * @param buffer BoundedBuffer class
     * @param length the length of the text
     * @param target where the text ís going
     */

    public Reader(BoundedBuffer buffer, int length, JTextArea target)
    {
        this.buffer = buffer;
        this.length = length;
        this.target = target;
    }


    @Override
    public void run() {
        target.setText("");

        for(int i = 0; i < this.length; ++i) {
            String s = this.buffer.read();
            target.append(s + "\n");
        }
    }
}
