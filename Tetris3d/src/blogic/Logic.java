package blogic;

/**
 * class contorls some logic aspect of the game
 *
 * @author Pawel
 */
public class Logic {

    /**
     * size of the world in X dimension
     */
    private static byte worldSizeX = 6;

    /**
     * size of the world in Y dimension
     */
    private static byte worldSizeY = 12;
    
    /**
     * size of the world in Z dimension
     */
    private static byte worldSizeZ = 6;

    /**
     * number of game ending level
     */
    private static byte endLevel = 9;

    /**
     * removes all full levels
     * @param world
     */
    public static void removeLevels(short[][][] world) {
        for (byte i = (byte) (worldSizeY - 2); i >= 0; i--) {
            boolean remove = true;
            for (byte j = 0; j < worldSizeX; j++) {
                for (byte k = 0; k < worldSizeZ; k++) {
                    if (world[j][i][k] == 0) {
                        remove = false;
                    }
                }
            }
            if (remove) {
                removeLevel(i, world);
            }
        }
    }

    /**
     * remove one given level, and moves all upper levels down
     * @param i number of level
     * @param world
     */
    private static void removeLevel(byte i, short[][][] world) {
        for (byte j = 0; j < worldSizeX; j++) {
            for (byte k = 0; k < worldSizeZ; k++) {
                world[j][i][k] = 0;
            }
        }
        for (byte l = i; l <= worldSizeY - 2; l++) {
           for (byte j = 0; j < worldSizeX; j++) {
                for (byte k = 0; k < worldSizeZ; k++) {
                    world[j][l][k] = world[j][l + 1][k];
                }
            }
        }
    }

    /**
     * return if game is over
     */
    public static boolean gameIsOver(short[][][] world) {
        for (byte i = 0; i < worldSizeX; i++) {
            for (byte j = 0; j < worldSizeZ; j++) {
                if (world[i][endLevel][j] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * clean and return the world
     * @param world
     */
    public static void cleanWorld(short[][][] world) {
         for (byte i = 0; i < worldSizeX; i++) {
            for (byte j = 0; j < worldSizeY; j++) {
                for (byte k = 0; k < worldSizeZ; k++) {
                    world[i][j][k] = 0;
                }
            }
        }
    }
}