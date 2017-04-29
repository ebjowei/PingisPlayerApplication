package src;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

	// Fetch data from rank web page via http.
	// Write the 18 latest rank lists to Xml file.
	public static void main(String[] args) throws Exception 
	{
		String fileNameLog = "C:\\Users\\Bjorn\\PingisXml\\ApplicationLog.xml";
		PrintWriter logWriter = new PrintWriter(fileNameLog);
		
		WriteXmlFile fileWrite = new WriteXmlFile();			
		String fileNameXml = "C:\\Users\\Bjorn\\PingisXml\\PlayerLists.xml";
		
		try
		{
		DataFetchHttp http1 = new DataFetchHttp();

		int latestListNumber = http1.getLatestListNumber();
		int oldestListNumber = latestListNumber - 17;

		ArrayList<PlayerList> pl = new ArrayList<PlayerList>();

		for (int iter = oldestListNumber; iter <= latestListNumber; iter++)
		{

			DataFetchHttp http = new DataFetchHttp();
			http.getBaseData();

			PlayerList p = new PlayerList();

			p = http.getPlayerList(iter);

			boolean playerListOk = p.verifyPlayerList();

			if (playerListOk)
			{
				System.out.println("Player list looks OK");
			}
			else
			{
				System.out.println("Player list looks NOT OK " + p.listDate);
				logWriter.println("Player list looks NOT OK " + p.listDate);
			}

			pl.add(p);
		}

		for (PlayerList p : pl)
		{
			if (p.getPlayerListSize() == 0)
			{
				logWriter.println("Size of player list is zero - something went wrong: " + p.getPlayerListDate());
			}
			
		}

		fileWrite.WritePlayerList(pl, fileNameXml);
		logWriter.close();
		}
		catch (Exception e)
		{
			logWriter.println("Exception in main: " + e);
		}
	}

}
