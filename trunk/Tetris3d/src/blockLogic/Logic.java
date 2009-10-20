/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blockLogic;

/**
 *
 * @author Pafciu
 */
public class Logic {

    private static byte worldSizeX = 6;
    private static byte worldSizeY = 12;
    private static byte worldSizeZ = 6;

    public static void removeLevels(short[][][] world) {
        for (byte i = (byte) (worldSizeY - 2); i >= 0; i--) {
            boolean ifRemove = true;
            for (byte j = 0; j < worldSizeX; j++) {
                for (byte k = 0; k < worldSizeZ; k++) {
                    System.out.println("remove " + i + " " + j + " " + k);
                    if (world[j][i][k] == 0) {
                        ifRemove = false;
                    }
                }
            }
            if (ifRemove) {
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
        for (byte l = (byte) (worldSizeY - 2); l >= i; l--) {
            for (byte j = 0; j < worldSizeX; j++) {
                for (byte k = 0; k < worldSizeZ; k++) {
                    world[j][l][k] = world[j][l + 1][k];
                }
            }
        }
    }
}