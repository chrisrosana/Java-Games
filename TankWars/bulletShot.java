package TankGameMaster;

import java.awt.*;
import java.awt.image.ImageObserver;

public class bulletShot extends Bullet {

    private int DamageBullet, angle;

    public bulletShot(Image img, int speed, Tank tank, int sideSpeed, int damage)
    {
        super(img, tank.x + ((tank.getWidth()/2) - (img.getWidth(null)/120)),
                tank.y+(img.getHeight(null)/2), speed, sideSpeed);

        DamageBullet = damage;
        mainTank = tank;
        sTemp = true;
        angle = mainTank.tankAngele();
        bulletX = img.getWidth(null)/60;
        bulletY = img.getHeight(null);

    }



    public void update(){

        x += speed * Math.cos(Math.toRadians(6*angle));
        y -= speed * Math.sin(Math.toRadians(6*angle));

        if(TankWorld.player1.collision(x, y, bulletX, bulletY) && sTemp && mainTank != TankWorld.player1 && !TankWorld.player1.Respawn())
        {
            sTemp = false;
            TankWorld.player1.BuletDammage(DamageBullet);
        }
        else if(TankWorld.player2.collision(x, y, bulletX, bulletY) && sTemp && mainTank != TankWorld.player2 && !TankWorld.player2.Respawn())
        {
            sTemp = false;
            TankWorld.player2.BuletDammage(DamageBullet);
        }

        else
            {
                for(int i = 0; i < TankWorld.getTankWorld().getWall().size()-1; i++)
            {
                Wall bWall = TankWorld.getTankWorld().getWall().get(i);
                if(bWall.wallGetRectangle().intersects(x, y, width/60, height)&&bWall.respawnWall())
                {
                    if(bWall.isDestroyAble())
                    {
                        bWall.breakWall();
                        addExplosion(TankWorld.getTankWorld().getWallImageEx(),bWall.x,bWall.y);
                    }
                    sTemp=false;
                }
            }
        }
        if(!sTemp)
        {
            addExplosion(TankWorld.getTankWorld().getWallImageEx(),x,y);
        }
    }

    //draw bullet
    public void draw(ImageObserver obs, Graphics2D g)
    {
        g.drawImage(img, x, y, x+(img.getWidth(null)/60),
                y+img.getHeight(null), angle*24, 0,
                angle*24+24, img.getHeight(null), obs);
        update();
    }

}
