package main;

import blogic.Block;
import blogic.BlockFactory;
import blogic.BlockMove;
import blogic.Logic;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
/**
 * This class is main class to create canvas for OpenGL,
 * also create game loop and aggregate other game classes,
 * here is place where game is going
 * @author Darek
 */
public class TetrisGL extends GLCanvas implements Runnable {
/**
 * Camera object
 */
	private Camera camera;
    /**
     * constatnt describes numbers of loop iteration (delay)
     * before animation thread yields to other threads (in game loop)
     */
	private final int NUM_DELAYS_PER_YIELD = 16;
    /**
     * max number of skips of render (in game loop)
     */
	private int MAX_RENDER_SKIPS = 5;
    /**
     * period of loop running (in nano sec)
     */
	private long period;
    /**
     * animation thread
     */
	private Thread animator;
    /**
     * interface for rendering surface
     */
  	private GLDrawable drawable;
    /**
     * rendering context
     */
  	private GLContext context;
    /**
     * JOGL's class to invoke OpenGL functions (from gl library)
     */
  	private GL gl;
    /**
     * JOGL's class to invoke OpenGL functions (from glu library)
     */
  	private GLU glu;
    /**
     * JOGL's class to invoke OpenGL functions (from glut library)
     */
    private GLUT glut;
    /**
     * changed when windows is resized
     */
  	private boolean isResized = false;
    /**
     * dimmensions of rendering panel
     */
  	private int panelWidth, panelHeight;
    /**
     * class whixh handle key events
     */
	private KeyChecker keys;
    /**
     * describes when loop is running
     */
    private boolean isRunning;
    /**
     * graphic class for this application
     */
    private graphics.Graphics myGraphic;
    /**
     * communicate which is shown
     */
    private String gameOverCommunicate = "Game Over";

    /**
     * block object
     */
    private Block block;
    /**
     * number of frames per sec which application should perform
     */
    private final int UPS = 80; // update per sec
    /**
     * gatger time passed
     */
    private int countTime = 0;
    /**
     * speed of block moving
     */
    private float blockSpeed = 2.0f;
    
    /**
     * object which handles block movement
     */
    private BlockMove blockMove;

    /**
     * world array
     */
    private short[][][] world = new short[][][]{
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}
         };
    /**
     * Class constructor, initialize and create rendering context
     * @param caps specifies a set of OpenGL capabilities that a rendering context must support
     */

    public TetrisGL(GLCapabilities caps) {
        //   	super(config);

        panelWidth = 800;
        panelHeight = 600;

        period = (long) 1000.0 / UPS;
        period *= 1000000L;    // ms -> nano

        drawable = GLDrawableFactory.getFactory().getGLDrawable(this, caps, null);
        context = drawable.createContext(null);

        camera = new Camera();

        keys = new KeyChecker(this);

        myGraphic = new graphics.Graphics();

        blockMove = new BlockMove();
    }

    /**
     * Overridden to track when this component is added to a container,
     * creates new animation thread
     */
    public void addNotify() {
        super.addNotify();
        drawable.setRealized(true);
        if (animator == null || !isRunning) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * invoke when game is stoped
     */
    public void stopGame() {
        isRunning = false;
    }

    /**
     * invoke when componend is resized
     * @param w width of panel
     * @param h height of panel
     */
    public void reshape(int w, int h) {
        isResized = true;
        if (h == 0) {
            h = 1;
        }
        panelWidth = w;
        panelHeight = h;
    }

    @Override
    /**
     * override paint method
     */
    public void paint(java.awt.Graphics g) {
    }

    /**
     * game thread run
     */
    public void run() {
        initGame();
        renderLoop();
        context.destroy();
        System.exit(0);
    }

    /**
     * makes contxt current
     */
    private void makeContentCurrent() {
        try {
            while (context.makeCurrent() == GLContext.CONTEXT_NOT_CURRENT) {
                System.out.println("nie mozna ustalic kontekstu");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialize game
     */
    private void initGame() {
        makeContentCurrent();
        gl = context.getGL();
        glu = new GLU();
        glut = new GLUT();
        resizeView();
        myGraphic.init(gl);
        myGraphic.setWorld(world);
        context.release();

       
    }

    /**
     * resize view (cange in OpenGL machinery)
     */
    private void resizeView() {
        myGraphic.reshape(gl, glu, panelWidth, panelHeight);
    }

    /**
     * game render and update loop
     */
    private void renderLoop() {
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int numDelays = 0;
        long excess = 0L;


        beforeTime = System.nanoTime();

        isRunning = true;

        while (isRunning) {
            makeContentCurrent();

            gameUpdate();		// update
            renderScene();     // rendering

            // swaps buffers double buffering)
            drawable.swapBuffers(); 

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) // to much time in loop?
            {
                try {
                    Thread.sleep(sleepTime / 1000000L);  // changing nano sec to ms
                } catch (InterruptedException ex) {
                }
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else // loop run slower than required fps (UPS)
            {
                excess -= sleepTime;
                overSleepTime = 0L;
                if (++numDelays >= NUM_DELAYS_PER_YIELD) {
                    Thread.yield();   // run other threads
                    numDelays = 0;
                }
            }

            beforeTime = System.nanoTime();
            // if rendering tooks to much time then update witch skipping render
            int skips = 0;
            while ((excess > period) && (skips < MAX_RENDER_SKIPS)) {
                excess -= period;
                gameUpdate();    // only update
                skips++;
            }
            context.release();
        }
    }

    /**
     * updates game in render loop
     */
    private void gameUpdate() {
        camera.Update(keys.keys);
        countTime++;
        if (block == null || block.isMoving() == false) {
            if (block != null) {
                block.addToWorld();
                Logic.removeLevels(world);
                if (Logic.gameIsOver(world)) {
                    JOptionPane.showMessageDialog(this, this.gameOverCommunicate);
                    Logic.cleanWorld(world);
                }
            }
            //create new block
            block = BlockFactory.create(world);
        }
        blockMove.setBlock(block);
        blockMove.update(keys.keys);
        myGraphic.setCoords(block.getLocation());
        myGraphic.setPatternBox(block.getPatternBox());
        if (countTime >= UPS * blockSpeed) {
            //System.out.println("wywo³anie co  sec");
            if (!block.go()) {
                block.setMoving(false);
            }
            countTime = 0;
        }
    }

    /**
     * invoke when application is closed
     */
    public void destroyAll() {
        context.destroy();
        System.out.println("destroy");
        System.exit(0);
    }

    /**
     * render scene in render loop
     */
    private void renderScene() {
        if (context.getCurrent() == null) {
            System.out.println("CURRENT CONTEXT null in RENDER SCENE");
            System.exit(0);
        }

        if (isResized) {
            resizeView();
            isResized = false;
        }

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        displayHUD();
        
        camera.LookAt(gl);

        myGraphic.display(gl);
    }

    /**
     * method to display HUD (head-up user display)
     */
    private void displayHUD(){
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(0.0f, panelWidth, panelHeight, 0.0f, -1.0f, 1.0f);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glDisable(GL.GL_DEPTH_TEST);

        gl.glRasterPos2i(100, panelHeight-50);
        gl.glColor3f(.7f, .2f, .1f);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "I LOVE TETRIS 3D ;-)");

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPopMatrix();

    }
}
