package TankGameMaster;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;


public abstract class GameObj implements Observer{ 
	int x, y;
	int speed, height, width;
	int tankHold = 0;

	Rectangle bbox ;
	 boolean explode;
	 Image[] imgages = new Image[3];
	 Image img;

	public GameObj(Image[] img, int x, int y, int speed)
	{
		this.imgages = img;
		this.x = x;
		this.y = y;
		height = img[tankHold].getHeight(null);
		width = img[tankHold].getWidth(null);
		this.speed = speed;
	}

	public void update(Observable obj, Object arg)
	{
	}

	public void draw(ImageObserver obs, Graphics2D g)
	{
	}

	//collision check
	 public boolean collision(int x, int y, int w, int h)
	 {
	        bbox = new Rectangle(this.x, this.y, this.width, this.height);
	        Rectangle otherBBox = new Rectangle (x,y,w,h);
	        if(this.bbox.intersects(otherBBox)&&!explode)
	        {
	            return true;
	        }
	        return false;
	 }


	public int getWidth()
	{
		return this.width;
	}

	public void addExplosion(Image[] boom, int a, int b)
	{
		TankWorld.getTankWorld().getExplosion().add(new Explode(boom, a, b, 0));
	}



	public GameObj(Image img, int x, int y, int speed){
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		height = img.getHeight(null);
		width = img.getWidth(null);
	}
}
