package blogic;

import java.awt.event.KeyEvent;

/**
 * that class contains code which handles key
 *
 * @author Pawel
 */
public class BlockMove {

    /**
     * block which move is controlled
     */
    private Block block;

    /**
     * methods control block's movement
     */
    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_LEFT] == true) {
            block.goLeft();
            keys[KeyEvent.VK_LEFT] = false;
        }
        if (keys[KeyEvent.VK_RIGHT] == true) {
            block.goRight();
            keys[KeyEvent.VK_RIGHT] = false;
        }

        if (keys[KeyEvent.VK_UP] == true) {
            block.goForward();
            keys[KeyEvent.VK_UP] = false;
        }
        if (keys[KeyEvent.VK_DOWN] == true) {
            block.goDown();
            keys[KeyEvent.VK_DOWN] = false;
        }
        if (keys[KeyEvent.VK_SPACE] == true) {
            if (!block.go()) {
                block.setMoving(false);
            }
            keys[KeyEvent.VK_SPACE] = false;
        }
        if (keys[KeyEvent.VK_X] == true) {
            block.turnLeftHor();
            keys[KeyEvent.VK_X] = false;
        }
        if (keys[KeyEvent.VK_C] == true) {
            block.turnRightHor();
            keys[KeyEvent.VK_C] = false;
        }
        if (keys[KeyEvent.VK_F] == true) {
            block.turnLeftVer();
            keys[KeyEvent.VK_F] = false;
        }
        if (keys[KeyEvent.VK_V] == true) {
            block.turnRightVer();
            keys[KeyEvent.VK_V] = false;
        }
    }

    /**
     * sets block
     * @param block
     */
    public void setBlock(Block block) {
        this.block = block;
    }
}
