package src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataFetchHttp {

	PlayerList playerList = new PlayerList();
	public int listNumber;
	private String session, listDate;

	// Get all player data for a certain list. Initial given: only list information fetched.
	public DataFetchHttp() throws Exception {}

	public int getLatestListNumber()
	{
		this.getBaseData();
		return listNumber;
	}	

	public PlayerList getPlayerList(int listNumberIn)
	{
		try
		{

			this.getBaseData();

			// Set which pages to fetch
			String[] numbers = {"1","501","1001","1501", "2001", "2501", "3001", "3501", "4001"};

			// Fetch all pages in different Http requests
			for(String number : numbers)
			{
		
				String basestring = "http://www.profixio.com/fx/ranking_sbtf/ranking_sbtf_list.php?rid=" + Integer.toString(listNumberIn) +"&from="+number;

				Document html = Jsoup.connect(String.format(basestring)).cookie("PHPSESSID",session).get();
				Element nametable = html.select("table").get(0); 
				Elements persons = nametable.select("tr");

				Element element = html.select("form").get(0);

				Elements rids = element.select("option");

				for (Element elem : rids)
				{
					if (elem.val().contains(Integer.toString(listNumberIn)))
					{
						listDate = elem.text();
						playerList.listDate = listDate;
						break;
					}
				}
				
				int playerListSize = persons.size() - 1;
				
				if (playerListSize > 0)
				{

					for (int i = 1; i < persons.size(); i++) 
					{
						Element person = persons.get(i);
						Elements personinfo = person.select("td");
						String name = personinfo.get(2).text();

						if (name.contains(","))
						{
							Player temp = new Player();

							Elements personid = person.select("span");
							String idstring = personid.get(0).id();

							temp.firstName = name.split(",")[1].trim();
							
							temp.lastName = name.split(",")[0].trim();
							
							temp.year = Integer.parseInt(personinfo.get(3).text());
						
							temp.club = personinfo.get(4).text().replace("*", "");
						
							if (temp.club.length() == 0)
							{
								temp.club = "SPELARE SAKNAR LICENS";
							}
							
							// Ugly code to handle world ranking
							String[] test = personinfo.get(0).text().split(" ");
							if (test.length == 2)
							{
							temp.rank = Integer.parseInt(test[1]);
							}
							else if (test.length == 1)
							{
								temp.rank = Integer.parseInt(test[0]);
							}
								
							temp.points = Integer.parseInt(personinfo.get(5).text());
							
							temp.id = Integer.parseInt(getPlayerId(idstring));

							temp.level = temp.getPlayerRank();
							
							//System.out.println("Added a player: " + temp.firstName + " " + temp.lastName);
							
							playerList.addToPlayerList(temp);
							
						}
					}
				}
			}
		}
		catch(Exception e)
		{
		}
		return playerList;
	}

	public void getBaseData(){

		try{
			Connection.Response getcookie = Jsoup.connect("http://www.profixio.com/fx/ranking_sbtf/ranking_sbtf_public.php").execute();
			session = getcookie.cookie("PHPSESSID"); 
			Document doc = Jsoup.connect("http://www.profixio.com/fx/ranking_sbtf/ranking_sbtf_public.php").cookie("PHPSESSID",session).get();
			Elements options = doc.select("select > option");
			listNumber = Integer.parseInt(options.get(0).attr("value")); 
		}
		catch(Exception e){}
	}

	public int getListNumber()
	{
		return listNumber;
	}

	public String getListDate()
	{
		return listDate;
	}

	public String getPlayerId(String string)
	{
		Pattern p = Pattern.compile("\\:(.*?)\\:");
		Matcher m = p.matcher(string);
		if (m.find())
		{
			String temp = m.group(1);
			temp.replace(':',' ').trim();
			return temp;
		}

		return null;
	}

}




