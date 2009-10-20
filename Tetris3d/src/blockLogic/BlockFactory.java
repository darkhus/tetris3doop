/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blockLogic;

import java.util.Random;

/**
 *
 * @author Pafciu
 */
abstract public class BlockFactory {

    private static short[][][][] kindsOfBlocks = {{
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 3, 0}, {0, 3, 0}, {0, 3, 0}},
        {{0, 3, 0}, {0, 0, 0}, {0, 0, 0}}
        }, {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{2, 2, 0}, {2, 2, 0}, {0, 0, 0}},
        {{2, 2, 0}, {2, 2, 0}, {0, 0, 0}}
        }, {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 4, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
        }, {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{1, 1, 1}, {0, 1, 0}, {0, 0, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
        }, {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
        {{0, 4, 0}, {0, 4, 0}, {0, 4, 0}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
        }};

    private static byte startX = 4;
    private static byte startY = 11;
    private static byte startZ = 4;


    public static Block create(short[][][] world) {
        Random rand = new Random();
        short[][][] pattern = kindsOfBlocks[rand.nextInt(kindsOfBlocks.length)];
        return new Block((byte)(rand.nextInt(4) + 2), startY, (byte)(rand.nextInt(4) + 2), pattern, world);
    }

}
