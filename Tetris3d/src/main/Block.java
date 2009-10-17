package main;



public class Block {

    private byte size = 3;
    private byte coordinateX;
    private byte coordinateY;
    private byte coordinateZ;
    private short[][][] patternBox;
    private byte[] worldSize;
    private short[][][] world;

    public Block(byte wX, byte wY, byte wZ, short[][][] patternBox, byte[] worldSize, short[][][] world) {
        coordinateX = wX;
        coordinateY = wY;
        coordinateZ = wZ;
        this.patternBox = patternBox;
        this.world = world;
    }

    public boolean goForward() {
        if (canMove(coordinateX, coordinateY, (byte)(coordinateZ - 1))) {
            move(coordinateX, coordinateY, (byte)(coordinateZ - 1));
            return true;
        }
        return false;
    }

    public boolean goBackward() {
        if (canMove(coordinateX, coordinateY, (byte)(coordinateZ + 1))) {
            move(coordinateX, coordinateY, (byte)(coordinateZ + 1));
            return true;
        }
        return false;
    }

    public boolean goLeft() {
        if (canMove((byte)(coordinateX - 1), coordinateY, coordinateZ)) {
            move((byte)(coordinateX - 1), coordinateY, coordinateZ);
            return true;
        }
        return false;
    }

    public boolean goRight() {
        if (canMove((byte)(coordinateX + 1), coordinateY, coordinateZ)) {
            move((byte)(coordinateX + 1), coordinateY, coordinateZ);
            return true;
        }
        return false;
    }

    public boolean goDown() {
        if (canMove(coordinateX, coordinateY, (byte)(coordinateZ - 1))) {
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
        loc[0] = coordinateX;
        loc[1] = coordinateY;
        loc[2] = coordinateZ;
        return loc;
    }

    private boolean canMove(byte x, byte y, byte z)
    {
        int it, jt, kt;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    it = i - 1;
                    jt = j - 1;
                    kt = k - 1;
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
     * @return
     */
    public short[][][] getPatternBox() {
        return patternBox;
    }

    public void turnRightHor() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[0][0][k] = this.patternBox[2][0][k];
            newPatternBox[2][0][k] = this.patternBox[2][2][k];
            newPatternBox[2][2][k] = this.patternBox[0][2][k];
            newPatternBox[0][2][k] = this.patternBox[0][0][k];

            newPatternBox[1][0][k] = this.patternBox[2][1][k];
            newPatternBox[2][1][k] = this.patternBox[1][2][k];
            newPatternBox[1][2][k] = this.patternBox[0][1][k];
            newPatternBox[0][1][k] = this.patternBox[1][0][k];
        }
    }

    public void turnLeftHor() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[0][0][k] = this.patternBox[0][2][k];
            newPatternBox[0][2][k] = this.patternBox[2][2][k];
            newPatternBox[2][2][k] = this.patternBox[2][0][k];
            newPatternBox[2][0][k] = this.patternBox[0][0][k];

            newPatternBox[1][0][k] = this.patternBox[0][1][k];
            newPatternBox[0][1][k] = this.patternBox[1][2][k];
            newPatternBox[1][2][k] = this.patternBox[2][1][k];
            newPatternBox[2][1][k] = this.patternBox[1][0][k];
        }
    }

    public void turnLeftVer() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[k][0][0] = this.patternBox[k][0][2];
            newPatternBox[k][0][2] = this.patternBox[k][2][2];
            newPatternBox[k][2][2] = this.patternBox[k][2][0];
            newPatternBox[k][2][0] = this.patternBox[k][0][0];

            newPatternBox[k][1][0] = this.patternBox[k][0][1];
            newPatternBox[k][0][1] = this.patternBox[k][1][2];
            newPatternBox[k][1][2] = this.patternBox[k][2][1];
            newPatternBox[k][2][1] = this.patternBox[k][1][0];
        }
    }

    public void turnRightVer() {
        short[][][] newPatternBox = new short[size][size][size];
        for (int k = 0; k < size; k++) {
            newPatternBox[k][0][0] = this.patternBox[k][2][0];
            newPatternBox[k][2][0] = this.patternBox[k][2][0];
            newPatternBox[k][2][2] = this.patternBox[k][0][2];
            newPatternBox[k][0][2] = this.patternBox[k][0][0];

            newPatternBox[k][1][0] = this.patternBox[k][2][1];
            newPatternBox[k][2][1] = this.patternBox[k][1][2];
            newPatternBox[k][1][2] = this.patternBox[k][0][1];
            newPatternBox[k][0][1] = this.patternBox[k][1][0];
        }
    }

    private void move(byte coordinateX, byte coordinateY, byte coordinateZ) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.coordinateZ = coordinateZ;
    }

}