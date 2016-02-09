package penBot;

import org.jibble.pircbot.PircBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PenBot extends PircBot {

	private UserData userData;
	private int kappa;
	private long lastTime;

	public PenBot(String name) {
		this.setName(name);
		this.changeNick(name);

		lastTime = System.currentTimeMillis();

		File f = new File("data");
		if (!(f.exists() && f.isDirectory())) {
			f.mkdir();
		}
		try {
			f = new File("data/var.txt");
			if (!(f.exists())) {
				f.createNewFile();
			} else {
				BufferedReader in = new BufferedReader(new FileReader("data/var.txt"));
				kappa = Integer.parseInt(in.readLine());
				in.close();
			}
		} catch (Exception e) {
			System.out.println("Var reader error");
		}
		userData = new UserData();
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
				playRoulette(channel, sender);
				break;
			case "kappa":
				kappa++;
				sendMessage(channel, "Kappa" + kappa + " Kappa");
				break;
			case "feelsgoodman":
				sendMessage(channel, "FeelsBadMan");
				break;
			case "feelsbadman":
				sendMessage(channel, "FeelsGoodMan");
				break;
		}
		checkUpdate();
	}

	public void playRoulette(String channel, String name) {
		User user = userData.getUser(name);
		user.doRoulette();
		if ((int) (Math.random() * 6) == 0) {
			user.increaseDeaths();
			sendMessage(channel, name + ", you have died. You have died " + user.getDeaths() + " times. You have played " +
					"roulette " + user.getRouletteCount() + " times.");
		} else {
			sendMessage(channel, name + ", you have lived. You have played roulette " + user.getRouletteCount() + " times.");
		}
		userData.updateUser(user);
	}

	public void checkUpdate() {
		if (System.currentTimeMillis() - lastTime > 60000) {
			userData.writeData();
			lastTime = System.currentTimeMillis();
		}
	}
}
