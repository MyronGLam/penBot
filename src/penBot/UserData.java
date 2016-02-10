package penBot;

import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

public class UserData {
	private HashMap<String, JSONObject> users = new HashMap<>();
	private File usersFile = new File("data/users.json");

	public UserData() {
		loadUsers();
	}

	public User addUser(String username) {
		JSONObject user = new JSONObject();
		user.put("name", username);
		users.put(username, user);
		return new User(user);
	}

	public User getUser(String username) {
		if (users.containsKey(username)) {
			return new User(users.get(username));
		} else {
			return addUser(username);
		}
	}

	public void updateUser(User user) {
		users.put(user.getName(), user.toJSONObject());
	}

	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile));
			for (JSONObject user : users.values()) {
				writer.write(user.toString());
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Failed to write to users.json");
		}

	}

	private void loadUsers() {
		if (usersFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(usersFile));
				String line = reader.readLine();
				while (line != null) {
					JSONObject json = new JSONObject(line);
					users.put((String) json.get("name"), json);
					line = reader.readLine();
				}
				reader.close();
			} catch (Exception e) {
				System.out.println("Failed to read users.json");
			}
		} else {
			try {
				usersFile.createNewFile();
			} catch (Exception e) {
				System.out.println("Failed to create users.json");
			}
		}
	}

}
