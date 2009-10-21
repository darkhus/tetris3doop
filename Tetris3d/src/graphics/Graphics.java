package graphics;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

/**
 * This is the class for handling graphics.
 * @author Monika
 */
public class Graphics {

    /**
     * A field with color look up table; each element contains R G B values
     * for color stored under given index
     */
    float[][] colorLookUpTable = new float[][]{{0.3f, 0.5f, 0f}, {0.6f, 0.3f, 0f}, {0f, 0.4f, 0.7f}, {0.3f, 0f, 0.5f}};
    /**
     * Represents falling block's shape and color
     */
    public short[][][] patternBox;/* = new short[][][]{
    {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
    {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
    {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };*/

    /**
     * Represents falling block's coordinates relative to the world;
     * the coordinate numbering starts from 1
     */
    public byte[] coords;// = new byte[]{0, 10, 0};
    /**
     * Represents the world and the blocks laid already in it, togethter
     * with their colors
     */
    short[][][] world = new short[][][]{
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}},
        {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}
    };
    /**
     * Width of the game area
     */
    private final int netSizeX = 4;
    /**
     * Depth of the game area
     */
    private final int netSizeY = 8;

    /**
     * Gets the coordinates of falling block relative to the world
     * (numbering starts from 1)
     * @return the coordinates of falling block
     */
    public synchronized byte[] getCoords() {
        return coords;
    }

    /**
     * Sets the coordinates of falling block relative to the world
     * (numbering starts from 1)
     * @param coords the coordinates of falling block
     */
    public synchronized void setCoords(byte[] coords) {
        this.coords = coords;
    }

    /**
     * Gets falling block's shape and color array
     * @return falling block's shape and color array
     */
    public synchronized short[][][] getPatternBox() {
        return patternBox;
    }

    /**
     * Sets falling block's shape and color array
     * @param patternBox falling block's shape and color array
     */
    public synchronized void setPatternBox(short[][][] patternBox) {
        this.patternBox = patternBox;
    }

    /**
     * Gets the world array
     * @return world array
     */
    public synchronized short[][][] getWorld() {
        return world;
    }

    /**
     * Sets the world array
     * @param world world array
     */
    public void setWorld(short[][][] world) {
        this.world = world;
    }

    /**
     * Initialize openGL machine settings
     * @param gl openGL machine
     */
    public void init(GL gl) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        //GL gl = drawable.getGL();

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
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

    /**
     * Changes the openGL matrix according to new window's dimensions
     * @param gl openGL machine
     * @param glu class calling functions from GLU library
     * @param width new window's width
     * @param height new window's height
     */
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

    /**
     * Displays the scene based on the state of this class' fields
     * @param gl openGL machine
     */
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

    //public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    //}
    /**
     * Builds the falling block
     * @param gl openGL machine
     * @param patternBox the shape and color array of the block
     * @param coords coordinates of the block in the world (numbering from 1)
     */
    private void buildTheFallingBlock(GL gl, short[][][] patternBox, byte[] coords) {
        if (patternBox != null && coords != null) {
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
    }

    /**
     * Builds the world's laid blocks
     * @param gl openGL machine
     * @param world the world array
     */
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
    /**
     * Draws single cube
     * @param gl openGL machine
     * @param netWidth - width of the world the cube is in
     * @param netDepth - depth of the world the cube is in
     * @param numberOfCellsW - number of cells across the world the cube is in
     * @param numberOfCellsD - number of cells along the world the cube is in
     * @param coordX - x coordinate of the cube in the world (numbering from 1)
     * @param coordY - y coordinate of the cube in the world (numbering from 1)
     * @param coordZ - z coordinate of the cube in the world (numbering from 1)
     * @param color - number of cube's color
     */
    private void drawCube(GL gl, float netWidth, float netDepth, int numberOfCellsW, int numberOfCellsD, int coordX, int coordY, int coordZ, short color) {
        //System.err.println("putting cube at: (" + coordX + "," + coordY + "," + zCoord + ")");
        float startingPointX = -(netWidth / 2);
        float startingPointY = -(netDepth / 2);
        float cellSizeX = netWidth / numberOfCellsW;
        float cellSizeY = netDepth / numberOfCellsD;
        float cellSizeZ = cellSizeX;
        gl.glBegin(GL.GL_QUADS);
        float[] colors = colorLookUpTable[color - 1];
        gl.glColor3d(colors[0], colors[1], colors[2]);
        int x = coordX;
        int y = coordY;

        //FRONT WALL
        gl.glNormal3f(0f, 0f, 1f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 1));
        //BACK WALL
        gl.glNormal3f(0f, 0f, -1f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ));

        //LEFT WALL:
        gl.glNormal3f(-1f, 0f, 0f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 0));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ + 0));
        //RIGHT WALL:
        gl.glNormal3f(1f, 0f, 0f);
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 0) * cellSizeY, cellSizeZ * (coordZ + 1));

        //TOP WALL:
        gl.glNormal3f(0f, 1f, 0f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 0));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y + 1) * cellSizeY, cellSizeZ * (coordZ + 0));
        //BOTTOM WALL:
        gl.glNormal3f(0f, -1f, 0f);
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (coordZ + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (coordZ + 0));
        gl.glVertex3f(startingPointX + (x + 1) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (coordZ + 1));
        gl.glVertex3f(startingPointX + (x + 0) * cellSizeX, startingPointY + (y) * cellSizeY, cellSizeZ * (coordZ + 1));

        gl.glEnd();
    }

    /**
     * Builds the world - a cuboid consisting of four rectangular and two quadratic walls
     * @param gl openGL machine
     * @param netWidth the size of quadratic wall
     * @param netDepth the size of longer dimension of rectangular wall
     * @param numberOfCellsW number of cells across the cuboid
     * @param numberOfCellsD number of cells along the cuboid
     */
    private void buildWorld(GL gl, float netWidth, float netDepth, int numberOfCellsW, int numberOfCellsD) {
        //BACK WALL
        buildWall(gl, netWidth, netWidth, numberOfCellsW, numberOfCellsW, 0.3f, -netDepth / 2);

        //RIGHT WALL
        gl.glPushMatrix();
        gl.glRotatef(270, 0f, 1f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netDepth / 4);
        buildWall(gl, netDepth, netWidth, numberOfCellsD, numberOfCellsW, 0.5f, 0.0f);
        gl.glPopMatrix();

        //LEFT WALL
        gl.glPushMatrix();
        gl.glRotatef(-270, 0f, 1f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netDepth / 4);
        buildWall(gl, netDepth, netWidth, numberOfCellsD, numberOfCellsW, 0.5f, 0.0f);
        gl.glPopMatrix();

        //TOP WALL
        gl.glPushMatrix();
        gl.glRotatef(90, 1f, 0f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netDepth / 4);
        buildWall(gl, netWidth, netDepth, numberOfCellsW, numberOfCellsD, 0.5f, 0.0f);
        gl.glPopMatrix();

        //BOTTOM WALL
        gl.glPushMatrix();
        gl.glRotatef(270, 1f, 0f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netDepth / 4);
        buildWall(gl, netWidth, netDepth, numberOfCellsW, numberOfCellsD, 0.5f, 0.0f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180, 1f, 0f, 0f);
        gl.glTranslatef(0.0f, 0.0f, -netDepth / 2);
        buildWall(gl, netWidth, netWidth, numberOfCellsW, numberOfCellsW, 0.8f, 0.0f);
        gl.glPopMatrix();

    }

    /**
     * Builds a wall so that the axis z goes through the middle of it
     * @param gl openGL machine
     * @param netSizeX width of the wall
     * @param netSizeY height of the wall
     * @param numberOfCellsX number of cells (squares) horizontally
     * @param numberOfCellsY number of cells (squares) vertically
     * @param grayColor the color weight, between 0 and 1
     * @param zCoord z coordinate at which the wall should be put
     */
    private void buildWall(GL gl, float netSizeX, float netSizeY, int numberOfCellsX, int numberOfCellsY, float grayColor, float zCoord) {
        //buildWallLines(gl, netWidth, netDepth, numberOfCellsW, numberOfCellsD, grayColor, zCoord);
        buildWallSquares(gl, netSizeX, netSizeY, numberOfCellsX, numberOfCellsY, grayColor, zCoord);

    }

    /**
     * Builds a wall consisting of squares so that the axis z goes through the middle of it
     * @param gl openGL machine
     * @param netSizeX width of the wall
     * @param netSizeY height of the wall
     * @param numberOfCellsX number of cells (squares) horizontally
     * @param numberOfCellsY number of cells (squares) vertically
     * @param grayColor the color weight, between 0 and 1
     * @param zCoord z coordinate at which the wall should be put
     */
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
}

