package observer;

import java.util.ArrayList;
import java.util.Iterator;

import mvc.DrawingModel;

public class SelectedShape implements Observable {
	private DrawingModel model;
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		
		while (it.hasNext()) {
			it.next().update(model);
		}
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
		
		notifyObservers();
	}
}
