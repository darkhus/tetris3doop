package blogic;


/**
 * that class represent currently moving block
 *
 * @author Pawel
 */
public class Block {

    /**
     * size of the block
     */
    private byte size = 3;

    /**
     * coordinate X of location
     */
    private byte coordinateX;

    /**
     * coordinate Y of location
     */
    private byte coordinateY;

    /**
     * coordinate Z of location
     */
    private byte coordinateZ;
    
     /**
     * array which indicates shape, and color of block
     */
    private short[][][] patternBox;

    /**
     * world array
     */
    private short[][][] world;

    /**
     * if block still is moving
     */
    private boolean moving = true;

    /**
     * return if block is still moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * set if block is still moving, or already stop
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * block consturctor
     *
     * @param wX x coordinate of block location
     * @param wY y coordinate of block location
     * @param wZ z coordinate of block location
     * @param patternBox array which indicates shape of block
     * @param world array which describes world
     */
    public Block(byte wX, byte wY, byte wZ, short[][][] patternBox, short[][][] world) {
        coordinateX = wX;
        coordinateY = wY;
        coordinateZ = wZ;
        this.patternBox = patternBox;
        this.world = world;
    }

    /**
     * moves block down
     */
    public boolean go() {
       if (canMove(coordinateX, (byte)(coordinateY - 1), coordinateZ, this.patternBox)) {
            move(coordinateX, (byte)(coordinateY - 1), coordinateZ);
            return true;
        }
        return false;
    }

    /**
     * moves block forward
     */
    public boolean goForward() {
        if (canMove(coordinateX, coordinateY, (byte)(coordinateZ + 1), this.patternBox)) {
            move(coordinateX, coordinateY, (byte)(coordinateZ + 1));
            return true;
        }
        return false;
    }

    /**
     * moves block backward
     */
    public boolean goBackward() {
        if (canMove(coordinateX, coordinateY, (byte)(coordinateZ - 1), this.patternBox)) {
            move(coordinateX, coordinateY, (byte)(coordinateZ - 1));
            return true;
        }
        return false;
    }

    /**
     * moves block left
     */
    public boolean goLeft() {
        if (canMove((byte)(coordinateX - 1), coordinateY, coordinateZ, this.patternBox)) {
            move((byte)(coordinateX - 1), coordinateY, coordinateZ);
            return true;
        }
        return false;
    }

    /**
     * moves block right
     */
    public boolean goRight() {
        if (canMove((byte)(coordinateX + 1), coordinateY, coordinateZ, this.patternBox)) {
            move((byte)(coordinateX + 1), coordinateY, coordinateZ);
            return true;
        }
        return false;
    }

    /**
     * moves block down
     */
    public boolean goDown() {
        if (canMove(coordinateX, coordinateY, (byte)(coordinateZ - 1), this.patternBox)) {
            move(coordinateX, coordinateY, (byte)(coordinateZ - 1));
            return true;
        }
        return false;
    }

    /**
     * returns location of the center point of the block
     *
     * @return array of coordinaties: x, y, z
     */
    public byte[] getLocation() {
        byte[] loc = new byte[3];
        loc[0] = (byte)(coordinateX);
        loc[1] = (byte)(coordinateY);
        loc[2] = (byte)(coordinateZ);
        return loc;
    }

    /**
     * return if block can be moved to given location
     *
     * @param x
     * @param y
     * @param z
     * @param patternBox
     * @return if block can be moved
     */
    private boolean canMove(byte x, byte y, byte z, short[][][] patternBox)
    {
        x = (byte)(x - 1);
        y = (byte)(y - 1);
        z = (byte)(z - 1);
        int it, jt, kt;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    it = i - 1;
                    jt = j - 1;
                    kt = k - 1;
                    if (patternBox[i][j][k] != 0 && (it + x > 5 || it + x < 0 || jt + y < 0 || kt + z > 5 || kt + z < 0)) {
                        return false;
                    }
                    if (patternBox[i][j][k] != 0 && world[it + x][jt + y][kt + z] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * returns patternBox
     *
     */
    public short[][][] getPatternBox() {
        return patternBox;
    }

    /**
     * turns block vertically
     */
    public void turnRightHor() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[0][0][k] = this.patternBox[2][0][k];
            newPatternBox[2][0][k] = this.patternBox[2][2][k];
            newPatternBox[2][2][k] = this.patternBox[0][2][k];
            newPatternBox[0][2][k] = this.patternBox[0][0][k];

            newPatternBox[1][1][k] = this.patternBox[1][1][k];

            newPatternBox[1][0][k] = this.patternBox[2][1][k];
            newPatternBox[2][1][k] = this.patternBox[1][2][k];
            newPatternBox[1][2][k] = this.patternBox[0][1][k];
            newPatternBox[0][1][k] = this.patternBox[1][0][k];
        }
        if (canMove(coordinateX, coordinateY, coordinateZ, newPatternBox)) {
            this.patternBox = newPatternBox;
        }
    }

