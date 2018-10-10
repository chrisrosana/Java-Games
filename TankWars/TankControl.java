//package TankWars;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

public class TankControl extends Observable implements KeyListener {

    private Tank tank;
    private final int up;
    private final int down;
    private final int right;
    private final int left;

    private int fire;

    // CONSTRUCTOR
    public TankControl(Tank tank, int up, int down, int left, int right, int fire) {

        this.tank = tank;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.fire = fire;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyPressed = e.getKeyCode();
        if (keyPressed == up) {
            this.tank.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.tank.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.tank.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.tank.toggleRightPressed();
        }
        if (keyPressed == fire) {
            this.tank.toggleFirePressed();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int keyPressed = e.getKeyCode();
        if (keyPressed == up) {
            this.tank.unToggleUpPressed();
        }
        if (keyPressed == down) {
            this.tank.unToggleDownPressed();
        }
        if (keyPressed == left) {
            this.tank.unToggleLeftPressed();
        }
        if (keyPressed == right) {
            this.tank.unToggleRightPressed();
        }
        if (keyPressed == fire) {
            this.tank.unToggleFirePressed();
        }

    }
}
