package game;

import java.awt.Graphics;
import java.util.LinkedList;

public class ObjectHandler {

	private static final ObjectHandler instance = new ObjectHandler(); // Thread-safe
	private LinkedList<GameObject> objectList = new LinkedList<>();
	
	private ObjectHandler() {}
	
	public static ObjectHandler getInstance() {
		return instance;
	}
	
	public void tick() {
		for (int i = 0; i < objectList.size(); i++) {
			GameObject object = objectList.get(i);
			object.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objectList.size(); i++) {
			GameObject object = objectList.get(i);
			object.render(g);
		}
	}

	public void addObject(GameObject object) {
		objectList.add(object);
	}

	public void removeObject(GameObject object) {
		objectList.remove(object);
	}

}
