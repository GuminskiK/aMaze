package Core;

import java.util.ArrayList;
import java.util.List;

public class Watched implements Observable {

    private List<Observer> observers;
    private String message;

    public Watched() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }

}
