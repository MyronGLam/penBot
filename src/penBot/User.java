package penBot;

import org.json.JSONObject;

public class User {
	private String name;
	private int rouletteCount;
	private int deaths;
	private int points;

	User(String name) {
		this.name = name;
	}

	User(JSONObject o) {
		name = o.getString("name");
		if (!o.isNull("rouletteCount")) {
			rouletteCount = o.getInt("rouletteCount");
		}
		if (!o.isNull("deaths")) {
			deaths = o.getInt("deaths");
		}
		if (!o.isNull("points")) {
			points = o.getInt("points");
		}
	}

	public int getRouletteCount() {
		return rouletteCount;
	}

	public int getDeaths() {
		return deaths;
	}

	public String getName() {
		return name;
	}

	public void doRoulette() {
		rouletteCount++;
	}

	public void increaseDeaths() {
		deaths++;
	}
	
	public int getPoints(){
		return points;
	}

	public void addPoints(int p) {
		points += p;
	}

	public void removePoints(int p) {
		points -= p;
	}

	public JSONObject toJSONObject() {
		JSONObject user = new JSONObject();
		user.put("name", name);
		user.put("rouletteCount", rouletteCount);
		user.put("deaths", deaths);
		user.put("points", points);
		return user;
	}

}
