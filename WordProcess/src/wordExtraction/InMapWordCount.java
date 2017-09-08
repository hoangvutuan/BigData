package wordExtraction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class InMapWordCount {
	private int m=3;
	private int r=4;
	private List<String> fileNames=new ArrayList<String>();
	private List<Mapper> mappers=new ArrayList<Mapper>();
	private List<Reducer> reducers=new ArrayList<Reducer>();

	public void initReducers(){
		for (int i=0;i<r;i++){
			Reducer reducer=new Reducer();
			reducers.add(reducer);
		}
	}
	public int getPartition(String key){
		return (int) key.hashCode() % r;
	}
	
	public void updatePairsForReducers(BufferedWriter bw){
		System.out.println("Start updatePairsForReducers");
		
		initReducers();
		try{
			int count=0;
			for (Mapper m:mappers){
				System.out.println("Mapper "+count+ " Output");
				List<MyPair<String,Integer>> pairs=new ArrayList<MyPair<String,Integer>>();
				pairs=m.getPairs();
				//System.out.print("Pairs send from Mapper "+count);
				for (MyPair<String,Integer> pair: pairs){	
	
				//		
						int partitionNum=getPartition(pair.getKey());
						reducers.get(partitionNum).addPairToPairList(pair);
						
						System.out.println("Pairs send from Mapper "+count+" to Reducer "+ partitionNum);
						System.out.println("<"+pair.getKey()+","+pair.getV()+">");
						
						bw.write("Pairs send from Mapper "+count+" to Reducer "+ partitionNum+"\n");
						bw.write("<"+pair.getKey()+","+pair.getV()+"> \n");
				}
				count++;
			}
		}
		catch (Exception e){
			System.out.println("Error in updatePairsForReducers.");
		}
		System.out.println("End updatePairsForReducers");
	}
	
	public void writeAllPairsToFile(String fileName){
		fileNames.add("InputSplit_0.txt");
		fileNames.add("InputSplit_1.txt");
		fileNames.add("InputSplit_2.txt");
		
		System.out.println("start writeAllPairsToFile!");
		for (int i=0;i<m;i++){
			Mapper m=new Mapper(fileNames.get(i));
			m.updatePairs();
			mappers.add(m);
		}
		
		try {			
			BufferedWriter bw = null;
			FileWriter fw = null;			
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			
			//write Mapper Output of all mappers
			int count=0;
			for (Mapper m:mappers){
				bw.write("Mapper "+count+ " Output\n");
				System.out.println("Mapper "+count+ " Output");
				List<MyPair<String,Integer>> pairs=new ArrayList<MyPair<String,Integer>>();
				pairs=m.getPairs();
				for (MyPair<String,Integer> pair: pairs){	
						bw.write("("+pair.getKey()+","+pair.getV()+")\n");
						System.out.println("("+pair.getKey()+","+pair.getV()+")");
	
				}
				count++;
			}
						
			updatePairsForReducers(bw);
			
			//create input for all reducers after update pairs
			//update updateGroupByPairs
			for (int i=0;i<r;i++){
				Reducer reducer=reducers.get(i);
				reducer.updateGroupByPairs();
				
				List<GroupByPair<String, Integer>> groupByPairs=new ArrayList<GroupByPair<String,Integer>>();
				groupByPairs=reducer.getGroupByPairs();
				
				bw.write("Reducer Input "+i+"\n");
				System.out.println("Reducer Input "+i+":");
				reducer.writeGroupByPairsOut(bw);

			}
			
			for (int i=0;i<r;i++){
				Reducer reducer=reducers.get(i);							
				bw.write("Reducer Output "+i+"\n");
				System.out.println("Reducer Output "+i+":");
				reducer.writeGroupByPairsReducedOut(bw);

			}
			
			bw.close();
			fw.close();
			System.out.println("Done writeAllPairsToFile");

		} catch (Exception e) {

			e.printStackTrace();

		} 
	}
}
