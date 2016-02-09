package penBot;

public class PenBotMain {

	public static void main(String[] args) throws Exception {

		// Now start our bot up.
		PenBot bot = new PenBot("penbotishere");
		System.out.println(bot.getLogin());

		// Enable debugging output.
		bot.setVerbose(true);

		// Connect to the IRC server.
		bot.connect("irc.twitch.tv", 6667, "oauth:ly98frmc48qp9tywex2a67org9txka");

		// Join the #pircbot channel.
		bot.joinChannel("#penbotishere");
	}

}
