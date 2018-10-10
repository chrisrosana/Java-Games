//package TankWars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TankApplication extends JPanel {//implements Runnable {

    private Thread thread;

    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 1280;

    private BufferedImage miniImage = null;

    Graphics2D miniGame;

    Graphics2D g;

    private BufferedImage leftView;
    private BufferedImage rightView;

    private BufferedImage miniMap;


    private ImageIcon background = new ImageIcon("Resources/wholebackground.jpg");

     Tank player1;
     Tank player2;

    private final GameEventObservable geobv;

    private TankControl p1;
    private TankControl p2;

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

//    Bullet[] bullets = new Bullet[10000];

//    Bullet bullet;

    private JFrame frame;

    public static void main(String args[]) {

        final TankApplication game = new TankApplication();

        game.init();

        game.start();


    }

    public TankApplication( ){
        this.geobv = new GameEventObservable();
    }

    public void init() {

        frame = new JFrame("Tank Wars");

        player1 = new Tank(10, 10, 0, 0, (short) 0, "Resources/Tank1.jpg");
        player2 = new Tank(1200,1180, 0, 0, (short) 180, "Resources/Tank1.jpg");

        p1 = new TankControl(player2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        p2 = new TankControl(player1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        frame.add(this);

        frame.addKeyListener(p1);
        this.geobv.addObserver(player1);

//        bullet = new Bullet(50,50,270);

        frame.addKeyListener(p2);
        this.geobv.addObserver(player2);

        frame.setSize( new Dimension( 800, 800 ) );

        frame.setResizable(true);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo( null );

        frame.setFocusable(true);
        frame.setVisible(true);

    }


    private void drawBackground(Graphics g) {

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).drawBullet(g);
        }

        g.drawImage(background.getImage(), 0, 0, null);

    }

    private void drawDemo(Graphics g) {

        drawBackground(g);

        player1.drawPlayer(g);
        player2.drawPlayer(g);


    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if(miniImage == null) {

            miniImage = (BufferedImage) createImage(GAME_WIDTH, GAME_HEIGHT);
            miniGame = miniImage.createGraphics();

        }

        drawDemo(miniGame);


        //SPLIT SCREEN

        int leftX = (player1.getX() - 200 <= 0) ? 0 : (player1.getX() - 200);
            if (leftX + 400 > GAME_WIDTH) {
                leftX = GAME_WIDTH - 401;
            }

        int leftY = (player1.getY() - 400 <= 0) ? 0 : (player1.getY() - 400);
            if (leftY + 800 > GAME_HEIGHT) {
                 leftY = GAME_HEIGHT - 801;
            }

        leftView = miniImage.getSubimage(leftX,leftY, 400, 800);

        int rightX = (player2.getX() - 200 <= 0) ? 0 : (player2.getX() - 200);
             if (rightX + 400 > GAME_WIDTH) {
                 rightX = GAME_WIDTH - 401;
             }

        int rightY = (player2.getY() - 400 <= 0) ? 0 : (player2.getY() - 400);
             if (rightY + 800 > GAME_HEIGHT) {
                 rightY = GAME_HEIGHT - 801;
             }

        rightView = miniImage.getSubimage(rightX, rightY, 400, 800);

        miniMap = miniImage;

        g.drawImage(leftView, 0, 0, null);
        g.drawImage(rightView, 400, 0, null);

        g.setColor(Color.BLACK);
        g.fillRect(395, 0, 10, 800);

//        //KINDA DISPLAY HB
//        g.setColor(Color.GREEN);
//        g.fillRect(10, 700, 100, 30 ); //for player 1
//        g.fillRect(420, 700, 100, 30 ); //for player 2
//
//        //KINDA DISPLAY LIVES
//        //for player 1
//        g.setColor(Color.BLUE);
//        g.fillOval(130 ,705,25,25);
//        g.fillOval(160 ,705,25,25);
//        g.fillOval(190 ,705,25,25);
//
//        //for player 2
//        g.setColor(Color.RED);
//        g.fillOval(550 ,705,25,25);
//        g.fillOval(580 ,705,25,25);
//        g.fillOval(610 ,705,25,25);
        g.drawImage(miniMap, 300, 0, 200, 200, null);


    }

    public void start() {

        try {
            while (true) {
                this.repaint();
                geobv.setChanged(); // change state of the game
                geobv.notifyObservers(); // tell object themselves
//                this.repaint();
                Thread.sleep(1000/144); // sleep in 6 milliseconds
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TankApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    @Override
//    public void run() {
//        while (true) {
//            try {
//                geobv.setChanged();
//                geobv.notifyObservers();
////                this.repaint();
//                Thread.sleep(1000 / 144);
//            } catch (InterruptedException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }

//    @Override
//    public void run() {
//
//        while (true) {
//            try {
//                this.geobv.notifyObservers();
//                Thread.sleep(1000 / 144);
//            } catch (InterruptedException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//
//    }

}
