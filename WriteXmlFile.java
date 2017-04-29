package src;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXmlFile {

	public WriteXmlFile() 
	{
	}

	public void WritePlayerList(ArrayList<PlayerList> playerListList, String fileName)
	{
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root element List
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("XML_Player_List");

			doc.appendChild(rootElement);

			for (PlayerList pl : playerListList)
			{
				System.out.println("Starting to build list: " + pl.getPlayerListNumber());

				// List element
				Element list = doc.createElement("List");
				rootElement.appendChild(list);

				System.out.println("Halvvägs");
				
				// Set number on List element
				list.setAttribute("List_number", String.valueOf(pl.getPlayerListNumber()));

				// Set date on List element
				list.setAttribute("List_date", pl.getPlayerListDate());

				// Go through all players and create tree
				for (Player iterPlayer : pl.getPlayerList()) 
				{

					// Player elements
					Element player = doc.createElement("Player");
					list.appendChild(player);

					// Set id on player
					player.setAttribute("id", String.valueOf(iterPlayer.id));

					// Set first name on player
					Element firstname = doc.createElement("Firstname");
					firstname.appendChild(doc.createTextNode(iterPlayer.firstName));
					player.appendChild(firstname);

					// Set last name on player
					Element lastname = doc.createElement("Lastname");
					lastname.appendChild(doc.createTextNode(iterPlayer.lastName));
					player.appendChild(lastname);

					// Set club on player
					Element club = doc.createElement("Club");
					club.appendChild(doc.createTextNode(iterPlayer.club));
					player.appendChild(club);

					// Set points on player
					Element points = doc.createElement("Points");
					points.appendChild(doc.createTextNode(String.valueOf(iterPlayer.points)));
					player.appendChild(points);

					// Set rank on player
					Element rank = doc.createElement("Rank");
					rank.appendChild(doc.createTextNode(String.valueOf(iterPlayer.rank)));
					player.appendChild(rank);

					// Set year on player
					Element year = doc.createElement("Year");
					year.appendChild(doc.createTextNode(String.valueOf(iterPlayer.year)));
					player.appendChild(year);

					// Set level on player
					Element level = doc.createElement("Level");
					level.appendChild(doc.createTextNode(String.valueOf(iterPlayer.level)));
					player.appendChild(level);
				}
				
				System.out.println("List built");
			}
			
			System.out.println("All lists built");
			
			/*
			try
			{
				docBuilder.parse(new InputSource(fileName));
			}
			catch (Exception e)
			{
				System.out.println("Seems Xml format is NOT OK: " + e);
			}
			 */
			System.out.println("Starting to write to file");

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));


			transformer.transform(source, result);

			System.out.println("File saved!");

		} 
		catch (Exception e) 
		{

		} 

	}
}