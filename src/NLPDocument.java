import java.util.*;

import javax.xml.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name="document") 
public class NLPDocument {
	int documentId;
	String documentName;
	ArrayList<Sentence> SentenceList;
	
	public NLPDocument(){
		super();
	 }
//	@XmlElement
	public void setDocumntId(int documntId) {
		  this.documentId = documentId;
	}
	 public int getDocumntId() {
		  return documentId;
	 }
//		@XmlElement
	public void setDocumentName(String documentName) {
		  this.documentName = documentName;
		 }
	public String getDocumntName() {
		  return documentName;
	 }

	@XmlElement(name = "sentence")
	public void setListOfSentence(ArrayList<Sentence> SentenceList) {
		  this.SentenceList = SentenceList;
		 }
    public ArrayList<Sentence> getListOfSentence() {
		  return SentenceList;
    }

}
