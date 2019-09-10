package TankGameMaster;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Wall extends GameObj{

	boolean breakableWall;
	private Rectangle shapeWall;
	private int resfresh, width, height;



	public Wall(Image img, int x, int y, boolean weakWall)
	{
		super(img, x, y, 0);
		resfresh = 0;
		breakableWall = weakWall;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);

		explode=false;
		shapeWall = new Rectangle(x,y,width,height);
	}

	public boolean respawnWall()
	{
		return resfresh == 0;
	}

	public boolean isDestroyAble()
	{
		return breakableWall;
	}

	public void breakWall()
	{
		resfresh = 500;
	}

	public Rectangle wallGetRectangle()
	{
		return shapeWall;
	}

	//draw wall
	public void draw(ImageObserver obs, Graphics2D g)
	{
		if(resfresh == 0)
		{
			g.drawImage(img, x, y, obs);
			update();
		}
		else
			{
				resfresh--;
			}
    }


	public void update() {

		//Player1 Wall collision
		if (TankWorld.player1.collision(this.x, this.y, width, height))
		{
			if(TankWorld.player1.x > (x))
			{
				TankWorld.player1.x += 3;
			}
			else if(TankWorld.player1.x < (this.x))
			{
				TankWorld.player1.x -= 3;
			}

			if(TankWorld.player1.y > (this.y))
			{
				TankWorld.player1.y += 3;
			}
			else if(TankWorld.player1.y < this.y)
			{
				TankWorld.player1.y -= 3;
			}
		}

		//Player 2 Wall collision
		if (TankWorld.player2.collision(this.x, this.y, width, height))
		{
			if(TankWorld.player2.x > (x))
			{
				TankWorld.player2.x += 3;
			}
			else if(TankWorld.player2.x < (this.x))
			{
				TankWorld.player2.x -= 3;
			}
			if(TankWorld.player2.y > (this.y))
			{
				TankWorld.player2.y += 3;
			}
			else if(TankWorld.player2.y<this.y)
			{
				TankWorld.player2.y -= 3;
			}
		}
	}
}