    /**
     * turns block vertically
     */
    public void turnLeftHor() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[0][0][k] = this.patternBox[0][2][k];
            newPatternBox[0][2][k] = this.patternBox[2][2][k];
            newPatternBox[2][2][k] = this.patternBox[2][0][k];
            newPatternBox[2][0][k] = this.patternBox[0][0][k];

            newPatternBox[1][1][k] = this.patternBox[1][1][k];

            newPatternBox[1][0][k] = this.patternBox[0][1][k];
            newPatternBox[0][1][k] = this.patternBox[1][2][k];
            newPatternBox[1][2][k] = this.patternBox[2][1][k];
            newPatternBox[2][1][k] = this.patternBox[1][0][k];
        }
        if (canMove(coordinateX, coordinateY, coordinateZ, newPatternBox)) {
            this.patternBox = newPatternBox;
        }
    }

    /**
     * turns block vertically
     */
    public void turnLeftVer() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[k][0][0] = this.patternBox[k][0][2];
            newPatternBox[k][0][2] = this.patternBox[k][2][2];
            newPatternBox[k][2][2] = this.patternBox[k][2][0];
            newPatternBox[k][2][0] = this.patternBox[k][0][0];

            newPatternBox[k][1][1] = this.patternBox[k][1][1];

            newPatternBox[k][1][0] = this.patternBox[k][0][1];
            newPatternBox[k][0][1] = this.patternBox[k][1][2];
            newPatternBox[k][1][2] = this.patternBox[k][2][1];
            newPatternBox[k][2][1] = this.patternBox[k][1][0];
        }
        if (canMove(coordinateX, coordinateY, coordinateZ, newPatternBox)) {
            this.patternBox = newPatternBox;
        }
    }

    /**
     * turns block vertically
     */
    public void turnRightVer() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[k][0][0] = this.patternBox[k][2][0];
            newPatternBox[k][2][0] = this.patternBox[k][2][2];
            newPatternBox[k][2][2] = this.patternBox[k][0][2];
            newPatternBox[k][0][2] = this.patternBox[k][0][0];

            newPatternBox[k][1][1] = this.patternBox[k][1][1];

            newPatternBox[k][1][0] = this.patternBox[k][2][1];
            newPatternBox[k][2][1] = this.patternBox[k][1][2];
            newPatternBox[k][1][2] = this.patternBox[k][0][1];
            newPatternBox[k][0][1] = this.patternBox[k][1][0];
        }
        if (canMove(coordinateX, coordinateY, coordinateZ, newPatternBox)) {
            this.patternBox = newPatternBox;
        }
    }

    /**
     * moves a block to given location
     * 
     * @param coordinateX
     * @param coordinateY
     * @param coordinateZ
     */
    private void move(byte coordinateX, byte coordinateY, byte coordinateZ) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
    }

    /**
     * method adds block to the world
     */
    public void addToWorld() {
        byte x = (byte)(this.coordinateX - 1);
        byte y = (byte)(this.coordinateY - 1);
        byte z = (byte)(this.coordinateZ - 1);
        int it, jt, kt;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    it = i - 1;
                    jt = j - 1;
                    kt = k - 1;
                    if (patternBox[i][j][k] != 0 ) {
                        this.world[it + x][jt + y][kt + z] = patternBox[i][j][k];
                    }
                }
            }
        }
    }

}