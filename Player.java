package src;

import java.util.List;
import java.util.ArrayList;

public class Player {
	public String firstName, lastName, club, level;
	public int id, year, rank, points;

	private List<Integer> pointList = new ArrayList<>();
	private List<Integer> placeList = new ArrayList<>();

	public Player() {}

	public void addplayerpointlist(int points) 
	{
		this.pointList.add(points);
	}

	public void addplayerplacelist(int places) 
	{
		this.placeList.add(places);
	}		

	public List<Integer> getPointList()
	{
		return pointList;
	}

	public List<Integer> getPlaceList()
	{
		return placeList;
	}

	public String getPlayerRank()
	{
		if (points > 2250) 
		{
			return "Elit";
		}
		else
			if (points >= 2000 && points <=2049)
			{
				return "Klass 1";
			}
			else
				if (points > 1750 && points <= 1999)
					return "Klass 2";
				else
					if (points >= 1500 && points <= 1749)
					{
						return "Klass 3";
					}
					else
						if (points >= 1250 && points <= 1749)
						{
							return "klass 4";
						}
						else
							if (points >= 1000 && points <= 1249)
							{
								return "Klass 5";
							}
							else
								if (points >= 750 && points <= 999)
								{
									return "Klass 6";
								}
								else
									if (points <= 749)
									{
										return "Klass 7";
									}
									else return "Klass kunde inte sättas";
	}

}