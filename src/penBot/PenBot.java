package penBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.jibble.pircbot.*;
import org.json.*;

public class PenBot extends PircBot {
	
	private ArrayList <User> users = new ArrayList();
	
	public PenBot(String name) {
		this.setName(name);
		this.changeNick(name);
		File f = new File("data");
		if (!(f.exists() && f.isDirectory())) {
			f.mkdir();
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader("data/users.json"));
			String line = in.readLine();
			while (line != null) {
				users.add(new User(new JSONObject(line)));
				line = in.readLine();
			}
		} catch(Exception e) {
			System.out.println("Reader error");
		}
	}

	public void setNameOfBot(String name) {
		this.setName(name);
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		switch (message.toLowerCase()) {
		case "!time":
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
			break;
		case "!roulette":
			playRoul(channel, sender);
			break;
		}
	}

	public void playRoul(String channel, String name) {
		//TODO make the search more efficient by binary search
		if ((int)(Math.random()*6) == 0) {
			int i=0;
			boolean found = false;
			while (i < users.size()){
				if (name.equalsIgnoreCase((users.get(i)).getName())){
					(users.get(i)).setRouletteCount((users.get(i)).getRouletteCount()+1);
					found = true;
				}
				i++;
			}
			if (found == false) {
				users.add(new User(name));
				(users.get(i)).setRouletteCount((users.get(i)).getRouletteCount()+1);
				
			}
			sendMessage(channel, name + ", you have died. Total deaths is: " + (users.get(i)).getRouletteCount()+1);
		} else {
			sendMessage(channel, name + ", you have lived.");
		}
	}
}
