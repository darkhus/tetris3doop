package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
// import java.util.Vector;
import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class TetrisGL extends GLCanvas implements Runnable {

    private Camera camera;
    private static long MAX_STATS_INTERVAL = 1000000000L;		// zapis klatek co 1 sec
    private static final int NUM_DELAYS_PER_YIELD = 16;			// liczba iteracji p?tli gry (opó?nienie) zanim wykonaja si? inne w?tki (inne ni? g?ówny w?tek)
    private static int MAX_RENDER_SKIPS = 5;   					// maksymalna liczba pomini?? renderowania obrazu w p?tli gry
    private static int NUM_FPS = 10;							// ilo?? wyników obliczenie klatek na sekunde z których brana jest ?rednia
//	private final int DEFAULT_FPS = 80;
    private long statsInterval = 0L;
    private long prevStatsTime;
    private long totalElapsedTime = 0L;
    private long gameStartTime;
    private int timeSpentInGame = 0;
    private long frameCount = 0;
    private double fpsStore[];
    private long statsCount = 0;
    private double averageFPS = 0.0;
    private long rendersSkipped = 0L;
    private long totalRendersSkipped = 0L;
    private double upsStore[];
    private double averageUPS = 0.0;
    private DecimalFormat df = new DecimalFormat("0.##");
    private DecimalFormat timedf = new DecimalFormat("0.####");
    private long period;				// odst?py mi?dywy?wietlaniem (w nano sec)
    private Thread animator;
    private GLDrawable drawable;  		// "powierzchnia" rysowania
    private GLContext context;    		// kontekst rysowania
    private GL gl;
    private GLU glu;
    private boolean isResized = false;
    private int panelWidth, panelHeight;
    private KeyChecker keys;
    private Font font;
    private FontMetrics metrics;
    private BufferedImage waitIm;
    private boolean isRunning;
    private Graphic myGraphic;

    public TetrisGL(GLCapabilities caps) {
        //   	super(config);

        panelWidth = 800;
        panelHeight = 600;

        font = new Font("SansSerif", Font.BOLD, 24);
        metrics = this.getFontMetrics(font);
        /*try {
            waitIm = ImageIO.read(getClass().getResourceAsStream("images/start.jpg"));
        } catch (IOException e) {
            System.out.println("cannot read images/start.jpg");
        }*/

        drawable = GLDrawableFactory.getFactory().getGLDrawable(this, caps, null);
        context = drawable.createContext(null);

        camera = new Camera();

        keys = new KeyChecker(this);

        myGraphic = new Graphic();

        fpsStore = new double[NUM_FPS];
        upsStore = new double[NUM_FPS];
        for (int i = 0; i < NUM_FPS; i++) {
            fpsStore[i] = 0.0;
            upsStore[i] = 0.0;
        }
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

    public void paint(Graphics g) {
        if (isRunning) {
            String msg = "Welcome In Tetris";
            int x = (panelWidth - metrics.stringWidth(msg)) / 2;
            int y = (panelHeight - metrics.getHeight()) / 3;
            g.setColor(Color.white);
            g.setFont(font);

            int xIm = (panelWidth - waitIm.getWidth()) / 2;
            int yIm = 0;
            g.drawImage(waitIm, xIm, yIm, this);
            //		g.drawString(msg, x, y);
        }

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
        myGraphic.initOpenGl(gl);
        camera.setPosition(0, 0, 10);
        context.release();
    }

    private void resizeView() {
        myGraphic.resizeView(gl, glu, panelWidth, panelHeight);
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
            rendersSkipped += skips;
            context.release();
            storeStats();
        }
        printStats();
    }

    private void gameUpdate() {
        camera.Update(keys.keys);
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

        camera.LookAt();

        myGraphic.drawGL(gl);

        gl.glPushAttrib(GL.GL_COLOR_BUFFER_BIT | GL.GL_POLYGON_BIT | GL.GL_LIGHTING_BIT | GL.GL_ENABLE_BIT | GL.GL_CURRENT_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_TEXTURE_BIT);
        gl.glDisable(GL.GL_LIGHTING);
        //		menu.showMenu(gl, true, terMap, States.NUM_OF_MAP);
//		menu.displayHUD(gl, tower1.getV0(), tower2.getV0(), tower1.getLastV0(), tower2.getLastV0() );
        gl.glPopAttrib();
    }

    private void storeStats() {
        frameCount++;
        statsInterval += period;

        if (statsInterval >= MAX_STATS_INTERVAL) {
            long timeNow = System.nanoTime();
            timeSpentInGame = (int) ((timeNow - gameStartTime) / 1000000000L);  // ns -> secs

            long realElapsedTime = timeNow - prevStatsTime;
            totalElapsedTime += realElapsedTime;

            double timingError = ((double) (realElapsedTime - statsInterval) / statsInterval) * 100.0f;

            totalRendersSkipped += rendersSkipped;

            double actualFPS = 0;
            double actualUPS = 0;

            if (totalElapsedTime > 0) {
                actualFPS = (((double) frameCount / totalElapsedTime) * 1000000000L);
                actualUPS = (((double) (frameCount + totalRendersSkipped) / totalElapsedTime) * 1000000000L);
            }

            fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
            upsStore[(int) statsCount % NUM_FPS] = actualUPS;
            statsCount = statsCount + 1;

            double totalFPS = 0.0;
            double totalUPS = 0.0;
            for (int i = 0; i < NUM_FPS; i++) {
                totalFPS += fpsStore[i];
                totalUPS += upsStore[i];
            }

            if (statsCount < NUM_FPS) {
                averageFPS = totalFPS / statsCount;
                averageUPS = totalUPS / statsCount;
            } else {
                averageFPS = totalFPS / NUM_FPS;
                averageUPS = totalUPS / NUM_FPS;
            }
            rendersSkipped = 0;
            prevStatsTime = timeNow;
            statsInterval = 0L;
        }
    }

    private void printStats() {
        System.out.println("srednia FPS: " + df.format(averageFPS));
        System.out.println("srednia UPS: " + df.format(averageUPS));
    }
}
