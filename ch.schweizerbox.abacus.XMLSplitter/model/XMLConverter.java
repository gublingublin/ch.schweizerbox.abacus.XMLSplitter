package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;





public class XMLConverter {
	
	private static double xmlSize;
	private static int anzahlXMLElemente;
	private static int anzahlNeuerXML;
	
	
	
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
	public Element getXMLMetaData(File inputFile, String XMLVaterElement, String XMLKindElement) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory fabrik = DocumentBuilderFactory.newInstance();
		Element out = null;
		
		fabrik.setNamespaceAware(true);
		DocumentBuilder documentBauer = fabrik.newDocumentBuilder();
		Document dokument = documentBauer.parse(inputFile);
		dokument.getDocumentElement().normalize();
		
		//		Erstellt eine Liste mit allen Elementen die den Namen des XML-Elements "XMLVaterElement" tragen (überall wo <XMLVaterElement> im XML steht)
		NodeList nodeList = dokument.getElementsByTagName(XMLVaterElement);
		//		Gibt alle Subelemente (Knoten) des XMLVaterelements an der Position 0 aus:
		
//		System.out.println("Beginn Nodelist für **********" +"'XMLVaterElement': " + XMLVaterElement + " / 'XMLKindElement': "+XMLKindElement);
//		System.out.println(XMLVaterElement +" " +nodeList.item(0).getTextContent());
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
//		System.out.println("Ende NodeList------");

