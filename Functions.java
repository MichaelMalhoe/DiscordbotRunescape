package com.malhoe.DiscordRunescapeBot;

import java.util.ArrayList;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Functions extends ListenerAdapter {

	public static void main(String[] args) throws Exception {
		JDA jda = new JDABuilder(AccountType.BOT).setToken(Credentials.token).buildBlocking();
		jda.addEventListener(new Functions());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent evt) {

		// Objects
		User objUser = evt.getAuthor();
		MessageChannel objMsgCh = evt.getChannel();
		Message objMsg = evt.getMessage();

		// Commandslist
		String rankcheck = "rankcheck";
		String help = "help";
		String rankstatus = "rankstatus";

		if (!objUser.getName().contains("Javabot")) {

			/**
			 * rankupcheck: Checks weather there are members in the clan with an incorrect
			 * rank
			 **/
			if (objMsg.getContentRaw().toLowerCase().contains(Credentials.prefix + rankcheck)) {
				String returnMessage = new String();
				try {
					ArrayList<MembersInfo> allUsers = new ApiCollect().getMembersList();

					for (MembersInfo mi : allUsers) {
						if (!MembersInfo.getCorrectRank(mi)) {
							returnMessage = returnMessage + "**" + mi.getName() + "** is a **" + mi.getRole()
									+ "** but should be a **" + mi.getCorrectRank() + "** with **" + mi.getTotalXP()
									+ "** xp" + System.getProperty("line.separator");
						}
					}
					objMsgCh.sendMessage(returnMessage).queue();
				} catch (Exception e) {
					objMsgCh.sendMessage("```Something went wrong, please send a pm to Soef```").queue();
					e.printStackTrace();
				}
			}

			/**
			 * help: Returns an message with the possible commands
			 */
			if (objMsg.getContentRaw().toLowerCase().contains(Credentials.prefix + help)) {
				{
					String print = new String();
					print = "```You can use the following commands to interact with this bot: "
							+ System.getProperty("line.separator") + Credentials.prefix + rankcheck
							+ "           |  To check the whole clan on rankups" + System.getProperty("line.separator")
							+ Credentials.prefix + rankstatus + " USERNAME |  For individual players"
							+ System.getProperty("line.separator") + Credentials.prefix + help
							+ "                |  To see the available commands" + System.getProperty("line.separator");
					objMsgCh.sendMessage(print + "```").queue();

				}
			}
			/**
			 * rankstatus: Returns the ranking information about a specific player
			 */
			if (objMsg.getContentRaw().toLowerCase().contains(Credentials.prefix + rankstatus)) {
				try {
					String username = objMsg.getContentRaw().substring(12);

					String returnMessage = new String();
					try {
						ArrayList<MembersInfo> allUsers = new ApiCollect().getMembersList();

						for (MembersInfo mi : allUsers) {
							if (mi.getName().equalsIgnoreCase(username)) {
								returnMessage = returnMessage + "CLAN INFORMATION ABOUT: " + "**" + mi.getName() + "**"
										+ System.getProperty("line.separator") + "YOUR CURRENT ROLE IS: " + "**"
										+ mi.getRole() + "**" + System.getProperty("line.separator")
										+ "YOUR CURRENT CLAN XP IS: " + "**" + mi.getTotalXP() + "**"
										+ System.getProperty("line.separator") + "CLAN INFORMATION ABOUT: " + "**"
										+ mi.getName() + "**" + System.getProperty("line.separator")
										+ "ACCORDING TO YOUR XP, YOUR RANK SHOULD BE " + "**" + mi.getCorrectRank()
										+ "**" + System.getProperty("line.separator");
							}
						}

						if (returnMessage == "") {
							objMsgCh.sendMessage("```There is no record of " + username + " in clan CC UH```").queue();
						} else {
							objMsgCh.sendMessage(returnMessage).queue();
						}
					}

					catch (Exception e) {
						objMsgCh.sendMessage("```Something went wrong, please send a pm to Soef```").queue();
						e.printStackTrace();
					}
				}

				catch (IndexOutOfBoundsException ioobe) {
					objMsgCh.sendMessage(
							"```You need to enter a username after rankstatus like: !rankstatus ExamplePlayer```").queue();
				}
			}
		}
	}
}
