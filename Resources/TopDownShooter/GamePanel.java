//package TopDownShooter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // FIELDS
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g; // paint brush

    private int FPS = 30;
    private double averageFPS;

    public static Player player;

    // CONSTRUCTOR
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // getting keyboard from the user
        setFocusable(true); // make able to focus
        requestFocus(); // get the focus

    }


    // FUNCTIONS
    @Override
    // This means JPanel is done loading
    public void addNotify() {
        super.addNotify();

        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }

        //keyboard input
        addKeyListener(this);
    }

    @Override
    public void run() {

        running = true;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        //create player
        player = new Player();

        long startTime;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = 0;

        long targetTime = 1000 / FPS;

        // GAME LOOP
        while (running) {

            startTime = System.nanoTime();

            gameUpdate();
            gameRender();
            gameDraw();

            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - URDTimeMillis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == maxFrameCount) {
                averageFPS = 1000.0 / ( (totalTime / frameCount) / 1000000 );
                frameCount = 0;
                totalTime = 0;
            }

        }

    }

    // Updating the everything in the game
    // player/enemy position etc.
    // all the game logic
    private void gameUpdate() {
        player.update();
    }

    // draw off screen ... everything that is active to the screen
    private void gameRender() {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);

        player.draw(g);
    }

    // draw on screen
    private void gameDraw() {
        Graphics g2 = this.getGraphics(); // get graphics from game panel...
        g2.drawImage(image, 0, 0 , null); // g2 is the actual paint brush for the screen
        g2.dispose();
    }


    // KEYBOARD INPUT
    public void keyTyped(KeyEvent key){}

    public void keyPressed(KeyEvent key){
        int keyCode = key.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT){
            player.setLeft(true);
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        if(keyCode == KeyEvent.VK_UP){
            player.setUp(true);
        }
        if(keyCode == KeyEvent.VK_DOWN){
            player.setDown(true);
        }
    }

    public void keyReleased(KeyEvent key){
        int keyCode = key.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT){
            player.setLeft(false);
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
        if(keyCode == KeyEvent.VK_UP){
            player.setUp(false);
        }
        if(keyCode == KeyEvent.VK_DOWN){
            player.setDown(false);
        }
    }
}
