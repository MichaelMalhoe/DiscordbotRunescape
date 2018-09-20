package com.malhoe.DiscordRunescapeBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class ApiCollect {

	// The url for the API call to the Runescape website.
	private final String apiUrl = "http://services.runescape.com/m=clan-hiscores/members_lite.ws?clanName="
			+ Credentials.clanname;

	// This method fetches the whole clanlist with all information. It stores the
	// data in a ArrayList for further methods.
	public ArrayList<MembersInfo> getMembersList() {
		ArrayList<MembersInfo> clanMembersList = new ArrayList<MembersInfo>();

		try {
			URL url = new URL(this.apiUrl);
			// To collect the big chunck of data
			try (InputStream input = url.openConnection().getInputStream();
					InputStreamReader inputReader = new InputStreamReader(input, "ISO-8859-1");
					BufferedReader reader = new BufferedReader(inputReader)
				) {
				// To convert the big chunck of data to readable List and add it to our list.
				String line;
				while ((line = reader.readLine()) != null) {

					String[] parts = line.split(",");
					if (!parts[0].contains("Clanmate")) {
						parts[0] = new String(parts[0].getBytes("UTF-8"), "ISO-8859-1");
						parts[0] = parts[0].replaceAll("\u00a0", " ").replaceAll("\u00C2", "");
						MembersInfo player = new MembersInfo(parts[0], parts[1], parts[2], parts[3]);
						clanMembersList.add(player);
					}
				}
			}

			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			// Sorting the list
			finally {

				Collections.sort(clanMembersList);
			}
		} catch (Exception E) {
		}
		return clanMembersList;
	}
		
}
