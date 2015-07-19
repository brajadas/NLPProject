import java.text.BreakIterator;
import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class NERThreadPool {
	public static void main(String[] argv) throws Exception {

		File[] files = new File("nlp_data").listFiles();

		ArrayList<String> wordList = readTrainingProperNouns();

		List<MyCallable> inputs = new ArrayList<MyCallable>();
		for (File file : files) {
		    inputs.add(new MyCallable(wordList, file));
		}
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
	    List<Future<Object>> futures = executorService.invokeAll(inputs);
	    
	    for (Future<Object> future : futures)
	    {
	      future.get();
	    }
	    executorService.shutdown();
	}

	static void processInputFile(ArrayList<String> wordList, File file)
			throws FileNotFoundException, IOException {
		ArrayList<String> fileText = new ArrayList<String>();
		FileReader fread = new FileReader(file);
		BufferedReader bread = new BufferedReader(fread);
		String sread;
		int fileId = 1;
		while ((sread = bread.readLine()) != null) {
			fileText.add(sread);
			System.out.println(sread);

			// creating document object
			NLPDocument nlpdoc = new NLPDocument();
			nlpdoc.setDocumntId(fileId++);
			nlpdoc.setDocumentName("Testing data");

			ArrayList<Sentence> sentenceArr = new ArrayList<Sentence>();
			ArrayList<Words> wordsArr = new ArrayList<Words>();

			String str1 = sread;
			int lastindex = 0;
			int count = 0;
			int wordcount = 0;
			String text = "";
			int index = 0;
			BreakIterator iterator = BreakIterator
					.getSentenceInstance(Locale.US);
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
					words.partsOfspeech = "other";
					for (int i = 0; i < wordList.size(); i++) {
						String value = wordList.get(i);
						if (value.equals(tokenMod)) {
							words.partsOfspeech = "noun";
						}

					}
					wordsArr.add(words);

				}
				sentence.setWordCount(wordcount);
				sentence.setListOfWords(wordsArr);
				sentenceArr.add(sentence);
				wordcount = 0;
			}
			nlpdoc.setListOfSentence(sentenceArr);

			try {
				// create JAXB context and initializing Marshaller
				JAXBContext jaxbContext = JAXBContext
						.newInstance(Sentence.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				// for getting nice formatted output
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						Boolean.TRUE);
				jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
				// specify the location and name of xml file to be created
				File XMLfile = new File("POSTagging" + file.getName() + ".xml");
				// File XMLfile = new File(file+".xml");
				jaxbMarshaller.marshal(nlpdoc, XMLfile);
				// Writing to console
				jaxbMarshaller.marshal(nlpdoc, System.out);
				// Writing to XML file
			} catch (JAXBException e) {
				// some exception occured
				e.printStackTrace();
			}

		}
		fread.close();
	}

	private static ArrayList<String> readTrainingProperNouns()
			throws FileNotFoundException, IOException {
		ArrayList<String> wordList = new ArrayList<String>();
		FileReader fr = new FileReader("NER.txt");
		BufferedReader br = new BufferedReader(fr);
		String s;
		while ((s = br.readLine()) != null) {
			// System.out.println(s);
			StringTokenizer st = new StringTokenizer(s);
			while (st.hasMoreTokens()) {
				String wordtoken = st.nextToken();
				// System.out.println(wordtoken);
				wordList.add(wordtoken);
			}
		}
		fr.close();
		return wordList;
	}
}

class MyCallable implements Callable<Object> {
	ArrayList<String> wordList;
	File file;
	public MyCallable(ArrayList<String> wordList, File file) {
		this.wordList = wordList;
		this.file = file;
	}

	@Override
	public Object call() throws Exception {
		NERThreadPool.processInputFile(wordList, file);
		return null;
	}
}