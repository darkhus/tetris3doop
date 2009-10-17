package main;

import java.awt.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;


public class TetrisGL extends GLCanvas implements Runnable
{

	private Camera camera;

	private final int NUM_DELAYS_PER_YIELD = 16;
	private int MAX_RENDER_SKIPS = 5;
	private long prevStatsTime;
	private long gameStartTime;
	private long period;

	private Thread animator;

  	private GLDrawable drawable;
  	private GLContext context;
  	private GL gl;
  	private GLU glu;

  	private boolean isResized = false;
  	private int panelWidth, panelHeight;

	private KeyChecker keys;

    private boolean isRunning;
    private graphics.Graphics myGraphic;
    private final int UPS = 80; // update per sec
    private int countTime = 0;
    private float blockSpeed = 2.0f; // liczba sekund na wywo�anie


    public TetrisGL(GLCapabilities caps)
    {
 //   	super(config);

   		panelWidth = 800;
    	panelHeight = 600;

        period = (long) 1000.0/UPS;
    	period *= 1000000L;    // ms -> nano

    	drawable = GLDrawableFactory.getFactory().getGLDrawable(this, caps, null);
    	context = drawable.createContext(null);

		camera = new Camera();

		keys = new KeyChecker(this);

        myGraphic = new graphics.Graphics();

    }

	public void addNotify()
  	{
    	super.addNotify();
    	drawable.setRealized(true);
    	if (animator == null || !isRunning)
    	{
      		animator = new Thread(this);
	  		animator.start();
    	}
	}

	public void resumeGame()
	{
	}

	public void pauseGame()
	{
	}

	public void stopGame()
	{
		isRunning = false;
	}

	public void reshape(int w, int h)
	{
     	isResized = true;
     	if (h == 0) h = 1;
    	panelWidth = w; panelHeight = h;
	}

	public void paint(java.awt.Graphics g)
	{
	}

    public void run()
    {
        initGame();
        renderLoop();
        context.destroy();
        System.exit(0);
    }

	private void makeContentCurrent()
	{
    	try {
      		while (context.makeCurrent() == GLContext.CONTEXT_NOT_CURRENT)
      		{
      			System.out.println("nie mozna ustalic kontekstu");
        		Thread.sleep(100);
      		}
    		}
    	catch (InterruptedException e)
    	{e.printStackTrace();}
	}

	private void initGame()
	{
		makeContentCurrent();
        gl = context.getGL();
    	glu = new GLU();
        myGraphic.init(gl);
	    context.release();
	}


	private void resizeView()
	{
        myGraphic.reshape(gl, glu, panelWidth, panelHeight);
	}

	private void renderLoop()
	{
	    long beforeTime, afterTime, timeDiff, sleepTime;
	    long overSleepTime = 0L;
	    int numDelays = 0;
	    long excess = 0L;

	    gameStartTime = System.nanoTime();
	    prevStatsTime = gameStartTime;
	    beforeTime = gameStartTime;

	    isRunning = true;

		while(isRunning)
    	{
			makeContentCurrent();

			gameUpdate();		// aktualizacja
			renderScene();     //rendering

			// zmiana bufor�w
			drawable.swapBuffers();  // wy?wietlanie grafiki

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) //nadmiar czasu w p?tli
			{
				try
	        	{
	          		Thread.sleep(sleepTime/1000000L);  // nano -> ms
	        	}
	        	catch(InterruptedException ex){}
	        overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
	      	}
	      	else // p?tal wykona?a si? wolniej ni? ?adana liczba klatek
	      	{
	        	excess -= sleepTime;
	        	overSleepTime = 0L;
	        	if (++numDelays >= NUM_DELAYS_PER_YIELD)
	        		{
	          			Thread.yield();   // wykonanie innych w?tk�w
	          			numDelays = 0;
	        		}
	      	}

			beforeTime = System.nanoTime();
			// je?li rendering zabra? za du?o czasu to aktualizuj
			// pomijaj?c rendering
			int skips = 0;
			while( (excess > period) && (skips < MAX_RENDER_SKIPS) )
			{
	        	excess -= period;
		    	gameUpdate();    // sama aktualizacja
	        	skips++;
	      	}	      	
	      	context.release();
		}
	}

	private void gameUpdate()
	{
        camera.Update(keys.keys);
        countTime++;
        if(countTime >= UPS * blockSpeed)
        {
//            System.out.println("wywo�anie co  sec");
            countTime = 0;
        }
	}

    public void destroyAll()
	{
        context.destroy();
        System.out.println("destroy");
        System.exit(0);
	}

	private void renderScene()
	{
    	if (context.getCurrent() == null)
    	{
      		System.out.println("nie mozna ustalic kontekstu");
     		 System.exit(0);
    	}

	    if (isResized)
	    {
	      resizeView();
	      isResized = false;
	    }

	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glLoadIdentity();

	    camera.LookAt(gl);

        myGraphic.display(gl);

		gl.glPushAttrib(GL.GL_COLOR_BUFFER_BIT|GL.GL_POLYGON_BIT | GL.GL_LIGHTING_BIT | GL.GL_ENABLE_BIT|GL.GL_CURRENT_BIT|GL.GL_DEPTH_BUFFER_BIT|GL.GL_TEXTURE_BIT);
        gl.glDisable(GL.GL_LIGHTING);
        //		menu.showMenu(gl, true, terMap, States.NUM_OF_MAP);
//		menu.displayHUD(gl, tower1.getV0(), tower2.getV0(), tower1.getLastV0(), tower2.getLastV0() );
		gl.glPopAttrib();
	}

}
