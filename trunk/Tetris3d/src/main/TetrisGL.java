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

public class TetrisGL extends GLCanvas implements Runnable {

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
    private GLUT glut;
  	private boolean isResized = false;
  	private int panelWidth, panelHeight;
	private KeyChecker keys;
    private boolean isRunning;
    private graphics.Graphics myGraphic;
    private Block block;
    private final int UPS = 80; // update per sec
    private int countTime = 0;
    private float blockSpeed = 2.0f; // liczba sekund na wywo³anie
    private BlockMove blockMove;
    private short[][][] world = new short[][][]{
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}
         };

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

    public void addNotify() {
        super.addNotify();
        drawable.setRealized(true);
        if (animator == null || !isRunning) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void resumeGame() {
    }

    public void pauseGame() {
    }

    public void stopGame() {
        isRunning = false;
    }

    public void reshape(int w, int h) {
        isResized = true;
        if (h == 0) {
            h = 1;
        }
        panelWidth = w;
        panelHeight = h;
    }

    public void paint(java.awt.Graphics g) {
    }

    public void run() {
        initGame();
        renderLoop();
        context.destroy();
        System.exit(0);
    }

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

    private void resizeView() {
        myGraphic.reshape(gl, glu, panelWidth, panelHeight);
    }

    private void renderLoop() {
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int numDelays = 0;
        long excess = 0L;

        gameStartTime = System.nanoTime();
        prevStatsTime = gameStartTime;
        beforeTime = gameStartTime;

        isRunning = true;

        while (isRunning) {
            makeContentCurrent();

            gameUpdate();		// aktualizacja
            renderScene();     //rendering

            // zmiana buforów
            drawable.swapBuffers();  // wy?wietlanie grafiki

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) //nadmiar czasu w p?tli
            {
                try {
                    Thread.sleep(sleepTime / 1000000L);  // nano -> ms
                } catch (InterruptedException ex) {
                }
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else // p?tal wykona?a si? wolniej ni? ?adana liczba klatek
            {
                excess -= sleepTime;
                overSleepTime = 0L;
                if (++numDelays >= NUM_DELAYS_PER_YIELD) {
                    Thread.yield();   // wykonanie innych w?tków
                    numDelays = 0;
                }
            }

            beforeTime = System.nanoTime();
            // je?li rendering zabra? za du?o czasu to aktualizuj
            // pomijaj?c rendering
            int skips = 0;
            while ((excess > period) && (skips < MAX_RENDER_SKIPS)) {
                excess -= period;
                gameUpdate();    // sama aktualizacja
                skips++;
            }
            context.release();
        }
    }

    private void gameUpdate() {
        camera.Update(keys.keys);
        countTime++;
        if (block == null || block.isMoving() == false) {
            if (block != null) {
                block.addToWorld();
                Logic.removeLevels(world);
                if (Logic.gameIsOver(world)) {
                    JOptionPane.showMessageDialog(this, "Game Over");
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
            System.out.println("wywo³anie co  sec");
            if (!block.go()) {
                block.setMoving(false);
            }
            //block.goRight();
            countTime = 0;
        }
    }

    public void destroyAll() {
        context.destroy();
        System.out.println("destroy");
        System.exit(0);
    }

    private void renderScene() {
        if (context.getCurrent() == null) {
            System.out.println("nie mozna ustalic kontekstu");
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
