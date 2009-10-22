package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;

/**
 * This class creates frame and opengl canvas to render
 * graphic, starts render thread and makes it visible
 * @author Darek
 */
public class JOGL extends JFrame implements WindowListener
{
    /**
     * rendering canvas for this application
     */
	private TetrisGL canvas;

    /**
     * constructor, starts game
     */
	public JOGL()
	{
	  	super("Tetris 3D");
	    Container c = getContentPane();
	    c.setLayout( new BorderLayout() );
	    c.add( makeRenderPanel(), BorderLayout.CENTER);

	    addWindowListener(this);

	    pack();
	    setVisible(true);
	}


    /**
     * this method makes rendering panel with opengl rendering canvas
     * @return panel witch rendering ranvas
     */
	public JPanel makeRenderPanel()
	{
	    JPanel renderPane = new JPanel();
	    renderPane.setLayout( new BorderLayout() );
	    renderPane.setOpaque(true);
	    renderPane.setPreferredSize( new Dimension(800, 600));

	    canvas = makeCanvas();

	    renderPane.add(canvas, BorderLayout.CENTER);

	    canvas.setFocusable(true);
	    canvas.requestFocus();

	    renderPane.addComponentListener( new ComponentAdapter() {
	      public void componentResized(ComponentEvent evt)
	      { Dimension d = evt.getComponent().getSize();
	        canvas.reshape(d.width, d.height);
	      }
	    });

	    return renderPane;
	}

    /**
     * create canvas accordin to hardware capabilities,
     * alse enabls 2x antialiasing
     * @return game canvas
     */
	public TetrisGL makeCanvas()
	{
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setHardwareAccelerated(true);
        capabilities.setNumSamples(2); // 2x antialiasing
        capabilities.setSampleBuffers(true);

	    return new TetrisGL(capabilities);
	}

    /**
     * implemented method from WindowListener interface
     * @param e window event
     */
	public void windowActivated(WindowEvent e)
	{  }

    /**
     * implemented method from WindowListener interface
     * @param e window event
     */
	public void windowDeactivated(WindowEvent e)
	{  }

    /**
     * implemented method from WindowListener interface
     * @param e window event
     */
	public void windowDeiconified(WindowEvent e)
	{ }

    /**
     * implemented method from WindowListener interface
     * @param e window event
     */
	public void windowIconified(WindowEvent e)
	{ }

    /**
     * implemented method from WindowListener interface,
     * stops game when closing
     * @param e window event
     */
	public void windowClosing(WindowEvent e)
	{ canvas.stopGame(); }

    /**
     * implemented method from WindowListener interface
     * @param e window event
     */
	public void windowClosed(WindowEvent e) {}
    /**
     * implemented method from WindowListener interface
     * @param e window event
     */
	public void windowOpened(WindowEvent e) {}

    /**
     * main application method, starts game
     * @param args args for this application
     */
	public static void main(String[] args)
	{
    	new JOGL();
	}
}

