package TankGameMaster;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class GameEvents extends Observable {
	public int type;
	public Object event;
	int collision;

	public void setValue(KeyEvent e)
	{
		type = 1; // let's assume this means key input.
		// Should use CONSTANT value for this when you program
		event = e;
		setChanged();
		// trigger notification
		notifyObservers(this);
	}

	public void setValue(String msg) {
		type = collision;
		event = msg;
		setChanged();
		// trigger notification
		notifyObservers(this);
	}

	public int getType(){
		return this.type;
	}

	public Object getEvent(){
		return this.event;
	}

}
