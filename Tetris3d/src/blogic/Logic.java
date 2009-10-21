/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blogic;

/**
 *
 * @author Pafciu
 */
public class Logic {

    private static byte worldSizeX = 6;
    private static byte worldSizeY = 12;
    private static byte worldSizeZ = 6;

    private static byte endLevel = 9;

    public static void removeLevels(short[][][] world) {
        for (byte i = (byte) (worldSizeY - 2); i >= 0; i--) {
            boolean remove = true;
            for (byte j = 0; j < worldSizeX; j++) {
                for (byte k = 0; k < worldSizeZ; k++) {
                    System.out.println("remove " + i + " " + j + " " + k + " " + world[j][i][k]);
                    if (world[j][i][k] == 0) {
                        remove = false;
                    }
                }
            }
            if (remove) {
                System.out.println("usuwa level " + i);
                removeLevel(i, world);
            }
        }
    }

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