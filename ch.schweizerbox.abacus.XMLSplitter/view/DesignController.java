package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import controller.Listener;
import controller.RBFileChooser;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DataEinstellungen;
import model.SaveLoadData;
import model.XMLConverter;


public class DesignController {

	// ----------------------------------------------Felder---------------------------------------------------
	@FXML private Button BTN_Speichern;
	@FXML private Button BTN_Testbutton;
	@FXML private TextField TF_Quelldatei;
	@FXML public static TextField TF_Testfeld;
	@FXML private TextField TF_Zieldatei;
	@FXML private TextField TF_Archivordner;
	@FXML private CheckBox CHB_DatenArchivieren; 
	@FXML private VBox VB_Archivordner;
	@FXML private Text T_Application;
	@FXML private Text T_ID;
	@FXML private Text T_MapID;
	@FXML private Text T_Version;
	@FXML private ComboBox<String> CBB_Aktion1;
	@FXML private ComboBox<String> CBB_XMLElement1;
	@FXML private TextField TF_Text1;
	@FXML private Text T_AnzahlDaten;
	@FXML private Text T_GroesseDatei;
	SaveLoadData saveLoadData = new SaveLoadData();

	
	// -----------------------------------------------Listener------------------------------------------------
	
	
    public void initialize() throws FileNotFoundException, ClassNotFoundException, IOException { 
//		System.out.println("hallo welt");
		TF_Quelldatei.setText(saveLoadData.leseDaten().getQuellDatei());
		TF_Zieldatei.setText(saveLoadData.leseDaten().getZielDatei());
		TF_Archivordner.setText(saveLoadData.leseDaten().getArchivOrdner());
		CHB_DatenArchivieren.setSelected(saveLoadData.leseDaten().getDatenArchivieren());
		setArchivordnervisible();
				
		CBB_Aktion1.setItems(model.ComboBox.aktionen);
	}
	
	
	
	
	public static void listener(){
		
	
	InvalidationListener iv = (evt) -> {
		if(!TF_Testfeld.isFocused()){
			System.out.println("testfocus");
		}
	};
	System.out.println("focus läuft");
	TF_Testfeld.focusedProperty().addListener(
			new WeakInvalidationListener(iv)
			);
	
	
	}
	

	
	
	// ----------------------------------------------Funktionen-----------------------------------------------
	public void test() throws ParserConfigurationException, SAXException, IOException{
		XMLConverter xmlcopy = new XMLConverter();
		xmlcopy.copyXMLtoArchiv(TF_Quelldatei.getText(), TF_Archivordner.getText());
	}
	
	public void XMLEinlesen() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException{
		XMLConverter xmlreader = new XMLConverter();
		// Welche Daten werden benötigt?
		File inputFile = new File(TF_Quelldatei.getText());
		Element applikation = xmlreader.getXMLMetaData(inputFile, "Parameter", "Application");
		Element iD = xmlreader.getXMLMetaData(inputFile, "Parameter", "Id");
		Element mapId = xmlreader.getXMLMetaData(inputFile, "Parameter", "MapId");
		Element version = xmlreader.getXMLMetaData(inputFile, "Parameter", "Version");
		int anzahlXMLElements = xmlreader.getNumberofXMLElements(inputFile, "Transaction");
		double groesseDatei = xmlreader.getSizeofXML(inputFile);
		DecimalFormat decimalFormat = new DecimalFormat("###0.000");
				
		
		//Schreibt die Daten in die Textfelder auf dem Hauptmenu
		T_Application.setText(applikation.getTextContent());
		T_ID.setText(iD.getTextContent());
		T_MapID.setText(mapId.getTextContent());
		T_Version.setText(version.getTextContent());
		T_AnzahlDaten.setText(String.valueOf(anzahlXMLElements));
		T_GroesseDatei.setText(decimalFormat.format(groesseDatei));
		
	}
	
	
	public void saveDataEinstellungen() throws FileNotFoundException, IOException{
		saveLoadData.speichereDaten(TF_Quelldatei.getText(), TF_Zieldatei.getText(), TF_Archivordner.getText(), CHB_DatenArchivieren.isSelected());
		JOptionPane.showMessageDialog(null, "Die Daten wurden gespeichert.");
//		System.out.println(TF_Quelldatei.getText());
	}

	
	public void openFileChooserQuelldatei(){
		RBFileChooser rbFileChooser = new RBFileChooser(TF_Quelldatei, 1);
	}
	
	
	public void openFileChooserZieldatei(){
		RBFileChooser rbFileChooser = new RBFileChooser(TF_Zieldatei, 2);
	}
	
	
	public void openFileChooserArchivordner(){
		RBFileChooser rbFileChooser = new RBFileChooser(TF_Archivordner, 2);
	}
	
	
	public void setArchivordnervisible(){
		boolean aktiv = CHB_DatenArchivieren.isSelected();
		if(aktiv == false){
			VB_Archivordner.setVisible(false);
		} else {
			VB_Archivordner.setVisible(true);
		}
		}

	// ----------------------------------------------getter/setter---------------------------------------------------
	
	public TextField getTF_Quelldatei() {
		return TF_Quelldatei;
	}

	public void setTF_Quelldatei(TextField tF_Quelldatei) {
		TF_Quelldatei = tF_Quelldatei;
	}


	
	
}
