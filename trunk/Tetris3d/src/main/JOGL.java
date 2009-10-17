package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;


public class JOGL extends JFrame implements WindowListener
{
	private TetrisGL canvas;

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

	public TetrisGL makeCanvas()
	{
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setHardwareAccelerated(true);
        capabilities.setNumSamples(2); // 2x antialiasing
        capabilities.setSampleBuffers(true);

	    return new TetrisGL(capabilities);
	}


	public void windowActivated(WindowEvent e)
	{ canvas.resumeGame(); }

	public void windowDeactivated(WindowEvent e)
	{ canvas.pauseGame(); }

	public void windowDeiconified(WindowEvent e)
	{ canvas.resumeGame(); }

	public void windowIconified(WindowEvent e)
	{ canvas.pauseGame(); }

	public void windowClosing(WindowEvent e)
	{ canvas.stopGame(); }

	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}

	public static void main(String[] args)
	{
    	new JOGL();
	}
}

