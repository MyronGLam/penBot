package penBot;

import org.jibble.pircbot.PircBot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class PenBot extends PircBot {

	private UserData userData;
	private int kappa;
	private long lastTime;

	public PenBot(String name) {
		this.setName(name);

		// TODO make data specific to channel
		// Note:Channel is not in the constructor.
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
		User user = userData.getUser(sender);
		String[] splitM;
		splitM = message.split(" ");
		switch (splitM[0].toLowerCase()) {
		case "!time":
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
			break;
		case "!roulette":
			//TODO make roulette with custom amount of points, and no modifying roulette to play more than allowed
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
		case "!points":
			sendMessage(channel, sender + ", you have " + user.getPoints() + " points");
			break;
		case "!leave":
			leave(channel, sender);
			break;
		case "!join":
			if (sender.equalsIgnoreCase("penbotishere") && splitM[1] != null) {
				sendMessage(channel, "Joining " + message.toLowerCase().substring(6) + "'s channel...");
				joinChannel("#" + message.toLowerCase().substring(6));
			} else {
				sendMessage(channel, "Joining " + sender + "'s channel...");
				joinChannel("#" + sender);
			}
			break;
		case "!help":
			// TODO link to formatted html which tells what commands exist
			sendMessage(channel,
					"!time- current time !roulette-plays russian roulette !points-displays your current points !join-joins your channel !leave-bot leaves this channel if you're the owner");
			break;
		case "!kill":
			try {
				int tempInt = (Integer.parseInt(splitM[2]) * 5);
				if (splitM[2] != null) {
					if (user.getPoints() >= tempInt) {
						sendMessage(channel, ".timeout " + splitM[1].toLowerCase() + " " + tempInt);
						user.removePoints(tempInt);
					}
				}
			} catch (Exception e) {
			}
			break;
		}

		checkUpdate();
	}

	public void playRoulette(String channel, String name) {
		User user = userData.getUser(name);
		user.doRoulette();
		if ((int) (Math.random() * 6) == 0) {
			user.increaseDeaths();
			sendMessage(channel, name + ", you have died. You lost 20 points. You have died " + user.getDeaths()
					+ " times. You have played " + "roulette " + user.getRouletteCount() + " times.");
			if (user.getPoints() >= 10) {
				user.removePoints(20);
			}
		} else {
			sendMessage(channel, name + ", you have lived. You gained 10 points. You have played roulette "
					+ user.getRouletteCount() + " times.");
			user.addPoints(10);
		}
		userData.updateUser(user);
	}

	public void checkUpdate() {
		if (System.currentTimeMillis() - lastTime > 60000) {
			writeData();
			lastTime = System.currentTimeMillis();
		}
	}

	public void leave(String channel, String name) {
		if (name.equals(channel.substring(1))) {
			sendMessage(channel, "Leaving " + name + "'s channel...");
			writeData();
			partChannel(channel);
		}
	}

	public void writeData() {
		File f;

		userData.writeData();
		try {
			f = new File("data/var.txt");
			if (!(f.exists())) {
				f.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/var.txt"));
			writer.write("" + kappa);
			writer.close();
		} catch (Exception e) {
			System.out.println("Var writer error");
		}
	}
}
