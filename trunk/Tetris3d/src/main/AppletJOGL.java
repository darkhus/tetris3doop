package main;

import java.applet.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.*;

public class AppletJOGL extends Applet implements Runnable{
	private Animator animator;
	private GLU glu = new GLU();

	TetrisGL canvas;

    public AppletJOGL()
    {
        glu = new GLU();
    }

	public TetrisGL makeCanvas()
	{
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setHardwareAccelerated(true);
        capabilities.setNumSamples(2); // 2x antialiasing
        capabilities.setSampleBuffers(true);

	    return new TetrisGL(capabilities);
	}

	public void init() {
//		cookieThread = new CookieThread();
		new Thread(this).start();
		setLayout(new BorderLayout());
		canvas = makeCanvas();

		add(canvas, BorderLayout.CENTER);
//		animator = new FPSAnimator(canvas, 60);
		animator = new Animator(canvas);

	}

	public void start() {
		animator.start();
	}

	public void stop() {
		canvas.destroyAll();
		animator.stop();
	}

    public void run()
	{
		while(true)
		{
		try{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
    	{e.printStackTrace();}
		}

	}

} // class