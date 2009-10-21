package blogic;

import java.util.Random;

/**
 * factory class, it creates new blocks
 *
 * @author Pawel
 */
abstract public class BlockFactory {

    /**
     * types of avaible blocks
     */
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

    /**
     * x start point for the block
     */
    private static byte startX = 4;
    
    /**
     * y start point for the block
     */
    private static byte startY = 11;

    /**
     * z start point for the block
     */
    private static byte startZ = 4;

    /**
     * randomly creates block
     *
     * @param world
     * @return new Block
     */
    public static Block create(short[][][] world) {
        Random rand = new Random();
        short[][][] pattern = kindsOfBlocks[rand.nextInt(kindsOfBlocks.length)];
        return new Block((byte)(rand.nextInt(4) + 2), startY, (byte)(rand.nextInt(4) + 2), pattern, world);
    }

}
