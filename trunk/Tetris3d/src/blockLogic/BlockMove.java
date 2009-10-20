/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blockLogic;

import java.awt.event.KeyEvent;

/**
 *
 * @author Pafciu
 */
public class BlockMove {

    private Block block;

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
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
