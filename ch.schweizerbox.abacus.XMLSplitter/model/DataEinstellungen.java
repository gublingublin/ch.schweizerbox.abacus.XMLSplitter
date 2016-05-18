package model;
import java.io.Serializable;

public class DataEinstellungen implements Serializable {
	private static final long serialVersionUID = 1268753340532154804L;
	
	// ----------------------------------------------Felder---------------------------------------------------
	private String quellDatei = "";
	private String zielDatei = "";
	private String archivOrdner = "";
	private Boolean datenArchivieren;
	
	
	
	// ----------------------------------------------Konstruktor----------------------------------------------

	
	
	
	// ----------------------------------------------Funktionen-----------------------------------------------


	
	
	//----------------------------------------------Getter- / Setter------------------------------------------
	public String getQuellDatei() {
		return quellDatei;
	}
	public void setQuellDatei(String quellDatei) {
		this.quellDatei = quellDatei;
	}
	public String getZielDatei() {
		return zielDatei;
	}
	public void setZielDatei(String zielDatei) {
		this.zielDatei = zielDatei;
	}
	public String getArchivOrdner() {
		return archivOrdner;
	}
	public void setArchivOrdner(String archivOrdner) {
		this.archivOrdner = archivOrdner;
	}
	public Boolean getDatenArchivieren() {
		return datenArchivieren;
	}
	public void setDatenArchivieren(Boolean datenArchivieren) {
		this.datenArchivieren = datenArchivieren;
	}
}
