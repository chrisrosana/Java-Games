package TankGameMaster;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Explode extends GameObj{
    Image[] img;
    int count, i;
    boolean boom;

    public Explode(Image[] img, int x, int y, int speed)
    {
        super(img, x, y, speed);
        this.img = img;
        count = 0;
        i = 0;
        boom = false;
    }

    public void draw(ImageObserver obs, Graphics2D g)
    {
        if (i < this.img.length)
        {
            g.drawImage(this.img[i % this.img.length], x, y, obs);
            if (((count++) % 10) == 0)
            {
                i++;
            }
        }
        else
            {
                boom = true;
            }
    }

    public boolean boom()
    {
        return boom;
    }

    public void update(Observable obj, Object arg)
    {

    }

}
