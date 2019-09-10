package TankGameMaster;

import java.awt.*;

public class bulletUp extends powerUp
{

    public bulletUp(Image img, int x, int y, int speed)
    {
        super(img, x, y, speed);
    }

    //collision with power up
    public void update()
    {
        y += speed;
        if(TankWorld.player1.collision(x, y, width, height))
        {
            TankWorld.player1.oneUp();
            flash = false;
        }
        else if(TankWorld.player2.collision(x, y, width, height))
        {
            TankWorld.player2.oneUp();
            flash = false;
        }
    }
}
