//package TankWars;

import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Tank implements Observer {

    private int x; // position x
    private int y; // position y
    private final int r = 3; // how fast the tank move
    private int vx; // velocity x
    private int vy; // velocity y
    private int angle;

    private String imagePath;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean firePressed;

    private int health =200;
    private int lives = 4;

    private int DELTA = 10;

    private boolean end = false;

    ArrayList<Bullet> ammo = new ArrayList<Bullet>();

    public Tank(int x, int y, int vx, int vy, int angle, String imagePath) {

        super();

        setX(x);
        setY(y);
        setVx(vx);
        setVy(vy);
        setAngle(angle);

        setImg(imagePath);

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setImg(String imagePath) {
        this.imagePath = imagePath;
    }

//    @Override
    public int getX() {
        return x;
    }

//    @Override
    public int getY() {
        return y;
    }

    //    @Override
    public int getVx() {
        return vx;
    }

    //    @Override
    public int getVy() {
        return vy;
    }

    //    @Override
    public int getAngle() { return angle; }


    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void toggleFirePressed() { this.firePressed = true; }

    public void unToggleFirePressed() {
        this.firePressed = false;
    }

//    @Override
//    public void paintComponents(Graphics g) {
//
//        super.paintComponents(g);
//
//
//    }

    public void drawPlayer(Graphics g) {

        ImageIcon img = new ImageIcon( imagePath );

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y); // draw tank
        rotation.rotate(Math.toRadians(angle), img.getIconWidth() / 2, img.getIconHeight() / 2); // rotate
        Graphics2D graphic2D = (Graphics2D) g;

        graphic2D.drawImage(img.getImage(), rotation, null); // null coz no image observers

        //HEALTH BAR
        if (health > 35){
            g.setColor(Color.green);
            g.fillRect(x, y+60, health-150,10);
        } else if (health > 20 && health <= 25) {
            g.setColor(Color.yellow);
            g.fillRect(x, y+60, health-150,10);
        } else if (health > 0 && health <= 40){
            g.setColor(Color.red);
            g.fillRect(x, y+60, health-150,10);
        } else {
            lives -= 1;
            health = 200;
        }


        // LIVES
        int pixel = 25;
        g.setColor(Color.WHITE);

        for (int life = 0; life < lives-1; life ++) {
            g.fillOval(x+life*pixel ,y+80,8,8);
        }

        if (lives == 0) {
            this.end = true;
            g.setColor(Color.black);
            Font font = g.getFont().deriveFont( 70.0f );
            g.setFont(font);
            g.drawString("Game Over!", 450, 500);

        }


    }

    @Override
    public void update(Observable o, Object arg) {

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.firePressed) {

            //add bullet thru arraylist
            Bullet b = new Bullet(x,y,angle);
            ammo.add(b);

        }

        //go thru bullet and call updates
        for (int i = 0; i < ammo.size(); i++) {
            ammo.get(i).update(o, arg);
        }

    }


    private void rotateLeft() {
        this.angle -= 3;
    }

    private void rotateRight() {
        this.angle += 3;
    }

    private void moveBackwards() {

        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();

    }

    private void moveForwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

//    public boolean collideWithTank(Tank anotherTank) {
//        Rectangle tank = new Rectangle(x,y,getWidth()-DELTA,getHeight()-DELTA);
//        Rectangle secondTank = new Rectangle(anotherTank.getX(), anotherTank.getY(),
//                anotherTank.getWidth()-DELTA, anotherTank.getHeight()-DELTA);
//
//        return tank.intersects(secondTank);
//    }

    //Borders are check so that it won't fly out the screen
    private void checkBorder() {
        if (x < 0) {
            x = 0;
        }
        if (x >= 1220) {
            x = 1220;
        }
        if (y < 0) {
            y = 0;
        }
        if (y >= 1200) {
            y = 1200;
        }
    }
}
