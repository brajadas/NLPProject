import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(NLPDocument.class)
public class Words {
	String wordname;
	String partsOfspeech;

	public Words() {
	}

	public void setWordName(String wordname) {
		this.wordname = wordname;
	}

	public String getWordName() {
		return wordname;
	}

	@XmlElement(name = "partsOfSpeech")
	public void setPos(String pos) {
		this.partsOfspeech = pos;
	}

	public String getPos() {
		return partsOfspeech;
	}

}
