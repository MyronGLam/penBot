package penBot;

import org.json.JSONObject;

public class User {
	private String name;
	private int rouletteCount;
	
	User(String name) {
		this.name = name; 
	}
	User(JSONObject o) {
		name = o.getString("name");
		rouletteCount = o.getInt("rouletteCount");
	}
	public int getRouletteCount() {
		return rouletteCount;
	}
	public String getName() {
		return name;
	}
	public void setRouletteCount(int rouletteCount) {
		this.rouletteCount = rouletteCount;
	}
}