		return out;
		
	}
	
	
	
	/**
	 * @param inputFile: Welches XML File soll gelesen werden?
	 * @param XMLVaterElement: Welches Vaterelement soll gelesen werden? (z.B. Paramter)
	 * @return Gibt die Anzahl der enthaltenen Transaktionen / Datensätze zurück.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public int getNumberofXMLElements(File inputFile, String XMLVaterElement) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory fabrik = DocumentBuilderFactory.newInstance();
		String XMLKindElement = "";
		Node out = null;
		
		fabrik.setNamespaceAware(true);
		DocumentBuilder documentBauer = fabrik.newDocumentBuilder();
		Document dokument = documentBauer.parse(inputFile);
		dokument.getDocumentElement().normalize();
		
		NodeList nodeList = dokument.getElementsByTagName(XMLVaterElement);
		anzahlXMLElemente = nodeList.getLength();
	
		return anzahlXMLElemente;
		

	}
	
	
	
	/**
	 * @param inputFile: Welches XML File soll gelesen werden?
	 * @return: Gibt die Grösse des XML in MB oder wenn kleiner als 1MB in KB an.
	 */
	public String getSizeofXML (File inputFile){
		//Grösse des Files in Byte:
		xmlSize = inputFile.length();
		
		//Umrechnen nach MB
		xmlSize = xmlSize / 1024 / 1024;
		
		//Ausgabe als MB oder wenn kleiner als 1MB als KB.
		String out;
		DecimalFormat decimalFormat = new DecimalFormat("###0.0");
		if(xmlSize >= 1){
			out = decimalFormat.format(xmlSize) + " MB";
		} else {
			xmlSize = xmlSize *1024;
			out = decimalFormat.format(xmlSize) + " KB";
		}
		 
		return out;
	}
	
	
	
	/**
	 * @param quelldatei: Welche Datei soll kopiert werden?
	 * @param archivPfad: in welchen Pfad soll sie kopiert werden?
	 * @throws IOException
	 */
	public void copyXMLtoArchiv(String quelldatei, String archivPfad) {
		String quellpfad = new File(quelldatei).getPath();
		File quelldateifile = new File(quelldatei);
		Path quellFile = Paths.get(quellpfad);
		
		LocalDate date = LocalDate.now();
		String datum = date.toString();
		
		Path archivpath = Paths.get(archivPfad+"\\Original_"+datum+"_"+quelldateifile.getName());
		try {
			Files.copy(quellFile, archivpath);
			JOptionPane.showMessageDialog(null, "Das XML wurde erfolgreich kopiert!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Das kopieren hat nicht geklappt! \n"
					+ "Prüfen Sie die Einstellungen! Bereits existierende Originale werden nicht überschrieben und müssen manuell gelöscht / verschoben werden!");
			
			e.printStackTrace();
		}
		
		
	}



	public void splitXML(String quelldatei, String zieldatei, String archivPfad, String auswahlSplitting, String maxGroesse) throws ParserConfigurationException, SAXException, IOException{
		//Prüfung der Eingaben
		File quellFile = new File(quelldatei);
		File zielFile = new File(zieldatei);
		File archivPfadFile = new File(archivPfad);
		String XMLVaterElement = "Transaction"; 
		String XMLKindElement = "Parameter";
		
		
		if(quellFile.exists() == false | archivPfadFile.exists() == false){
			JOptionPane.showMessageDialog(null, "Die Quelldatei oder der Archivpfad existieren nicht! Bitte überprüfen Sie die Einstellungen!");
		} else if(auswahlSplitting == null | maxGroesse.length() < 1){
			JOptionPane.showMessageDialog(null, "Bitte geben Sie an wie Sie das XML aufteilen möchten!");
		} else if(xmlSize == 0){
			JOptionPane.showMessageDialog(null, "Es wurde noch kein XML-File für das Splitting eingelesen!");
			} else {
				
			//Berechnung Anzahl Datensätze pro Datei je nach Auswahl in der Combobox	
			switch (auswahlSplitting) {
			case "Maximale Grösse in MB":
				double anzahlXML = xmlSize / Double.parseDouble(maxGroesse);
				anzahlXML = Math.ceil(anzahlXML);
				anzahlNeuerXML = anzahlXMLElemente / (int)anzahlXML;
				System.out.println(anzahlNeuerXML);
				break;
				
			case "Anzahl Dateien":
				anzahlNeuerXML = anzahlXMLElemente / Integer.parseInt(maxGroesse);
				System.out.println(anzahlNeuerXML);
				break;
				
			default:
				JOptionPane.showMessageDialog(null, "mit den eingegebenen Daten stimmt etwas nicht....!");
				break;
			}
			
			
			//Aufbereitung der XML
			DocumentBuilderFactory fabrik = DocumentBuilderFactory.newInstance();
			Element out = null;
			
			fabrik.setNamespaceAware(true);
			DocumentBuilder documentBauer = fabrik.newDocumentBuilder();
			Document dokument = documentBauer.parse(quellFile);
			dokument.getDocumentElement().normalize();
			
			//		Erstellt eine Liste mit allen Elementen die den Namen des XML-Elements "XMLVaterElement" tragen (überall wo <XMLVaterElement> im XML steht)
			NodeList nodeList = dokument.getElementsByTagName(XMLVaterElement);
			//		Gibt alle Subelemente (Knoten) des XMLVaterelements an der Position 0 aus:
			
			System.out.println("Beginn Nodelist für **********" +"'XMLVaterElement': " + XMLVaterElement + " / 'XMLKindElement': "+XMLKindElement);
			//		In der Liste sind nun alle SubElemente (Knoten) des XMLVaterElement zu finden. 
			//		Die nachstehende Funktion prüft nun welches Element in out geschrieben werden soll.
			//		Mann kann den Namen des XMLKindeElement angeben oder auch nach Elementtyp / Attribut / Text Filtern.
			
//			System.out.println("NodeName: " + nodeList.item(0).getNodeName());
//			System.out.println("NodeType: " + nodeList.item(0).getNodeType());
//			System.out.println("NodeValue: " + nodeList.item(0).getNodeValue());
//			System.out.println("NodeParentNode: " + nodeList.item(0).getParentNode());
//			System.out.println("NodeParentNode.NodeName: " + nodeList.item(0).getParentNode().getNodeName());
//			System.out.println("NodeChildNodes: " + nodeList.item(0).getChildNodes());
////			System.out.println("TextContent: " + nodeList.item(0).getTextContent());
////			System.out.println("NextSibling: " + nodeList.item(0).getNextSibling().getNodeName());
//			System.out.println("Prefix: " + nodeList.item(0).getPrefix());
//			System.out.println("Attribute: " + nodeList.item(0).getAttributes());

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node knoten = nodeList.item(i);
				
				System.out.println("NodeName: " + nodeList.item(i).getNodeName());
				System.out.println("NodeType: " + nodeList.item(i).getNodeType());
				System.out.println("NodeValue: " + nodeList.item(i).getNodeValue());
				System.out.println("Attribute: " + nodeList.item(i).getAttributes());
				System.out.println(i);
			}
				
			
//			for(int i = 0; i<nodeList.getLength(); i++){
//				Node knoten = nodeList.item(i);
//				if(knoten.getNodeName() == XMLKindElement){
//					Element element = (Element) knoten;
//					out = (Element) element.getElementsByTagName(XMLKindElement).item(i);
//					System.out.println("Nodeout: " + out);
//				}
//				
//			}
			
//			for(int i = 0; i<nodeList.getLength(); i++){
//				Node knoten = nodeList.item(i);
//				if(knoten.getNodeType() == Node.ELEMENT_NODE){
//					Element element = (Element) knoten;
//					out = (Element) element.getElementsByTagName(XMLKindElement).item(i);
//					System.out.println(out);
//				}
//				
//			}
			
			System.out.println("Ende NodeList------");

			
			
				
				
			}
	}
	
}


