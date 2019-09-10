package TankGameMaster;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class powerUp extends GameObj
{
    boolean flash;

    public powerUp(Image img, int x, int y, int speed)
    {
        super(img, x, y, speed);
        flash = true;
    }

    public void update()
    {
    }

    public boolean isShow()
    {
        return flash;
    }

    public void draw(ImageObserver obs, Graphics2D g)
    {
        if(flash)
            g.drawImage(img, x, y, obs);
    }
}
