//package TopDownShooter;

import java.awt.*;

public class Player {

    // FIELDS
    //player be circle
    private int x;
    private int y;
    private int r; //radius

    //velocity
    private int dx;
    private int dy;
    private int speed;

    //movement
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;


    private int lives;

    private Color color1;
    private Color color2;

    //CONSTRUCTOR
    public Player() {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        r = 5;

        dx = 0;
        dy = 0;
        speed = 5;

        lives = 3;
        color1 = Color.WHITE; //when alive
        color1 = Color.red; //when hit
    }

    // FUNCTIONS

    // create setters
    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setUp(boolean b) { up = b; }
    public void setDown(boolean b) { down = b; }

    public void update() {

        if(left) {
            dx = -speed;
        }

        if(right) {
            dx = speed;
        }

        if(up) {
            dy = -speed;
        }

        if(down) {
            dy = speed;
        }

        x += dx;
        y += dy;

        // check the bounds/borders
        if(x < r) x = r;
        if(y < r) y = r;
        if(x > GamePanel.WIDTH - r) x = GamePanel.WIDTH - r;
        if(y > GamePanel.HEIGHT - r) y = GamePanel.HEIGHT - r;

        dx = 0;
        dy = 0;
    }

    public void draw(Graphics2D g) {
        //draw player
        g.setColor(color1);
        g.fillOval(x - r, y - r, 2 * r, 2 * r); //makes on the center of the player

        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval(x - r, y - r, 2 * r, 2 * r); //makes on the center of the player
        g.setStroke(new BasicStroke(1));
    }
}
