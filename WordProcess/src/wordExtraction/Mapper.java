package wordExtraction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {
	private String filename;
	private List<MyPair<String,Integer>> pairs=new ArrayList<MyPair<String,Integer>>();
	
	public Mapper(){
		
	}
    public Mapper(String filename) {
        this.filename = filename;
    }
	
    
	public List<MyPair<String, Integer>> getPairs() {
		return pairs;
	}
	public void setPairs(List<MyPair<String, Integer>> pairs) {
		this.pairs = pairs;
	}
	public List<GroupByPair<String, Integer>> getMapper(List<MyPair<String,Integer>> pairs){
		List<GroupByPair<String, Integer>> groupByPairs=new ArrayList<GroupByPair<String, Integer>>();
		int i=0;
		int sizePairs=pairs.size();
		int t=0;
		boolean flag=true;
		while (i<sizePairs){
			MyPair<String, Integer> p=pairs.get(i);
			String key=p.getKey();
			List<Integer> values=new ArrayList<Integer>();
			values.add(p.getV());
			for (int k=i+1;k<sizePairs;k++){
				MyPair<String, Integer> p1=pairs.get(k);
				String key1=p1.getKey();
				if (key1.equals(key)){
					values.add(p1.getV());
					flag=true;
				}
				else{	
					flag=false;
					i=k;
					break;
				}
			}	
			GroupByPair<String, Integer> groupByPair=new GroupByPair(key, values);
			groupByPairs.add(groupByPair);
			if (i==sizePairs-1){
				if (flag==false){
					MyPair<String, Integer> p2=pairs.get(i);
					String key2=p2.getKey();
					List<Integer> values2=new ArrayList<Integer>();
					values2.add(p.getV());
					GroupByPair<String, Integer> groupByPair2=new GroupByPair(key2, values2);
					groupByPairs.add(groupByPair2);
				}
				break;
			}
		}
		
		return groupByPairs;
	}

    public List<MyPair<String, Integer>> map() throws IOException {
        
        List<String> listWords = Files.lines(Paths.get(this.filename))
                .map(line -> line.replace("-", " ").split("\\s"))
                .flatMap(Arrays::stream)
                .filter(word -> {
                    if (!word.isEmpty() && word.matches("[^0-9_]+")) {
                        final int periodIndex = word.indexOf(".");
                        return periodIndex < 0 || periodIndex == word.length() - 1;
                    }
                    return false;
                })
                .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase())
                .collect(Collectors.toList());
        
    	Map<String, Integer> mapped = new HashMap<>();
        for (String word:listWords){

        	if (!mapped.containsKey(word))
            	mapped.put(word, 1);
        	else
        		mapped.put(word, mapped.get(word) + 1);
        }

        List<MyPair<String, Integer>> list = new ArrayList<MyPair<String, Integer>>();
        for (String word: mapped.keySet()){
        	MyPair<String, Integer> p=new MyPair(word, mapped.get(word));
        	list.add(p);
        }
        								       		
      //  list.sort(Comparator.comparing(MyPair::getKey));
        return list;
        
    }

     
	//add more -----
    public List<MyPair<String, Integer>> map1() throws IOException {
        List<MyPair<String, Integer>> list = Files.lines(Paths.get(this.filename))
                .map(line -> line.replace("-", " ").split("\\s"))
                .flatMap(Arrays::stream)
                .filter(word -> {
                    if (!word.isEmpty() && word.matches("[^0-9_]+")) {
                        final int periodIndex = word.indexOf(".");
                        return periodIndex < 0 || periodIndex == word.length() - 1;
                    }
                    return false;
                })
                .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase())
                .map(word -> new MyPair<>(word, 1))
                .collect(Collectors.toList());
        list.sort(Comparator.comparing(MyPair::getKey));
        return list;
    }
    
    public void updatePairs(){
    	try {
			pairs=map();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void writePairsToFile(String fileName){
		try {
			pairs =map();
			BufferedWriter bw = null;
			FileWriter fw = null;
	
			
				fw = new FileWriter(fileName);
				bw = new BufferedWriter(fw);
	
				for (MyPair<String,Integer> pair: pairs){
	
						bw.write("("+pair.getKey()+","+pair.getV()+")\n");
						System.out.println("("+pair.getKey()+","+pair.getV()+")");
	
				}
				bw.close();
				fw.close();
				System.out.println("DoneMap");

			} catch (Exception e) {
	
				e.printStackTrace();
	
			} 
	}
}
