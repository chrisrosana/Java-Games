import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;

public class Bullet extends JComponent implements Observer {

    public int x, vx; // position x
    public int y, vy; // position y
    public int r = 6;
    public int angle;

    public String imagePath;

    Graphics g;
//    private boolean firePressed;

    public Bullet(int x, int y, int angle) {

        super();

        setX(x);
        setY(y);

        setAngle(angle);


    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAngle(int  angle) {
        this.angle = angle;
    }

    @Override
    public void update(Observable o, Object arg) {

        moveBulletForwards();
//        System.out.println("Update Called");
//        this.repaint();

    }

    private void moveBulletForwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y -= vy;
    }


    public void drawBullet(Graphics g) {

            moveBulletForwards();
            g.setColor(Color.orange);
            g.fillOval(x, y, 10,10);

    }

}
