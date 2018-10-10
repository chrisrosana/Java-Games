//package TankWars;

import java.util.Observable;

public class GameEventObservable extends Observable {

    public GameEventObservable() {

    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }
}
