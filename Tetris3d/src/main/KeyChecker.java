package main;


import java.awt.event.*;
import java.awt.*;
import javax.media.opengl.GLCanvas;

/**
 * This calss handle events for keys
 * @author Darek
 */
public class KeyChecker implements KeyListener
{
    /**
     * boolean array for keys pressed, if key is pressed
     * then appropriate array's elemt chnge it's state to true
     */
	public static boolean[] keys = new boolean[256];

    /**
     * Constructor which add key listener to game canvas
     * @param can cavnas for game
     */
    public KeyChecker(Canvas can)
    {
    	can.addKeyListener(this);
    }

    /**
     * implemented method form KeyListener interface,
     * when key is released changes it atate (boolean in array)
     * to false
     * @param e key event
     */
    public void keyReleased(KeyEvent e)
  	{
  		keys[e.getKeyCode()] = false;
  	}

    /**
     * implemented method form KeyListener interface,
     * when key is released changes it atate (boolean in array)
     * to true
     * @param e key event
     */
 	public void keyPressed(KeyEvent e)
 	{
		keys[e.getKeyCode()] = true;
	}

    /**
     * implemented method form KeyListener interface
     * @param e key event
     */
  	public void keyTyped(KeyEvent e)
 	{
    }


}