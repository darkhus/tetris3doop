package main;

import java.awt.*;
import javax.media.opengl.*;
import javax.swing.JApplet;


public class AppletJOGL extends JApplet{
	
	TetrisGL canvas;

	public TetrisGL makeCanvas()
	{
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setHardwareAccelerated(true);
        capabilities.setNumSamples(2); // 2x antialiasing
        capabilities.setSampleBuffers(true);

	    return new TetrisGL(capabilities);
	}

	public void init() {
        new Thread().start();
		setLayout(new BorderLayout());
        setSize(800, 600);
		canvas = makeCanvas();

		add(canvas, BorderLayout.CENTER);
	}

	public void start() {
		
	}

	public void stop() {
		canvas.destroyAll();		
	}


} // class