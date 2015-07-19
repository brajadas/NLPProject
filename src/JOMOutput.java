import java.text.BreakIterator;
import java.io.*;
import java.util.*;
import javax.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JOMOutput {
	
	public static void main(String[] argv) throws Exception {
		
		NLPDocument nlpdoc = new NLPDocument();
		nlpdoc.setDocumntId(1);
		nlpdoc.setDocumentName("Training data");

		ArrayList<Sentence> sentenceArr = new ArrayList<Sentence>();
		ArrayList<Words> wordsArr = new ArrayList<Words>();
		String str1 = readFile();
		int lastindex = 0;
		int count = 0;
		int wordcount = 0;
		String text = "";
		int index = 0;
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(str1);
		for (index = iterator.first(); index != BreakIterator.DONE; index = iterator
				.next()) {

			text = str1.substring(lastindex, index);
			Sentence sentence = new Sentence();
			sentence.setTextString(text);
			sentence.setId(count);
			lastindex = index;
			count++;

			StringTokenizer st = new StringTokenizer(text);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				String tokenMod = token.replaceAll("[^a-zA-Z0-9]", "");
				wordcount++;
				Words words = new Words();
				words.wordname = tokenMod;
				wordsArr.add(words);

			}
			sentence.setWordCount(wordcount);
			sentence.setListOfWords(wordsArr);
			sentenceArr.add(sentence);
			wordcount = 0;
		}
		nlpdoc.setListOfSentence(sentenceArr);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Sentence.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			File XMLfile = new File("SentenceCollection.xml");
			jaxbMarshaller.marshal(nlpdoc, XMLfile);
			jaxbMarshaller.marshal(nlpdoc, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private static String readFile() throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader("nlp_data.txt"));
		String string = "";
		String line;
		while((line = br.readLine()) != null){
			string += line;
		}
		br.close();
		return string;
	}
}