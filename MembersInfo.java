package com.malhoe.DiscordRunescapeBot;

//This class gets the input from the API and covert it to the ranking system.
public class MembersInfo implements Comparable<MembersInfo> {

	private final String name;
	private final String role;
	private final long totalXP;
	private final String kills;
	private String correctRank;

	public MembersInfo(String name, String role, String totalXP, String kills) {
		this.name = name;
		this.role = role;
		this.totalXP = Long.parseLong(totalXP);
		this.kills = kills;
		this.correctRank = setCorrectRank(Long.parseLong(totalXP));
	}

	private String setCorrectRank(long xp) {
		if (xp < 10_000_000)
			return "Recruit";
		if (xp >= 10_000_000 && xp < 25_000_000)
			return "Corporal";
		if (xp >= 25_000_000 && xp < 75_000_000)
			return "Sergeant";
		if (xp >= 75_000_000 && xp < 150_000_000)
			return "Lieutenant";
		if (xp >= 150_000_000 && xp < 250_000_000)
			return "Captain";
		if (xp >= 250_000_000)
			return "General";
		else
			return "Your rank is no longer based on xp gained.";
	}

	public String getName() {
		return name;
	}

	public String getCorrectRank() {
		return correctRank;
	}

	public String getRole() {
		return role;
	}

	public long getTotalXP() {
		return totalXP;
	}

	public String getKills() {
		return kills;
	}

	public int compareTo(MembersInfo compareMembers) {
		long compareXP = ((MembersInfo) compareMembers).getTotalXP();
		return (int) (this.totalXP - compareXP);
	}

	// Static to ensure all instances are able to call the same method.
	public static boolean getCorrectRank(MembersInfo mi) {
			if (mi.role.equals("Recruit") || mi.role.equals("Corporal") || mi.role.equals("Sergeant")
					|| mi.role.equals("Lieutenant") || mi.role.equals("Captain") || mi.role.equals("General")) {
				if (mi.role.equals(mi.getCorrectRank())) {
					return true;
				} else {
					return false;
				}
			} else
				return true;
		}
	}
