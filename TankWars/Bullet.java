package TankGameMaster;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Bullet extends GameObj
{
    boolean sTemp;
    int bulletSpeed;
    int bulletX, bulletY;

    Tank mainTank;

    Bullet(Image img, int x, int y, int speed, int bulletSpeed)
    {
        super(img, x, y, speed);

        sTemp = true;
        this.bulletSpeed = bulletSpeed;
    }

    public void draw(ImageObserver obs, Graphics2D g)
    {
        if(sTemp)
        {
            g.drawImage(img, x, y, obs);
        }
        update();
    }

    public boolean flashing()
    {
        return sTemp;
    }

    public void update()
    {
    }

}