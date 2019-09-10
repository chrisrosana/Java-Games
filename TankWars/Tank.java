package TankGameMaster;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Observable;


public class Tank extends GameObj {

    int  health = 4, life = 3, coolDown = 0, respawnX, respawnY;
	int left , right, up, down, fireKey, angle = 0, fireCoolDown = 0, shootRate;
	boolean updateLeft, updateRight, updateUp, updateDown;
	boolean powerUp;



	public Tank(Image img, int x, int y, int totalSpeed, int left, int right, int up, int down, int shoot)
	{
    	super( img, x, y, totalSpeed);
    	this.width = img.getWidth(null)/60;
    	this.left = left;
    	this.right = right;
    	this.up = up;
    	this.down = down;
    	this.fireKey = shoot;
		this.updateLeft= false;
		this.updateRight = false;
		this.updateUp = false;
		this.updateDown= false;
    	this.explode = false;
		this.shootRate= 50;
    	//give location
		this.respawnX = x;
		this.respawnY = y;
		this.powerUp = false;
    }

	//get Health
	public int currentLife()
	{
		return life;
	}

	//Tank Damage
    public void BuletDammage(int dammage)
	{
	   health -= dammage;
    }

    public boolean Respawn()
	{
		return (coolDown != 0 );
	}

	//powerUP
    public void oneUp()
	{
    	this.powerUp = true;
		fireCoolDown = 50;
    }

    //healthUp
    public void healthUp()
	{
    	this.health = 4; //give max health
    }

    public void draw(ImageObserver obs, Graphics2D g)
	{
		fireCoolDown--;

        if(health <= 0)
        {
        	//explode animation as long as health is up
			explode = true;
        }

        if(health>0&&coolDown==0&&life>0)
        {
			explode = false;
        	g.drawImage(img, x, y, x+(img.getWidth(null)/60), y+img.getHeight(null), angle*64, 0, angle*64+64, img.getHeight(null), obs);

        	if((!TankWorld.player1.Respawn()) &&
					(!TankWorld.player2.Respawn()))
        	{
        		if (TankWorld.player1.collision(TankWorld.player2.x, TankWorld.player2.y,
						TankWorld.player2.width, TankWorld.player2.height))
        		{
        			if(TankWorld.player1.x > x)
        			{
						TankWorld.player1.x+=speed*5;
						TankWorld.player2.x-=speed*5;
        			}
        			else if(TankWorld.player1.x < (this.x))
        			{
						TankWorld.player1.x -= speed*5;
						TankWorld.player2.x += speed*5;
        			}
        			if(TankWorld.player1.y > this.y)
        			{
						TankWorld.player1.y += speed*5;
						TankWorld.player2.y -= speed*5;
        			}
        			else if(TankWorld.player1.y<this.y)
        			{
						TankWorld.player1.y -= speed*5;
						TankWorld.player2.y += speed*5;
        			}
        		}
        	}
        }

        //explosion
        else if(explode==true&&coolDown==0&&life>0)
        {
    		addExplosion(TankWorld.getTankWorld().getExplosions(),x,y);
    		coolDown = 150;
			powerUp = false;
    		if(--life>=0)
    			health = 4;
				explode = false;
    			x = respawnX;
    			y = respawnY;
        }
        else
        	{
        		coolDown--;
        	}
    }

    //speed of bullet and damage
    public void shoot(Tank p)
	{
		if(powerUp)
		{
			TankWorld.getTankWorld().getBullet().add(new bulletShot(TankWorld.getTankWorld().getBigBullet(), speed*5, this, 0, 2));
		}
		else
			{
				TankWorld.getTankWorld().getBullet().add(new bulletShot(TankWorld.getTankWorld().getSmallBullet(), speed*3, this, 0, 1));
			}
    }


	public void update(Observable obj, Object arg)
	{
		GameEvents ge = (GameEvents) arg;
		if (ge.type == 1 && !explode && coolDown==0)
		{
			KeyEvent e = (KeyEvent) ge.event;

			//left
			if (e.getKeyCode() == left)
			{
				if (e.getID() == KeyEvent.KEY_RELEASED)
				{
					updateLeft = false;
				} else if (e.getID() == KeyEvent.KEY_PRESSED)
				{
					updateLeft = true;
				}
			}

			//right
			if (e.getKeyCode() == right)
			{
				if (e.getID() == KeyEvent.KEY_RELEASED)
				{

					updateRight = false;
				} else if (e.getID() == KeyEvent.KEY_PRESSED)
				{
					updateRight = true;
				}
			}

			//UP
			if (e.getKeyCode() == up)
			{
				if (e.getID() == KeyEvent.KEY_RELEASED)
				{
					updateUp = false;
				} else if (e.getID() == KeyEvent.KEY_PRESSED)
				{
					updateUp = true;
				}
			}

			//Down
			if (e.getKeyCode() == down)
			{
				if (e.getID() == KeyEvent.KEY_RELEASED)
				{
					updateDown = false;
				} else if (e.getID() == KeyEvent.KEY_PRESSED)
				{
					updateDown = true;
				}
			}

			if (e.getKeyCode() == fireKey
					&& fireCoolDown<=0)
			{
				if (e.getID() == KeyEvent.KEY_PRESSED)
				{
					fireCoolDown = shootRate;
					shoot(this);
				}
			}

		}
	}

	
    public void MoveTank()
	{
		if (updateLeft == true)
		{
			angle += 1;
		}
		if (updateRight == true)
		{
			angle -= 1;
		}
		if (updateUp == true )
		{
			x+=speed*Math.cos(Math.toRadians(6*angle));
			y-=speed*Math.sin(Math.toRadians(6*angle));

		}
		if (updateDown == true)
		{
			x-=speed*Math.cos(Math.toRadians(6*angle));
			y+=speed*Math.sin(Math.toRadians(6*angle));
		}

		if(angle == -1) angle = 59;
		else if(angle == 60) angle = 0;
    }

	public int getHealth()
	{
		return health;
	}

	//facing tank
	public int tankAngele()
	{
		return angle;
	}

	//center the tank
	public int CenterX()
	{
		return x + ((img.getWidth(null)/60) / 2);
	}


	public int CenterY()
	{
		return y + (img.getHeight(null) / 2);
	}

}
