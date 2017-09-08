package wordExtraction;

import java.util.ArrayList;
import java.util.List;

public class GroupByPair<U extends Comparable<U>,V extends Comparable<V> > implements Comparable<MyPair<U, V>>{
	private U key;
	private List<V> values=new ArrayList<V>();
	
    public GroupByPair(U key) {
        this.key = key;
        this.values = new ArrayList<>();
    }
    
    public void addValue(V v) {
        this.values.add(v);
    }
    
	public GroupByPair(U key, List<V> values) {
		super();
		this.key = key;
		this.values = values;
	}

	public U getKey() {
		return key;
	}

	public void setKey(U u) {
		this.key = u;
	}




	public List<V> getValues() {
		return values;
	}

	public void setValues(List<V> values) {
		this.values = values;
	}

	@Override
	public int compareTo(MyPair<U, V> that) {
		// TODO Auto-generated method stub
       // int cmp = this.getU().compareTo(that.getU());
		int cmp = this.getKey().toString().compareTo(that.getKey().toString());
		//int cmp = this.getU().toString()-that.getU().toString();
        return cmp;
	}
}
