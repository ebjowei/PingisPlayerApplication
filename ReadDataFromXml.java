package src;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadDataFromXml {

	public static void main(String argv[]) 
	{

		try {

			System.out.println("Börja hämta data.");
			
			File fXmlFile = new File("C:\\Users\\Bjorn\\PingisXml\\PlayerLists.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("List");

			ArrayList<PlayerList> pll = new ArrayList<PlayerList>();

			for (int tempNodeList = 0; tempNodeList < nList.getLength(); tempNodeList++) {

				PlayerList currentPlayerList = new PlayerList(); 

				Node nNode = nList.item(tempNodeList);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{

					Element eElement = (Element) nNode;

					currentPlayerList.listDate = eElement.getAttribute("List_date");
					currentPlayerList.listNumber = Integer.parseInt(eElement.getAttribute("List_number"));

					NodeList nListp = eElement.getElementsByTagName("Player");

					for (int tempNodePlayer = 0; tempNodePlayer < nListp.getLength(); tempNodePlayer++) 
					{

						Node nNodep = nListp.item(tempNodePlayer);
			
						if (nNode.getNodeType() == Node.ELEMENT_NODE) 
						{
							Element eElementp = (Element) nNodep;

							Player p = new Player();

							p.firstName = eElementp.getElementsByTagName("Firstname").item(0).getTextContent();
							p.lastName = eElementp.getElementsByTagName("Lastname").item(0).getTextContent();
							p.club = eElementp.getElementsByTagName("Club").item(0).getTextContent();
							p.points = Integer.parseInt(eElementp.getElementsByTagName("Points").item(0).getTextContent());
							p.rank = Integer.parseInt(eElementp.getElementsByTagName("Rank").item(0).getTextContent()); 
							p.level = eElementp.getElementsByTagName("Level").item(0).getTextContent(); 
							//p.id = Integer.parseInt(eElementp.getElementsByTagName("id").item(0).getTextContent()); 
							p.year = Integer.parseInt(eElementp.getElementsByTagName("Year").item(0).getTextContent()); 

							currentPlayerList.addToPlayerList(p);
						}
					}
				} 

				pll.add(currentPlayerList);
			}
			
			System.out.println("Data hämtat.");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

}
