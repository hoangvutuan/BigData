package wordExtraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.lang.*;

public class MyText {
	List<MyPair<String,Integer>> pairs=new ArrayList<MyPair<String,Integer>>();
	String text=null;	
	List<String> sentences=new ArrayList<String>();
	String output=null;
	List<GroupByPair<String, Integer>> groupByPairs=new ArrayList<GroupByPair<String,Integer>>();
	
	public  boolean isWord(String word){
		if (word.matches("[a-z]+"))
			return true;
		else
		{
			if (word.matches("\".*\""))
				return true;
			else
				return false;
		}
	}
	
	public List<String>  filterTrueWord(List<String> words){
		List<String> trueWords=new ArrayList<String>();
		for (String word:words){
			if (word.indexOf(".")==word.length()-1)
				word=word.replaceAll("\\.", "");
			if (isWord(word)){
				trueWords.add(word);
			}
		}
		return trueWords;
		
	}
	
	public void readFromFile(String fileName) throws IOException{

		try {
			BufferedReader br = null;
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    
		    
		    while (line != null) {
		    	List<MyPair<String,Integer>> pairsSentence=new ArrayList<MyPair<String,Integer>>();
		        sb.append(line);
		        sentences.add(line);
		        line=line.toLowerCase();
		        
		        //remove . at the end
		      // line=line.substring(0, line.length()-1);
		        line=line.replaceAll("\"", "");
		       // line=line.replaceAll("\\. ", " ");
		        
		        String[] words_=line.split("-|\\s|,");
		        

		        List<String> words = new ArrayList<String>(Arrays.asList(words_));
		        words=filterTrueWord(words);
		        
		        for (int i=0;i<words.size();i++){
		        	int count=1;
		        	MyPair<String, Integer> p=new MyPair(words.get(i),count);
		        	pairsSentence.add(p);	        		
		        }
		        sb.append(System.lineSeparator());
		        for (MyPair<String,Integer> pair:pairsSentence){
		        	pairs.add(pair);
		        }
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    text=everything;
		    br.close();
		    

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    //sort
	    pairs.sort((MyPair p1, MyPair p2)->p1.compareTo(p2));
		// pairs.sort( (MyPair<String,Integer> p1, MyPair<String,Integer> p2)->p1.getU()-p2.getU());
	}
	
	public void writeGroupByPairsToFile(String fileName){
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			int count=0;
			int size=pairs.size();
			bw.write("Mapper Output\n");
			System.out.println("Mapper Output");
			for (MyPair<String,Integer> pair: pairs){
					bw.write("<"+pair.getKey()+","+pair.getV()+">\n");
					System.out.println("<"+pair.getKey()+","+pair.getV()+">\n");
			}
			
			Mapper mapper=new Mapper();
			groupByPairs=mapper.getMapper(pairs);
			
			bw.write("Reducer Input\n");
			System.out.println("Reducer Input");
			for (GroupByPair<String,Integer> p:groupByPairs){
				bw.write("<"+p.getKey()+", ["); 
				System.out.print("<"+p.getKey()+", [");
				List<Integer> values=new ArrayList<Integer>();
				values=p.getValues();
				for (int i=0;i<values.size()-1;i++){
					Integer v=values.get(i);
					bw.write(v+","); 
					System.out.print(v+",");
				}
				bw.write(values.get(values.size()-1)+"] >\n");
				System.out.println(values.get(values.size()-1)+"] >");
			}
			
			bw.write("Reducer Output\n");
			System.out.println("Reducer Output");
			for (GroupByPair<String,Integer> p:groupByPairs){
				bw.write("<"+p.getKey()+", "); 
				System.out.print("<"+p.getKey()+", ");
				Reducer r=new Reducer();
				int t=r.reduce(p);
				bw.write(t+" >\n");
				System.out.println(t+" >");
			}
			
			bw.close();
			fw.close();
			System.out.println("Done");
			System.out.println(text);

		} catch (Exception e) {

			e.printStackTrace();

		} 	
	}
	
	public void writePairsToFile(String fileName){
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			int count=0;
			int size=pairs.size();
			for (MyPair<String,Integer> pair: pairs){
				if (count<size-1){
					bw.write("("+pair.getKey()+","+pair.getV()+")\n");
					System.out.println("("+pair.getKey()+","+pair.getV()+")");
				}
				else{
					bw.write("("+pair.getKey()+","+pair.getV()+")");
					System.out.print("("+pair.getKey()+","+pair.getV()+")");		
				}
				count++;
			}
			bw.close();
			fw.close();
			System.out.println("Done");
			System.out.println(text);

		} catch (Exception e) {

			e.printStackTrace();

		} 
	}
}
