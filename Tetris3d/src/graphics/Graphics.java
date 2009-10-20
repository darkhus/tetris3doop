package graphics;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;


public class Graphics {

    private final int netSizeX = 4;
    private final int netSizeY = 8;
    
    float[][] colorLookUpTable = new float[][]{{0.3f, 0.5f, 0f}, {0.6f, 0.3f, 0f}, {0f, 0.4f, 0.7f}, {0.3f, 0f, 0.5f}};
    public short[][][] patternBox = new short[][][]{
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    public byte[] coords = new byte[]{0, 10, 0};
    short[][][] world = new short[][][]{
        {{1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{1, 0, 0, 0, 0, 0}, {4, 4, 4, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{1, 0, 3, 3, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{1, 0, 3, 3, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{2, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{2, 2, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}
    };

    public synchronized  byte[] getCoords() {
        return coords;
    }

    public synchronized  void setCoords(byte[] coords) {
        this.coords = coords;
    }

    public synchronized  short[][][] getPatternBox() {
        return patternBox;
    }

    public synchronized  void setPatternBox(short[][][] patternBox) {
        this.patternBox = patternBox;
    }

    public synchronized  short[][][] getWorld() {
        return world;
    }

    public void setWorld(short[][][] world) {
        this.world = world;
    }

    public void init(GL gl) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        //GL gl = drawable.getGL();


        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glFrontFace(GL.GL_CCW);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glCullFace(gl.GL_BACK);
        gl.glEnable(gl.GL_CULL_FACE);

        //set the light:
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_COLOR_MATERIAL);

        float[] LightAmbient = {0.9f, 0.9f, 0.9f, 1.0f};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, LightAmbient, 0);
        float[] LightDiffuse = {0.9f, 0.9f, 0.9f, 1.0f};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, LightDiffuse, 0);
        float[] LightPosition = {2.0f, 2.0f, 2.0f, 1.0f};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, LightPosition, 0);

        gl.glEnable(GL.GL_LIGHT0);

    }

    public void reshape(GL gl, GLU glu, int width, int height) {

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 500.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public synchronized void display(GL gl) {


        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);

        buildWorld(gl, netSizeX, netSizeY, world.length, world[0].length);
        // Flush all drawing operations to the graphics card
        gl.glFlush();

        //gl.glDisable(gl.GL_CULL_FACE);
        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, netSizeX / 2, -netSizeY / 2);

        buildLaidBlocks(gl, world);

        buildTheFallingBlock(gl, patternBox, coords);

        gl.glPopMatrix();
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private void buildTheFallingBlock(GL gl, short[][][] patternBox, byte[] coords) {
        int xSize = patternBox.length;
        //System.out.println("corX=" + coords[0] + ",corY=" + coords[2] + ",corZ=" + coords[1]);
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < xSize; y++) {
                for (int z = 0; z < xSize; z++) {
                    if (patternBox[x][y][z] != 0) {
                        //System.out.println("x=" + x + ",y=" + z + ",z=" + y + "|xx=" + (x + coords[0] - xSize) + ",yy=" + (z + coords[2] - xSize) + ",zz=" + (y + coords[1] - xSize));
                        drawCube(gl, netSizeX, netSizeY, world.length, world[0].length, x + coords[0] - xSize / 2 - 1, z + coords[2] - xSize / 2 - 1, y + coords[1] - xSize / 2 - 1, patternBox[x][y][z]);
                    }
                }
            }
        }
    }

