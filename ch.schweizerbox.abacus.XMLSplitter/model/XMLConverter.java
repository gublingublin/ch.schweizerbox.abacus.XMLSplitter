package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		
		System.out.println("Beginn Nodelist für **********" +"'XMLVaterElement': " + XMLVaterElement + " / 'XMLKindElement': "+XMLKindElement);
		
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
		System.out.println("Ende NodeList------");

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
		int anzahlXMLElemente = nodeList.getLength();
	
		return anzahlXMLElemente;
		

	}
	
	
	
	/**
	 * @param inputFile: Welches XML File soll gelesen werden?
	 * @return: Gibt die Grösse des XML in MB an
	 */
	public double getSizeofXML (File inputFile){
		double out = inputFile.length();
		out = out / 1024 / 1024;
		
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
}


