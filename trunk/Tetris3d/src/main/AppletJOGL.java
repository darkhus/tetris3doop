package main;

import java.awt.*;
import javax.media.opengl.*;
import javax.swing.JApplet;

/**
 * This class creates applets for JOGL display
 * @author Daras
 */
public class AppletJOGL extends JApplet{

    /**
     * game canvas
     */
	TetrisGL canvas;

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
     * initialize applet, starts game
     */
	public void init() {
        new Thread().start();
		setLayout(new BorderLayout());
        setSize(800, 600);
		canvas = makeCanvas();

		add(canvas, BorderLayout.CENTER);
	}

    /**
     * applet starts
     */
	public void start() {		
	}

    /**
     * applet stops
     */
	public void stop() {
	}

    /**
     * applet closed, destroy all
     */
    public void destroy(){
        canvas.stopGame();
    }


} // class