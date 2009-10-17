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
	  	// pobieranie konfiguracji odpowiedniej dla p?ótna (canvas)
	    GLCapabilities caps = new GLCapabilities();

		System.out.println(caps.toString());
	    AWTGraphicsDevice dev = new AWTGraphicsDevice(null);
	    // wybiera konfiguracje graficzn? odpowiedni? dla danego systemu, opart? n ainformacji z GLCapabilities
	    AWTGraphicsConfiguration awtConfig = (AWTGraphicsConfiguration)
	       GLDrawableFactory.getFactory().chooseGraphicsConfiguration(caps, null, dev);

	    GraphicsConfiguration config = null;
	    if (awtConfig != null)
	      config = awtConfig.getGraphicsConfiguration();

	    return new TetrisGL(caps);
	}

	public void init() {
//		cookieThread = new CookieThread();
		new Thread(this).start();
//		States.applet = true;
		setLayout(new BorderLayout());
		canvas = makeCanvas();

		add(canvas, BorderLayout.CENTER);
//		animator = new FPSAnimator(canvas, 60);
		animator = new Animator(canvas);

//		writeCookie("Daras_5||||TEST_10|");

//		String cookie = getCookieByName(cookieName);
//		System.out.println("ciasteczka:  "+cookie);

//		loadSetup(cookie);

//		int i=0;
//		i=readCounter();
//		++i;
//		writeCounter(i);
//		writeCookie("aaa_5|ccc_3|||TEST_10|");
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
//		System.out.println(a++);
		try{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
    	{e.printStackTrace();}
		}

	}

} // class