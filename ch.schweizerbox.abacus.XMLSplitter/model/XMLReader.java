package model;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;





public class XMLReader {
	
	
	
	/** Liefert ein gewünschtes Element aus einem XML File (Funktioniert nur wenn das Vater / Kindelement einmalig im XML vorkommt.)
	 *  (noch nicht geklärt ist, wie auf ein bestimmtes Vater oder Kindelement zugegriffen wird, wenn mehrere vorhanden sind)
	 * @param inputFile: Welches XML File soll gelesen werden?
	 * @param XMLVaterElement: Welches Vaterelement soll gelesen werden? (z.B. Paramter)
	 * @param XMLKindElement: Welches Kindelement ist relevant? (z.B. Version)
	 * @return Element
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Element buildXML(File inputFile, String XMLVaterElement, String XMLKindElement) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory fabrik = DocumentBuilderFactory.newInstance();
		Element out = null;
		
		fabrik.setNamespaceAware(true);
		DocumentBuilder documentBauer = fabrik.newDocumentBuilder();
		Document dokument = documentBauer.parse(inputFile);
		dokument.getDocumentElement().normalize();
		
		//		Erstellt eine Liste mit allen Elementen die den Namen des XML-Elements "XMLVaterElement" tragen (überall wo <XMLVaterElement> im XML steht)
		NodeList nodeList = dokument.getElementsByTagName(XMLVaterElement);
		//		Gibt alle Subelemente (Knoten) des XMLVaterelements an der Position 0 aus:
		System.out.println(XMLVaterElement +" " +nodeList.item(0).getTextContent());
		//		In der Liste sind nun alle SubElemente (Knoten) des XMLVaterElement zu finden. 
		//		Die nachstehende Funktion prüft nun welches Element in out geschrieben werden soll.
		//		Mann kann den Namen des XMLKindeElement angeben oder auch nach Elementtyp / Attribut / Text Filtern.
		for(int i = 0; i<nodeList.getLength(); i++){
			Node knoten = nodeList.item(i);
			if(knoten.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) knoten;
				out = (Element) element.getElementsByTagName(XMLKindElement).item(i);
			}
			
		}
		System.out.println("Resultat Funktion " + out.getTextContent());
		System.out.println("------");
		return out;
		
	}
	
	
	/** Liefert alle Kind-Elemente des angegebenen Vater Elements. 
	 * @param inputFile: Welches XML File soll gelesen werden?
	 * @param XMLVaterElement: Welches Vaterelement soll gelesen werden? (z.B. Paramter)
	 * @param XMLKindElement: Welches Kindelement ist relevant? (z.B. Version)
	 * @return Element
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void getXMLElements(File inputFile, String XMLVaterElement) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory fabrik = DocumentBuilderFactory.newInstance();
		String XMLKindElement = "";
		Node out = null;
		
		fabrik.setNamespaceAware(true);
		DocumentBuilder documentBauer = fabrik.newDocumentBuilder();
		Document dokument = documentBauer.parse(inputFile);
		dokument.getDocumentElement().normalize();
		
		//		Erstellt eine Liste mit allen Elementen die den Namen des XML-Elements "XMLVaterElement" tragen (überall wo <XMLVaterElement> im XML steht)
		NodeList nodeList = dokument.getElementsByTagName(XMLVaterElement);
//		Gibt alle Subelemente (Knoten) des XMLVaterelements an der Position 0 aus:
//		System.out.println(XMLVaterElement +" " +nodeList.item(0).getTextContent());
//		System.out.println("------------");
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			System.out.println("getNodeName: " + nodeList.item(i).getNodeName());
			System.out.println("getNodeValue: " + nodeList.item(i).getNodeValue());
			System.out.println("hasChildNodes: " + nodeList.item(i).hasChildNodes());
			System.out.println("getChildNodes.getlength: " + nodeList.item(i).getChildNodes().getLength());
			System.out.println("******");
			
			int anzahlNodes = nodeList.item(i).getChildNodes().getLength();
						
			for (int j = 0; j < anzahlNodes; j++) {
				System.out.println("Nummer "+ j);
				System.out.println("NodeListgetText: " + nodeList.item(i).getChildNodes().item(j).getTextContent());
				System.out.println("NodeListgetNodeName: " + nodeList.item(i).getChildNodes().item(j).getNodeName());
				System.out.println("---------------------");
			}
			
						
		}
		
		//		In der Liste sind nun alle SubElemente (Knoten) des XMLVaterElement zu finden. 
		//		Die nachstehende Funktion prüft nun welches Element in out geschrieben werden soll.
		//		Mann kann den Namen des XMLKindeElement angeben oder auch nach Elementtyp / Attribut / Text Filtern.
//		for(int i = 0; i<nodeList.getLength(); i++){
//			System.out.println(nodeList.getLength());
//			Node knoten = nodeList.item(i);
//			System.out.println(knoten);
//			String test = nodeList.item(i).toString();
//			System.out.println("Testout: " + test);
			
//			if(knoten.getNodeName() == "Transaction id"){
//				Element element = (Element) knoten;
//				out = element.getFirstChild();
//				System.out.println(out);
//			}
			
//		}
//		System.out.println("Resultat Funktion " + out.getTextContent());
//		return out;
		
	}
	
	
}