    private void buildLaidBlocks(GL gl, short[][][] world) {

        int xSize = world.length;
        int zSize = xSize;
        int ySize = world[0].length;
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    if (world[x][y][z] != 0) {
                        drawCube(gl, netSizeX, netSizeY, world.length, world[0].length, x, z, y, world[x][y][z]);
                    }
                }
            }
        }

    }

    /* coordinates from 1 not 0 */
    private void drawCube(GL gl, float netSizeX, float netSizeY, int numberOfCellsX, int numberOfCellsY, int coordX, int coordY, int zCoord, short color) {
        //System.err.println("putting cube at: (" + coordX + "," + coordY + "," + zCoord + ")");
        float startingPointX = -(netSizeX / 2);
        float startingPointY = -(netSizeY / 2);
        float cellSizeX = netSizeX / numberOfCellsX;
        float cellSizeY = netSizeY / numberOfCellsY;
        float cellSizeZ = cellSizeX;
        gl.glBegin(GL.GL_QUADS);
        float[] colors = colorLookUpTable[color - 1];
        gl.glColor3d(colors[0], colors[1], colors[2]);
        int x = coordX;
        int y = coordY;

        //FRONT WALL
        gl.glNormal3f(0f, 0f, 1f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 1));
        //BACK WALL
        gl.glNormal3f(0f, 0f, -1f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord));

        //LEFT WALL:
        gl.glNormal3f(-1f, 0f, 0f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 0));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord + 0));
        //RIGHT WALL:
        gl.glNormal3f(1f, 0f, 0f);
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (zCoord + 1));

        //TOP WALL:
        gl.glNormal3f(0f, 1f, 0f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 0));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (zCoord + 0));
        //BOTTOM WALL:
        gl.glNormal3f(0f, -1f, 0f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (zCoord + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (zCoord + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (zCoord + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (zCoord + 1));

        gl.glEnd();
    }

    private void buildWorld(GL gl, float netSizeX, float netSizeY, int numberOfCellsX, int numberOfCellsY) {
        //BACK WALL
        buildWall(gl, netSizeX, netSizeX, numberOfCellsX, numberOfCellsX, 0.3f, -netSizeY / 2);

        //RIGHT WALL
        gl.glPushMatrix();
        gl.glRotatef(270, 0f, 1f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netSizeY / 4);
        buildWall(gl, netSizeY, netSizeX, numberOfCellsY, numberOfCellsX, 0.5f, 0.0f);
        gl.glPopMatrix();

        //LEFT WALL
        gl.glPushMatrix();
        gl.glRotatef(-270, 0f, 1f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netSizeY / 4);
        buildWall(gl, netSizeY, netSizeX, numberOfCellsY, numberOfCellsX, 0.5f, 0.0f);
        gl.glPopMatrix();

        //TOP WALL
        gl.glPushMatrix();
        gl.glRotatef(90, 1f, 0f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netSizeY / 4);
        buildWall(gl, netSizeX, netSizeY, numberOfCellsX, numberOfCellsY, 0.5f, 0.0f);
        gl.glPopMatrix();

        //BOTTOM WALL
        gl.glPushMatrix();
        gl.glRotatef(270, 1f, 0f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netSizeY / 4);
        buildWall(gl, netSizeX, netSizeY, numberOfCellsX, numberOfCellsY, 0.5f, 0.0f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180, 1f, 0f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netSizeY / 2);
        buildWall(gl, netSizeX, netSizeX, numberOfCellsX, numberOfCellsX, 0.8f, 0.0f);
        gl.glPopMatrix();

    }

    private void buildWall(GL gl, float netSizeX, float netSizeY, int numberOfCellsX, int numberOfCellsY, float grayColor, float zCoord) {
        //buildWallLines(gl, netSizeX, netSizeY, numberOfCellsX, numberOfCellsY, grayColor, zCoord);
        buildWallSquares(gl, netSizeX, netSizeY, numberOfCellsX, numberOfCellsY, grayColor, zCoord);

    }

    private void buildWallSquares(GL gl, float netSizeX, float netSizeY, int numberOfCellsX, int numberOfCellsY, float grayColor, float zCoord) {
        float startingPointX = -(netSizeX / 2);
        float startingPointY = -(netSizeY / 2);
        gl.glBegin(GL.GL_QUADS);
        float cellSizeX = netSizeX / numberOfCellsX;
        float cellSizeY = netSizeY / numberOfCellsY;
        gl.glColor3f(grayColor, grayColor, grayColor);
        //draw the long lines: up and left
        gl.glVertex3f(-startingPointX, startingPointY, zCoord);
        gl.glVertex3f(-startingPointX, -startingPointY, zCoord);
        gl.glVertex3f(startingPointX, -startingPointY, zCoord);
        gl.glVertex3f(startingPointX, startingPointY, zCoord);

        //in loop draw down and right short lines of each cell
        for (int x = 0; x < numberOfCellsX; x++) {
            for (int y = 0; y < numberOfCellsY; y++) {
                gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, zCoord);
                gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, zCoord);
                gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, zCoord);
                gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, zCoord);
            }
        }
        gl.glEnd();
    }

    private void buildWallLines(GL gl, float netSizeX, float netSizeY, int numberOfCellsX, int numberOfCellsY, float grayColor, float zCoord) {
        float startingPointX = -(netSizeX / 2);
        float startingPointY = -(netSizeY / 2);
        float cellSizeX = netSizeX / numberOfCellsX;
        float cellSizeY = netSizeY / numberOfCellsY;
        gl.glBegin(GL.GL_LINE_STRIP);
        //gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(grayColor, grayColor, grayColor);
        //draw the long lines: up and left
        gl.glVertex3f(-startingPointX, startingPointY, zCoord);
        gl.glVertex3f(-startingPointX, -startingPointY, zCoord);
        gl.glVertex3f(startingPointX, -startingPointY, zCoord);

        //in loop draw down and right short lines of each cell
        for (int x = 0; x < numberOfCellsX; x++) {
            for (int y = 0; y < numberOfCellsY; y++) {
                gl.glVertex3f(startingPointX + x * cellSizeX, startingPointY + y * cellSizeY, zCoord);
                gl.glVertex3f(startingPointX + x * cellSizeX + cellSizeX, startingPointY + y * cellSizeY, zCoord);
                gl.glVertex3f(startingPointX + x * cellSizeX + cellSizeX, startingPointY + y * cellSizeY + cellSizeY, zCoord);
            }
        }
        gl.glEnd();
    }
}

