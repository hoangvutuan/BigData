package wordExtraction;

import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyText myText=new MyText();
		String fileName="input.txt";
		try {
			myText.readFromFile(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fileName="output0.txt";
		myText.writePairsToFile(fileName);
		
		fileName="output0A.txt";
		myText.writeGroupByPairsToFile(fileName);
		
		fileName="outputIS_0.txt";
        Mapper mapper1 = new Mapper("InputSplit_0.txt");
        mapper1.writePairsToFile(fileName);

        fileName="output.txt";
        WordCount wc=new WordCount();
        wc.writeAllPairsToFile(fileName);
	}

}
