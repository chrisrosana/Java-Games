package TankGameMaster;
import java.awt.*;

public class HealthUp extends powerUp
{

	public HealthUp(Image img, int x, int y, int speed)
	{
		super(img, x, y, speed);
	}

	public void update()
	{
		y += speed;
		if(TankWorld.player1.collision(x, y, width, height)){
			TankWorld.player1.healthUp();
			flash = false;
		}
		else if(TankWorld.player2.collision(x, y, width, height)){
			TankWorld.player2.healthUp();
			flash = false;
		}

	}

}
