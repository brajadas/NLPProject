import java.util.*;
import javax.xml.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class Sentence {
	int id;
	String textstring;
	int wordcount;
	ArrayList<Words> listOfWords;

	public Sentence() {
		super();
	}

	// @XmlElement
	public void setTextString(String textstring) {
		this.textstring = textstring;
	}

	public String getTextString() {
		return textstring;
	}

	// Assign the id of the Sentence to the variable id.
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/* Assign the wordcount to the variable wordcount. */
	@XmlElement
	public void setWordCount(int wordcount) {
		this.wordcount = wordcount;
	}

	public int getWordCount() {
		return wordcount;
	}

	
	@XmlElement(name = "word")
	public void setListOfWords(ArrayList<Words> listOfWords) {
		this.listOfWords = listOfWords;
	}

	public ArrayList<Words> getListOfWords() {
		return listOfWords;
	}

}
