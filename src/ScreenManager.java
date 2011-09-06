import java.util.HashMap;
import java.util.Map;

public class ScreenManager {

	private Map<String,Screen> screenMap;
	private Screen activeScreen;
	
	public ScreenManager() {
		this.screenMap = new HashMap<String,Screen>();
	}
	
	public void addScreen(Screen screen, String name) {
		this.screenMap.put(name, screen);
	}
	
	public void setActiveScreen(String name) {
		if (this.activeScreen != null) {
			this.activeScreen.pause();
		}
		this.activeScreen = this.screenMap.get(name);
		this.activeScreen.resume();
	}
	
	public Screen getActiveScreen() {
		return this.activeScreen;
	}
	
}
