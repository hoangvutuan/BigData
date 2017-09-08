package wordExtraction;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Reducer {
	private List<MyPair<String,Integer>> pairs=new ArrayList<MyPair<String,Integer>>();
	List<GroupByPair<String, Integer>> groupByPairs=new ArrayList<GroupByPair<String,Integer>>();
	
	public Reducer(){
		
	}
	
	public void updateGroupByPairs(){
		
		//sort 
	//	pairs.sort(Comparator.comparing(MyPair::getKey));
		pairs.sort((MyPair<String,Integer> p1, MyPair<String,Integer> p2)->p1.compareTo(p2));
		groupByPairs=createReducerInput();
	}
	
	public List<MyPair<String, Integer>> getPairs() {
		return pairs;
	}

	public void addPairToPairList(MyPair<String,Integer> pair){

			pairs.add(pair);

			
	}

	public void setPairs(List<MyPair<String, Integer>> pairs) {
		this.pairs = pairs;
	}


	public List<GroupByPair<String, Integer>> getGroupByPairs() {
		return groupByPairs;
	}


	public void setGroupByPairs(List<GroupByPair<String, Integer>> groupByPairs) {
		this.groupByPairs = groupByPairs;
	}


	public int reduce(GroupByPair<String, Integer> pair ){
		
		int sum=0;
		List<Integer> values=new ArrayList<Integer>();
		values=pair.getValues();
		for (Integer x:values){
			sum+=x;
		}
		return sum;		
	}
	
    public Reducer(List<MyPair<String, Integer>> keyValuePairs) {
        this.pairs = keyValuePairs;
    }

	public List<GroupByPair<String, Integer>> createReducerInput1(){
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
    //add more -------------
    public List<GroupByPair<String, Integer>> createReducerInput() {
        List<GroupByPair<String, Integer>> result = new ArrayList<>();
        GroupByPair<String, Integer> groupByPair = null;

        for (MyPair<String, Integer> pair : this.pairs) {
            String key = pair.getKey();
            Integer value = pair.getV();

            if (groupByPair == null || !groupByPair.getKey().equals(key)) {
                groupByPair = new GroupByPair<String, Integer>(key);
                result.add(groupByPair);
            }

            groupByPair.addValue(value);
        }

        return result;
    }
    
    public List<MyPair<String, Integer>> reduce(List<GroupByPair<String, Integer>> input) {
        return input.stream()
                .map(groupPair -> {
                    int sum = groupPair.getValues().stream().mapToInt(a -> a).sum();
                    return new MyPair<>(groupPair.getKey(), sum);
                }).collect(Collectors.toList());
    }
    
    public void writeGroupByPairsOut(BufferedWriter bw){
    	try{
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
    	}
    	catch (Exception e){
    		System.out.println("Error in writeOut");
    	}
    }

    public void writeGroupByPairsReducedOut(BufferedWriter bw){
    	List<MyPair<String, Integer>> groupByPairsReduced=reduce(groupByPairs);
    	try{
			for (MyPair<String, Integer> p:groupByPairsReduced){
					bw.write("<"+p.getKey()+", "); 
					System.out.print("<"+p.getKey()+", ");
					int t=p.getV();
					bw.write(t+" >\n");
					System.out.println(t+" >");

			}
    	}
    	catch (Exception e){
    		System.out.println("Error in writeOut");
    	}
    }
}
