package main;


import java.awt.event.*;
import java.awt.*;
import javax.media.opengl.GLCanvas;

public class KeyChecker implements KeyListener // MouseMotionListener, MouseListener
{
	private Canvas canvas;
	public static boolean[] keys = new boolean[256];
	Cursor cursor;

    public KeyChecker(Canvas can)
    {
    	canvas = can;
    	canvas.addKeyListener(this);   
    }

    public void keyReleased(KeyEvent e)
  	{
  		keys[e.getKeyCode()] = false;
  	}

 	public void keyPressed(KeyEvent e)
 	{
		keys[e.getKeyCode()] = true;
	}

  	public void keyTyped(KeyEvent e)
 	{
    }


}