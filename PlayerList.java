package src;

import java.util.ArrayList;

public class PlayerList {

	public int listNumber;
	public String listDate;

	private ArrayList<Player> playerList = new ArrayList<Player>();

	public void addToPlayerList(Player player)
	{
		playerList.add(player);
	}

	public ArrayList<Player> getPlayerList()
	{
		return playerList;
	}

	public int getPlayerListNumber()
	{
		return listNumber;
	}
	
	public String getPlayerListDate()
	{
		return listDate;
	}

	public int getPlayerListSize()
	{
		return playerList.size();
	}
	
	public boolean verifyPlayerList()
	{

		boolean verdict = true;
		
		for (Player iterPlayer : playerList)
		{
			
		   	verdict = true;
			
			if (iterPlayer.firstName.length() == 0)
			{
				verdict = false;
			}

			if (iterPlayer.lastName.length() == 0)
			{
				verdict = false;
			}

			if (iterPlayer.club.length() == 0)
			{
				verdict = false;
			}
			
			if (iterPlayer.level.length() == 0)
			{
				verdict = false;
			}

			if (verdict == false)
			{
				System.out.println("Kolla upp spelare: " + iterPlayer.firstName + iterPlayer.lastName);
			}
		}
		
		return verdict;
	}

}
